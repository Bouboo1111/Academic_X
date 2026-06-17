package org.example.tiliaswebmanagement.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.tiliaswebmanagement.pojo.EmpExpr;

import java.util.List;

/**
 * 员工工作经历数据访问层接口（Mapper）
 * 负责与数据库emp_expr表进行交互，管理员工的工作经历信息
 */
@Mapper
public interface EmpExprMapper {
    /**
     * 批量插入工作经历
     * 用于新增或修改员工时保存多条工作经历
     * SQL定义在XML中，使用foreach实现批量插入
     * 
     * @param exprs 工作经历列表
     */
    void insertBatch(@Param("exprList")List<EmpExpr> exprs);
    /**
     * 根据员工ID批量删除工作经历
     * 用于删除员工或更新工作经历前清空旧数据
     * 
     * @param empIds 员工ID列表
     */
    void deleteByEmpIds(List<Integer> empIds);
}
