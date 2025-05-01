package com.olvera.accounts.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class AccountsDto {


    private Long accountNumber;

    private String accountType;

    private String branchAddress;

}
