package org.example.tiliaswebmanagement.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.tiliaswebmanagement.pojo.ClazzOption;
import org.example.tiliaswebmanagement.pojo.JobOption;
import org.example.tiliaswebmanagement.pojo.Result;
import org.example.tiliaswebmanagement.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 报表统计控制器
 * 提供各类数据统计接口，用于前端图表展示
 */
@RestController
@RequestMapping("/report")// 把带“/report"后缀 的请求路径映射到 这个类
@Slf4j
public class ReportController {
    @Autowired
    private ReportService reportService;
    /**
     * 统计员工职位分布
     * GET /report/empJobData
     */
    @GetMapping("/empJobData")
    public Result getEmpJobData(){
        log.info("统计员工职位人数");
        JobOption jobOption =reportService.getEmpJobData();
        return Result.success(jobOption);
    }

    /**
     * 统计员工性别分布
     * GET /report/empGenderData
     */
    @GetMapping("/empGenderData")
    public Result getEmpGenderData(){
        log.info("统计员工性别人数");
        List<Map<String,Object>> genderList =reportService.getEmpGenderData();
        return Result.success(genderList);
    }
    /**
     * 统计学生学历分布
     * GET /report/studentDegreeData
     */
    @GetMapping("/studentDegreeData")
    public Result getStudentDegreeData(){
        log.info("统计学生学历人数");
        List<Map<String,Object>> degreeList =reportService.getStudentDegreeData();
        return Result.success(degreeList);
    }

    /**
     * 统计各班级学生人数
     * GET /report/studentCountData
     */
    @GetMapping("/studentCountData")
    public Result getStudentCountData(){
        log.info("统计班级人数");
        ClazzOption clazzOption =reportService.getStudentCountData();
        return Result.success(clazzOption);
    }

}
