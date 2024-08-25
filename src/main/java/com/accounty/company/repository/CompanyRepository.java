package com.accounty.company.repository;

import com.accounty.company.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByTicketAndDelete(String ticker, LocalDateTime delDate);

    Optional<Company> findByNameAndDelete(String name, LocalDateTime delDate);
}
