package com.culturespot.culturespotdomain.auth.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;

  public LoginFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  protected String obtainUsername(HttpServletRequest request) {
    return request.getParameter("email");
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
  {
    String email = obtainUsername(request);
    String password = obtainPassword(request);

    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password);

    return authenticationManager.authenticate(authToken);
  }

  // 로그인 성공시 실행 (JWT 발급)
  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
    // TODO
  }

  // 로그인 실패시 실행
  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
    // TODO
  }
}
