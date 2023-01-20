package site.moasis.monolithicbe.application.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.moasis.monolithicbe.domain.useraccount.ResponseSignIn;
import site.moasis.monolithicbe.domain.useraccount.JwtFilter;
import site.moasis.monolithicbe.domain.useraccount.dto.UserAccountJoinRequestDto;
import site.moasis.monolithicbe.domain.useraccount.dto.UserAccountSignInRequestDto;
import site.moasis.monolithicbe.domain.useraccount.entity.UserAccount;
import site.moasis.monolithicbe.domain.useraccount.service.UserAccountService;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserAccountController {

	private final UserAccountService userAccountService;


	@PostMapping("/signin") // Account 인증 API
	public ResponseEntity<ResponseSignIn> authorize(@Valid @RequestBody UserAccountSignInRequestDto userAccountSignInRequestDto) {
		ResponseSignIn token = userAccountService.signIn(userAccountSignInRequestDto.email(), userAccountSignInRequestDto.password());
		// response header 에도 넣고 응답 객체에도 넣는다.
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + token.getAccessToken());
		return new ResponseEntity<>(token, httpHeaders, HttpStatus.OK);
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
	public UserAccount join(@RequestBody UserAccountJoinRequestDto userAccountJoinRequestDto) {
		return userAccountService.create(userAccountJoinRequestDto);
	}
}
