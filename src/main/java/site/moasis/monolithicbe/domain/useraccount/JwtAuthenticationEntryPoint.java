package site.moasis.monolithicbe.domain.useraccount;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

// 인증 실패 시
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request,
	                     HttpServletResponse response,
	                     AuthenticationException authException) throws IOException {

		// 유효한 자격증명을 제공하지 않고 접근하려 할때 401(인증 실패)
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	}
}