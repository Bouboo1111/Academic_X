package org.example.tiliaswebmanagement.mapper;

import org.apache.ibatis.annotations.*;
import org.example.tiliaswebmanagement.pojo.Dept;

import java.util.List;

/**
 * 部门数据访问层接口（Mapper）
 * 负责与数据库dept表进行交互，提供部门信息的增删改查操作
 * 
 * 特点：所有SQL都使用注解方式，适合简单查询
 */
@Mapper
public interface DeptMapper {
    /**
     * 查询所有部门信息
     * 按更新时间降序排列，最新修改的部门排在前面
     * 
     * @return 部门列表
     */
    @Select("select id,name,create_time,update_time from dept order by update_time desc ")
    List<Dept> findAll();
    /**
     * 删除指定部门
     * 
     * @param id 部门ID
     * @return 影响行数（1表示成功，0表示失败）
     */
    @Delete("delete from dept where id=#{id}")
    Integer delete(Integer id);

    /**
     * 新增部门
     * 需要在Service层设置createTime和updateTime
     * 
     * @param dept 部门对象，必须包含name字段
     * @return 影响行数（1表示成功）
     */
    @Insert("insert into dept(name,create_time,update_time) values(#{name},#{createTime},#{updateTime})")
    Integer add(Dept dept);

    /**
     * 根据ID查询单个部门
     * 
     * @param id 部门ID
     * @return 部门信息，未找到返回null
     */
    @Select("select id,name,create_time,update_time from dept where id=#{id}")
    Dept findById(Integer id);

    /**
     * 更新部门信息
     * 需要在Service层设置updateTime为当前时间
     * 
     * @param dept 部门对象，必须包含id和name字段
     * @return 影响行数（1表示成功）
     */
    @Update("update dept set name = #{name},update_time = #{updateTime} where id = #{id}")
    Integer update(Dept dept);
}
