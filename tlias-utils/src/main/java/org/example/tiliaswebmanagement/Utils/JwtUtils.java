package org.example.tiliaswebmanagement.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

/**
 * JWT 工具类
 * 用于生成和解析 JWT 令牌
 */
public class JwtUtils {
    
    // JWT 签名密钥（Base64 编码）
    private static final String SECRET_KEY = "aXRoZWltYQ==";
    
    // 签名算法
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
    
    // 默认过期时间：1 小时（毫秒）
    private static final long DEFAULT_EXPIRATION_TIME = 3600 * 1000;
    
    /**
     * 生成 JWT 令牌
     * 
     * @param claimsMap 自定义声明数据（如 username、id 等）
     * @return JWT 令牌字符串
     */
    public static String generateJwt(Map<String, Object> claimsMap) {
        return generateJwt(claimsMap, DEFAULT_EXPIRATION_TIME);
    }
    
    /**
     * 生成 JWT 令牌（可指定过期时间）
     * 
     * @param claimsMap 自定义声明数据
     * @param expirationTime 过期时间（毫秒）
     * @return JWT 令牌字符串
     */
    public static String generateJwt(Map<String, Object> claimsMap, long expirationTime) {
        return Jwts.builder()
                .signWith(SIGNATURE_ALGORITHM, SECRET_KEY)// 设置签名算法和密钥
                .addClaims(claimsMap)// 添加自定义声明数据
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))// 设置过期时间
                .compact();// 生成 JWT
    }
    
    /**
     * 解析 JWT 令牌
     * 
     * @param jwt JWT 令牌字符串
     * @return Claims 对象，包含 JWT 中的所有声明信息
     * @throws io.jsonwebtoken.ExpiredJwtException 当 JWT 已过期时抛出
     * @throws io.jsonwebtoken.SignatureException 当签名验证失败时抛出
     * @throws io.jsonwebtoken.MalformedJwtException 当 JWT 格式不正确时抛出
     */
    public static Claims parseJwt(String jwt) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)// 设置密钥
                .parseClaimsJws(jwt)// 解析 JWT
                .getBody();// 获取 Claims 对象
    }
    
    /**
     * 从 JWT 中获取指定声明的值
     * 
     * @param jwt JWT 令牌字符串
     * @param claimName 声明名称
     * @param clazz 目标类型
     * @param <T> 返回类型
     * @return 声明值，如果不存在则返回 null
     */
    public static <T> T getClaimFromJwt(String jwt, String claimName, Class<T> clazz) {
        try {
            Claims claims = parseJwt(jwt);
            return claims.get(claimName, clazz);
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * 从 JWT 中获取用户名
     * 
     * @param jwt JWT 令牌字符串
     * @return 用户名，如果不存在或解析失败则返回 null
     */
    public static String getUsernameFromJwt(String jwt) {
        return getClaimFromJwt(jwt, "username", String.class);
    }
    
    /**
     * 检查 JWT 是否有效
     * 
     * @param jwt JWT 令牌字符串
     * @return true-有效，false-无效（过期、签名错误或格式错误）
     */
    public static boolean isJwtValid(String jwt) {
        try {
            parseJwt(jwt);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * 检查 JWT 是否已过期
     * 
     * @param jwt JWT 令牌字符串
     * @return true-已过期，false-未过期
     */
    public static boolean isJwtExpired(String jwt) {
        try {
            Claims claims = parseJwt(jwt);
            return claims.getExpiration().before(new Date());
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
