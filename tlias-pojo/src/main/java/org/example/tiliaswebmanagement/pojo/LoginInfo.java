package org.example.tiliaswebmanagement.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录信息实体类 - 封装用户登录成功后的返回数据
 *
 * 用途说明：
 * 1. 作为登录接口的返回值载体（DTO - Data Transfer Object）
 * 2. 包含用户基本信息和身份认证令牌（JWT Token）
 * 3. 前端收到后会将token保存到本地存储（localStorage/sessionStorage）
 *
 * 数据流向：
 * 用户登录成功 → Service层生成此对象 → 转换为JSON → 返回给前端
 *
 * 使用场景：
 * - LoginController.login() 方法返回此对象
 * - 前端解析获取token，后续请求携带在Header中
 */
@Data // Lombok注解：自动生成getter、setter、toString、equals、hashCode方法
@NoArgsConstructor // Lombok注解：自动生成无参构造方法（框架反射创建对象时需要）
@AllArgsConstructor // Lombok注解：自动生成全参构造方法（方便手动创建对象）
public class LoginInfo {

    /**
     * 用户ID
     * 数据库中员工表的唯一标识
     * 用于后续业务操作中标识当前操作用户
     */
    private Integer id;

    /**
     * 用户名
     * 用户登录时使用的账号名称
     * 前端可用于显示"欢迎, xxx"等信息
     */
    private String username;

    /**
     * 密码
     * 注意：实际项目中建议不要返回明文密码！
     * 安全做法：
     * - 方案1：直接移除该字段，前端不需要知道密码
     * - 方案2：返回空字符串或null
     * - 方案3：返回脱敏后的密码（如 "******"）
     *
     * 安全风险：
     * 如果返回真实密码，可能被网络嗅探窃取，或前端意外展示
     */
    private String password;

    /**
     * JWT令牌（JSON Web Token）
     *
     * 什么是JWT？
     * - 一种开放标准（RFC 7519），用于在各方之间安全地传输信息
     * - 格式：xxxxx.yyyyy.zzzzz（头部.载荷.签名）
     * - 示例：eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJhZG1pbiJ9.xxx
     *
     * Token的作用：
     * 1. 身份凭证：证明用户已经登录且身份合法
     * 2. 无状态认证：服务器不需要存储session，减轻压力
     * 3. 跨域支持：可以在不同域名间传递
     *
     * 使用流程：
     * 1. 用户登录成功，服务器生成Token返回给前端
     * 2. 前端将Token保存到 localStorage 或 sessionStorage
     * 3. 后续每个请求都在Header中携带：Authorization: Bearer {token}
     * 4. 后端拦截器验证Token有效性，通过则放行
     *
     * Token包含的信息（需解密后查看）：
     * - 用户ID
     * - 用户名
     * - 过期时间
     * - 自定义 claims（角色、权限等）
     *
     * 有效期：
     * - 通常设置为 24小时、7天、30天等
     * - 过期后需要重新登录或使用refresh token刷新
     *
     * 安全提示：
     * - Token相当于"钥匙"，一旦泄露他人可冒充用户
     * - 前端必须使用HTTPS传输
     * - 敏感操作应二次验证
     */
    private String token;
}
