package com.accounty.company.dto;

import lombok.*;

public class CreateCompanyDTO {
    @Getter
    @Setter // 테스트용
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder(toBuilder = true)
    @ToString
    public static class Request {
        private String ticker;
    }

    @Getter
    @Setter // 테스트용
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder(toBuilder = true)
    @ToString
    public static class Response {
        private String companyName;
        private String ticker;
    }
}
