package com.nandana.transactapi.service;

import com.nandana.transactapi.dto.request.TopUpRequest;
import com.nandana.transactapi.dto.request.TransactionRequest;
import com.nandana.transactapi.dto.response.PagingResponse;
import com.nandana.transactapi.dto.response.TransactionResponse;

import java.util.Map;

public interface ITransactionService {
    PagingResponse<?> getAllTransactions(int offset, int limit);

    Map<String, Long> topUpBalance(TopUpRequest request);

    TransactionResponse createTransaction(TransactionRequest request);
}
