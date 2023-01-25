package site.moasis.monolithicbe.application.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.moasis.monolithicbe.common.response.CommonResponse;
import site.moasis.monolithicbe.domain.useraccount.entity.UserAccount;
import site.moasis.monolithicbe.domain.useraccount.service.UserAccountReadService;
import site.moasis.monolithicbe.domain.useraccount.service.UserAccountWriteService;

import static site.moasis.monolithicbe.domain.useraccount.dto.UserAccountDto.*;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserAccountController {

	private final UserAccountReadService userAccountReadService;
	private final UserAccountWriteService userAccountWriteService;

	@PostMapping("/signin")
	public ResponseEntity<CommonResponse<?>> authorize(@Valid @RequestBody UserAccountSignInRequestDto userAccountSignInRequestDto) {
		UserAccountSignInResponseDto token = userAccountReadService.signIn(userAccountSignInRequestDto.email(), userAccountSignInRequestDto.password());
		return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.success(token, "로그인 완료"));
	}

	@PostMapping()
	public ResponseEntity<CommonResponse<?>> join(@RequestBody UserAccountJoinRequestDto userAccountJoinRequestDto) {
		UserAccount savedUserAccount = userAccountWriteService.signUp(userAccountJoinRequestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.success(UserAccountJoinResponseDto.toDto(savedUserAccount), "회원가입 완료"));
	}
}