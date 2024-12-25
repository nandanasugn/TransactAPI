package com.nandana.transactapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateRequest {

    @Schema(description = "Nama depan pengguna", example = "John")
    @NotBlank(message = "Nama depan tidak boleh kosong")
    @Size(max = 100, message = "Jumlah maksimal karakter untuk nama depan adalah 100 karakter")
    private String first_name;

    @Schema(description = "Nama belakang pengguna", example = "Wick")
    @NotBlank(message = "Nama belakang tidak boleh kosong")
    @Size(max = 100, message = "Jumlah maksimal karakter untuk nama belakang adalah 100 karakter")
    private String last_name;
}
