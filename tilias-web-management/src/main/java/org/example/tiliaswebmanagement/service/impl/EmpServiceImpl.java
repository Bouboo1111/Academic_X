package org.example.tiliaswebmanagement.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.example.tiliaswebmanagement.Utils.JwtUtils;
import org.example.tiliaswebmanagement.mapper.EmpExprMapper;
import org.example.tiliaswebmanagement.mapper.EmpMapper;
import org.example.tiliaswebmanagement.pojo.*;
import org.example.tiliaswebmanagement.service.EmpLogService;
import org.example.tiliaswebmanagement.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 员工业务逻辑层实现类
 * 实现员工管理的核心业务逻辑，包括分页查询、增删改查、登录认证等
 * 
 * 主要功能：
 * 1. 分页查询：使用PageHelper插件实现物理分页
 * 2. 事务管理：新增/修改员工时使用@Transactional保证数据一致性
 * 3. 登录认证：验证用户名密码，生成JWT令牌
 * 4. 日志记录：操作完成后记录业务日志
 */
@Slf4j
@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private EmpExprMapper empExprMapper;
    @Autowired
    private EmpLogService empLogService;


    /**
     * 分页查询员工列表
     * 使用PageHelper插件实现物理分页，自动在SQL后追加LIMIT子句
     * 
     * 执行流程：
     * 1. PageHelper.startPage()设置分页参数（绑定到ThreadLocal）
     * 2. 执行查询，PageHelper拦截SQL并添加LIMIT
     * 3. 将结果转换为Page对象，获取总记录数
     * 4. 封装为PageResult返回
     * 
     * @param param 查询参数，包含页码、每页数量、筛选条件
     * @return 分页结果（总记录数 + 当前页数据）
     */
    // ... existing code ...
    public PageResult<Emp> page(EmpQueryParam  param){
        // 开启PageHelper分页功能，设置当前页码和每页记录数
        // 该方法会将分页参数绑定到ThreadLocal中，供后续SQL拦截使用
        PageHelper.startPage(param.getPage(),param.getPageSize());

        // 执行员工列表查询，PageHelper会自动在SQL后追加LIMIT子句实现物理分页
        List<Emp> rows = empMapper.list(param);

        // 将查询结果强制转换为Page对象，以获取分页元数据（总记录数等）
        // Page是ArrayList的子类，包含了total总数、pageNum页码、pageSize页大小等信息
        Page<Emp> p = (Page<Emp>) rows;

        // 封装分页结果，返回包含总记录数和当前页数据的PageResult对象
        return new PageResult<>(p.getTotal(),p.getResult());
    }
