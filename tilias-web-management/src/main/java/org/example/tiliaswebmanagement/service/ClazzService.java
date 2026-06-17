package org.example.tiliaswebmanagement.service;

import org.example.tiliaswebmanagement.pojo.Clazz;
import org.example.tiliaswebmanagement.pojo.ClazzQueryParam;
import org.example.tiliaswebmanagement.pojo.PageResult;

import java.util.List;

/**
 * 班级业务逻辑层接口
 * 定义班级管理相关的业务方法
 */
public interface ClazzService {
    /** 分页查询班级列表 */
    PageResult<Clazz> getQuery(ClazzQueryParam param);
    /** 查询所有班级 */
    List<Clazz> getAll();
    /** 新增班级 */
    void addClazz(Clazz clazz);
    /** 根据ID查询班级 */
    Clazz selectById(Integer id);
    /** 删除班级 */
    void deleteById(Integer id);
    /** 更新班级信息 */
    void update(Clazz clazz);
}
