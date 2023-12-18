package com.eazyBank.Accounts.services;

import com.eazyBank.Accounts.Dtos.CustomerDto;

public interface IAccountServices {
    /**
    * @param customerDto - CustomerDto Object
    */
    void createAccount(CustomerDto customerDto);

    CustomerDto getAccountDetails(String mobileNumber);

    boolean updateAccount(CustomerDto customerDto);

    boolean deleteAccount(String mobileNumber);
}
