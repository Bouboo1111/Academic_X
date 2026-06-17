package org.example.tiliaswebmanagement.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.tiliaswebmanagement.pojo.Emp;
import org.example.tiliaswebmanagement.pojo.EmpQueryParam;
import org.example.tiliaswebmanagement.pojo.PageResult;
import org.example.tiliaswebmanagement.pojo.Result;
import org.example.tiliaswebmanagement.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * 员工管理控制器
 * 提供员工信息的增删改查、分页查询等RESTful API接口
 */
@Slf4j
@RestController
@RequestMapping("/emps")
public class EmpController {
    @Autowired
    private EmpService empService;

    /**
     * 分页查询员工列表
     * GET /emps?page=1&pageSize=10&name=张三&gender=1&begin=2024-01-01&end=2024-12-31
     * 
     * @param param 查询参数（页码、每页数量、筛选条件）
     * @return 分页结果
     */
    @GetMapping("")
    //RequestParam 即使前端不传参数也有默认值
    /**public Result page(@RequestParam(defaultValue = "1")Integer page,
                       @RequestParam(defaultValue = "10")Integer pageSize,
                       String name, Integer gender,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){

        log.info("分页查询：{}，{} ,{},{},{},{}",page, pageSize, name, gender, begin, end);
        PageResult<Emp> pageResult = empService.page(page, pageSize, name, gender, begin, end);
        return Result.success(pageResult);
    }**/
    public Result page(EmpQueryParam param){
        log.info("分页查询：{}",param);
        PageResult<Emp> pageResult = empService.page(param);
        return Result.success(pageResult);
    }

    /**
     * 新增员工
     * POST /emps
     * 
     * @param emp 员工对象（JSON格式）
     * @return 操作结果
     */
    @PostMapping
    public Result save(@RequestBody Emp emp) {
        log.info("保存员工：{}",emp);

            empService.save(emp);

        return Result.success();
    }
    /**
     * 批量删除员工
     * DELETE /emps?ids=1,2,3
     * 
     * @param ids 要删除的员工ID列表
     * @return 操作结果
     */
    @DeleteMapping
    public Result delete(@RequestParam List<Integer> ids){
        log.info("删除员工：{}", ids);
        empService.deleteByIds(ids);
        return Result.success();

    }
    /**
     * 根据ID查询员工详细信息
     * GET /emps/1
     * 
     * @param id 员工ID
     * @return 员工信息（含工作经历）
     */
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id){
        log.info("回显员工信息：{}", id);
        Emp emp = empService.getInfo(id);
        return Result.success(emp);
    }

    /**
     * 更新员工信息
     * PUT /emps
     * 
     * @param emp 员工对象（JSON格式，必须包含id）
     * @return 操作结果
     */
    @PutMapping
    public Result update(@RequestBody Emp emp){
        log.info("修改员工信息：{}", emp);
        empService.update(emp);
        return Result.success();
    }
}
