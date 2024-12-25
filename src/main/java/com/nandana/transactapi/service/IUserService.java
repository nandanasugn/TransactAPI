package com.nandana.transactapi.service;

import com.nandana.transactapi.dto.request.RegistrationRequest;
import com.nandana.transactapi.dto.request.UpdateRequest;
import com.nandana.transactapi.dto.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface IUserService extends UserDetailsService {
    Map<String, Long> getUserBalance();

    UserResponse createUser(RegistrationRequest request);

    UserResponse getProfile();

    UserResponse updateProfile(UpdateRequest updateRequest);

    UserResponse updateProfileImage(MultipartFile file);
}
