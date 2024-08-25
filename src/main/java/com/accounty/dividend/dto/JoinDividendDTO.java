package com.accounty.dividend.dto;

import lombok.*;

import java.time.LocalDateTime;

public class JoinDividendDTO {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder(toBuilder = true)
    @ToString
    public static class Response {
        private LocalDateTime date;
        private String dividend;
    }
}
