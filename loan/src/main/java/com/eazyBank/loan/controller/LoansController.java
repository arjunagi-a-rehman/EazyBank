package com.eazyBank.loan.controller;

import com.eazyBank.loan.constants.LoansConstants;
import com.eazyBank.loan.dtos.ErrorResponseDto;
import com.eazyBank.loan.dtos.LoansDto;
import com.eazyBank.loan.dtos.ResponseDto;
import com.eazyBank.loan.services.ILoansServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Tag(
        name = "CURD API's for Loans in Eazy bank",
        description = "CURD REST API's in eazy bank to create, update, fetch and delete loans"
)
@RestController
@RequestMapping(path = "/api",produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Validated
public class LoansController {
    private ILoansServices loansServices;
    @Operation(
            summary = "Create Loan REST API",
            description = "This Rest API I create new  loan for a user by taking mobile number "
    )
    @ApiResponses(
            {
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
    public ResponseEntity<ResponseDto> createNewLoan(@RequestParam @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits") String mobileNumber){

        loansServices.createLoan(mobileNumber);
        return ResponseEntity.
                status(HttpStatus.CREATED).
                body(new ResponseDto(LoansConstants.STATUS_201,LoansConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Fetch Loan Details REST API",
            description = "API to Fetch Loan details based on Mobile number"
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
    @GetMapping("/fetch/{mobileNumber}")
    public ResponseEntity<LoansDto> getLoanByMobileNumber(@PathVariable @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits") String mobileNumber){
        return new ResponseEntity<>( loansServices.fetchLoan(mobileNumber),
                HttpStatus.OK);
    }

    @Operation(
            summary = "Update Loan Details REST API",
            description = "API to update Loan details  Details based on Loan number and Mobile Number Note:- Loan number and Mobile number cannot be changed"
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
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateLoan(@RequestBody @Valid LoansDto loansDto){
        boolean isUpdated= loansServices.updateLoan(loansDto);
        if(isUpdated){
            return new ResponseEntity<>(new ResponseDto(LoansConstants.STATUS_200,LoansConstants.MESSAGE_200),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new ResponseDto(LoansConstants.STATUS_417,LoansConstants.MESSAGE_417_UPDATE),HttpStatus.EXPECTATION_FAILED);
        }
    }
    @Operation(
            summary = "Delete Loan Details REST API",
            description = "API to Delete Loan details Details based on Mobile number"
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
    public ResponseEntity<ResponseDto> deleteLoan(@RequestParam @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits") String mobileNumber){
        boolean isDeleted= loansServices.deleteLoan(mobileNumber);
        if(isDeleted){
            return new ResponseEntity<>(new ResponseDto(LoansConstants.STATUS_200,LoansConstants.MESSAGE_200),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new ResponseDto(LoansConstants.STATUS_417,LoansConstants.MESSAGE_417_DELETE),HttpStatus.EXPECTATION_FAILED);
        }
    }
}
