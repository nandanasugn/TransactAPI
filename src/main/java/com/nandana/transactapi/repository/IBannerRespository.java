package com.nandana.transactapi.repository;

import com.nandana.transactapi.model.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IBannerRespository extends JpaRepository<Banner, UUID> {
    @Query(value = "SELECT * FROM banners", nativeQuery = true)
    List<Banner> findAllBanners();
}
