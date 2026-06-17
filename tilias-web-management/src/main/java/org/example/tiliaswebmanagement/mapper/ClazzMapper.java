package org.example.tiliaswebmanagement.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.tiliaswebmanagement.pojo.Clazz;
import org.example.tiliaswebmanagement.pojo.ClazzQueryParam;
import org.example.tiliaswebmanagement.pojo.Student;

import java.util.List;

/**
 * 班级数据访问层接口（Mapper）
 * 负责与数据库clazz表进行交互，提供班级信息的增删改查操作
 */
@Mapper
public interface ClazzMapper {
    /**
     * 分页查询班级列表（支持多条件筛选）
     * SQL定义在XML中，配合PageHelper实现分页
     * 
     * @param param 查询参数，包含页码、每页数量、筛选条件
     * @return 班级列表
     */
    List<Clazz> getQuery(ClazzQueryParam param);
    /**
     * 查询所有班级（不分页）
     * 用于下拉框等需要全部数据的场景
     * 
     * @return 所有班级列表
     */
    @Select("select id,name,room,master_id,begin_date,end_date,subject,update_time from clazz ")
    List<Clazz> getAll();
    /**
     * 新增班级
     * SQL定义在XML中
     * 
     * @param clazz 班级对象
     */
    void addClazz(Clazz clazz);
    /**
     * 根据ID查询单个班级
     * 
     * @param id 班级ID
     * @return 班级信息
     */
    @Select("select *from clazz where id =#{id}")
    Clazz selectById(Integer id);
    /**
     * 删除指定班级
     * 
     * @param id 班级ID
     */
    @Delete("delete from clazz where id = #{id}")
    void deleteById(Integer id);
    /**
     * 更新班级信息
     * SQL定义在XML中，使用动态SQL
     * 
     * @param clazz 班级对象，必须包含id
     */
    void update(Clazz clazz);

}
