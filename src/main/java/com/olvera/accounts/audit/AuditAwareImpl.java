package com.olvera.accounts.audit;

import org.hibernate.annotations.Comment;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditAwareImpl")
public class AuditAwareImpl implements AuditorAware<String> {

    /**
     * This method returns the current auditor. In this case, it returns an empty Optional.
     * In a real-world application, you would typically return the username of the currently logged-in user.
     *
     * @return an Optional containing the current auditor's username
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("ACCOUNTS_MS");
    }
}
