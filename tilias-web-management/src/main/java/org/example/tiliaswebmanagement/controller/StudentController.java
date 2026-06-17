package org.example.tiliaswebmanagement.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.tiliaswebmanagement.pojo.PageResult;
import org.example.tiliaswebmanagement.pojo.Result;
import org.example.tiliaswebmanagement.pojo.Student;
import org.example.tiliaswebmanagement.pojo.StudentQueryParam;
import org.example.tiliaswebmanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 学生管理控制器
 * 提供学生信息的增删改查、分页查询、操行分管理等RESTful API接口
 */
@Slf4j
@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;
    /**
     * 分页查询学生列表
     * GET /students?page=1&pageSize=10
     */
    @GetMapping
    public Result getQuery(StudentQueryParam  param){
        log.info("分页查询学生信息", param);
        PageResult<Student> pageResult =studentService.getQuery(param);
        return Result.success(pageResult);
    }
    /**
     * 根据ID查询学生信息
     * GET /students/1
     */
    @GetMapping("/{id}")
    public Result getSelectById(@PathVariable Integer id){
        log.info("查询学生信息", id);
        Student student =studentService.getSelectById(id);
        return Result.success(student);
    }

    /**
     * 新增学生
     * POST /students
     */
    @PostMapping
    public Result insertStudent(@RequestBody Student student){
        studentService.insertStudent(student);
        return Result.success();
    }

    /**
     * 批量删除学生
     * DELETE /students/1,2,3
     */
    @DeleteMapping("/{ids}")
    public Result deleteStudent(@PathVariable List< Integer> ids){
        studentService.deleteStudent(ids);
        return Result.success();
    }

    /**
     * 更新学生信息
     * PUT /students
     */
    @PutMapping
    public Result update(@RequestBody Student student){
        log.info("修改学生信息：{}", student);
        studentService.update(student);
        return Result.success();
    }

    /**
     * 更新学生操行分（加分/扣分）
     * PUT /students/violation/1/5 （给学生ID为1的加5分）
     * PUT /students/violation/1/-3 （给学生ID为1的扣3分）
     */
    @PutMapping("/violation/{id}/{score}")
    public Result updateScore(@PathVariable Integer id, @PathVariable Integer score){
        studentService.updateScore(id, score);
        return Result.success();
    }
}
