package com.accounty.scraper;

import com.accounty.company.domain.Company;
import com.accounty.dividend.domain.Dividend;

import java.util.List;

public interface ScraperInterface {
    Company getCompanyName(String ticker);

    List<Dividend> getDividendList(String ticker);
}
