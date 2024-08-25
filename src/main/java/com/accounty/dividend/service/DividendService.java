package com.accounty.dividend.service;

import com.accounty.company.domain.Company;
import com.accounty.dividend.domain.Dividend;
import com.accounty.dividend.repository.DividendRepository;
import com.accounty.scraper.YahooScraper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class DividendService {
    private final DividendRepository dividendRepository;
    private final YahooScraper yahooScraper;

    public List<Dividend> getDividendInfo(Company company) {
        return yahooScraper.getDividendList(company);
    }

}
