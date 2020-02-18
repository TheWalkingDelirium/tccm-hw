package com.ataccama.hw.controller;

import com.ataccama.hw.model.Table;
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

@Api(value = "Endpoing for listing tables")
@RestController
@RequestMapping("/table")
public class TableController {
    private final DbService dbService;

    @Autowired
    public TableController(final DbService dbService) {
        this.dbService = dbService;
    }


    @ApiOperation(value = "List tables for the given connection id.")
    @GetMapping
    public ResponseEntity<List<Table>> listTables(@RequestParam(required = true) final Long connectionId) throws Exception {
        return new ResponseEntity<>(dbService.listTables(connectionId), HttpStatus.OK);
    }




}
