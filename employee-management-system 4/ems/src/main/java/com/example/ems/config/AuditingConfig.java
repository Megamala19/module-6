package com.example.ems.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * Exercise 7: supplies the "current auditor" (i.e. who created/modified a
 * record) used to populate @CreatedBy / @LastModifiedBy on entities that
 * extend Auditable. This project has no login configured, so it always
 * returns "system" - if you add Spring Security later, replace the body
 * with SecurityContextHolder.getContext().getAuthentication().getName().
 */
@Configuration
public class AuditingConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of("system");
    }
}
