package org.example.tiliaswebmanagement.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Select;
import org.example.tiliaswebmanagement.pojo.Clazz;
import org.example.tiliaswebmanagement.pojo.ClazzQueryParam;
import org.example.tiliaswebmanagement.pojo.PageResult;
import org.example.tiliaswebmanagement.pojo.Result;
import org.example.tiliaswebmanagement.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 班级管理控制器
 * 提供班级信息的增删改查、分页查询等RESTful API接口
 */
@RestController
@RequestMapping("/clazzs")
@Slf4j
public class ClazzController {
    @Autowired
    private ClazzService clazzService;
    /**
     * 分页查询班级列表
     * GET /clazzs?page=1&pageSize=10
     */
    @GetMapping
    public Result getQuery(ClazzQueryParam  param){
        log.info("分页查询班级", param);
        PageResult<Clazz> pageResult = clazzService.getQuery(param);
        return Result.success(pageResult);
    }
    /**
     * 查询所有班级（不分页）
     * GET /clazzs/list
     */
    @GetMapping("/list")
    public Result getAll(){
        log.info("查询所有班级");
        List<Clazz> clazzList =clazzService.getAll();
        return Result.success(clazzList);
    }

    /**
     * 新增班级
     * POST /clazzs
     */
    @PostMapping
    public Result addClazz(@RequestBody Clazz clazz){
        log.info("添加班级", clazz);
        clazzService.addClazz(clazz);
        return Result.success();
    }

    /**
     * 根据ID查询班级
     * GET /clazzs/1
     */
    @GetMapping("/{id}")
    public Result selectById (@PathVariable Integer id){
        log.info("查询班级", id);
        Clazz clazz = clazzService.selectById(id);

        return Result.success(clazz);
    }

    /**
     * 删除班级
     * DELETE /clazzs/1
     */
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Integer id){
        log.info("删除班级id为："+ id+"的班级信息");
        clazzService.deleteById(id);
        return Result.success();
    }

    /**
     * 更新班级信息
     * PUT /clazzs
     */
    @PutMapping
    public Result update(@RequestBody Clazz clazz){
        log.info("修改班级信息：{}", clazz);
        clazzService.update(clazz);
        return Result.success();
    }
}
