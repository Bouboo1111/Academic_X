package org.example.tiliaswebmanagement.mapper;

import org.example.tiliaswebmanagement.pojo.OperateLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * 操作日志数据访问层接口（Mapper）
 * 负责记录系统操作的详细日志，通过AOP自动拦截Controller方法
 * 
 * 记录内容包括：
 * - 操作人ID、操作时间
 * - 调用的类名、方法名
 * - 方法参数、返回值
 * - 执行耗时
 */
@Mapper
public interface OperateLogMapper {

    /**
     * 插入操作日志
     * 由AOP切面自动调用，记录Controller方法的执行情况
     * 
     * @param log 日志对象，包含完整的操作信息
     */
    //插入日志数据
    @Insert("insert into operate_log (operate_emp_id, operate_time, class_name, method_name, method_params, return_value, cost_time) " +
            "values (#{operateEmpId}, #{operateTime}, #{className}, #{methodName}, #{methodParams}, #{returnValue}, #{costTime});")
    public void insert(OperateLog log);

}
