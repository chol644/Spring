package org.scoula.security.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;


@Configuration
@EnableWebSecurity  // Spring Security 활성화
@Slf4j
@MapperScan(basePackages = {"org.scoula.security.account.mapper"})  // 매퍼 스캔 설정

@ComponentScan(basePackages = {"org.scoula.security"})    // 서비스 클래스 스캔
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /* 필드  추가 */
    private final UserDetailsService userDetailsService;   // CustomUserDetailsService 주입


    // PasswordEncoder(BCryptPasswordEncoder) Bean 등록 설정
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // BCrypt 해시 함수 사용
    }


    // 문자셋 필터 메서드
    public CharacterEncodingFilter encodingFilter() {
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");           // UTF-8 인코딩 설정
        encodingFilter.setForceEncoding(true);         // 강제 인코딩 적용
        return encodingFilter;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // CSRF 필터보다 앞에 인코딩 필터 추가
        // - CSRF 필터는 Spring Security 환경에서 기본적으로 활성화 되어있음!
        http.addFilterBefore(encodingFilter(), CsrfFilter.class);


        // 경로별 접근 권한 설정
        http.authorizeRequests()
                // 모든 사용자 허용
                .antMatchers("/security/all").permitAll()

                // ADMIN만 허용
                .antMatchers("/security/admin").access("hasRole('ROLE_ADMIN')")

                // MEMBER, ADMIN 중 하나라도 만족하면 허용
                .antMatchers("/security/member").access("hasAnyRole('ROLE_MEMBER', 'ROLE_ADMIN')")

                // 로그인 사용자에게 허용 (예시)
                .antMatchers("/board/write", "/board/modify", "/board/delete").authenticated();



        // 폼 기반 로그인 활성화 (모든 설정이 기본값)
        http.formLogin()
                // 커스텀 로그인 페이지 설정 (Security 기본 로그인 사용 X, 사용자가 만든 로그인 페이지 사용!)
                .loginPage("/security/login")           // 로그인 폼 GET 요청 URL
                .loginProcessingUrl("/security/login")  // 로그인 처리 POST 요청 URL
                .defaultSuccessUrl("/");                // 로그인 성공 시 리다이렉트 URL



        // 로그아웃 설정
        http.logout()
                .logoutUrl("/security/logout")                    // POST: 로그아웃 요청 URL
                .invalidateHttpSession(true)                      // 세션 무효화
                .deleteCookies("remember-me", "JSESSION-ID")      // 쿠키 삭제
                .logoutSuccessUrl("/security/logout");            // GET: 로그아웃 완료 후 이동할 페이지
    }




    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        log.info("configure .........................................");

        // UserDetailsService와 PasswordEncoder 설정
        auth.userDetailsService(userDetailsService)  // 커스텀 서비스 사용
                .passwordEncoder(passwordEncoder()); // BCrypt 암호화 사용

    }
}