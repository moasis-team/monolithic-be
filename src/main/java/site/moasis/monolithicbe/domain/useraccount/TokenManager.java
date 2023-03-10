package site.moasis.monolithicbe.domain.useraccount;

import com.google.common.net.HttpHeaders;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class TokenManager {

	private static final String AUTHORITIES_KEY = "auth";
	private final Logger logger = LoggerFactory.getLogger(AccessTokenManager.class);
	private final long tokenValidityInMilliseconds;
	private final Key key;


	public TokenManager(String tokenSecret, Long tokenValidityInSeconds)
	{
		this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
		byte[] keyBytes = Decoders.BASE64.decode(tokenSecret);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}


	public String createToken(Authentication authentication) {
		String authorities = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));

		long now = (new Date()).getTime();
		Date validity = new Date(now + this.tokenValidityInMilliseconds);
		return Jwts.builder()
				.setSubject(authentication.getName())
				.claim(AUTHORITIES_KEY, authorities)
				.signWith(key, SignatureAlgorithm.HS512)
				.setExpiration(validity)
				.compact();
	}

	public Authentication getAuthentication(String token) {

		Claims claims = Jwts
				.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();

		Collection<? extends GrantedAuthority> authorities =
				Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
						.map(SimpleGrantedAuthority::new)
						.collect(Collectors.toList());

		User principal = new User(claims.getSubject(), "", authorities);

		return new UsernamePasswordAuthenticationToken(principal, token, authorities);
	}

	public boolean validateToken(String token) {
		try {
			Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
			return true;
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			logger.info("????????? JWT ???????????????.");
		} catch (ExpiredJwtException e) {
			logger.info("????????? JWT ???????????????.");
		} catch (UnsupportedJwtException e) {
			logger.info("???????????? ?????? JWT ???????????????.");
		} catch (IllegalArgumentException e) {
			logger.info("JWT ????????? ?????????????????????.");
		}
		return false;
	}

	public static String getTokenFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

	public static String getTokenFromHeader(org.springframework.http.HttpHeaders headers) {
		String bearerToken = Objects.requireNonNull(headers.get(org.springframework.http.HttpHeaders.AUTHORIZATION)).get(0);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
}