// ... existing code ...


    /**
     * 新增员工（含工作经历）
     * 使用@Transactional保证事务一致性：员工信息和工作经历要么都成功，要么都失败
     * 操作完成后记录业务日志
     * 
     * 业务流程：
     * 1. 设置创建时间和更新时间
     * 2. 插入员工基本信息（主键回填获取ID）
     * 3. 如果有工作经历，批量插入工作经厉表
     * 4. 记录操作日志（无论成功失败都记录）
     * 
     * @param emp 员工对象，可包含exprList工作经历列表
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(Emp emp)  {
    try {
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.insert(emp);

        List<EmpExpr> exprList = emp.getExprList();
        if(exprList!=null && !exprList.isEmpty()){
            for (EmpExpr expr : exprList) {
                expr.setEmpId(emp.getId());
            }
            empExprMapper.insertBatch(exprList);
        }

    } finally {
        //记录操作日志
        EmpLog empLog = new EmpLog(null, LocalDateTime.now(), "新增员工信息：" + emp);
        empLogService.insertLog(empLog);
    }
    }

    /**
     * 批量删除员工及其工作经历
     * 同时删除emp表和emp_expr表的数据
     * 
     * @param ids 要删除的员工ID列表
     */
    public void deleteByIds(List<Integer> ids) {
        empMapper.deleteByIds(ids);
        empExprMapper.deleteByEmpIds(ids);
    }

    /**
     * 根据ID查询员工详细信息（含工作经历）
     * 使用resultMap映射一对多关系
     * 
     * @param id 员工ID
     * @return 员工完整信息
     */
    public Emp getInfo(Integer id){
        Emp emp = empMapper.getInfo(id);
        return emp;
    }
    /**
     * 更新员工信息（含工作经历）
     * 使用@Transactional保证事务一致性
     * 
     * 业务流程：
     * 1. 设置更新时间
     * 2. 更新员工基本信息
     * 3. 删除该员工的旧工作经历
     * 4. 批量插入新的工作经历
     * 
     * @param emp 员工对象，必须包含id
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(Emp emp){
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.update(emp);
        empExprMapper.deleteByEmpIds(Arrays.asList(emp.getId()));
        List<EmpExpr> exprList = emp.getExprList();
        if(exprList!=null && !exprList.isEmpty()){
            for (EmpExpr expr : exprList) {
                expr.setEmpId(emp.getId());
            }
            empExprMapper.insertBatch(exprList);
        }

    }
    /**
     * 员工登录认证
     *
     * 核心功能：
     * 1. 验证用户名和密码是否正确
     * 2. 登录成功后生成JWT令牌
     * 3. 封装用户信息和Token返回给前端
     *
     * 业务流程：
     * Step 1: 接收前端传来的用户名和密码（Emp对象）
     *    ↓
     * Step 2: 调用Mapper查询数据库，匹配用户名和密码
     *    ↓
     * Step 3: 判断查询结果
     *    ├─ 成功：生成JWT Token，封装LoginInfo返回
     *    └─ 失败：返回null，由Controller层处理错误提示
     *
     * JWT生成逻辑：
     * - 将用户ID和用户名放入claims（载荷）
     * - 调用JwtUtils.generateJwt()生成加密令牌
     * - Token有效期在JwtUtils中配置（通常24小时）
     *
     * @param emp 员工登录参数对象，必须包含：
     *            - username: 用户名（必填）
     *            - password: 密码（必填，建议前端先加密）
     * @return LoginInfo 登录成功时返回用户信息和JWT令牌；失败返回null
     *         包含字段：
     *         - id: 用户ID
     *         - username: 用户名
     *         - token: JWT身份令牌
     *
     */
    public LoginInfo login(Emp emp){
        // 根据用户名和密码查询数据库
        // 如果匹配成功，返回完整的员工信息；否则返回null
        Emp e = empMapper.selectByUsernameAndPassword(emp);

        // 判断是否查询到匹配的员工记录
        if(e != null){
            // 登录成功：记录日志（生产环境建议脱敏）
            log.info("员工登录成功：{}", e);

            // 生成JWT令牌（JSON Web Token）
            // 步骤1：创建claims地图，存放需要加密到Token中的用户信息
            // claims是Token的"载荷"部分，携带了用户的身份信息
            Map<String, Object> claimsMap = new HashMap<>();
            claimsMap.put("username", e.getUsername());  // 存入用户名
            claimsMap.put("id", e.getId());              // 存入用户ID

            // 步骤2：调用JWT工具类生成令牌
            // 内部流程：
            // 1. 将claimsMap转换为JSON格式
            // 2. 添加头部信息（算法、类型）
            // 3. 使用密钥签名（防止篡改）
            // 4.  Base64编码生成最终字符串
            // 生成的Token类似：eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.xxx.yyy
            String jwt = JwtUtils.generateJwt(claimsMap);

            // 步骤3：封装登录信息并返回
            // 包含用户基本信息 + JWT令牌
            // 前端收到后会：
            // 1. 提取token保存到localStorage
            // 2. 后续请求在Header中携带：Authorization: Bearer {token}
            return new LoginInfo(e.getId(), e.getUsername(), e.getPassword(), jwt);
        }

        // 登录失败：用户名或密码不匹配
        // 返回null，由Controller层统一处理错误响应
        return null;
    }





}
