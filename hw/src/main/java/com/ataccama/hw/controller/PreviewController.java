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
import java.util.Map;

@Api(value = "Endpoint for data preview")
@RestController
@RequestMapping("/preview")
public class PreviewController {
    private final DbService dbService;

    @Autowired
    public PreviewController(final DbService dbService) {
        this.dbService = dbService;
    }

    @ApiOperation(value = "Serves for getting preview of the table for the given connection.")
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getDataPreview(@RequestParam final Long connectionId,
                                                                    @RequestParam(required = false) final String schema,
                                                                    @RequestParam final String tableName) throws Exception {

        return new ResponseEntity<>(
                dbService.getPreview(connectionId, schema, tableName),
                HttpStatus.OK);
    }


}
