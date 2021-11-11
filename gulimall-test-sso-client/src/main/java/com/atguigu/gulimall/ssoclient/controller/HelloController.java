package com.atguigu.gulimall.ssoclient.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HelloController {

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {

        return "hello";
    }

    /**
     * 检验单点登录
     * @param model
     * @param session
     * @param token
     * @return
     */
    @GetMapping(value = "/employees")
    public String employees(Model model, HttpSession session, @RequestParam(value = "token", required = false) String token) {

        if (!StringUtils.isEmpty(token)) {
            RestTemplate restTemplate=new RestTemplate();
            ResponseEntity<String> forEntity = restTemplate.getForEntity("http://sso.mroldx.cn:8082/userinfo?token=" + token, String.class);
            String body = forEntity.getBody();

            session.setAttribute("loginUser", body);
        }
        Object loginUser = session.getAttribute("loginUser");

        if (loginUser == null) {

            return "redirect:" + "http://sso.mroldx.cn:8082/login.html"+"?redirect_url=http://localhost:8083/employees";
        } else {


            List<String> emps = new ArrayList<>();

            emps.add("张三");
            emps.add("李四");

            model.addAttribute("emps", emps);
            return "employees";
        }
    }

}
