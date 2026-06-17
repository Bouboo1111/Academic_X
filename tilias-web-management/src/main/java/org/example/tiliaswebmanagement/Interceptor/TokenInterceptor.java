package org.example.tiliaswebmanagement.Interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.tiliaswebmanagement.Utils.JwtUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
@Slf4j
//@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    // 在业务处理器处理请求之前被调用
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.获取请求的路径
        String requestURI = request.getRequestURI();
        //2.判断是否是登录请求  放行
        if(requestURI.contains("login")){
            log.info("登录请求");
            return true;
        }
        //3.获取请求头的token
        String token = request.getHeader("token");
        //4.判断token是否合法,存在，不合法返回401
        if(token == null|| token.isEmpty()){
            log.info("令牌token为空");
            response.setStatus(401);
            return  false;
        }
        //5.校验令牌，校验失败返回401
        try{

            //解析令牌获取ID
            Integer id = JwtUtils.getClaimFromJwt(token, "id", Integer.class);
            log.info("用户id:{}",id);
        }catch (Exception e){
            log.info("令牌token解析失败");
            response.setStatus(401);
            return  false;
        }
        //6.放行
        log.info("令牌token合法,放行");
        return true;
    }
}
