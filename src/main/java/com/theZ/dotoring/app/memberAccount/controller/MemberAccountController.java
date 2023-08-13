package com.theZ.dotoring.app.memberAccount.controller;

import com.theZ.dotoring.app.memberAccount.dto.*;
import com.theZ.dotoring.app.memberAccount.service.MemberAccountService;
import com.theZ.dotoring.app.memberAccount.service.MemberEmailService;
import com.theZ.dotoring.app.mento.dto.MentoNicknameRequestDTO;
import com.theZ.dotoring.common.ApiResponse;
import com.theZ.dotoring.common.ApiResponseGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberAccountController {

    private final MemberAccountService memberAccountService;
    private final MemberEmailService memberEmailService;

    @PostMapping("/member/valid-loginId")
    public ApiResponse<ApiResponse.CustomBody<Void>> validateMemberLoginId(@RequestBody @Valid MemberLoginIdRequestDTO memberLoginIdRequestDTO){
        memberAccountService.validateLoginId(memberLoginIdRequestDTO.getLoginId());
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @PostMapping("/member/valid-code")
    public ApiResponse<ApiResponse.CustomBody<Void>> validateMemberEmailCode(@RequestBody @Valid MemberEmailCodeRequestDTO memberEmailCodeRequestDTO){
        memberEmailService.validateCode(memberEmailCodeRequestDTO.getEmailVerificationCode(),memberEmailCodeRequestDTO.getEmail());
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @GetMapping("/member/code")
    public ApiResponse<ApiResponse.CustomBody<MemberEmailCodeResponseDTO>> sendEmail(@Valid MemberEmailRequestDTO memberEmailRequestDTO) throws MessagingException {
        MemberEmailCodeResponseDTO memberEmailCodeResponseDTO = memberEmailService.sendEmail(memberEmailRequestDTO);
        return ApiResponseGenerator.success(memberEmailCodeResponseDTO,HttpStatus.OK);
    }


}