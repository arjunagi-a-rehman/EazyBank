package com.eazyBank.cards.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "Cards",
        description = "This schema holds all the card details for a particular user"
)
public class CardsDto {
    @Schema(
            description = "customer mobile number",
            example = "9876543210"
    )
    @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @Schema(
            description = "unique card number for each card"
            ,example = "1099882369"
    )
    @Pattern(regexp = "^\\d{10}$", message = "Card number number must be 10 digits")
    private String cardNumber;
    @Schema(
            description = "The card Type"
            ,example = "Credit card"
    )
    @NotEmpty(message = "Card Type cannot be empty")
    private String cardType;
    @Schema(
            description = "total allowed amount for particular card",
            example = "100000"
    )
    @Min(value = 1,message = "Total limit must be > 0")
    private Integer totalLimit;

    @Schema(
            description = "amount used by customer",
            example = "10"
    )
    @NotNull(message = "amount used cannot be null")
    private Integer amountUsed;

    @Schema(
            description = "The available amount",
            example = "99990"
    )
    @NotNull(message = "available amount cannot be null")
    private Integer availableAmount;
}
