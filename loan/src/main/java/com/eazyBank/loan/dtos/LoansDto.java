package com.eazyBank.loan.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "Loans",
        description = "This schema hold loan details"
)
public class LoansDto {
    @Schema(
            description = "customer mobile number",
            example = "9876543210"
    )
    @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @Schema(
            description = "unique loan number for each loan"
            ,example = "1099882369"
    )
    @Pattern(regexp = "^\\d{10}$", message = "LoanNumber number must be 10 digits")
    private String loanNumber;

    @Schema(
            description = "The loan Type"
            ,example = "HomeLoan"
    )
    @NotEmpty(message = "Loan Type cannot be empty")
    private String loanType;

    @Schema(
            description = "total loan amount for particular loan",
            example = "100000"
    )
    @Min(value = 1,message = "Total loan must be > 0")
    private Integer totalLoan;

    @Schema(
            description = "amount paid by customer",
            example = "10"
    )
    @NotNull(message = "amount paid cannot be null")
    private Integer amountPaid;

    @Schema(
            description = "The outstanding amount",
            example = "99990"
    )
    @NotNull(message = "out standing amount cannot be null")
    private Integer outstandingAmount;
}
