package com.olvera.accounts.repository;

import com.olvera.accounts.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Find a customer by their mobile number
     *
     * @param mobileNumber - Mobile number of the customer
     * @return Optional<Customer> - Customer object if found, otherwise empty
     */
    Optional<Customer> findByMobileNumber(String mobileNumber);

}
