package com.accounty.company.dto;

import com.accounty.dividend.dto.JoinDividendDTO;
import lombok.*;

import java.util.List;

public class CompanyWithDividendDTO {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder(toBuilder = true)
    @ToString
    public static class Response {
        private String name;
        private String ticker;
        private List<JoinDividendDTO.Response> dividendDTOList;
    }
}
