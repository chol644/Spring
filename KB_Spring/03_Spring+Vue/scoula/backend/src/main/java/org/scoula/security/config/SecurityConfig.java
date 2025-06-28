package org.scoula.security.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.mybatis.spring.annotation.MapperScan;
import org.scoula.security.handler.JwtUsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.CorsFilter;

//동작 흐름
// -> 1. 클라이언트 요청 도착
//    2. CharacterEncodingFilter 실행 -> 요청/응답을 UTF-8로 강제 설정
//    3. CsrfFilter 실행 -> CSRF 토큰 유효성 검사
//    4. 그 외 Spring Security 필터들 실행 -> 인증, 권한 검사 등

@Configuration
@EnableWebSecurity//spring scurity의 웹 보안 기능 활성화 -> 기본적인 보아나 필터 체인과 인증 메커니즘 동작
//@EnableWebSecurity를 작성하면 WebSecurityConfigureerAdapter를 통해 세부 설정이 가능해진다.
@Log4j2
@MapperScan(basePackages = {"org.scoula.security.account.mapper"})
@ComponentScan(basePackages = {"org.scoula.security"})
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {//WebSecurityConfigureAdapter를 상속하여 보안 필터 체인 커스터마이징

    private final UserDetailsService userDetailsService;
    @Autowired
    private JwtUsernamePasswordAuthenticationFilter jwtUsernamePasswordAuthenticationFilter;

    // 문자 인코딩 필터를 CSRF 필터보다 먼저 적용
    // 보안 설정 클래스를 만들 때는 전체적인 필터 체인 순서와 우선순위를 항상 고려해야 한다.
    // 문자셋 필터
    public CharacterEncodingFilter encodingFilter() {
        //CharacterEncodingFilter는 HTTP 요청과 응답의 문자 인코딩을 설정하는 서블릿 필터이다.
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceEncoding(true);
        return encodingFilter;
    }

    //비밀번호는 반드시 암호화해서 처리해야 한다
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationManager 빈 등록
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    // cross origin 접근 허용
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    // 접근 제한 무시 경로 설정 - resource : security 를 체크하지 않을 곳들 설정
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/assets/**","/*","/api/member/**",
                // Swagger 관련 url은 보안에서 제외
                "/swagger-ui.html", "/webjars/**", "/swagger-resources/**", "/v2/api-docs"
        );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //encodingFilter()를 Spring Security의 CsrfFilter 보다 이전에 실행되도록 필터체인에 추가한다
        //CsrfFilter는 CSRF 공격 방어를 위한 토큰 검사 필터이다.
        http.addFilterBefore(encodingFilter(), CsrfFilter.class)
        // 로그인 인증 필터
        .addFilterBefore(jwtUsernamePasswordAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()// 일단 모든 접근 허용
                .anyRequest().permitAll();

        http.httpBasic().disable()// 기본HTTP인증비활성화
                .csrf().disable()// CSRF 비활성화
                .formLogin().disable() // formLogin 비활성화  관련 필터해제
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 세션 생성 모드 설정(세션 관리를 무상태성으로 진행하겠다)
    }

    // 인증 설정 담당
    // AuthenticationManagerBuilder는 인증 제공자(AuthenticationProvider)를 등록하는 빌더
    @Override
    protected void configure(AuthenticationManagerBuilder auth)throws Exception {
        log.info("configure ...........................................");

        // inMemoryAuthentication() 는 애플리케이션 구동 시점에 메모리 상에 사용자 계정을 생성한다
        // -> 별도 DB 없이 빠르게 테스트할 때 유용하다
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());


        auth.inMemoryAuthentication()
                .withUser("member")
                //.password("{noop}1234")
                .password("$2a$10$EsIMfxbJ6NuvwX7MDj4WqOYFzLU9U/lddCyn0nic5dFo3VfJYrXYC")
                .roles("MEMBER");
    }
}