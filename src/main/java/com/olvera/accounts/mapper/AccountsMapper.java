package com.olvera.accounts.mapper;

import com.olvera.accounts.dto.AccountsDto;
import com.olvera.accounts.entity.Accounts;

public class AccountsMapper {

    public static AccountsDto mapToAccountsDto(Accounts accounts) {
        return AccountsDto.builder()
                .accountNumber(accounts.getAccountNumber())
                .accountType(accounts.getAccountType())
                .branchAddress(accounts.getBranchAddress())
                .build();
    }

    public static Accounts mapToAccounts(AccountsDto accountsDto) {
        return Accounts.builder()
                .accountNumber(accountsDto.getAccountNumber())
                .accountType(accountsDto.getAccountType())
                .branchAddress(accountsDto.getBranchAddress())
                .build();
    }
}
