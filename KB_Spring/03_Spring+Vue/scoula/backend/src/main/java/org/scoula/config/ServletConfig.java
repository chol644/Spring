package org.scoula.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * 🌐 Spring MVC Web Context 설정 클래스
 * - Spring MVC의 웹 계층(Presentation Layer)을 담당하는 컨텍스트 설정 클래스
 * - 사용자 요청 처리와 관련된 모든 웹 컴포넌트들을 관리하고 설정함
 * <p>
 * 📋 주요 어노테이션 설명:
 *
 * @EnableWebMvc - Spring MVC 기능을 활성화하는 핵심 어노테이션
 * - DispatcherServlet, HandlerMapping, HandlerAdapter 등 MVC 인프라 자동 설정
 * - JSON/XML 변환, 데이터 바인딩, 유효성 검증 등 웹 기능 활성화
 * - <mvc:annotation-driven />의 자바 설정 버전
 * @ComponentScan - 지정된 패키지에서 Spring 컴포넌트를 자동으로 스캔하여 빈으로 등록
 * - 현재 설정: "org.scoula.controller" 패키지의 @Controller 클래스들을 스캔
 * <p>
 * WebMvcConfigurer 인터페이스 구현
 * - Spring MVC의 세부 설정을 커스터마이징할 수 있는 인터페이스
 * - 필요한 메서드만 오버라이드하여 선택적 설정 가능
 */
@EnableWebMvc
@ComponentScan(basePackages = {
        "org.scoula.exception",
        "org.scoula.controller",
        "org.scoula.board.controller"
}) // Spring MVC용 컴포넌트 등록을 위한 스캔 패키지
public class ServletConfig implements WebMvcConfigurer {

    /**
     * 📁 정적 자원 핸들러 설정
     * - 웹 애플리케이션의 정적 자원(CSS, JavaScript, 이미지, 폰트 등)에 대한 URL 매핑과 물리적 경로를 설정
     * <p>
     * 🔧 설정 내용:
     * - URL 패턴: /resources/** (예: /resources/css/style.css)
     * - 물리적 위치: /resources/ (webapp/resources/ 디렉토리)
     * <p>
     * 📁 추천 디렉토리 구조:
     * webapp/resources/
     * ├── css/          (스타일시트)
     * ├── js/           (자바스크립트)
     * ├── images/       (이미지 파일)
     * ├── fonts/        (웹 폰트)
     * └── libs/         (외부 라이브러리)
     */

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/") // 요청 url
                .setViewName("forward:/resources/index.html");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/resources/**") // URL이 /resources/로 시작하는 모든 경로
                .addResourceLocations("/resources/"); // webapp/resources/ 경로로 매핑
        registry.addResourceHandler("/assets/**") // 정적 파일 경로 해석
                .addResourceLocations("/resources/assets/");

        // Swagger UI 리소스를 위한 핸들러 설정
        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        // Swagger WebJar 리소스 설정
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        // Swagger 리소스 설정
        registry.addResourceHandler("/swagger-resources/**")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/v2/api-docs")
                .addResourceLocations("classpath:/META-INF/resources/");
    }

    // 📍 Servlet 3.0 파일 업로드 설정
    @Bean
    public MultipartResolver multipartResolver() {
        StandardServletMultipartResolver resolver =
                new StandardServletMultipartResolver();
        return resolver;
    }


}