package com.ataccama.hw.service;

import com.ataccama.hw.datasource.ConnectionDataSourceProvider;
import com.ataccama.hw.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
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
        final java.sql.Connection connection = getDbConnection(connectionId);
        return columns.stream().map(column -> getColumnStat(connection, column)).collect(Collectors.toList());
    }

    public List<TableStat> getStatsForTable(final Long connectionId, final List<Table> tables) throws Exception {
        final java.sql.Connection connection = getDbConnection(connectionId);
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
        final ResultSet resultSet = getDbConnection(connectionId).createStatement().executeQuery(buildStatement(schema, tableName));

        final ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        final int columns = resultSetMetaData.getColumnCount();
        final ArrayList<Map<String, Object>> list = new ArrayList<>(10);

        while (resultSet.next()) {
            final HashMap<String, Object> row = new HashMap<>(columns);
            for (int i = 1; i <= columns; ++i) {
                row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
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

    private Column toColumn(final ResultSet columnResultSet) throws SQLException {
        return new Column(
                columnResultSet.getString(1), // column index
                columnResultSet.getString(2),
                columnResultSet.getString(3),
                columnResultSet.getString(4),
                columnResultSet.getString(5),
                columnResultSet.getString(6),
                columnResultSet.getString(7),
                columnResultSet.getString(8),
                columnResultSet.getString(9),
                columnResultSet.getString(10),
                columnResultSet.getString(11),
                columnResultSet.getString(12)
        );
    }

    private String buildStatement(final String schema, final String table) {
        return "SELECT * FROM " + Optional.ofNullable(schema).map(i -> i + ".").orElse("") + table + " LIMIT 10;";
    }

    private Table toTable(final ResultSet tableResultSet) throws SQLException {
        return new Table(
                tableResultSet.getString(1), // column index
                tableResultSet.getString(2),
                tableResultSet.getString(3),
                tableResultSet.getString(4),
                tableResultSet.getString(5),
                tableResultSet.getString(6),
                tableResultSet.getString(7),
                tableResultSet.getString(8),
                tableResultSet.getString(9),
                tableResultSet.getString(10)
        );
    }

    private ColumnStat getColumnStat(final java.sql.Connection connection, final Column column) {
        String min = "";
        String max = "";
        String avg = "";
        String median = "";

        try {
            final Statement statement = connection.createStatement();

            final ResultSet minRs = statement.executeQuery("SELECT MIN(" + column.getColumnName() + ") FROM " + column.getTableName() + ";");
            while (minRs.next()) {
                min = minRs.getString(1);
            }

            final ResultSet maxRs = statement.executeQuery("SELECT MAX(" + column.getColumnName() + ") FROM " + column.getTableName() + ";");
            while (maxRs.next()) {
                max = maxRs.getString(1);
            }

            final ResultSet avgRs = statement.executeQuery("SELECT AVG(" + column.getColumnName() + ") FROM " + column.getTableName() + ";");
            while (avgRs.next()) {
                avg = avgRs.getString(1);
            }

            // get median value
            final ResultSet medianRs = statement.executeQuery("SELECT percentile_disc(0.5) within group (order by " + column.getColumnName() + ") FROM " + column.getTableName() + ";");
            while (medianRs.next()) {
                median = medianRs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ColumnStat(column.getTableName(), column.getColumnName(), min, max, avg, median);
    }

    private TableStat getTableStat(final java.sql.Connection connection, final Table table) {
        int numberOfRecords = 0;
        int numberOfAttributes = 0;

        try {
            final Statement statement = connection.createStatement();
            final ResultSet attrCountRS = statement.executeQuery("SELECT COUNT(column_name) FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME='" + table.getName() + "';");
            while (attrCountRS.next()) {
                numberOfAttributes = attrCountRS.getInt(1);
            }

            final ResultSet numberOfRecordsRS = statement.executeQuery("SELECT COUNT(*) FROM " + table.getName() + ";");
            while (numberOfRecordsRS.next()) {
                numberOfRecords = numberOfRecordsRS.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new TableStat(numberOfRecords, numberOfAttributes);
    }
}
