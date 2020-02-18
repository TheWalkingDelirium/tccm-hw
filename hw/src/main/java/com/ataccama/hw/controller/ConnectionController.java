package com.ataccama.hw.controller;

import com.ataccama.hw.model.Connection;
import com.ataccama.hw.service.ConnectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value="Saving, updating, listing and deleting connection details.")
@RestController
@RequestMapping("/connection")
public class ConnectionController {

    private final ConnectionService connectionService;

    @Autowired
    public ConnectionController(final ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    @ApiOperation(value = "View a list of stored connections")
    @GetMapping
    public ResponseEntity<List<Connection>> listConnection() {
        var savedConnections = connectionService.listConnection();
        return new ResponseEntity<>(savedConnections, HttpStatus.OK);
    }

    @ApiOperation(value = "Store new connection")
    @PostMapping
    public ResponseEntity<Connection> saveConnection(@RequestBody final Connection connection) {
        final Connection savedConnection = connectionService.saveConnection(connection);
        return new ResponseEntity<>(savedConnection, HttpStatus.OK);
    }

    @ApiOperation(value = "Update given connection")
    @PutMapping
    public ResponseEntity<Connection> updateConnection(@RequestBody final Connection connection) {
        final Connection updatedConnection = connectionService.replaceConnection(connection);
        return new ResponseEntity<>(updatedConnection, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete given connection")
    @DeleteMapping
    public ResponseEntity<Connection> deleteConnection(@RequestBody final Connection connection) {
        connectionService.deleteConnection(connection);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
