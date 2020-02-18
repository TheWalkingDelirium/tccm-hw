package com.ataccama.hw.controller;

import com.ataccama.hw.model.Column;
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

@Api(value="Column Listing endpoint")
@RestController
@RequestMapping("/column")
public class ColumnController {
    private final DbService dbService;

    @Autowired
    public ColumnController(final DbService dbService) {
        this.dbService = dbService;
    }

    @ApiOperation(value = "lists information about columns. Table can be specified additionally.")
    @GetMapping
    public ResponseEntity<List<Column>> listColumns(@RequestParam final Long connectionId,
                                                    @RequestParam(required = false) final String catalog,
                                                    @RequestParam(required = false) final String schemaPattern,
                                                    @RequestParam(required = false) final String tableNamePattern,
                                                    @RequestParam(required = false) final String columnNamePattern) throws Exception {

        return new ResponseEntity<>(
                dbService.listColumns(connectionId, catalog, schemaPattern, tableNamePattern, columnNamePattern),
                HttpStatus.OK);
    }


}
