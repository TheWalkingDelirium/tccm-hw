package com.ataccama.hw.service;

import com.ataccama.hw.model.Connection;
import com.ataccama.hw.repository.ConnectionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionServiceTest {
    final ConnectionRepository localMockRepository = Mockito.mock(ConnectionRepository.class);
    final ConnectionService connectionService = new ConnectionService(localMockRepository);

    @Test
    void listConnection() {
        Connection mockConnection = new Connection();
        mockConnection.setName("test");
        Mockito.when(localMockRepository.findAll()).thenReturn(List.of(mockConnection));
        assertEquals(connectionService.listConnection(), List.of(mockConnection));
    }
}