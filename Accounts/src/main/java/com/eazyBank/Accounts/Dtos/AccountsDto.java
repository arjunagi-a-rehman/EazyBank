package com.eazyBank.Accounts.Dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Schema(
        name = "Account",
        description = "Schema to hold Customer Account Information"
)
@Data
public class AccountsDto {

    @Schema(
            description = "customers account number",
            example = "9876543210"
    )
    @Pattern(regexp = "^\\d{10}$", message = "Account number must be 10 digits")
    private Long account_number;

    @Schema(
            description = "customer Account type"
            ,example = "Saving"
    )
    @NotEmpty(message = "Account type cannot be empty")
    private String accountType;

    @Schema(
            description = "branch address where account is created",
            example = "123 Main Street, Old York"
    )
    @NotEmpty(message = "Branch Address Cannot be Empty")
    private String branchAddress;
}
