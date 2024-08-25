package com.accounty.dividend.domain;

import com.accounty.company.domain.Company;
import com.accounty.dividend.dto.JoinDividendDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(toBuilder = true)
@ToString
public class Dividend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private String dividend;

    @Column
    private LocalDateTime delDate;

    // Dividend -> JoinDividendDTO.Response
    public JoinDividendDTO.Response toJoinDividendDTO() {
        return JoinDividendDTO.Response.builder()
                .dividend(dividend)
                .date(date)
                .build();
    }
}
