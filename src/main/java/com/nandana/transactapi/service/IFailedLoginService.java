package com.nandana.transactapi.service;

public interface IFailedLoginService {
    void incrementFailedAttempts(String email);

    int getFailedAttempts(String email);

    void resetFailedAttempts(String email);

    boolean isAccountLocked(String email);
}
