package org.example.tiliaswebmanagement.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.tiliaswebmanagement.mapper.ClazzMapper;
import org.example.tiliaswebmanagement.pojo.Clazz;
import org.example.tiliaswebmanagement.pojo.ClazzQueryParam;
import org.example.tiliaswebmanagement.pojo.PageResult;
import org.example.tiliaswebmanagement.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 班级业务逻辑层实现类
 * 实现班级管理的增删改查功能
 */
@Service
public class ClazzServiceImpl implements ClazzService {
    @Autowired
    private ClazzMapper clazzMapper;

    /**
     * 分页查询班级列表
     * 使用PageHelper实现物理分页
     */
    public PageResult getQuery(ClazzQueryParam param){
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<Clazz> list = clazzMapper.getQuery(param);
        Page<Clazz> p = (Page<Clazz>) list;
        return new PageResult(p.getTotal(),p.getResult());
    }

    /** 查询所有班级 */
    public List<Clazz> getAll(){
        return clazzMapper.getAll();
    }

    /** 新增班级 */
    public void addClazz(Clazz clazz){
        clazzMapper.addClazz(clazz);
    }

    /** 根据ID查询班级 */
    public Clazz selectById(Integer id){
        return clazzMapper.selectById(id);
    }

    /** 删除班级 */
    public void deleteById(Integer id){
        clazzMapper.deleteById(id);
    }

    /** 更新班级信息 */
    public void update(Clazz clazz){
        clazzMapper.update(clazz);
    }
}
