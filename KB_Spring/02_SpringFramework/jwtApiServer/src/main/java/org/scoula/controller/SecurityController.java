package org.scoula.controller;

import lombok.extern.slf4j.Slf4j;
import org.scoula.security.account.domain.CustomUser;
import org.scoula.security.account.domain.MemberVO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collection;

@Slf4j
@RequestMapping("/api/security")
@RestController
public class SecurityController {

  @GetMapping("/all")      // 모든 사용자 접근 가능
  public ResponseEntity<String> doAll() {
    log.info("do all can access everybody");
    return ResponseEntity.ok("All can access everybody");
  }

  @GetMapping("/member")
  public ResponseEntity<String> doMember(Authentication authentication) {
    UserDetails userDetails = (UserDetails)authentication.getPrincipal();
    log.info("username = " + userDetails.getUsername());
    return ResponseEntity.ok(userDetails.getUsername());
  }


//  @GetMapping("/admin")    // ADMIN 권한만 접근 가능
//  public void doAdmin() {
//    log.info("admin only");
//  }

  @GetMapping("/admin")
  public ResponseEntity<MemberVO> doAdmin(@AuthenticationPrincipal CustomUser customUser) {
    MemberVO member = customUser.getMember();
    log.info("username = " + member);
    return ResponseEntity.ok(member);
  }

  @GetMapping("/login")    // 로그인 요청 매핑
  public void login() {
    log.info("login page");
  }

  // 로그아웃
  @GetMapping("/logout")
  public void logout() {
    log.info("logout page");
  }
}