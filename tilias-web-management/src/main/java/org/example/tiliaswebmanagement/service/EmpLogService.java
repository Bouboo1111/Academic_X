package org.example.tiliaswebmanagement.service;

import org.example.tiliaswebmanagement.pojo.EmpLog;

/**
 * 员工操作日志业务逻辑层接口
 */
public interface EmpLogService {

    /**
     * 插入操作日志
     * @param empLog 日志对象
     */
    public void insertLog(EmpLog empLog);

}
