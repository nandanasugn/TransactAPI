package com.nandana.transactapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRequest {
    @Schema(description = "kode layanan tidak boleh blank dan harus diisi sesuai dengan layanan yang tersedia", example = "PAJAK")
    @NotBlank(message = "Kode layanan tidak boleh blank")
    private String service_code;
}
