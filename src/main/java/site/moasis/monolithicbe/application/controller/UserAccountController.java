package site.moasis.monolithicbe.application.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.moasis.monolithicbe.common.response.CommonResponse;
import site.moasis.monolithicbe.domain.useraccount.dto.UserAccountJoinRequestDto;
import site.moasis.monolithicbe.domain.useraccount.dto.UserAccountJoinResponseDto;
import site.moasis.monolithicbe.domain.useraccount.dto.UserAccountSignInRequestDto;
import site.moasis.monolithicbe.domain.useraccount.dto.UserAccountSignInResponseDto;
import site.moasis.monolithicbe.domain.useraccount.entity.UserAccount;
import site.moasis.monolithicbe.domain.useraccount.service.UserAccountService;

import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserAccountController {

	private final UserAccountService userAccountService;


	@PostMapping("/signin") // Account 인증 API
	public ResponseEntity<CommonResponse<?>> authorize(@Valid @RequestBody UserAccountSignInRequestDto userAccountSignInRequestDto) {
		UserAccountSignInResponseDto token = userAccountService.signIn(userAccountSignInRequestDto.email(), userAccountSignInRequestDto.password());
		return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.success(token, "로그인완료"));
	}

	// GET /users
	// 토큰이 잘 작동하는지 확인하는 테스트용 라우터
	// 헤더에 토큰을 담았을때 이 라우터에 잘 들어오게 되면 토큰이 잘 작동하는 것
	// 편의를 위한 것이니 없어도 상관없음.
	@GetMapping()
	void authTest(){
		System.out.println("succ가ess");
	}

	@PostMapping()
	public ResponseEntity<CommonResponse<UserAccountJoinResponseDto>> join(@RequestBody UserAccountJoinRequestDto userAccountJoinRequestDto) {
		UserAccount savedUserAccount = userAccountService.signUp(userAccountJoinRequestDto);
		return ResponseEntity.created(URI.create("/users/" + savedUserAccount.getId())).body(
				CommonResponse.success(UserAccountJoinResponseDto.toDto(savedUserAccount), "User Created")
		);
	}
}
