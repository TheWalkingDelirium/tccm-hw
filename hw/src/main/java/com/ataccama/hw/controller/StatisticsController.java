package com.ataccama.hw.controller;

import com.ataccama.hw.model.Column;
import com.ataccama.hw.model.ColumnStat;
import com.ataccama.hw.model.Table;
import com.ataccama.hw.model.TableStat;
import com.ataccama.hw.service.DbService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Api(value = "Endpoint for statistics")
@RestController
@RequestMapping("/stat")
public class StatisticsController {
    private static final List<String> SUPPORTED_NUMBER_COLUMN_TYPES = List.of("int8", "float8", "int", "int4", "decimal", "float4", "int2");
    private final DbService dbService;

    @Autowired
    public StatisticsController(final DbService dbService) {
        this.dbService = dbService;
    }

    @ApiOperation("endpoint for statistics about column: min, max, avg and median value for the column [only for limited data types]")
    @GetMapping(path="/columns")
    public ResponseEntity<List<ColumnStat>> getColumnStat(@RequestParam final Long connectionId,
                                                          @RequestParam(required = false) final String catalog,
                                                          @RequestParam(required = false) final String schemaName,
                                                          @RequestParam(required = false) final String tableName,
                                                          @RequestParam(required = false) final String columnName) throws Exception {

        final List<Column> columns = dbService.listColumns(connectionId, catalog, schemaName, tableName, columnName);
        final List<Column> filtered = columns
                .stream()
                .filter(c -> SUPPORTED_NUMBER_COLUMN_TYPES.contains(c.getTypeName()))
                .collect(Collectors.toList());

        final List<ColumnStat> result = dbService.getStatsForColumns(connectionId, filtered);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "endpoint for table statistics. Gives details about number of records and number of attributes")
    @GetMapping(path="/tables")
    public ResponseEntity<List<TableStat>> getTableStat(@RequestParam final Long connectionId) throws Exception {
        final List<Table> tables = dbService.listTables(connectionId);
        final List<TableStat> stats = dbService.getStatsForTable(connectionId, tables);
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }
}
