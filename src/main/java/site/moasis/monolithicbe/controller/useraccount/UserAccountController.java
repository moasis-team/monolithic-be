package site.moasis.monolithicbe.controller.useraccount;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.moasis.monolithicbe.controller.common.CommonResponse;
import site.moasis.monolithicbe.domain.useraccount.TokenManager;
import site.moasis.monolithicbe.domain.useraccount.entity.UserAccount;
import site.moasis.monolithicbe.domain.useraccount.service.UserAccountReadService;
import site.moasis.monolithicbe.domain.useraccount.service.UserAccountWriteService;

import java.util.HashMap;

import static site.moasis.monolithicbe.domain.useraccount.dto.UserAccountDto.*;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserAccountController {

	private final UserAccountReadService userAccountReadService;
	private final UserAccountWriteService userAccountWriteService;

	@PostMapping("/signin")
	public ResponseEntity<CommonResponse<?>> signIn(@Valid @RequestBody UserAccountSignInRequestDto userAccountSignInRequestDto) {
		UserAccountSignInResponseDto dto = userAccountWriteService.signIn(userAccountSignInRequestDto.email(), userAccountSignInRequestDto.password());
		HashMap<String, String> tokenObject = new HashMap<>();
		tokenObject.put("accessToken", dto.accessToken());

		ResponseCookie responseCookie = ResponseCookie.from("refreshToken", dto.refreshToken())
				.httpOnly(true)
				.secure(true)
				.path("/")
				.maxAge(60)
				.build();

		return ResponseEntity.status(HttpStatus.OK)
				.header(HttpHeaders.SET_COOKIE, responseCookie.toString())
				.body(CommonResponse.success(tokenObject, "로그인 완료"));
	}

	@PostMapping()
	public ResponseEntity<CommonResponse<?>> join(@RequestBody UserAccountJoinRequestDto userAccountJoinRequestDto) {
		UserAccount savedUserAccount = userAccountWriteService.signUp(userAccountJoinRequestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.success(UserAccountJoinResponseDto.toDto(savedUserAccount), "회원가입 완료"));
	}

	@PatchMapping("/token")
	public ResponseEntity<CommonResponse<?>> reIssueToken(@RequestHeader HttpHeaders headers, @CookieValue(name = "refreshToken") String refreshToken){
		String accessToken = TokenManager.getTokenFromHeader(headers);
		ReissueTokenResponseDto dto = this.userAccountWriteService.ReissueToken(accessToken, refreshToken);

		HashMap<String, String> tokenObject = new HashMap<>();
		tokenObject.put("accessToken", dto.accessToken());

		return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.success(tokenObject, "success"));
	}
}