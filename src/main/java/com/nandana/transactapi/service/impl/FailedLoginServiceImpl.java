package com.nandana.transactapi.service.impl;

import com.nandana.transactapi.service.ICacheService;
import com.nandana.transactapi.service.IFailedLoginService;
import com.nandana.transactapi.strings.ExceptionStrings;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class FailedLoginServiceImpl implements IFailedLoginService {
    private final ICacheService cacheService;
    private static final long LOCKOUT_TTL = 300;

    @Override
    public void incrementFailedAttempts(String email) {
        String cacheKey = ExceptionStrings.FAILED_ATTEMPTS + email;
        Integer attempts = (Integer) cacheService.get(cacheKey);

        if (attempts == null) {
            cacheService.set(cacheKey, 1, LOCKOUT_TTL);
        } else {
            cacheService.set(cacheKey, attempts + 1, LOCKOUT_TTL);
        }
    }

    @Override
    public int getFailedAttempts(String email) {
        String cacheKey = ExceptionStrings.FAILED_ATTEMPTS + email;
        Integer attempts = (Integer) cacheService.get(cacheKey);

        return attempts == null ? 0 : attempts;
    }

    @Override
    public void resetFailedAttempts(String email) {
        String cacheKey = ExceptionStrings.FAILED_ATTEMPTS + email;
        cacheService.delete(cacheKey);
    }

    @Override
    public boolean isAccountLocked(String email) {
        return getFailedAttempts(email) >= 5;
    }
}
