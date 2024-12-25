package com.nandana.transactapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {
    @Schema(description = "Alamat email harus mengikuti format yang berlaku dan tidak boleh kosong", example = "johndoe@example.com")
    @NotBlank(message = "ALamat email tidak boleh kosong")
    @Email(message = "Alamat email tidak sesuai format")
    private String email;

    @Schema(description = "Kata sandi setidaknya harus berisi satu huruf kapital dan satu angka", example = "Password1")
    @NotBlank(message = "Kata sandi tidak boleh kosong")
    @Size(min = 8, max = 100, message = "Panjang kata sandi minimal adalah 8 karakter dan maksimal 100 karakter")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]*$", message = "Kata sandi setidaknya harus berisi satu huruf kapital dan satu angka")
    private String password;
}
