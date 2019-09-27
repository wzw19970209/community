package spring_boot.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Greeting {
    @GetMapping("/")
    public String greeting(@RequestParam(name = "name",required = false,defaultValue = "")String name, Model model){
        model.addAttribute("name",name);
        System.out.println("controller执行了");
        return "index";
    }
}
