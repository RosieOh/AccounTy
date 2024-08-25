package com.accounty.global;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(toBuilder = true)
@ToString
public class GlobalApiResponse {
    private int status;
    private String message;
    private List<?> result;

    // api 응답 성공 시 apiResponse로 반환 메소드
    public static GlobalApiResponse toGlobalApiResponse(List<?> dtoList) {
        return GlobalApiResponse.builder()
                .message("성공")
                .status(200)
                .result(dtoList)
                .build();
    }


}
