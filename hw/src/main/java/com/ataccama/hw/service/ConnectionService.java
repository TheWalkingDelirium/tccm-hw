package com.ataccama.hw.service;

import com.ataccama.hw.model.Connection;
import com.ataccama.hw.repository.ConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
public class ConnectionService {
    private final ConnectionRepository connectionRepository;

    @Autowired
    public ConnectionService(final ConnectionRepository connectionRepository) {
        this.connectionRepository = connectionRepository;
    }

    public Connection findConnection(Long id) {
        return connectionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Connection saveConnection(Connection connection) {
        return connectionRepository.save(connection);
    }

    public List<Connection> listConnection() {
        return connectionRepository.findAll();
    }

    public Connection replaceConnection(Connection connection) {
        return connectionRepository
                .findById(connection.getId())
                .map(i -> connectionRepository.save(connection))
                .orElseThrow(EntityNotFoundException::new);
    }

    public void deleteConnection(Connection connection) {
        connectionRepository.delete(connection);
    }
}
