package org.example.tiliaswebmanagement.service;

import org.apache.ibatis.annotations.Select;
import org.example.tiliaswebmanagement.pojo.PageResult;
import org.example.tiliaswebmanagement.pojo.Student;
import org.example.tiliaswebmanagement.pojo.StudentQueryParam;

import java.util.List;

/**
 * 学生业务逻辑层接口
 * 定义学生管理相关的业务方法
 */
public interface StudentService {
    /** 分页查询学生列表 */
    PageResult<Student> getQuery(StudentQueryParam param);
    /** 根据ID查询学生 */
    Student getSelectById(Integer  id);
    /** 新增学生 */
    void insertStudent(Student student);
    /** 批量删除学生 */
    void deleteStudent(List<Integer> ids);
    /** 更新学生信息 */
    void update(Student student);
    /** 更新学生操行分 */
    void updateScore(Integer id,Integer score);
}
