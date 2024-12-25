package com.nandana.transactapi.model;

import com.nandana.transactapi.audit.Auditable;
import com.nandana.transactapi.dto.response.BannerResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@Table(name = "banners")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Banner extends Auditable {
    @Column(nullable = false)
    private String bannerName;

    @Column(nullable = false)
    private String bannerImage;

    @Column(nullable = false)
    private String description;

    public BannerResponse convertToBannerResponse() {
        return BannerResponse.builder().banner_name(bannerName).banner_image(bannerImage).description(description).build();
    }
}
