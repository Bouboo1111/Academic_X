package org.example.tiliaswebmanagement.controller;

import org.example.tiliaswebmanagement.pojo.Result;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * Session和Cookie演示控制器
 * 用于演示HTTP会话管理和Cookie的使用
 * 
 * 注意：这是教学演示代码，实际项目中使用JWT进行身份验证
 */
@Slf4j
@RestController
public class SessionController {

    //设置Cookie
    /**
     * 设置Cookie示例
     * GET /c1
     */
    @GetMapping("/c1")
    public Result cookie1(HttpServletResponse response){
        response.addCookie(new Cookie("login_username","itheima")); //设置Cookie/响应Cookie
        return Result.success();
    }

    //获取Cookie
    /**
     * 获取Cookie示例
     * GET /c2
     */
    @GetMapping("/c2")
    public Result cookie2(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("login_username")){
                System.out.println("login_username: "+cookie.getValue()); //输出name为login_username的cookie
            }
        }
        return Result.success();
    }



    /**
     * 设置Session数据
     * GET /s1
     */
    @GetMapping("/s1")
    public Result session1(HttpSession session){
        log.info("HttpSession-s1: {}", session.hashCode());

        session.setAttribute("loginUser", "tom"); //往session中存储数据
        return Result.success();
    }

    /**
     * 获取Session数据
     * GET /s2
     */
    @GetMapping("/s2")
    public Result session2(HttpSession session){
        log.info("HttpSession-s2: {}", session.hashCode());

        Object loginUser = session.getAttribute("loginUser"); //从session中获取数据
        log.info("loginUser: {}", loginUser);
        return Result.success(loginUser);
    }
}
