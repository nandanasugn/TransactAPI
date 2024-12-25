package com.nandana.transactapi.util;

import com.nandana.transactapi.dto.response.CommonResponse;
import com.nandana.transactapi.dto.response.PagingResponse;
import com.nandana.transactapi.dto.response.TransactionResponse;
import com.nandana.transactapi.dto.response.UserResponse;

import java.time.LocalDateTime;

public class DtoMapper {
    public static CommonResponse<?> mapToCommonResponse(int status, String message, Object data) {
        return CommonResponse.builder().status(status).message(message).data(data).build();
    }

    public static PagingResponse<?> mapToPagingResponse(int offset, int limit, Object records) {
        return PagingResponse.builder().offset(offset).limit(limit).records(records).build();
    }

    public static TransactionResponse mapToTransactionResponse(String invoiceNumber, String serviceCode, String serviceName, String transactionType, Long totalAmount, LocalDateTime createdOn) {
        return TransactionResponse.builder().invoice_number(invoiceNumber).service_code(serviceCode).service_name(serviceName).transaction_type(transactionType).total_amount(totalAmount).created_on(createdOn).build();
    }

    public static UserResponse mapToUserResponse(String email, String firstName, String lastName, String profileImage) {
        return UserResponse.builder().email(email).first_name(firstName).last_name(lastName).profile_image(profileImage).build();
    }
}
