package com.nandana.transactapi.service.impl;

import com.nandana.transactapi.exception.CustomException;
import com.nandana.transactapi.model.Balance;
import com.nandana.transactapi.model.User;
import com.nandana.transactapi.repository.IBalanceRepository;
import com.nandana.transactapi.service.IBalanceService;
import com.nandana.transactapi.strings.ExceptionStrings;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BalanceServiceImpl implements IBalanceService {
    private final IBalanceRepository balanceRepository;

    @Override
    public Balance getBalance() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return balanceRepository.findBalanceByUserId(user.getId()).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND.value(), ExceptionStrings.BALANCE_NOT_FOUND));
    }
}
