package com.nandana.transactapi.model;

import com.nandana.transactapi.audit.Auditable;
import com.nandana.transactapi.dto.response.TransactionHistoryResponse;
import com.nandana.transactapi.model.enums.ETransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Transaction extends Auditable {
    @Column(nullable = false, unique = true)
    private String invoiceNumber;

    @Enumerated(EnumType.STRING)
    private ETransactionType transactionType;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Long totalAmount;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    private User user;

    public TransactionHistoryResponse convertToTransactionHistoryResponse() {
        return TransactionHistoryResponse.builder().invoice_number(invoiceNumber).transaction_type(transactionType.name()).description(description).total_amount(totalAmount).created_on(getCreatedDate()).build();
    }
}
