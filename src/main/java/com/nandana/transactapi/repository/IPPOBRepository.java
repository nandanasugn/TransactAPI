package com.nandana.transactapi.repository;

import com.nandana.transactapi.model.PPOB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IPPOBRepository extends JpaRepository<PPOB, UUID> {
    @Query(value = "SELECT * FROM ppobs", nativeQuery = true)
    List<PPOB> findPPOBs();

    @Query(value = "SELECT * FROM ppobs WHERE service_code = :serviceCode", nativeQuery = true)
    Optional<PPOB> findPPOBByServiceCode(String serviceCode);
}
