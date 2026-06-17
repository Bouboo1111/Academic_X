package org.example.tiliaswebmanagement.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.tiliaswebmanagement.pojo.Result;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)//捕获所有异常
    public Result handleException(Exception e) {
        log.error("服务器发生异常：{}", e.getMessage());
        return Result.error("服务器发生异常");
    }

    @ExceptionHandler
    public Result handleDuplicateKeyException(DuplicateKeyException e) {
        log.error("程序出错了！");
        String msg = e.getMessage();
        int i = msg.indexOf("Duplicate entry");
        String errMsg = msg.substring(i);
        String[] arr = errMsg.split(" ");
        return Result.error("【" + arr[2] + "】已存在");
    }
}
