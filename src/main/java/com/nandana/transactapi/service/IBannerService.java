package com.nandana.transactapi.service;

import com.nandana.transactapi.dto.response.BannerResponse;

import java.util.List;

public interface IBannerService {
    List<BannerResponse> getBanners();
}
