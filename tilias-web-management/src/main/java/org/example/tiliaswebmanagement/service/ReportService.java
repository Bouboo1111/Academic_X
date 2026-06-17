package org.example.tiliaswebmanagement.service;

import org.example.tiliaswebmanagement.pojo.ClazzOption;
import org.example.tiliaswebmanagement.pojo.JobOption;

import java.util.List;
import java.util.Map;

/**
 * 报表业务逻辑层接口
 * 提供统计数据查询功能，用于图表展示
 */
public interface ReportService {

    /** 统计员工职位分布 */
    JobOption getEmpJobData();
    /** 统计员工性别分布 */
    List<Map<String,Object>> getEmpGenderData();
    /** 统计学生学历分布 */
    List<Map<String,Object>> getStudentDegreeData();
    /** 统计各班级学生人数 */
    ClazzOption getStudentCountData();
}
