package com.nandana.transactapi.service.impl;

import com.nandana.transactapi.dto.request.TopUpRequest;
import com.nandana.transactapi.dto.request.TransactionRequest;
import com.nandana.transactapi.dto.request.UpdateRequest;
import com.nandana.transactapi.dto.response.PPOBResponse;
import com.nandana.transactapi.dto.response.PagingResponse;
import com.nandana.transactapi.dto.response.TransactionResponse;
import com.nandana.transactapi.exception.CustomException;
import com.nandana.transactapi.model.Transaction;
import com.nandana.transactapi.model.User;
import com.nandana.transactapi.model.enums.ETransactionType;
import com.nandana.transactapi.repository.ITransactionRepository;
import com.nandana.transactapi.service.IPPOBService;
import com.nandana.transactapi.service.ITransactionService;
import com.nandana.transactapi.service.IUserService;
import com.nandana.transactapi.strings.ExceptionStrings;
import com.nandana.transactapi.util.DtoMapper;
import com.nandana.transactapi.util.InvoiceGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionServiceImpl implements ITransactionService {
    private final ITransactionRepository transactionRepository;
    private final IPPOBService ppobService;
    private final IUserService userService;

    @Override
    public PagingResponse<?> getAllTransactions(int offset, int limit) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Pageable pageable = PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, "created_date"));

        Page<Transaction> transactions = transactionRepository.findAllTransactionByUserId(user.getId(), pageable);

        return DtoMapper.mapToPagingResponse(offset, limit, transactions.stream().map(Transaction::convertToTransactionHistoryResponse).collect(Collectors.toList()));
    }

    @Override
    public Map<String, Long> topUpBalance(TopUpRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long finalUserBalance = user.getBalance().getBalance() + request.getTop_up_amount();
        user.getBalance().setBalance(finalUserBalance);

        String invoiceNumber = InvoiceGenerator.generateInvoice();

        Transaction transaction = Transaction.builder().invoiceNumber(invoiceNumber).transactionType(ETransactionType.TOPUP).description("Top Up balance").totalAmount(request.getTop_up_amount()).user(user).build();

        UpdateRequest updateRequest = new UpdateRequest();
        userService.updateProfile(updateRequest);
        transactionRepository.save(transaction);

        Map<String, Long> balanceMap = new HashMap<>();
        balanceMap.put("balance", finalUserBalance);

        return balanceMap;
    }

    @Override
    public TransactionResponse createTransaction(TransactionRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userBalance = user.getBalance().getBalance();

        PPOBResponse ppobResponse = ppobService.getPPOBByServiceCode(request.getService_code());
        Long serviceTariff = ppobResponse.getService_tariff();

        if (userBalance <= serviceTariff) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), ExceptionStrings.INSUFFICIENT_BALANCE);
        }

        Long finalUserBalance = userBalance - serviceTariff;
        user.getBalance().setBalance(finalUserBalance);

        String invoiceNumber = InvoiceGenerator.generateInvoice();
        String transactionType = ETransactionType.PAYMENT.name();

        Transaction transaction = Transaction.builder().invoiceNumber(invoiceNumber).transactionType(ETransactionType.PAYMENT).description(ppobResponse.getService_name()).totalAmount(ppobResponse.getService_tariff()).user(user).build();

        transaction = transactionRepository.save(transaction);

        return DtoMapper.mapToTransactionResponse(invoiceNumber, ppobResponse.getService_code(), ppobResponse.getService_name(), transactionType, serviceTariff, transaction.getCreatedDate());
    }
}
