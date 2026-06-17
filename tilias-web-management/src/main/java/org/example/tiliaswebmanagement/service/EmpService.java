package org.example.tiliaswebmanagement.service;

import org.example.tiliaswebmanagement.pojo.Emp;
import org.example.tiliaswebmanagement.pojo.EmpQueryParam;
import org.example.tiliaswebmanagement.pojo.LoginInfo;
import org.example.tiliaswebmanagement.pojo.PageResult;

import java.time.LocalDate;
import java.util.List;

/**
 * 员工业务逻辑层接口
 * 定义员工管理相关的业务方法，包括分页查询、增删改查、登录认证等
 */
public interface EmpService {
   // PageResult<Emp> page(Integer page, Integer pageSize, String name, Integer gender, LocalDate begin, LocalDate end);
    /**
     * 分页查询员工列表
     * 
     * @param param 查询参数对象
     * @return 分页结果（包含总记录数和当前页数据）
     */
    PageResult<Emp> page(EmpQueryParam  param);
    /**
     * 新增员工（含工作经历）
     * 使用事务保证员工信息和工作经历同时成功或失败
     * 
     * @param emp 员工对象，可包含exprList工作经历列表
     */
    void save(Emp  emp) ;
    /**
     * 批量删除员工（含工作经历）
     * 
     * @param ids 要删除的员工ID列表
     */
    void deleteByIds(List< Integer> ids);
    /**
     * 根据ID查询员工详细信息（含工作经历）
     * 
     * @param id 员工ID
     * @return 员工完整信息
     */
    Emp getInfo(Integer id);
    /**
     * 更新员工信息（含工作经历）
     * 先删除旧的工作经历，再插入新的工作经历
     * 
     * @param emp 员工对象，必须包含id
     */
    void update(Emp emp);
    /**
     * 员工登录认证
     * 验证用户名和密码，成功后生成JWT令牌
     * 
     * @param emp 包含username和password的登录信息
     * @return 登录成功返回LoginInfo（含token），失败返回null
     */
    LoginInfo login(Emp emp);
}
