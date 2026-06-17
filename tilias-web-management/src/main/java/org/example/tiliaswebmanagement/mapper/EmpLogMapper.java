package org.example.tiliaswebmanagement.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.example.tiliaswebmanagement.pojo.EmpLog;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 员工操作日志数据访问层接口（Mapper）
 * 负责记录员工业务操作的日志信息
 */
@Mapper
public interface EmpLogMapper {

    /**
     * 插入员工操作日志
     * 用于记录员工的业务操作，如新增、修改、删除等
     * 
     * @param empLog 日志对象，包含操作时间和操作内容
     */
    @Insert("insert into emp_log (operate_time, info) values (#{operateTime}, #{info})")
    public void insert(EmpLog empLog);

}
