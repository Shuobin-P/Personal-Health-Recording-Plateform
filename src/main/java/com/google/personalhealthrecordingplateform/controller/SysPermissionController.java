package com.google.personalhealthrecordingplateform.controller;

import com.google.personalhealthrecordingplateform.entity.SysPermission;
import com.google.personalhealthrecordingplateform.service.SysPermissionService;
import com.google.personalhealthrecordingplateform.util.QueryInfo;
import com.google.personalhealthrecordingplateform.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author W&F
 * @version 1.0
 */

@RestController
@RequestMapping("/permission")
@Api(tags = "权限数据接口")
public class SysPermissionController {

    private final SysPermissionService sysPermissionService;

    @Autowired
    public SysPermissionController(SysPermissionService sysPermissionService) {
        this.sysPermissionService = sysPermissionService;
    }

    @PostMapping("/insert")
    @ApiOperation(value = "增加权限接口")
    public Result insert(@RequestBody SysPermission sysPermission) {
        sysPermissionService.insert(sysPermission);
        return Result.success("成功插入权限数据");
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除权限接口")
    public Result delete(@PathVariable Long id) {
        sysPermissionService.delete(id);
        return Result.success("成功删除权限数据");
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改权限接口")
    public Result update(@RequestBody SysPermission sysPermission) {
        sysPermissionService.update(sysPermission);
        return Result.success("成功修改权限信息");
    }


    /**
     * 输入一个字符串，即根据该字符串进行模糊查询，返回所有符合条件的记录
     *
     * @param queryInfo
     * @return
     */
    @PostMapping("/findPage")
    @ApiOperation(value = "模糊查询权限接口")
    public Result findPage(@RequestBody QueryInfo queryInfo) {
        return sysPermissionService.findPage(queryInfo);
    }

    @ApiOperation(value = "查询所有的权限接口")
    @GetMapping("/findAll")
    public Result findAll() {
        return Result.success("成功查询到所有权限信息", sysPermissionService.findAll());
    }
}
