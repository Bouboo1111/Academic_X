package org.example.tiliaswebmanagement.service;

import org.example.tiliaswebmanagement.pojo.Dept;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 部门业务逻辑层接口
 * 定义部门管理相关的业务方法
 */
public interface DeptService {
    /**
     * 查询所有部门
     * @return 部门列表
     */
    List<Dept> findAll();
    /**
     * 删除部门
     * @param id 部门ID
     * @return 影响行数
     */
    Integer delete(Integer id);
    /**
     * 新增部门
     * @param dept 部门对象
     * @return 影响行数
     */
    Integer add(Dept dept);
    /**
     * 根据ID查询部门
     * @param id 部门ID
     * @return 部门信息
     */
    Dept findById(Integer id);
    /**
     * 更新部门信息
     * @param dept 部门对象
     * @return 影响行数
     */
    Integer update(Dept dept);
}
