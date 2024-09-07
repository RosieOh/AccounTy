package com.accounty.member.controller;

import com.accounty.auth.jwt.TokenProvider;
import com.accounty.global.GlobalApiResponse;
import com.accounty.member.domain.Member;
import com.accounty.member.dto.MemberAuthDTO;
import com.accounty.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/auth")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final TokenProvider tokenProvider;

    // 회원가입
    @PostMapping("/signUp")
    public ResponseEntity<GlobalApiResponse<MemberAuthDTO.Response>> signUp(@RequestBody MemberAuthDTO.SignUp request) {
        MemberAuthDTO.Response register = memberService.register(request);
        GlobalApiResponse<MemberAuthDTO.Response> response = GlobalApiResponse.toGlobalApiResponse(register);
        return ResponseEntity.ok(response);
    }

    // 로그인을 위한 API
    @PostMapping("/signIn")
    public ResponseEntity<GlobalApiResponse<String>> signIn(@RequestBody MemberAuthDTO.SignIn request) {
        Member member = memberService.authenticate(request);
        String token = tokenProvider.generateToken(member.getUsername(), member.getRoles());
        GlobalApiResponse<String> response = GlobalApiResponse.toGlobalApiResponse(token);
        return ResponseEntity.ok(response);
    }
}
