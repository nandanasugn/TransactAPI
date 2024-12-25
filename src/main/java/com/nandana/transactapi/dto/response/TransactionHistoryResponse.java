package com.nandana.transactapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionHistoryResponse {
    private String invoice_number;
    private String transaction_type;
    private String description;
    private Long total_amount;
    private LocalDateTime created_on;
}
