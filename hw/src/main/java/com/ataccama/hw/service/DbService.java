package com.ataccama.hw.service;

import com.ataccama.hw.ThrowingConsumer;
import com.ataccama.hw.datasource.ConnectionDataSourceProvider;
import com.ataccama.hw.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class DbService {
    private final ConnectionDataSourceProvider dataSourceProvider;
    private final ConnectionService connectionService;

    @Autowired
    public DbService(final ConnectionDataSourceProvider dataSourceProvider, final ConnectionService connectionService) {
        this.dataSourceProvider = dataSourceProvider;
        this.connectionService = connectionService;
    }

    public List<ColumnStat> getStatsForColumns(final Long connectionId, final List<Column> columns) throws Exception {
        java.sql.Connection connection = getDbConnection(connectionId);
        return columns.stream().map(column -> getColumnStat(connection, column)).collect(Collectors.toList());
    }

    public List<TableStat> getStatsForTable(final Long connectionId, final List<Table> tables) throws Exception {
        java.sql.Connection connection = getDbConnection(connectionId);
        return tables.stream().map(table -> getTableStat(connection, table)).collect(Collectors.toList());
    }

    public List<String> listSchemas(final Long id) throws Exception {
        final ResultSet result = getDbConnection(id).getMetaData().getSchemas();

        final int columnCount = result.getMetaData().getColumnCount();
        final List<String> metaData = new ArrayList<>();
        while (result.next()) {
            StringBuilder s = new StringBuilder();
            for (int i = 1; i < columnCount; i++) {
                s.append(result.getString(i));
            }
            metaData.add(s.toString());
        }

        return metaData;
    }

    public List<Table> listTables(final Long id) throws Exception {
        final List<Table> metaData = new ArrayList<>();
        final ResultSet result = getDbConnection(id)
                .getMetaData()
                .getTables(null, null, null, new String[]{"TABLE"});
        while (result.next()) {
            metaData.add(toTable(result));
        }
        return metaData;
    }

    public List<Map<String, Object>> getPreview(final Long connectionId,
                                                final String schema,
                                                final String tableName) throws Exception {
        final ResultSet rs = getDbConnection(connectionId).createStatement().executeQuery(buildStatement(schema, tableName));

        final ResultSetMetaData md = rs.getMetaData();
        final int columns = md.getColumnCount();
        final ArrayList<Map<String, Object>> list = new ArrayList<>(10);

        while (rs.next()) {
            HashMap<String, Object> row = new HashMap<>(columns);
            for (int i = 1; i <= columns; ++i) {
                row.put(md.getColumnName(i), rs.getObject(i));
            }
            list.add(row);
        }

        return list;
    }

    public List<Column> listColumns(final Long connectionId,
                                    final String catalog,
                                    final String schemaPattern,
                                    final String tableNamePattern,
                                    final String columnNamePattern) throws Exception {

        final ResultSet result = getDbConnection(connectionId)
                .getMetaData()
                .getColumns(catalog, schemaPattern, tableNamePattern, columnNamePattern);

        final List<Column> metaData = new ArrayList<>();
        while (result.next()) {
            metaData.add(toColumn(result));
        }

        return metaData;
    }

    private java.sql.Connection getDbConnection(final Long connectionId) throws Exception {
        final Connection connection = connectionService.findConnection(connectionId);
        return Optional.ofNullable(
                dataSourceProvider.getJdbcTemplate(connection).getDataSource())
                .map(dataSource -> {
                    try {
                        return dataSource.getConnection();
                    } catch (SQLException e) {
                        return null;
                    }
                }).orElseThrow(() -> new Exception("failed to get connection"));
    }

    // result is parsed according to the documentation https://docs.oracle.com/en/java/javase/13/docs/api/java.sql/java/sql/DatabaseMetaData.html#getColumns
    //TABLE_CAT String => table catalog (may be null)
    //TABLE_SCHEM String => table schema (may be null)
    //TABLE_NAME String => table name
    //COLUMN_NAME String => column name
    //DATA_TYPE int => SQL type from java.sql.Types
    //TYPE_NAME String => Data source dependent type name, for a UDT the type name is fully qualified
    //COLUMN_SIZE int => column size.
    //BUFFER_LENGTH is not used.
    //DECIMAL_DIGITS int => the number of fractional digits. Null is returned for data types where DECIMAL_DIGITS is not applicable.
    //NUM_PREC_RADIX int => Radix (typically either 10 or 2)
    //NULLABLE int => is NULL allowed.
    //REMARKS String => comment describing column (may be null)
    //COLUMN_DEF String => default value for the column, which should be interpreted as a string when the value is enclosed in single quotes (may be null)
    //SQL_DATA_TYPE int => unused
    //SQL_DATETIME_SUB int => unused
    //CHAR_OCTET_LENGTH int => for char types the maximum number of bytes in the column
    //ORDINAL_POSITION int => index of column in table (starting at 1)
    //IS_NULLABLE String => ISO rules are used to determine the nullability for a column.
    //SCOPE_CATALOG String => catalog of table that is the scope of a reference attribute (null if DATA_TYPE isn't REF)
    //SCOPE_SCHEMA String => schema of table that is the scope of a reference attribute (null if the DATA_TYPE isn't REF)
    //SCOPE_TABLE String => table name that this the scope of a reference attribute (null if the DATA_TYPE isn't REF)
    //SOURCE_DATA_TYPE short => source type of a distinct type or user-generated Ref type, SQL type from java.sql.Types (null if DATA_TYPE isn't DISTINCT or user-generated REF)
    //IS_AUTOINCREMENT String => Indicates whether this column is auto incremented
    //IS_GENERATEDCOLUMN String => Indicates whether this is a generated column
    private Column toColumn(final ResultSet r) throws SQLException {
        return new Column(
                r.getString("TABLE_CAT"),
                r.getString("TABLE_SCHEM"),
                r.getString("TABLE_NAME"),
                r.getString("COLUMN_NAME"),
                r.getString("DATA_TYPE"),
                r.getString("TYPE_NAME"),
                r.getString("COLUMN_SIZE"),
                r.getString("BUFFER_LENGTH"),
                r.getString("DECIMAL_DIGITS"),
                r.getString("NUM_PREC_RADIX"),
                r.getString("NULLABLE"),
                r.getString("REMARKS"),
                r.getString("COLUMN_DEF"),
                r.getString("SQL_DATA_TYPE"),
                r.getString("SQL_DATETIME_SUB"),
                r.getString("CHAR_OCTET_LENGTH"),
                r.getString("ORDINAL_POSITION"),
                r.getString("IS_NULLABLE"),
                r.getString("SCOPE_CATALOG"),
                r.getString("SCOPE_SCHEMA"),
                r.getString("SCOPE_TABLE"),
                r.getString("SOURCE_DATA_TYPE"),
                r.getString("IS_AUTOINCREMENT"),
                r.getString("IS_GENERATEDCOLUMN")
        );
    }

    private String buildStatement(final String schema, final String table) {
        return "SELECT * FROM " + Optional.ofNullable(schema).map(i -> i + ".").orElse("") + table + " LIMIT 10;";
    }


    // result is parsed based on the documentation https://docs.oracle.com/en/java/javase/13/docs/api/java.sql/java/sql/DatabaseMetaData.html#getTables
    // TABLE_CAT => table catalog (may be null)
    // TABLE_SCHEM => table schema (may be null)
    // TABLE_NAME => table name
    // TABLE_TYPE => table type. Typical types are "TABLE", "VIEW", "SYSTEM TABLE", "GLOBAL TEMPORARY", "LOCAL TEMPORARY", "ALIAS", "SYNONYM".
    // REMARKS => explanatory comment on the table
    // TYPE_CAT => the types catalog (may be null)
    // TYPE_SCHEM => the types schema (may be null)
    // TYPE_NAME => type name (may be null)
    // SELF_REFERENCING_COL_NAME => name of the designated "identifier" column of a typed table (may be null)
    // REF_GENERATION > specifies how values in SELF_REFERENCING_COL_NAME are created. Values are "SYSTEM", "USER", "DERIVED". (may be null)
    private Table toTable(final ResultSet r) throws SQLException {
        return new Table(
                r.getString("TABLE_CAT"),
                r.getString("TABLE_SCHEM"),
                r.getString("TABLE_NAME"),
                r.getString("TABLE_TYPE"),
                r.getString("REMARKS"),
                r.getString("TYPE_CAT"),
                r.getString("TYPE_SCHEM"),
                r.getString("TYPE_NAME"),
                r.getString("SELF_REFERENCING_COL_NAME"),
                r.getString("REF_GENERATION") //
        );
    }

    private ColumnStat getColumnStat(final java.sql.Connection connection, final Column column) {
        String min = "";
        String max = "";
        String avg = "";
        String median = "";

        try {
            Statement statement = connection.createStatement();

            ResultSet minRs = statement.executeQuery("SELECT MIN(" + column.getColumnName() + ") FROM " + column.getTableName() + ";");
            while (minRs.next()) {
                min = minRs.getString(1);
            }

            ResultSet maxRs = statement.executeQuery("SELECT MAX(" + column.getColumnName() + ") FROM " + column.getTableName() + ";");
            while (maxRs.next()) {
                max = maxRs.getString(1);
            }

            ResultSet avgRs = statement.executeQuery("SELECT AVG(" + column.getColumnName() + ") FROM " + column.getTableName() + ";");
            while (avgRs.next()) {
                avg = avgRs.getString(1);
            }

            // get median value
            ResultSet medianRs = statement.executeQuery("SELECT percentile_disc(0.5) within group (order by " + column.getColumnName() + ") FROM " + column.getTableName() + ";");
            while (medianRs.next()) {
                median = medianRs.getString(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return new ColumnStat(column.getTableName(), column.getColumnName(), min, max, avg, median);
    }

    private TableStat getTableStat(final java.sql.Connection connection, final Table table) {
        int numberOfRecords = 0;
        int numberOfAttributes = 0;

        try {
            Statement statement = null;
            statement = connection.createStatement();
            ResultSet attrCountRS = statement.executeQuery("SELECT COUNT(column_name) FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME='" + table.getName() + "';");
            while (attrCountRS.next()) {
                numberOfAttributes = attrCountRS.getInt(1);
            }

            ResultSet numberOfRecordsRS = statement.executeQuery("SELECT COUNT(*) FROM " + table.getName() + ";");
            while (numberOfRecordsRS.next()) {
                numberOfRecords = numberOfRecordsRS.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return new TableStat(numberOfRecords, numberOfAttributes);
    }
}
