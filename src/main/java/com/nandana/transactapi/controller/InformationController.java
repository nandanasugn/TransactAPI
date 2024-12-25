package com.nandana.transactapi.controller;

import com.nandana.transactapi.dto.response.BannerResponse;
import com.nandana.transactapi.dto.response.CommonResponse;
import com.nandana.transactapi.dto.response.PPOBResponse;
import com.nandana.transactapi.service.IBannerService;
import com.nandana.transactapi.service.IPPOBService;
import com.nandana.transactapi.strings.EndpointStrings;
import com.nandana.transactapi.util.DtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Module Information", description = "Endpoints for information operations")
@RestController
@RequiredArgsConstructor
public class InformationController {
    private final IBannerService bannerService;
    private final IPPOBService ppobService;

    @Operation(summary = "Get all banners", description = "Retrieve a list of all available banners")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request Successfully",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                      "status": 0,
                                      "message": "Sukses",
                                      "data": [
                                        {
                                          "banner_name": "Banner 1",
                                          "banner_image": "https://nutech-integrasi.app/dummy.jpg",
                                          "description": "Lerem Ipsum Dolor sit amet"
                                        },
                                        {
                                          "banner_name": "Banner 2",
                                          "banner_image": "https://nutech-integrasi.app/dummy.jpg",
                                          "description": "Lerem Ipsum Dolor sit amet"
                                        },
                                        {
                                          "banner_name": "Banner 3",
                                          "banner_image": "https://nutech-integrasi.app/dummy.jpg",
                                          "description": "Lerem Ipsum Dolor sit amet"
                                        },
                                        {
                                          "banner_name": "Banner 4",
                                          "banner_image": "https://nutech-integrasi.app/dummy.jpg",
                                          "description": "Lerem Ipsum Dolor sit amet"
                                        },
                                        {
                                          "banner_name": "Banner 5",
                                          "banner_image": "https://nutech-integrasi.app/dummy.jpg",
                                          "description": "Lerem Ipsum Dolor sit amet"
                                        },
                                        {
                                          "banner_name": "Banner 6",
                                          "banner_image": "https://nutech-integrasi.app/dummy.jpg",
                                          "description": "Lerem Ipsum Dolor sit amet"
                                        }
                                      ]
                                    }
                                    """),
                            schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                      "status": 500,
                                      "message": "Terjadi kesalahan pada server",
                                      "data": null
                                    }
                                    """)))
    })
    @GetMapping(EndpointStrings.BANNER)
    public ResponseEntity<?> getAllBanners() {
        List<BannerResponse> banners = bannerService.getBanners();

        CommonResponse<?> response = DtoMapper.mapToCommonResponse(HttpStatus.OK.value(), "Sukses", banners);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Get all PPOB services", description = "Retrieve a list of all available PPOB services")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request Successfully",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                     {
                       "status": 200,
                       "message": "Sukses",
                       "data": [
                         {
                           "service_code": "PAJAK",
                           "service_name": "Pajak PBB",
                           "service_icon": "https://nutech-integrasi.app/dummy.jpg",
                           "service_tariff": 40000
                         },
                         {
                           "service_code": "PLN",
                           "service_name": "Listrik",
                           "service_icon": "https://nutech-integrasi.app/dummy.jpg",
                           "service_tariff": 10000
                         },
                         {
                           "service_code": "PDAM",
                           "service_name": "PDAM Berlangganan",
                           "service_icon": "https://nutech-integrasi.app/dummy.jpg",
                           "service_tariff": 40000
                         },
                         {
                           "service_code": "PULSA",
                           "service_name": "Pulsa",
                           "service_icon": "https://nutech-integrasi.app/dummy.jpg",
                           "service_tariff": 40000
                         },
                         {
                           "service_code": "PGN",
                           "service_name": "PGN Berlangganan",
                           "service_icon": "https://nutech-integrasi.app/dummy.jpg",
                           "service_tariff": 50000
                         },
                         {
                           "service_code": "MUSIK",
                           "service_name": "Musik Berlangganan",
                           "service_icon": "https://nutech-integrasi.app/dummy.jpg",
                           "service_tariff": 50000
                         },
                         {
                           "service_code": "TV",
                           "service_name": "TV Berlangganan",
                           "service_icon": "https://nutech-integrasi.app/dummy.jpg",
                           "service_tariff": 50000
                         },
                         {
                           "service_code": "PAKET_DATA",
                           "service_name": "Paket data",
                           "service_icon": "https://nutech-integrasi.app/dummy.jpg",
                           "service_tariff": 50000
                         },
                         {
                           "service_code": "VOUCHER_GAME",
                           "service_name": "Voucher Game",
                           "service_icon": "https://nutech-integrasi.app/dummy.jpg",
                           "service_tariff": 100000
                         },
                         {
                           "service_code": "VOUCHER_MAKANAN",
                           "service_name": "Voucher Makanan",
                           "service_icon": "https://nutech-integrasi.app/dummy.jpg",
                           "service_tariff": 200000
                         },
                         {
                           "service_code": "QURBAN",
                           "service_name": "Qurban",
                           "service_icon": "https://nutech-integrasi.app/dummy.jpg",
                           "service_tariff": 200000
                         },
                         {
                           "service_code": "ZAKAT",
                           "service_name": "Zakat",
                           "service_icon": "https://nutech-integrasi.app/dummy.jpg",
                           "service_tariff": 300000
                         }
                       ]
                     }
                     """),
                            schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                     {
                       "status": 401,
                       "message": "Token tidak valid atau kadaluwarsa",
                       "data": null
                     }
                     """))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                     {
                       "status": 500,
                       "message": "Terjadi kesalahan pada server",
                       "data": null
                     }
                     """)))
    })
    @GetMapping(EndpointStrings.SERVICES)
    public ResponseEntity<?> getServices() {
        List<PPOBResponse> ppobResponses = ppobService.getAllPPOBs();

        CommonResponse<?> response = DtoMapper.mapToCommonResponse(HttpStatus.OK.value(), "Sukses", ppobResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
