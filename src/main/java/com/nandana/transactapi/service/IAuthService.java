package com.nandana.transactapi.service;

import com.nandana.transactapi.dto.request.LoginRequest;
import com.nandana.transactapi.dto.request.RegistrationRequest;
import com.nandana.transactapi.dto.response.UserResponse;

import java.util.Map;

public interface IAuthService {
    UserResponse register(RegistrationRequest request);

    Map<String, String> login(LoginRequest request);
}
