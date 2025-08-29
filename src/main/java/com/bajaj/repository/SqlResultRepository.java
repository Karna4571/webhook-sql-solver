package com.bajaj.repository;

import com.bajaj.entity.SqlResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SqlResultRepository extends JpaRepository<SqlResult, Long> {
} 