package com.nandana.transactapi.repository;

import com.nandana.transactapi.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ITransactionRepository extends JpaRepository<Transaction, UUID> {
    @Query(value = "SELECT * FROM transactions WHERE user_id = :user_id", nativeQuery = true)
    Page<Transaction> findAllTransactionByUserId(@Param("user_id") UUID userId, Pageable pageable);
}
