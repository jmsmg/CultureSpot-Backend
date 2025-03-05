package com.culturespot.culturespotdomain.auth.security.config;

import com.culturespot.culturespotdomain.auth.security.jwt.LoginFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final AuthenticationConfiguration authenticationConfiguration;

  public SecurityConfig(AuthenticationConfiguration authenticationConfiguration) {
    this.authenticationConfiguration = authenticationConfiguration;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable()) // CSRF 비활성화 (JWT 사용 시 필요)
        .formLogin(form -> form.disable()) // 폼 로그인 비활성화
        .httpBasic(basic -> basic.disable()) // HTTP 기본 인증 비활성화
        .authorizeHttpRequests((auth) -> auth
                .requestMatchers("/", "api/v1/auth/signup", "api/v1/auth/signin").permitAll()
                .anyRequest().authenticated())
        .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration)), UsernamePasswordAuthenticationFilter.class)
        .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // 세션 사용 안 함 (JWT 사용)
    return http.build();
  }
}
