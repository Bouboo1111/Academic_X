package org.example.tiliaswebmanagement.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.tiliaswebmanagement.pojo.Student;
import org.example.tiliaswebmanagement.pojo.StudentQueryParam;

import java.util.List;
import java.util.Map;

/**
 * 学生数据访问层接口（Mapper）
 * 负责与数据库student表进行交互，提供学生信息的增删改查操作
 */
@Mapper
public interface StudentMapper {
    /**
     * 分页查询学生列表（支持多条件筛选）
     * SQL定义在XML中，配合PageHelper实现分页
     * 
     * @param param 查询参数，包含页码、每页数量、筛选条件
     * @return 学生列表
     */
    List<Student> getQuery( StudentQueryParam param);
    /**
     * 根据ID查询单个学生
     * 
     * @param id 学生ID
     * @return 学生信息
     */
    @Select("select * from student where id = #{id}")
    Student getSelectById(Integer id);
    /**
     * 新增学生
     * SQL定义在XML中
     * 
     * @param student 学生对象
     */
    void insertStudent(Student student);
    /**
     * 批量删除学生
     * SQL定义在XML中，使用foreach实现IN查询
     * 
     * @param ids 要删除的学生ID列表
     */
    void deleteStudent(List<Integer> ids);
    /**
     * 更新学生信息
     * SQL定义在XML中，使用动态SQL
     * 
     * @param student 学生对象，必须包含id
     */
    void update(Student student);
    /**
     * 更新学生操行分（加分/扣分）
     * 
     * @param id 学生ID
     * @param score 分数变化量（正数加分，负数扣分）
     */
    void updateScore(Integer id,Integer  score);
    /**
     * 统计学生学历分布
     * 用于报表展示
     * 
     * @return List<Map> 每个Map包含学历和人数
     */
    List<Map<String,Object>> getStudentDegreeData();
    /**
     * 统计各班级学生人数
     * 用于报表展示
     * 
     * @return List<Map> 每个Map包含clazzName(班级名)和num(人数)
     */
    List<Map<String,Object>> getStudentCountData();
}
