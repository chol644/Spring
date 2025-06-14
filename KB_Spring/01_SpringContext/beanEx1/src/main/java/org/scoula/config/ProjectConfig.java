package org.scoula.config;

import org.scoula.domain.Parrot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @Configuration
// - Spring 설정 정보를 담는 클래스임을 표시하는 어노테이션
// - Spring Container 초기화 시점에 해당 클래스 내용이 해석됨
@Configuration
public class ProjectConfig {

  @Bean // 반환되는 객체를 Bean 등록
  Parrot parrot() {
    var p = new Parrot();
    p.setName("Koko");
    return p;
  }

  @Bean
  String hello() {
    return "Hello";
  }

  @Bean
  Integer ten() {
    return 10;
  }
}
