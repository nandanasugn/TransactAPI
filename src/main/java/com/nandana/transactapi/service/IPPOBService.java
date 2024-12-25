package com.nandana.transactapi.service;

import com.nandana.transactapi.dto.response.PPOBResponse;

import java.util.List;

public interface IPPOBService {
    List<PPOBResponse> getAllPPOBs();

    PPOBResponse getPPOBByServiceCode(String serviceCode);
}
