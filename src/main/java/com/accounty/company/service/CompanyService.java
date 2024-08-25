package com.accounty.company.service;

import com.accounty.company.domain.Company;
import com.accounty.company.exception.CompanyErrorCode;
import com.accounty.company.exception.CompanyException;
import com.accounty.company.repository.CompanyRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class CompanyService {
    private final CompanyRepository companyRepository;


    // 회사 조회
    @Transactional(readOnly = true)
    public Company getCompany(String companyName) {
        return companyRepository.findByNameAndDelete(companyName, null).orElseThrow(
                () -> new CompanyException(CompanyErrorCode.NOT_FOUND_NAME.getMessage()));
    }
}
