package com.nandana.transactapi.service.impl;

import com.nandana.transactapi.dto.request.RegistrationRequest;
import com.nandana.transactapi.dto.request.UpdateRequest;
import com.nandana.transactapi.dto.response.UserResponse;
import com.nandana.transactapi.exception.CustomException;
import com.nandana.transactapi.model.Balance;
import com.nandana.transactapi.model.User;
import com.nandana.transactapi.model.UserDetail;
import com.nandana.transactapi.model.enums.ERole;
import com.nandana.transactapi.repository.IUserRepository;
import com.nandana.transactapi.service.IBalanceService;
import com.nandana.transactapi.service.IUserService;
import com.nandana.transactapi.strings.ExceptionStrings;
import com.nandana.transactapi.util.DtoMapper;
import com.nandana.transactapi.util.UpdateUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final IBalanceService balanceService;
    private final FileStorageServiceImpl fileStorageService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Map<String, Long> getUserBalance() {
        Balance balance = balanceService.getBalance();

        Map<String, Long> balanceMap = new HashMap<>();
        balanceMap.put("balance", balance.getBalance());
        return balanceMap;
    }

    @Override
    public UserResponse createUser(RegistrationRequest request) {
        User user = User.builder().email(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).role(ERole.USER).build();
        UserDetail userDetail = UserDetail.builder().firstName(request.getFirst_name()).lastName(request.getLast_name()).build();
        Balance balance = Balance.builder().balance(0L).build();

        user.setUserDetail(userDetail);
        user.setBalance(balance);

        user = userRepository.save(user);

        return DtoMapper.mapToUserResponse(user.getEmail(), user.getUserDetail().getFirstName(), user.getUserDetail().getLastName(), null);
    }

    @Override
    public UserResponse getProfile() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return DtoMapper.mapToUserResponse(user.getEmail(), user.getUserDetail().getFirstName(), user.getUserDetail().getLastName(), user.getUserDetail().getProfileImage());
    }

    @Override
    public UserResponse updateProfile(UpdateRequest updateRequest) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UpdateUtil.updateIfNotNull(updateRequest.getFirst_name(), user.getUserDetail()::setFirstName);
        UpdateUtil.updateIfNotNull(updateRequest.getLast_name(), user.getUserDetail()::setLastName);

        userRepository.save(user);

        return DtoMapper.mapToUserResponse(user.getEmail(), user.getUserDetail().getFirstName(), user.getUserDetail().getLastName(), user.getUserDetail().getProfileImage());
    }

    @Override
    public UserResponse updateProfileImage(MultipartFile file) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (file == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), ExceptionStrings.FILE_MUST_EXIST);
        }

        if (user.getUserDetail().getProfileImage() != null) {
            fileStorageService.deleteFile(user.getUserDetail().getProfileImage());
        }
        user.getUserDetail().setProfileImage(fileStorageService.storeFile(file));

        userRepository.save(user);

        return DtoMapper.mapToUserResponse(user.getEmail(), user.getUserDetail().getFirstName(), user.getUserDetail().getLastName(), user.getUserDetail().getProfileImage());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
    }
}
