package org.example.tiliaswebmanagement.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.tiliaswebmanagement.pojo.Emp;
import org.example.tiliaswebmanagement.pojo.LoginInfo;
import org.example.tiliaswebmanagement.pojo.Result;
import org.example.tiliaswebmanagement.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录控制器 - 处理用户登录认证请求
 *
 * 核心职责：
 * 1. 接收前端传来的用户名和密码
 * 2. 调用业务层进行身份验证
 * 3. 返回JWT令牌（Token）或错误信息
 *
 * 工作流程：
 * 前端请求 → 接收参数 → 调用Service验证 → 生成JWT → 返回结果
 *
 */
@Slf4j // Lombok注解：自动生成日志对象 log，用于记录程序运行信息
@RestController // Spring注解：标记这是一个REST风格的控制器，所有方法都返回JSON数据
public class LoginController {

    /**
     * 员工服务层对象
     * 通过@Autowired自动注入EmpService的实现类
     * 负责处理登录相关的业务逻辑（查询数据库、验证密码、生成Token等）
     */
    @Autowired
    private EmpService empService;

    /**
     * 员工登录接口
     *
     * 接口说明：
     * - 请求路径：POST /login
     * - 请求方式：POST（因为涉及敏感数据传输）
     * - 请求参数：JSON格式的员工信息 { "username": "xxx", "password": "xxx" }
     * - 返回结果：成功返回JWT令牌，失败返回错误提示
     *

     * @param emp 员工登录信息对象，包含用户名和密码字段
     *            通过@RequestBody注解从HTTP请求体中解析JSON数据
     * @return Result 统一响应结果对象
     *         - 成功时：{ code: 1, msg: "success", data: { token: "xxx", ... } }
     *         - 失败时：{ code: 0, msg: "用户名或密码错误", data: null }
     *
     * 使用示例：
     * <pre>
     * 请求：
     * POST http://localhost:8080/login
     * Content-Type: application/json
     *
     * {
     *   "username": "admin",
     *   "password": "123456"
     * }
     *
     * 成功响应：
     * {
     *   "code": 1,
     *   "msg": "success",
     *   "data": {
     *     "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
     *     "id": 1,
     *     "username": "admin"
     *   }
     * }
     * </pre>
     */
    @PostMapping("/login") // 映射POST请求到 /login 路径
    public Result login(@RequestBody Emp emp){
        // 记录登录请求日志，方便追踪用户登录行为
        // {} 是占位符，会被emp对象的toString()结果替换
        log.info("员工登录：{}", emp);

        // 调用业务层进行登录验证
        // 该方法内部会：
        // 1. 根据用户名查询数据库
        // 2. 验证密码是否正确
        // 3. 如果成功，生成JWT令牌并封装到LoginInfo对象
        // 4. 如果失败，返回null
        LoginInfo loginInfo = empService.login(emp);

        // 判断登录是否成功
        if(loginInfo == null){
            // 登录失败：用户名不存在或密码错误
            // 统一返回模糊提示，避免泄露具体是哪个字段错误
            return Result.error("用户名或密码错误");
        }

        // 登录成功：返回JWT令牌和用户基本信息
        // loginInfo通常包含：
        // - token: JWT字符串，后续请求需要携带
        // - id: 用户ID
        // - username: 用户名
        // - 其他需要展示的用户信息
        return Result.success(loginInfo);
    }
}
