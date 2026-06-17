package org.example.tiliaswebmanagement.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.tiliaswebmanagement.Utils.AliyunOSSOperator;
import org.example.tiliaswebmanagement.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件上传控制器
 * 提供文件上传功能，支持阿里云OSS存储
 */
@Slf4j
@RestController
public class UploadController {
    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;
    /**
     * 文件上传到阿里云OSS
     * POST /upload
     * 
     * @param file 上传的文件
     * @return OSS文件的访问URL
     */
    @PostMapping("/upload")
    /**public Result upload(String name, Integer age, MultipartFile file) throws IOException {
        log.info("参数接收：{},{},{}",name,age,file);
        //保存文件
        String origin = file.getOriginalFilename();
        String extension = origin.substring(origin.lastIndexOf("."));
        String newFileName = UUID.randomUUID().toString()+extension;
        file.transferTo(new File("D:/images/"+newFileName));//
        return Result.success();
    }**/
    public Result upload(MultipartFile file) throws Exception {
        log.info("文件上传：{}",file.getOriginalFilename());
        String url = aliyunOSSOperator.upload(file.getBytes(),file.getOriginalFilename());
        log.info("文件上传OSS，url:{}",url);
        return Result.success(url);
    }
}
