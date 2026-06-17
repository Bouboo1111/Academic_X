package org.example.tiliaswebmanagement.service.impl;
import org.example.tiliaswebmanagement.mapper.EmpLogMapper;
import org.example.tiliaswebmanagement.pojo.EmpLog;
import org.example.tiliaswebmanagement.service.EmpLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 员工操作日志业务逻辑层实现类
 * 使用REQUIRES_NEW传播级别，确保日志记录不受主事务影响
 */
@Service
public class EmpLogServiceImpl implements EmpLogService {

    @Autowired
    private EmpLogMapper empLogMapper;
    /**
     * 插入操作日志
     * 使用REQUIRES_NEW传播级别：无论主事务是否成功，日志都会独立提交
     * 这样即使主业务回滚，操作日志也能保留下来
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)//无论如何都会新开启一个事务
    @Override
    public void insertLog(EmpLog empLog) {
        empLogMapper.insert(empLog);
    }
}
