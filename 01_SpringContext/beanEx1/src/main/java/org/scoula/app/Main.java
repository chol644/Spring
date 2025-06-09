package org.scoula.app;

import org.scoula.config.ProjectConfig;
import org.scoula.domain.Parrot;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
  public static void main(String[] args) {
    var context
            = new AnnotationConfigApplicationContext(ProjectConfig.class);

    Parrot p = context.getBean(Parrot.class);
    System.out.println(p.getName()); // 출력: Koko

    Parrot p2 = context.getBean(Parrot.class);
    System.out.println(p == p2);
    
    String str = context.getBean(String.class);
    System.out.println("str = " + str);

    Integer ten = context.getBean(Integer.class);
    System.out.println("ten = " + ten);
    
  }
}