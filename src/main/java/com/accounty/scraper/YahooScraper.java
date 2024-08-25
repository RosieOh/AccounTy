package com.accounty.scraper;

import com.accounty.company.domain.Company;
import com.accounty.dividend.domain.Dividend;
import lombok.extern.log4j.Log4j2;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
public class YahooScraper implements ScraperInterface{
    private static final String DIVIDEND_URL = "https://finance.yahoo.com/quote/%s/history?period1=%d&period2=%d&frequency=1mo";
    private static final String COMPANY_URL = "https://finance.yahoo.com/quote/%s/p=%s";
    private static final long START_TIME = 86400;

    @Override
    public Company getCompanyName(String ticker) {
        Company company;
        try {
            String url = String.format(COMPANY_URL, ticker, ticker);
            Connection connect = Jsoup.connect(url);
            Document document = connect.get();
            Element element = document.getElementsByTag("h1").get(1);
            String title = element.text().replaceAll("\\(.*\\)", "");

            company = Company.builder()
                    .name(title)
                    .ticker(ticker)
                    .build();
        } catch (IOException e) {
            log.error("스크랩을 통해서 회사 정보를 가져오기 실패했습니다. = {}", e.getMessage());
            company = null;
        }
        return company;
    }

    @Override
    public List<Dividend> getDividendList(Company company) {
        List<Dividend> dividendList = new ArrayList<>();
        try {
            long end = System.currentTimeMillis() / 1000;
            String url = String.format(DIVIDEND_URL, company.getTicker(), START_TIME, end);
            Connection connect = Jsoup.connect(url);
            Document document = connect.get();

            Elements elements = document.getElementsByClass("table svelte-ewueuo");
            Element element = elements.get(0);

            Element tbody = element.children().get(1);
            for (Element child : tbody.children()) {
                String text = child.text();
                if (!text.endsWith("Dividend")) {
                    continue;
                }

                String[] split = text.split(" ");
                int month = Month.stringToMonth(split[0]);
                int day = Integer.parseInt(split[1].replace(",", ""));
                int year = Integer.parseInt(split[2]);
                String dividend = split[3];
                if (month == 1) {
                    throw new RuntimeException("Month enum에서 해당하는 month를 찾을 수 없습니다. : " + split[0]);
                }

                Dividend dividendBuild = Dividend.builder()
                        .dividend(dividend)
                        .date(LocalDateTime.of(year, month, day, 0, 0))
                        .company(company)
                        .build();
                dividendList.add(dividendBuild);
            }
        } catch (IOException e) {
            log.error("스크랩을 통해 배당금 정보 가져오기 실패!! = {}", e.getMessage());
        }
        return dividendList;
    }
}
