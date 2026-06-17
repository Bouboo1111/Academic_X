package org.example.tiliaswebmanagement.filter;

import com.sun.net.httpserver.HttpsServer;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.tiliaswebmanagement.Utils.CurrentHolder;
import org.example.tiliaswebmanagement.Utils.JwtUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Slf4j
@WebFilter(urlPatterns = "/*")
public class TokenFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;//获取请求
        HttpServletResponse response = (HttpServletResponse) servletResponse;//获取响应
        //1.获取请求的路径
        String requestURI = request.getRequestURI();
        //2.判断是否是登录请求  放行
        if(requestURI.contains("login")){
            log.info("登录请求");
            filterChain.doFilter(request,response);
            return;
        }
        //3.获取请求头的token
        String token = request.getHeader("token");
        //4.判断token是否合法,存在，不合法返回401
        if(token == null|| token.isEmpty()){
            log.info("令牌token为空");
            response.setStatus(401);
            return;
        }
        //5.校验令牌，校验失败返回401
        try{
            Claims claims =JwtUtils.parseJwt(token);//解析token
            //解析令牌获取ID
            // 应该从自定义 claims 中获取
            Integer userId = claims.get("id", Integer.class);

            CurrentHolder.setCurrentId(userId);
            log.info("当前用户ID为：{}",userId);
        }catch (Exception e){
            log.info("令牌token解析失败");
            response.setStatus(401);
            return;
        }
        //6.放行
        log.info("令牌token合法,放行");
        filterChain.doFilter(request,response);

        //7.删除threadLocal中的数据
        CurrentHolder.remove();
    }
}
