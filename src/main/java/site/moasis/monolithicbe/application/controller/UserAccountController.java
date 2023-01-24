package site.moasis.monolithicbe.application.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.moasis.monolithicbe.common.response.CommonResponse;
import site.moasis.monolithicbe.domain.useraccount.dto.*;
import site.moasis.monolithicbe.domain.useraccount.entity.UserAccount;
import site.moasis.monolithicbe.domain.useraccount.service.UserAccountService;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserAccountController {

	private final UserAccountService userAccountService;

	@PostMapping("/signin") // Account 인증 API
	public ResponseEntity<CommonResponse<?>> authorize(@Valid @RequestBody UserAccountDto.UserAccountSignInRequestDto userAccountSignInRequestDto) {
		UserAccountDto.UserAccountSignInResponseDto token = userAccountService.signIn(userAccountSignInRequestDto.email(), userAccountSignInRequestDto.password());
		return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.success(token, "로그인 완료"));
	}

	@PostMapping()
	public ResponseEntity<CommonResponse<?>> join(@RequestBody UserAccountDto.UserAccountJoinRequestDto userAccountJoinRequestDto) {
		UserAccount savedUserAccount = userAccountService.signUp(userAccountJoinRequestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.success(UserAccountDto.UserAccountJoinResponseDto.toDto(savedUserAccount), "회원가입 완료"));
	}
}