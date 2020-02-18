package com.ataccama.hw.controller;

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

@Api(value = "Endpoing for listing schemas")
@RestController
@RequestMapping("/schema")
public class SchemasController {
    private final DbService dbService;

    @Autowired
    public SchemasController(final DbService dbService) {
        this.dbService = dbService;
    }

    @ApiOperation(value = "lists schemas for the given connection")
    @GetMapping
    public ResponseEntity<List<String>> listSchemas(@RequestParam final Long id) throws Exception {
        return new ResponseEntity<>(dbService.listSchemas(id), HttpStatus.OK);
    }
}
