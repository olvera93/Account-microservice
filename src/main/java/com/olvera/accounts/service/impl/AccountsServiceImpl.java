package com.olvera.accounts.service.impl;

import com.olvera.accounts.dto.CustomerDto;
import com.olvera.accounts.entity.Accounts;
import com.olvera.accounts.entity.Customer;
import com.olvera.accounts.exception.CustomerAlreadyExistsException;
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
import static com.olvera.accounts.mapper.CustomerMapper.mapToCustomer;

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

        Customer customer = mapToCustomer(customerDto);

        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());

        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNUmber "
                    + customerDto.getMobileNumber());
        }

        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Anonymous");

        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));

    }

    /**
     * Create a new account for the customer
     *
     * @param customer - Customer object
     * @return Accounts object
     */
    private Accounts createNewAccount(Customer customer) {

        Accounts newAccount = new Accounts();

        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount = Accounts.builder()
                .customerId(customer.getCustomerId())
                .accountNumber(randomAccNumber)
                .accountType(SAVINGS)
                .branchAddress(ADDRESS)
                .build();

        newAccount.setCreatedAt(LocalDateTime.now());
        newAccount.setCreatedBy("Anonymous");

        return newAccount;
    }
}
