package site.moasis.monolithicbe.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import site.moasis.monolithicbe.domain.useraccount.AccessTokenManager;
import site.moasis.monolithicbe.domain.useraccount.JwtAccessDeniedHandler;
import site.moasis.monolithicbe.domain.useraccount.JwtAuthenticationEntryPoint;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig{
	private final CorsFilter corsFilter;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	private final AccessTokenManager accessTokenManager;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf().disable()

			.addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
			.exceptionHandling()
			.authenticationEntryPoint(jwtAuthenticationEntryPoint)
			.accessDeniedHandler(jwtAccessDeniedHandler)

			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

			.and().authorizeHttpRequests()
			.requestMatchers(HttpMethod.POST,"/users").permitAll()
			.requestMatchers("/users/signin").permitAll()
			.anyRequest().permitAll()

			.and()
			.apply(new JwtSecurityConfig(accessTokenManager));

		return http.build();
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
