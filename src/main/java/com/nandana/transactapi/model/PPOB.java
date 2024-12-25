package com.nandana.transactapi.model;

import com.nandana.transactapi.audit.Auditable;
import com.nandana.transactapi.dto.response.PPOBResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "ppobs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PPOB extends Auditable {
    @Column(nullable = false, unique = true)
    private String serviceCode;

    @Column(nullable = false)
    private String serviceName;

    @Column(nullable = false)
    private String serviceIcon;

    @Column(nullable = false)
    private Long serviceTariff;

    public PPOBResponse convertToPPOBResponse() {
        return PPOBResponse.builder().service_code(serviceCode).service_name(serviceName).service_icon(serviceIcon).service_tariff(serviceTariff).build();
    }
}
