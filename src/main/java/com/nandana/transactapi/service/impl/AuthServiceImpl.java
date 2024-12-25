package com.nandana.transactapi.service.impl;

import com.nandana.transactapi.dto.request.LoginRequest;
import com.nandana.transactapi.dto.request.RegistrationRequest;
import com.nandana.transactapi.dto.response.UserResponse;
import com.nandana.transactapi.exception.CustomException;
import com.nandana.transactapi.model.User;
import com.nandana.transactapi.service.IAuthService;
import com.nandana.transactapi.service.IFailedLoginService;
import com.nandana.transactapi.service.IUserService;
import com.nandana.transactapi.strings.ExceptionStrings;
import com.nandana.transactapi.util.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {
    private final IUserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final IFailedLoginService failedLoginService;

    @Override
    public UserResponse register(RegistrationRequest request) {
        return userService.createUser(request);
    }

    @Override
    public Map<String, String> login(LoginRequest request) {
        if (failedLoginService.isAccountLocked(request.getEmail())) {
            throw new CustomException(HttpStatus.UNAUTHORIZED.value(), ExceptionStrings.LOGIN_LOCKED);
        }

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = (User) authentication.getPrincipal();

            String generateToken = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
            Map<String, String> response = new HashMap<>();
            response.put("token", generateToken);

            failedLoginService.resetFailedAttempts(request.getEmail());

            return response;
        } catch (Exception e) {
            failedLoginService.incrementFailedAttempts(request.getEmail());
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), ExceptionStrings.WRONG_CREDENTIALS);
        }
    }
}
