package org.example.tiliaswebmanagement.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.example.tiliaswebmanagement.anno.Log;
import org.example.tiliaswebmanagement.pojo.Dept;
import org.example.tiliaswebmanagement.service.DeptService;
import org.example.tiliaswebmanagement.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门管理控制器
 * 负责处理部门相关的HTTP请求，提供部门的增删改查功能
 *
 * RESTful API设计规范：
 * - GET /depts       查询所有部门
 * - GET /depts/{id}  根据ID查询单个部门
 * - POST /depts      新增部门
 * - PUT /depts       修改部门
 * - DELETE /depts    删除部门
 */
@Slf4j  // Lombok注解：自动生成SLF4J日志对象（log），用于记录日志信息
@RestController  // Spring MVC注解：组合注解 = @Controller + @ResponseBody，表示这是一个RESTful控制器，所有方法返回JSON数据
@RequestMapping("/depts")  // 后缀"/depts" 路径映射
public class DeptController {

    @Autowired  // Spring注解：自动注入依赖，从Spring容器中查找DeptService类型的Bean并赋值给该字段
    private DeptService deptService;  // 部门业务逻辑层服务对象

    /**
     * 查询所有部门信息
     * 请求方式：GET
     * 请求路径：/depts
     * 响应数据：包含所有部门列表的Result对象
     *
     * @return Result 统一响应结果，data字段包含部门列表
     */
    @GetMapping("")  // Spring MVC注解：映射GET请求，完整路径为 GET /depts
    public Result list(){
        System.out.println("查询全部部门信息");
        log.info("查询全部部门信息");
        List<Dept> list = deptService.findAll();  // 调用服务层查询所有部门
        return Result.success(list);  // 返回成功响应，携带部门列表数据
    }

    /**
     * 删除指定部门
     * 请求方式：DELETE
     * 请求路径：/depts?id=1
     * 响应数据：操作结果的Result对象
     *
     * @param id 要删除的部门ID，通过请求参数传递
     * @return Result 统一响应结果，表示删除操作是否成功
     */
    @DeleteMapping("")  // Spring MVC注解：映射DELETE请求，完整路径为 DELETE /depts
    @Log  // 自定义注解：标记该方法需要记录操作日志，由AOP切面拦截处理
    public Result delete(@RequestParam Integer id){  // @RequestParam注解：从请求参数中获取id值（如 ?id=1）
        System.out.println("删除部门id为："+ id+"的部门信息");
        Integer count = deptService.delete(id);  // 调用服务层删除部门，返回影响的行数
        return Result.success();  // 返回成功响应，不携带额外数据
    }

    /**
     * 新增部门
     * 请求方式：POST
     * 请求路径：/depts
     * 请求体：JSON格式的部门信息 {"name": "技术部", "updateTime": "2026-06-12 23:50:00"}
     * 响应数据：操作结果的Result对象
     *
     * @param dept 部门对象，通过@RequestBody从请求体中自动解析JSON数据
     * @return Result 统一响应结果，表示新增操作是否成功
     */
    @PostMapping("")  // Spring MVC注解：映射POST请求，完整路径为 POST /depts
    @Log  // 自定义注解：记录操作日志
    public Result add(@RequestBody Dept dept){  // @RequestBody注解：将请求体中的JSON数据自动转换为Dept对象
        System.out.println("添加部门信息："+dept);
        Integer count = deptService.add(dept);  // 调用服务层新增部门，返回影响的行数
        return Result.success();  // 返回成功响应
    }

    /**
     * 根据ID查询单个部门信息
     * 请求方式：GET
     * 请求路径：/depts/1 （1为部门ID）
     * 响应数据：包含部门详情的Result对象
     *
     * @param id 部门ID，通过路径变量传递（URL中的{id}部分）
     * @return Result 统一响应结果，data字段包含部门详细信息
     */
    @GetMapping("/{id}")  // Spring MVC注解：映射GET请求，{id}是路径变量占位符，完整路径如 GET /depts/1
    // @PathVariable 获取路径中的参数
    public Result findById(@PathVariable Integer id){  // @PathVariable注解：从URL路径中提取{id}的值赋给参数id
        System.out.println("查询id为："+id+"的部门信息");
        Dept dept = deptService.findById(id);  // 调用服务层根据ID查询部门详情
        System.out.println(dept);
        return Result.success(dept);  // 返回成功响应，携带部门详细信息
    }

    /**
     * 修改部门信息
     * 请求方式：PUT
     * 请求路径：/depts
     * 请求体：JSON格式的部门信息（必须包含id字段）{"id": 1, "name": "技术研发部", "updateTime": "2026-06-12 23:50:00"}
     * 响应数据：操作结果的Result对象
     *
     * @param dept 部门对象，包含要修改的字段和部门ID
     * @return Result 统一响应结果，表示修改操作是否成功
     */
    @PutMapping("")  // Spring MVC注解：映射PUT请求，完整路径为 PUT /depts
    @Log  // 自定义注解：记录操作日志
    public Result update(@RequestBody Dept dept){  // @RequestBody注解：从请求体中解析JSON数据为Dept对象
        System.out.println("修改部门信息："+dept);
        Integer count = deptService.update(dept);  // 调用服务层更新部门信息，返回影响的行数
        return Result.success();  // 返回成功响应
    }
}
