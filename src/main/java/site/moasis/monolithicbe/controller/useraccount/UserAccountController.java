package site.moasis.monolithicbe.controller.useraccount;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.moasis.monolithicbe.controller.common.CommonResponse;
import site.moasis.monolithicbe.domain.useraccount.entity.UserAccount;
import site.moasis.monolithicbe.domain.useraccount.service.UserAccountReadService;
import site.moasis.monolithicbe.domain.useraccount.service.UserAccountWriteService;
import site.moasis.monolithicbe.domain.useraccount.service.UserEmailService;

import static site.moasis.monolithicbe.domain.useraccount.dto.UserAccountDto.*;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserAccountController {

	private final UserAccountReadService userAccountReadService;
	private final UserAccountWriteService userAccountWriteService;
	private final UserEmailService userEmailService;

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

	@GetMapping("/auth/code")
	public ResponseEntity<CommonResponse<?>> getCode(@NotBlank String email) {
		userEmailService.sendCode(email);
		return ResponseEntity.ok(CommonResponse.success(email));
	}

	@PostMapping("/auth/certificate")
	public ResponseEntity<CommonResponse<?>> certificateCode(@RequestBody EmailCertificationRequestDto emailCertificationRequestDto) {
		userEmailService.certificate(emailCertificationRequestDto.email(), emailCertificationRequestDto.code());
		return ResponseEntity.ok(CommonResponse.success("이메일 인증 완료"));
	}
}