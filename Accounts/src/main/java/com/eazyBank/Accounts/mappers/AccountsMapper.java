package com.eazyBank.Accounts.mappers;

import com.eazyBank.Accounts.Dtos.AccountsDto;
import com.eazyBank.Accounts.models.Accounts;
import org.springframework.beans.factory.BeanFactory;

public class AccountsMapper   {
    public static AccountsDto mapToAccountsDto( Accounts accounts,AccountsDto accountsDto){
        accountsDto.setAccount_number(accounts.getAccount_number());
        accountsDto.setAccountType(accounts.getAccountType());
        accountsDto.setBranchAddress(accounts.getBranchAddress());
        return accountsDto;
    }

    public static Accounts mapToAccounts(AccountsDto accountsDto,Accounts accounts){
        accounts.setAccount_number(accountsDto.getAccount_number());
        accounts.setAccountType(accountsDto.getAccountType());
        accounts.setBranchAddress(accountsDto.getBranchAddress());
        return accounts;
    }
}
