package com.nandana.transactapi.controller;

import com.nandana.transactapi.dto.request.TopUpRequest;
import com.nandana.transactapi.dto.request.TransactionRequest;
import com.nandana.transactapi.dto.response.CommonResponse;
import com.nandana.transactapi.dto.response.PagingResponse;
import com.nandana.transactapi.dto.response.TransactionResponse;
import com.nandana.transactapi.service.ITransactionService;
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

import java.util.Map;

@Tag(name = "Module Transaction", description = "Endpoints for managing transactions")
@RestController
@RequiredArgsConstructor
public class TransactionController {
    private final ObjectValidator objectValidator;
    private final ITransactionService transactionService;
    private final IUserService userService;

    @Operation(summary = "Get user balance", description = "Retrieve the current balance of the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Balance retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                     {
                       "status": 200,
                       "message": "Get balance berhasil",
                       "data": {
                         "balance": 1000000
                       }
                     }
                     """),
                            schema = @Schema(implementation = Map.class))),
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
    @GetMapping(EndpointStrings.BALANCE)
    public ResponseEntity<?> getBalance() {
        Map<String, Long> balanceMap = userService.getUserBalance();

        CommonResponse<?> response = DtoMapper.mapToCommonResponse(HttpStatus.OK.value(), "Get balance berhasil", balanceMap);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Top up balance", description = "Add balance to user's account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Balance successfully topped up",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                     {
                       "status": 200,
                       "message": "Top Up Balance berhasil",
                       "data": {
                         "balance": 2000000
                       }
                     }
                     """),
                            schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                     {
                       "status": 400,
                       "message": "Paramter amount hanya boleh angka dan tidak boleh lebih kecil dari 0",
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
    @PostMapping(EndpointStrings.TOP_UP)
    public ResponseEntity<?> topUpBalance(@RequestBody TopUpRequest request) {
        objectValidator.validate(request);

        Map<String, Long> responseMap = transactionService.topUpBalance(request);

        CommonResponse<?> response = DtoMapper.mapToCommonResponse(HttpStatus.OK.value(), "Top Up Balance berhasil", responseMap);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Create a transaction", description = "Create a new transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction successfully created",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                     {
                       "status": 200,
                       "message": "Transaksi berhasil",
                       "data": {
                         "invoice_number": "INV17082023-001",
                         "service_code": "PLN_PRABAYAR",
                         "service_name": "PLN Prabayar",
                         "transaction_type": "PAYMENT",
                         "total_amount": 10000,
                         "created_on": "2023-08-17T10:10:10.000Z"
                       }
                     }
                     """),
                            schema = @Schema(implementation = TransactionResponse.class))),
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
    @PostMapping(EndpointStrings.TRANSACTION)
    public ResponseEntity<?> createTransaction(@RequestBody TransactionRequest request) {
        objectValidator.validate(request);

        TransactionResponse transactionResponse = transactionService.createTransaction(request);

        CommonResponse<?> response = DtoMapper.mapToCommonResponse(HttpStatus.OK.value(), "Transaksi berhasil", transactionResponse);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get transaction history", description = "Retrieve a paginated list of transaction history")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request successfully processed",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                     {
                       "status": 200,
                       "message": "Get History Berhasil",
                       "data": {
                         "offset": 0,
                         "limit": 3,
                         "records": [
                           {
                             "invoice_number": "INV17082023-001",
                             "transaction_type": "TOPUP",
                             "description": "Top Up balance",
                             "total_amount": 100000,
                             "created_on": "2023-08-17T10:10:10.000Z"
                           },
                           {
                             "invoice_number": "INV17082023-002",
                             "transaction_type": "PAYMENT",
                             "description": "PLN Pascabayar",
                             "total_amount": 10000,
                             "created_on": "2023-08-17T10:10:10.000Z"
                           },
                           {
                             "invoice_number": "INV17082023-003",
                             "transaction_type": "PAYMENT",
                             "description": "Pulsa Indosat",
                             "total_amount": 40000,
                             "created_on": "2023-08-17T10:10:10.000Z"
                           },
                         ]
                       }
                     }
                     """),
                            schema = @Schema(implementation = PagingResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                     {
                       "status": 401,
                       "message": "Token tidak tidak valid atau kadaluwarsa",
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
    @GetMapping(EndpointStrings.TRANSACTION_HISTORY)
    public ResponseEntity<?> getTransactionHistory(@RequestParam(defaultValue = "0", required = false) int offset, @RequestParam(defaultValue = "3", required = false) int limit) {
        PagingResponse<?> pagingResponse = transactionService.getAllTransactions(offset, limit);

        CommonResponse<?> response = DtoMapper.mapToCommonResponse(HttpStatus.OK.value(), "Get History Berhasil", pagingResponse);
        return ResponseEntity.ok(response);
    }
}
