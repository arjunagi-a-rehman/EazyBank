package com.eazyBank.cards.controllers;

import com.eazyBank.cards.constants.CardsConstants;
import com.eazyBank.cards.dtos.CardsDto;
import com.eazyBank.cards.dtos.ErrorResponseDto;
import com.eazyBank.cards.dtos.ResponseDto;
import com.eazyBank.cards.services.ICardsServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CURD API's for Card in Eazy bank",
        description = "CURD REST API's in eazy bank to create, update, fetch and delete cards"
)
@RestController
@RequestMapping(path = "/api",produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class CardsController {

    private ICardsServices cardsServices;

    @Operation(
            summary = "Create card REST API",
            description = "This Rest API I create new  card for a user by taking mobile number "
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
    public ResponseEntity<ResponseDto> createNewCard(@RequestParam String mobileNumber){
        cardsServices.createCard(mobileNumber);
        return new ResponseEntity<>(new ResponseDto(CardsConstants.STATUS_201,CardsConstants.MESSAGE_201), HttpStatus.CREATED);
    }
    @Operation(
            summary = "Fetch cards Details REST API",
            description = "API to Fetch cards details based on Mobile number"
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
    public ResponseEntity<CardsDto> fetchCard(@RequestParam String mobileNumber){
        return ResponseEntity.ok(cardsServices.fetchCard(mobileNumber));
    }
    @Operation(
            summary = "Update cards Details REST API",
            description = "API to update cards details  Details based on card number and Mobile Number Note:- Loan number and Mobile number cannot be changed"
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
    public ResponseEntity<ResponseDto> updateCard(@RequestBody CardsDto cardsDto){
        boolean isUpdated= cardsServices.updateCard(cardsDto);
        if(isUpdated){
            return new ResponseEntity<>(new ResponseDto(CardsConstants.STATUS_200,CardsConstants.MESSAGE_200),HttpStatus.OK);

        }else {
            return new ResponseEntity<>(new ResponseDto(CardsConstants.STATUS_417,CardsConstants.MESSAGE_417_UPDATE),HttpStatus.EXPECTATION_FAILED);
        }
    }
    @Operation(
            summary = "Delete card Details REST API",
            description = "API to Delete card details Details based on Mobile number"
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
    public ResponseEntity<ResponseDto> deleteCard(@RequestParam String mobileNumber){
        boolean isDeleted=cardsServices.deleteCard(mobileNumber);
        if(isDeleted){
            return new ResponseEntity<>(new ResponseDto(CardsConstants.STATUS_200,CardsConstants.MESSAGE_200),HttpStatus.OK);

        }else {
            return new ResponseEntity<>(new ResponseDto(CardsConstants.STATUS_417,CardsConstants.MESSAGE_417_DELETE),HttpStatus.EXPECTATION_FAILED);
        }
    }
}
