package kr.co.jhta.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

	@Bean
	public SecurityFilterChain config(HttpSecurity http) throws Exception {
		return http
				//.csrf().disable() // csrf(사이트간 요청위조) 해킹 공격을 방지하는 기능을 꺼버린다. 활성화시 로그인,아웃 다 포스트여야 함
				.formLogin()
					.loginPage("/member/login")
					.usernameParameter("id")
					.passwordParameter("password")
					.loginProcessingUrl("/member/login")
					.defaultSuccessUrl("/")
					.failureUrl("/login?error=fail")
			.and()
				.logout()
					.logoutUrl("/member/logout")
					.logoutSuccessUrl("/")
					.invalidateHttpSession(true)
			.and()
				.exceptionHandling()
				.authenticationEntryPoint((request, response, authException) -> response.sendRedirect("/member/login?error=denied"))
			.and()
				.exceptionHandling()
				.accessDeniedHandler((request, response, accessDeniedException) -> response.sendRedirect("/member/login?error=forbidden"))
			.and()
				.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
