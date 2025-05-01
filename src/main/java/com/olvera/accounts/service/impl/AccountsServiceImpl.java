package com.olvera.accounts.service.impl;

import com.olvera.accounts.dto.AccountsDto;
import com.olvera.accounts.dto.CustomerDto;
import com.olvera.accounts.entity.Accounts;
import com.olvera.accounts.entity.Customer;
import com.olvera.accounts.exception.CustomerAlreadyExistsException;
import com.olvera.accounts.exception.ResourceNotFoundException;
import com.olvera.accounts.repository.AccountsRepository;
import com.olvera.accounts.repository.CustomerRepository;
import com.olvera.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import static com.olvera.accounts.constants.AccountsConstants.ADDRESS;
import static com.olvera.accounts.constants.AccountsConstants.SAVINGS;
import static com.olvera.accounts.mapper.AccountsMapper.mapToAccounts;
import static com.olvera.accounts.mapper.AccountsMapper.mapToAccountsDto;
import static com.olvera.accounts.mapper.CustomerMapper.mapToCustomer;
import static com.olvera.accounts.mapper.CustomerMapper.mapToCustomerDto;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    /**
     * @param customerDto - CustomerDto Object
     */
    @Override
    public void createAccount(CustomerDto customerDto) {

        Customer customer = mapToCustomer(customerDto, new Customer());

        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());

        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNUmber "
                    + customerDto.getMobileNumber());
        }

        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));

    }

    /**
     * Fetch account details for a customer
     *
     * @param mobileNumber - Input Mobile Number
     * @return
     */
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        CustomerDto customerDto = mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(mapToAccountsDto(accounts, new AccountsDto()));
        return customerDto;
    }

    /**
     * Update account details for a customer
     *
     * @param customerDto - CustomerDto Object
     * @return boolean indicating if the update of Account details is successful or not
     */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto !=null ){
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );
            mapToAccounts(accountsDto, accounts);
            accounts = accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            mapToCustomer(customerDto, customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return  isUpdated;
    }

    /**
     * Delete account details for a customer
     *
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the deletion of Account details is successful or not
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }

    /**
     * Create a new account for the customer
     *
     * @param customer - Customer object
     * @return Accounts object
     */
    private Accounts createNewAccount(Customer customer) {

        Accounts newAccount;

        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount = Accounts.builder()
                .customerId(customer.getCustomerId())
                .accountNumber(randomAccNumber)
                .accountType(SAVINGS)
                .branchAddress(ADDRESS)
                .build();

        return newAccount;
    }
}
