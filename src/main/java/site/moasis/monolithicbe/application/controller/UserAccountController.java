package site.moasis.monolithicbe.application.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

	@PostMapping()
	public UserAccount join(@RequestBody UserAccountJoinRequestDto userAccountJoinRequestDto) {
		return userAccountService.create(userAccountJoinRequestDto);
	}
}
