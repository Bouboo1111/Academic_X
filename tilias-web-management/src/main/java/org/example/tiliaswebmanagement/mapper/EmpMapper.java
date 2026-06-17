package org.example.tiliaswebmanagement.mapper;

import org.apache.ibatis.annotations.*;
import org.example.tiliaswebmanagement.pojo.Emp;
import org.example.tiliaswebmanagement.pojo.EmpQueryParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 员工数据访问层接口（Mapper）
 * 负责与数据库emp表进行交互，提供员工信息的增删改查操作
 * 
 * MyBatis映射方式：
 * - 简单SQL：使用注解（@Select、@Insert等）直接写在方法上
 * - 复杂SQL：在XML文件中编写（支持动态SQL、多表关联等）
 */
@Mapper
public interface EmpMapper {
    //@Select("select count(*) from emp e left outer join dept d on e.dept_id = d.id")
    //@Select("select e.*,d.name dept_name from emp e left outer join dept d on e.dept_id = d.id " +
           // "order by e.update_time desc limit #{start},#{pageSize};")
   // @Select("select e.*,d.name dept_name from emp e left outer join dept d on e.dept_id = d.id order by e.update_time desc ")

    /**
     * 分页查询员工列表（支持多条件筛选）
     * 配合PageHelper插件实现物理分页
     * SQL定义在EmpMapper.xml中，支持动态WHERE条件
     * 
     * @param param 查询参数对象，包含：页码、每页数量、姓名、性别、入职日期范围
     * @return 员工列表（含部门名称）
     */
    List<Emp> list(EmpQueryParam param);
    /**
     * 新增员工信息
     * 使用@Options实现主键回填，插入后可通过emp.getId()获取自动生成的ID
     * 
     * @param emp 员工对象，包含所有必填字段
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")// 设置主键回填
    @Insert("insert into emp(username, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time) \n" +
            "values(#{username},#{name},#{gender},#{phone},#{job},#{salary},#{image},#{entryDate},#{deptId},#{createTime},#{updateTime});")
    void insert(Emp emp);

    /**
     * 批量删除员工（根据ID列表）
     * SQL定义在XML中，使用foreach标签实现IN查询
     * 
     * @param ids 要删除的员工ID列表
     */
    void deleteByIds(List<Integer> ids);

    /**
     * 根据ID查询员工详细信息（含工作经历）
     * 使用resultMap映射一对多关系（员工→工作经历列表）
     * 
     * @param id 员工ID
     * @return 员工完整信息（含exprList工作经历集合）
     */
    Emp getInfo(Integer id);

    /**
     * 更新员工信息
     * 使用动态SQL（set标签），只更新非空字段
     * 
     * @param emp 员工对象，必须包含id字段
     */
    void update(Emp emp);

    /**
     * 统计各职位员工人数
     * 用于报表展示，返回职位名称和对应人数
     * 
     * @return List<Map> 每个Map包含pos(职位名称)和num(人数)
     * [
     *   {"pos": "讲师", "num": 10},
     *   {"pos": "班主任", "num": 5},
     *   {"pos": "咨询师", "num": 3}
     * ]
     */
    List<Map<String,Object>> getEmpJobData();

    /**
     * 统计男女员工人数
     * 用于性别分布图表展示
     * 
     * @return List<Map> 每个Map包含name(性别)和value(人数)
     */
    List<Map<String,Object>> getEmpGenderData();
    /**
     * 员工登录验证
     * 根据用户名和密码查询员工信息
     * 
     * @param emp 包含username和password的查询条件
     * @return 匹配的员工信息（仅含id、username、password），未找到返回null
     */
    @Select("select id,username,password from emp where username=#{username} and password=#{password}")
    Emp selectByUsernameAndPassword(Emp emp);
}
