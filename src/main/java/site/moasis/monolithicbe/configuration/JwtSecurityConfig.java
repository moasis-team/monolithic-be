package site.moasis.monolithicbe.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import site.moasis.monolithicbe.domain.useraccount.JwtFilter;
import site.moasis.monolithicbe.domain.useraccount.AccessTokenManager;

@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	private final AccessTokenManager accessTokenManager;

	@Override
	public void configure(HttpSecurity http) {
		JwtFilter customFilter = new JwtFilter(accessTokenManager);
		http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
	}
}