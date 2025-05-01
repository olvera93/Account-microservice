package com.olvera.accounts.repository;

import com.olvera.accounts.entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long> {

    /**
     * Find an account by its customer ID
     *
     * @param customerId - ID of the customer
     * @return Optional<Accounts> - Account object if found, otherwise empty
     */
    Optional<Accounts> findByCustomerId(Long customerId);
}
