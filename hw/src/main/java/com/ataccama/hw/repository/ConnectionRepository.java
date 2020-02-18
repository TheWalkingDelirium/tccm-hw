package com.ataccama.hw.repository;

import com.ataccama.hw.model.Connection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {
    @Override
    List<Connection> findAll();
}
