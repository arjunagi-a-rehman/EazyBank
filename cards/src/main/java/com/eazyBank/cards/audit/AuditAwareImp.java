package com.eazyBank.cards.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component(value = "AuditAwareImp")
public class AuditAwareImp implements AuditorAware {
    /**
     * @return auditor
     */
    @Override
    public Optional getCurrentAuditor() {
        return Optional.of("CARDS_MC");
    }
}
