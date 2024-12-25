package com.nandana.transactapi.repository;

import com.nandana.transactapi.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IBalanceRepository extends JpaRepository<Balance, UUID> {
    @Query(value = "SELECT * FROM balances WHERE user_id = :user_id", nativeQuery = true)
    Optional<Balance> findBalanceByUserId(UUID userId);
}
