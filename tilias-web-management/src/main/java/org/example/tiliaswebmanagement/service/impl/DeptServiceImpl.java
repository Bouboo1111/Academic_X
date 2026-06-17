package org.example.tiliaswebmanagement.service.impl;

import org.example.tiliaswebmanagement.mapper.DeptMapper;
import org.example.tiliaswebmanagement.pojo.Dept;
import org.example.tiliaswebmanagement.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 部门业务逻辑层实现类
 * 实现部门管理的增删改查功能
 */
@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;

    /**
     * 查询所有部门
     * @return 部门列表（按更新时间降序）
     */
    public List<Dept> findAll(){
        return deptMapper.findAll();
    }

    /**
     * 删除部门
     * @param id 部门ID
     * @return 影响行数
     */
    public Integer delete(Integer id){
        return deptMapper.delete(id);
    }

    /**
     * 新增部门
     * 自动设置创建时间和更新时间为当前时间
     * 
     * @param dept 部门对象
     * @return 影响行数
     */
    public Integer add(Dept dept){
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        return deptMapper.add(dept);
    }

    /**
     * 根据ID查询部门
     * @param id 部门ID
     * @return 部门信息
     */
    public Dept findById(Integer  id){
        return deptMapper.findById(id);
    }

    /**
     * 更新部门信息
     * 自动设置更新时间为当前时间
     * 
     * @param dept 部门对象
     * @return 影响行数
     */
    public Integer update(Dept dept){
        dept.setUpdateTime(LocalDateTime.now());
        return deptMapper.update(dept);
    }
}
