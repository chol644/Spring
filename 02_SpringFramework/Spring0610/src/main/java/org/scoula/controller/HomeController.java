package org.scoula.controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
@Slf4j
public class HomeController {
    @GetMapping("/")
    public String home(Model model) {
        log.info("==================> HomeController");

        model.addAttribute("title","Spring 2일차");
        return "index"; // View의 이름
    }

    /* 컨트롤러 메서드에 필요한 객체를 매개 변수로 작성해두면
    Spring Container가 Argument Resolver 이용하여
    1) 해당 Bean 존재하면 주입
    2) 없으면 새로운 Bean을 생성해서 주입
     */
}