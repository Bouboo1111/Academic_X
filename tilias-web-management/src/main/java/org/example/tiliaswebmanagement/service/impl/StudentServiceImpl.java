package org.example.tiliaswebmanagement.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.tiliaswebmanagement.mapper.StudentMapper;
import org.example.tiliaswebmanagement.pojo.PageResult;
import org.example.tiliaswebmanagement.pojo.Student;
import org.example.tiliaswebmanagement.pojo.StudentQueryParam;
import org.example.tiliaswebmanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 学生业务逻辑层实现类
 * 实现学生管理的增删改查功能
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    /**
     * 分页查询学生列表
     * 使用PageHelper实现物理分页
     */
    public PageResult<Student> getQuery(StudentQueryParam param){
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<Student> list = studentMapper.getQuery(param);
        Page<Student> p = (Page<Student>) list;
        return new PageResult(p.getTotal(),p.getResult());
    }

    /** 根据ID查询学生 */
    public Student getSelectById(Integer id){
        return studentMapper.getSelectById(id);
    }

    /** 新增学生 */
    public void insertStudent(Student student){
        studentMapper.insertStudent(student);
    }

    /** 批量删除学生 */
    public void deleteStudent(List<Integer>  ids){
        studentMapper.deleteStudent(ids);
    }

    /** 更新学生信息 */
    public void update(Student student){
        studentMapper.update(student);
    }

    /** 更新学生操行分（加分/扣分） */
    public void updateScore(Integer id,Integer score){
        studentMapper.updateScore(id,score);
    }



}
