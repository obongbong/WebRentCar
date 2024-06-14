package com.mysite.rentcar;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String main() {
        return "main";
    }

    @GetMapping("/users")  // 변경된 경로
    public String members() {
        return "member_list";
    }

    @GetMapping("/cars")
    public String cars() {
        return "car_list";
    }

    @GetMapping("/reservations")
    public String reservations() {
        return "reservation_list";
    }
}
