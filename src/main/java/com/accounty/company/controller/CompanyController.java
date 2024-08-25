package com.accounty.company.controller;

import com.accounty.company.domain.Company;
import com.accounty.company.dto.CreateCompanyDTO;
import com.accounty.company.service.CompanyService;
import com.accounty.global.GlobalApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@Validated
public class CompanyController {
    private final CompanyService companyService;

    // 회사 생성
    @PostMapping("/company")
    public ResponseEntity<GlobalApiResponse> create(@Valid @RequestBody CreateCompanyDTO.Request request) {
        log.info("입력받은 ticker={}", request.getTicker());

        Company company = companyService.createCompany(request);
        List<CreateCompanyDTO.Response> list = new ArrayList<>(List.of(company.toCreateResponseDTO()));
        return ResponseEntity.ok(GlobalApiResponse.toGlobalApiResponse(list));
    }

    // 회사 삭제
    @DeleteMapping("/company/{ticker}")
    public ResponseEntity<GlobalApiResponse> delete(@PathVariable @NotBlank(message = "삭제할 ticker를 입력해주세요.") @Size(min = 1, message = "ticker는 최소 1글자 이상입니다.") String ticker) {
        log.info("삭제할 ticker={}", ticker);

        companyService.deleteCompany(ticker);
        GlobalApiResponse response = GlobalApiResponse.builder()
                .message("성공")
                .status(200)
                .build();
        return ResponseEntity.ok(response);
    }
}
