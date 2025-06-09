package org.scoula.app;

import org.scoula.beans.Person2;
import org.scoula.beans.Person3;
import org.scoula.config.ProjectConfig2;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main2 {

  public static void main(String[] args) {
    var context = new AnnotationConfigApplicationContext(ProjectConfig2.class);

    Person2 p = context.getBean(Person2.class);

    System.out.println("Person's name: " + p.getName());
    System.out.println("Person's parrot: " + p.getParrot());

    System.out.println("------------------------------");
    System.out.println("생성자 방식 @Autowired 확인");

    Person3 p3 = context.getBean(Person3.class);
    System.out.println("Person3.getParrot() : " + p3.getParrot());



  }

}