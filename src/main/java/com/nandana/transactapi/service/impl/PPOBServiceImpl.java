package com.nandana.transactapi.service.impl;

import com.nandana.transactapi.dto.response.PPOBResponse;
import com.nandana.transactapi.exception.CustomException;
import com.nandana.transactapi.model.PPOB;
import com.nandana.transactapi.repository.IPPOBRepository;
import com.nandana.transactapi.service.IPPOBService;
import com.nandana.transactapi.strings.ExceptionStrings;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PPOBServiceImpl implements IPPOBService {
    private final IPPOBRepository ppobRepository;

    @Override
    public List<PPOBResponse> getAllPPOBs() {

        List<PPOBResponse> ppobs = ppobRepository.findPPOBs().stream().map(PPOB::convertToPPOBResponse).toList();

        if (ppobs.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND.value(), ExceptionStrings.SERVICES_NOT_FOUND);
        }

        return ppobs;
    }

    @Override
    public PPOBResponse getPPOBByServiceCode(String serviceCode) {
        PPOB ppob = ppobRepository.findPPOBByServiceCode(serviceCode).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND.value(), ExceptionStrings.SERVICE_NOT_FOUND));
        return ppob.convertToPPOBResponse();
    }
}
