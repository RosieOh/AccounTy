package com.accounty.company.domain;

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

    @Column(nullable = false)
    private String ticker;

    @Column
    private LocalDateTime delDate;
}
