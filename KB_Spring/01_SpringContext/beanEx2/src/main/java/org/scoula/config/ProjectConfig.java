package org.scoula.config;

import org.scoula.beans.Parrot;
import org.scoula.beans.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {

  @Bean
  public Parrot parrot() {
    Parrot p = new Parrot();
    p.setName("Koko");
    return p;
  }

  /**
   * Person Bean 등록 메서드
   * 
   * @param parrot // Spring Container에 등록된 Bean 중 같은 타입을 자동으로 주입
   * @return
   */
  @Bean
  public Person person(Parrot parrot) {
    Person p = new Person();
    p.setName("Ella");

    // 위에 작성된 parrot() 메서드 호출
    // -> 새로 만들어진 Parrot 객체가 반환된걸까? -> Nope!
    // -> 처음 만들어지고 Bean으로 등록된 Parrot 반환!
    // p.setParrot(parrot()); // 권장하지 않는 작성법!
    

    // 권장하는 방법
    p.setParrot(parrot); // 자동 주입된 Bean을 Person 필드에 세팅

    return p;
  }
}
