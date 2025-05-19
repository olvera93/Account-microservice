package com.olvera.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
        name = "Accounts",
        description = "Schema to hold Account information"
)
public class AccountsDto {

    @Schema(description = "Account Number of Eazy Bank Account", example = "1234567890")
    @NotEmpty(message = "AccountNumber can not be null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "AccountNumber must be 10 digits")
    private Long accountNumber;

    @Schema(description = "Account type of Eazy Bank account", example = "Savings")
    @NotEmpty(message = "AccountType can not be null or empty")
    private String accountType;

    @Schema(description = "Eazy Bank branch address", example = "123 Main St, Cityville")
    @NotEmpty(message = "BranchName can not be null or empty")
    private String branchAddress;
}
