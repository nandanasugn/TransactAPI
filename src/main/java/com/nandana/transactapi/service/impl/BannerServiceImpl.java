package com.nandana.transactapi.service.impl;

import com.nandana.transactapi.dto.response.BannerResponse;
import com.nandana.transactapi.exception.CustomException;
import com.nandana.transactapi.model.Banner;
import com.nandana.transactapi.repository.IBannerRespository;
import com.nandana.transactapi.service.IBannerService;
import com.nandana.transactapi.strings.ExceptionStrings;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BannerServiceImpl implements IBannerService {
    private final IBannerRespository bannerRepository;

    @Override
    public List<BannerResponse> getBanners() {

        List<BannerResponse> banners = bannerRepository.findAllBanners().stream().map(Banner::convertToBannerResponse).toList();

        if (banners.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND.value(), ExceptionStrings.BANNER_NOT_FOUND);
        }

        return banners;
    }
}
