package org.example.tiliaswebmanagement.aop;


import com.alibaba.fastjson.JSON;
import org.example.tiliaswebmanagement.Utils.CurrentHolder;
import org.example.tiliaswebmanagement.mapper.OperateLogMapper;
import org.example.tiliaswebmanagement.pojo.OperateLog;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

import static org.apache.logging.log4j.message.MapMessage.MapFormat.JSON;

@Aspect
@Component
@Slf4j
public class OperateLogAspect {

    @Autowired
    private OperateLogMapper operateLogMapper;

    @Around("@annotation(org.example.tiliaswebmanagement.anno.Log)")
    public Object recordOperateLog(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        
        Object result = null;
        try {
            result = pjp.proceed();
            return result;
        } finally {
            long endTime = System.currentTimeMillis();
            long costTime = endTime - startTime;
            
            saveOperateLog(pjp, result, costTime);
        }
    }

    private void saveOperateLog(ProceedingJoinPoint pjp, Object result, long costTime) {
        try {
            OperateLog operateLog = new OperateLog();
            
            operateLog.setOperateEmpId(getCurrentUserId());
            operateLog.setOperateTime(LocalDateTime.now());
            operateLog.setClassName(pjp.getTarget().getClass().getName());
            operateLog.setMethodName(pjp.getSignature().getName());
            operateLog.setMethodParams(com.alibaba.fastjson.JSON.toJSONString(pjp.getArgs()));
            operateLog.setReturnValue(com.alibaba.fastjson.JSON.toJSONString(result));
            operateLog.setCostTime(costTime);
            
            operateLogMapper.insert(operateLog);
            
            log.info("操作日志已保存: {}.{}", 
                    operateLog.getClassName(), 
                    operateLog.getMethodName());
        } catch (Exception e) {
            log.error("保存操作日志失败", e);
        }
    }

    private Integer getCurrentUserId() {
        try {
            Integer userId = CurrentHolder.getCurrentId();
            if (userId != null) {
                return userId;
            }
        } catch (Exception e) {
            log.warn("获取当前用户 ID 失败", e);
        }
        return null;
    }
}
