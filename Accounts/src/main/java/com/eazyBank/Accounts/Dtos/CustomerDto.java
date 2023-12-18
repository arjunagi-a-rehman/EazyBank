package com.eazyBank.Accounts.Dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

@Schema(
        name = "Customer",
        description = "Schema holds the customer details"
)
@Data
public class CustomerDto {
    @Schema(
            description = "customer name",
            example = "Jon Targaryen"
    )
    @Size(min = 3,max = 30,message = "Name filed cannot be empty")
    private String name;

    @Schema(
            description = "customer email",
            example = "jon123@crow.com"
    )
    @Email(message = "Email filed cannot be empty")
    private String email;

    @Schema(
            description = "customer mobile number",
            example = "8967452310"
    )
    @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @Schema(
            description = "Account details"
    )
    private AccountsDto accountsDto;
}
