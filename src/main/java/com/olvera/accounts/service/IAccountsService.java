package com.olvera.accounts.service;

import com.olvera.accounts.dto.CustomerDto;

public interface IAccountsService {

    /**
     *
     * @param customerDto - CustomerDto Object
     */
    void createAccount(CustomerDto customerDto);

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return CustomerDto - CustomerDto Object
     */
    CustomerDto fetchAccount(String mobileNumber);

}
