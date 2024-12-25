package com.nandana.transactapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TopUpRequest {
    @Schema(description = "Jumlah top up. Harus lebih dari nol dan tidak boleh null", example = "10000")
    @NotNull(message = "Jumlah top up tidak boleh null")
    @Min(value = 1, message = "Jumlah top up harus lebih dari nol")
    private Long top_up_amount;
}
