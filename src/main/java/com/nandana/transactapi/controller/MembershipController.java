package com.nandana.transactapi.controller;

import com.nandana.transactapi.dto.request.LoginRequest;
import com.nandana.transactapi.dto.request.RegistrationRequest;
import com.nandana.transactapi.dto.request.UpdateRequest;
import com.nandana.transactapi.dto.response.CommonResponse;
import com.nandana.transactapi.dto.response.UserResponse;
import com.nandana.transactapi.service.IAuthService;
import com.nandana.transactapi.service.IUserService;
import com.nandana.transactapi.strings.EndpointStrings;
import com.nandana.transactapi.util.DtoMapper;
import com.nandana.transactapi.util.ObjectValidator;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Tag(name = "Module Membership", description = "Endpoints for membership operations")
@RestController
@RequiredArgsConstructor
public class MembershipController {
    private final IAuthService authService;
    private final IUserService userService;
    private final ObjectValidator objectValidator;

    @Operation(summary = "Register a new user", description = "Register a new user with the given details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registration successful",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                      "status": 200,
                                      "message": "Registrasi berhasil silahkan login",
                                      "data": null
                                    }
                                    """),
                            schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                      "status": 400,
                                      "message": "Request tidak valid",
                                      "data": null
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
                                    """),
                            schema = @Schema(implementation = CommonResponse.class)))
    })
    @PostMapping(EndpointStrings.REGISTRATION)
    public ResponseEntity<?> register(@RequestBody RegistrationRequest registrationRequest) {
        objectValidator.validate(registrationRequest);

        UserResponse userResponse = authService.register(registrationRequest);

        CommonResponse<?> response = DtoMapper.mapToCommonResponse(HttpStatus.OK.value(), "Registrasi berhasil silahkan login", userResponse);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Login user", description = "Authenticate a user with valid credentials")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request Successfully",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                     {
                       "status": 200,
                       "message": "Login sukses",
                       "data": {
                         "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
                       }
                     }
                     """),
                            schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid credentials",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                     {
                       "status": 400,
                       "message": "Email atau password salah",
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
    @PostMapping(EndpointStrings.LOGIN)
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        objectValidator.validate(loginRequest);

        Map<String, String> token = authService.login(loginRequest);
        CommonResponse<?> response = DtoMapper.mapToCommonResponse(HttpStatus.OK.value(), "Login Sukses", token);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Get user profile", description = "Retrieve the user's profile information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                     {
                       "status": 200,
                       "message": "Sukses",
                       "data": {
                         "email": "user@nutech-integrasi.com",
                         "first_name": "User",
                         "last_name": "Nutech",
                         "profile_image": "https://yoururlapi.com/profile.jpeg"
                       }
                     }
                     """),
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                     {
                       "status": 500,
                       "message": "Terjadi kesalahan pada server",
                       "data": null
                     }
                     """))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                     {
                       "status": 401,
                       "message": "Token tidak tidak valid atau kadaluwarsa",
                       "data": null
                     }
                     """)))
    })
    @GetMapping(EndpointStrings.PROFILE)
    public ResponseEntity<?> getProfile() {
        UserResponse userResponse = userService.getProfile();

        CommonResponse<?> response = DtoMapper.mapToCommonResponse(HttpStatus.OK.value(), "Sukses", userResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Update user profile", description = "Update the user's profile information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile updated successfully",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                     {
                       "status": 200,
                       "message": "Update Pofile berhasil",
                       "data": {
                         "email": "user@nutech-integrasi.com",
                         "first_name": "User Edited",
                         "last_name": "Nutech Edited",
                         "profile_image": "https://yoururlapi.com/profile.jpeg"
                       }
                     }
                     """),
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                     {
                       "status": 400,
                       "message": "Validasi gagal",
                       "data": null
                     }
                     """))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                     {
                       "status": 401,
                       "message": "Token tidak tidak valid atau kadaluwarsa",
                       "data": null
                     }
                     """)))
    })
    @PutMapping(EndpointStrings.PROFILE_UPDATE)
    public ResponseEntity<?> updateProfile(@RequestBody UpdateRequest updateRequest) {
        objectValidator.validate(updateRequest);

        UserResponse userResponse = userService.updateProfile(updateRequest);

        CommonResponse<?> response = DtoMapper.mapToCommonResponse(HttpStatus.OK.value(), "Update Pofile berhasil", userResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Update profile image", description = "Update the user's profile image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile image updated successfully",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                     {
                       "status": 200,
                       "message": "Update profile image berhasil",
                       "data": {
                         "email": "user@nutech-integrasi.com",
                         "first_name": "User Edited",
                         "last_name": "Nutech Edited",
                         "profile_image": "https://yoururlapi.com/profile-updated.jpeg"
                       }
                     }
                     """),
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                     {
                       "status": 400,
                       "message": "Validasi gagal",
                       "data": null
                     }
                     """))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                     {
                       "status": 401,
                       "message": "Token tidak tidak valid atau kadaluwarsa",
                       "data": null
                     }
                     """)))
    })
    @PutMapping(EndpointStrings.PROFILE_IMAGE)
    public ResponseEntity<?> updateProfileImage(@RequestParam("file") MultipartFile file) {
        UserResponse userResponse = userService.updateProfileImage(file);

        CommonResponse<?> response = new CommonResponse<>(HttpStatus.OK.value(), "Update profile image berhasil", userResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
