package com.accounty.company.service;

import com.accounty.company.domain.Company;
import com.accounty.company.dto.AutoCompleteDTO;
import com.accounty.company.dto.CreateCompanyDTO;
import com.accounty.company.exception.CompanyErrorCode;
import com.accounty.company.exception.CompanyException;
import com.accounty.company.repository.CompanyRepository;
import com.accounty.dividend.domain.Dividend;
import com.accounty.dividend.service.DividendService;
import com.accounty.scraper.YahooScraper;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final YahooScraper yahooScraper;
    private final DividendService dividendService;

    /**
     * 회사 정보 생성
     *
     * @param request 회사의 ticker
     * @return 생성된 회사 정보
      */
    public Company createCompany(CreateCompanyDTO.Request request) {
        String ticker = request.getTicker();
        Company company = companyRepository.findByTicker(ticker).orElse(null);
        if (company != null) {
            throw new CompanyException(CompanyErrorCode.ALREADY_EXIST_COMPANY.getMessage());
        }
        company = yahooScraper.getCompanyName(ticker);
        return companyRepository.save(company);
    }

    /**
     * 회사 조회
     *
     * @param companyName 회사 이름
     * @return 조회된 회사
     */
    @Transactional(readOnly = true)
    public Company getCompany(String companyName) {
        return companyRepository.findByName(companyName).orElseThrow(
                () -> new CompanyException(CompanyErrorCode.NOT_FOUND_NAME.getMessage()));
    }

    /**
     * 회사 삭제
     *
     * @param ticker 삭제할 회사의 ticker
     */
    public void deleteCompany(String ticker) {
        Company company = companyRepository.findByTicker(ticker).orElseThrow(() -> new CompanyException(CompanyErrorCode.NOT_FOUND_TICKER.getMessage()));
        companyRepository.delete(company);
    }

    /**
     * 회사 정보와 배당금 정보 조회
     *
     * @param companyName 조회할 회사 이름
     * @return 조회된 회사 정보와 배당금 정보
     */
    public Company getCompanyInfo(String companyName) {
        Company company = companyRepository.findByName(companyName).orElseThrow(() -> new CompanyException(CompanyErrorCode.NOT_FOUND_NAME.getMessage()));
        List<Dividend> dividendInfo = dividendService.getDividendInfo(company);
        Company withDividend = company.toBuilder()
                .dividendsList(dividendInfo)
                .build();
        return companyRepository.save(withDividend);
    }

    /**
     * 검색한 단어에 해당하는 회사명 중 10개를 정렬하여 조회
     *
     * @param request 검색할 회사명
     * @return 조회된 회사 리스트
     */
    @Transactional(readOnly = true)
    public List<Company> getAutoComplete(AutoCompleteDTO.Request request) {
        String prefix = request.getPrefix();
        return companyRepository.findTop10ByNameLikeOrderByNameDesc(prefix);
    }

    /**
     *
     * @param pageable 페이징 처리
     * @return 페이징 처리된 모든 회사 리스트
     */
    public Page<Company> getAllCompany(Pageable pageable) {
        return companyRepository.findAll(pageable);
    }
}
