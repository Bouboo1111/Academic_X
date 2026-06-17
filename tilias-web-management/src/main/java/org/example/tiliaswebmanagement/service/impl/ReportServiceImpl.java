package org.example.tiliaswebmanagement.service.impl;

import org.example.tiliaswebmanagement.mapper.EmpMapper;
import org.example.tiliaswebmanagement.mapper.StudentMapper;
import org.example.tiliaswebmanagement.pojo.ClazzOption;
import org.example.tiliaswebmanagement.pojo.JobOption;
import org.example.tiliaswebmanagement.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 报表业务逻辑层实现类
 * 实现各类统计数据的查询和格式化，用于前端图表展示
 */
@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private StudentMapper studentMapper;
    /**
     * 统计员工职位分布
     * 将数据库返回的List<Map>转换为JobOption对象，便于前端图表使用
     */
    @Override
    public JobOption getEmpJobData(){
        List<Map<String,Object>> list = empMapper.getEmpJobData();
        List<Object> jobList = list.stream().map(dataMap-> dataMap.get("pos")).toList();
        List<Object> dataList = list.stream().map(dataMap-> dataMap.get("num")).toList();
        return new JobOption(jobList,dataList);
    }
    /** 统计员工性别分布 */
    @Override
    public List<Map<String,Object>> getEmpGenderData(){
        List<Map<String,Object>> genderList = empMapper.getEmpGenderData();
        return genderList;
    }
    /** 统计学生学历分布 */
    @Override
    public List<Map<String,Object>> getStudentDegreeData(){
        List<Map<String,Object>> degreeList = studentMapper.getStudentDegreeData();
        return degreeList;
    }

    /**
     * 统计各班级学生人数
     * 将数据库返回的List<Map>转换为ClazzOption对象
     */
    public ClazzOption getStudentCountData(){
        List<Map<String,Object>> clazzList = studentMapper.getStudentCountData();
        List<Object> clazzNameList = clazzList.stream().map(clazz->clazz.get("clazzName")).toList();
        List<Object> dataList = clazzList.stream().map(clazz->clazz.get("num")).toList();
        return new ClazzOption(clazzNameList,dataList);
    }

}
