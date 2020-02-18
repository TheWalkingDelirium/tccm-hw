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

    private Column toColumn(final ResultSet r) throws SQLException {
        return new Column(
                r.getString(1), // column index
                r.getString(2),
                r.getString(3),
                r.getString(4),
                r.getString(5),
                r.getString(6),
                r.getString(7),
                r.getString(8),
                r.getString(9),
                r.getString(10),
                r.getString(11),
                r.getString(12)
        );
    }

    private String buildStatement(final String schema, final String table) {
        return "SELECT * FROM " + Optional.ofNullable(schema).map(i -> i + ".").orElse("") + table + " LIMIT 10;";
    }

    private Table toTable(final ResultSet r) throws SQLException {
        return new Table(
                r.getString(1), // column index
                r.getString(2),
                r.getString(3),
                r.getString(4),
                r.getString(5),
                r.getString(6),
                r.getString(7),
                r.getString(8),
                r.getString(9),
                r.getString(10)
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
            e.printStackTrace();
        }

        return new ColumnStat(column.getTableName(), column.getColumnName(), min, max, avg, median);
    }

    private TableStat getTableStat(final java.sql.Connection connection, final Table table) {
        int numberOfRecords = 0;
        int numberOfAttributes = 0;

        try {
            Statement statement = connection.createStatement();
            ResultSet attrCountRS = statement.executeQuery("SELECT COUNT(column_name) FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME='" + table.getName() + "';");
            while (attrCountRS.next()) {
                numberOfAttributes = attrCountRS.getInt(1);
            }

            ResultSet numberOfRecordsRS = statement.executeQuery("SELECT COUNT(*) FROM " + table.getName() + ";");
            while (numberOfRecordsRS.next()) {
                numberOfRecords = numberOfRecordsRS.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new TableStat(numberOfRecords, numberOfAttributes);
    }
}
