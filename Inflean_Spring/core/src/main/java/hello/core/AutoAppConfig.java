package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan (
        excludeFilters = @ComponentScan.Filter (type= FilterType.ANNOTATION, classes = Configuration.class)
)// @Component 붙은 클래스를 찾아서 자동으로 Spring bean 등록. excludeFilters = bean으로 등록 제외할 것 지정
public class AutoAppConfig {


}
