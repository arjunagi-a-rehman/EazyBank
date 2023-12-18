package com.eazyBank.Accounts.controller;

import com.eazyBank.Accounts.Dtos.CustomerDto;
import com.eazyBank.Accounts.Dtos.ErrorResponseDto;
import com.eazyBank.Accounts.Dtos.ResponseDto;
import com.eazyBank.Accounts.constants.AccountsConstants;
import com.eazyBank.Accounts.services.IAccountServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

@Tag(
        name = "CURD API's for Accounts in eazyBank",
        description = "CURD REST API's in EasyBank to Create, Read, Update and Update Accounts "
)
@RestController
@RequestMapping(path = "/api",produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class AccountController {
    private IAccountServices accountServices;

    @Operation(
            summary = "Create Account REST API",
            description = "API to create new Customer and Account for that customer in easyBank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@RequestBody @Valid CustomerDto customerDto){
        accountServices.createAccount(customerDto);
        return ResponseEntity.
                status(HttpStatus.CREATED).
                body(new ResponseDto(AccountsConstants.STATUS_201,AccountsConstants.MESSAGE_201));
    }
    @Operation(
            summary = "Fetch Account Details REST API",
            description = "API to Fetch Account details and Customer Details based on Mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> getAccountDetails(@RequestParam@Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits")
                                                             String mobileNumber){
        CustomerDto customerDto=accountServices.getAccountDetails(mobileNumber);
        return new ResponseEntity<>(customerDto,HttpStatus.OK);
    }
    @Operation(
            summary = "Update Account Details REST API",
            description = "API to update Account details and Customer Details based on Account number and Mobile Number Note:- Account number and Mobile number cannot be changed"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PutMapping("/account")
    public ResponseEntity<ResponseDto> updateAccount(@RequestBody @Valid CustomerDto customerDto){
        boolean isUpdated=accountServices.updateAccount(customerDto);
        if(isUpdated){
            return new ResponseEntity<>(new ResponseDto(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200),HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDto(AccountsConstants.STATUS_500,AccountsConstants.MESSAGE_500),HttpStatus.INTERNAL_SERVER_ERROR);

    }
    @Operation(
            summary = "Delete Account Details REST API",
            description = "API to Delete Account details and Customer Details based on Mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccount(@RequestParam @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits")
                                                         String mobileNumber){
        boolean isDeleted=accountServices.deleteAccount(mobileNumber);
        if(isDeleted){
            return ResponseEntity.ok(new ResponseDto(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(AccountsConstants.STATUS_417,AccountsConstants.MESSAGE_417_DELETE));
    }
}

