package com.accounty.company.domain;

import com.accounty.company.dto.AutoCompleteDTO;
import com.accounty.company.dto.CreateCompanyDTO;
import com.accounty.company.dto.CompanyWithDividendDTO;
import com.accounty.dividend.domain.Dividend;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(toBuilder = true)
@ToString
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @OneToMany(mappedBy = "company", cascade = CascadeType.PERSIST)
    private List<Dividend> dividendsList;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String ticker;

    @Column
    private LocalDateTime delDate;

    public CreateCompanyDTO.Response toCreateResponseDTO() {
        return CreateCompanyDTO.Response.builder()
                .companyName(name)
                .ticker(ticker)
                .build();
    }

    public AutoCompleteDTO.Response toAutoCompleteResponseDTO() {
        return AutoCompleteDTO.Response.builder()
                .ticker(ticker)
                .name(name)
                .build();
    }

    public CompanyWithDividendDTO.Response toCompanyWithDividendDTO() {
        return CompanyWithDividendDTO.Response.builder()
                .dividendDTOList(dividendsList.stream().map(Dividend::toJoinDividendDTO).toList())
                .ticker(ticker)
                .name(name)
                .build();
    }

}
