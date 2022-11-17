package com.google.personalhealthrecordingplateform.controller;

import com.google.personalhealthrecordingplateform.entity.SysRole;
import com.google.personalhealthrecordingplateform.service.SysRoleService;
import com.google.personalhealthrecordingplateform.util.PageResult;
import com.google.personalhealthrecordingplateform.util.QueryInfo;
import com.google.personalhealthrecordingplateform.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/10/26 22:50
 */

@Api(tags = "角色数据接口")
@RestController
@RequestMapping("/role")
public class SysRoleController {
    private final SysRoleService sysRoleService;

    @Autowired
    public SysRoleController(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    @PostMapping("/insert")
    @ApiOperation(value = "增加角色接口")
    public Result insert(@RequestBody SysRole sysRole) {
        return sysRoleService.insert(sysRole);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除角色接口")
    public Result delete(@PathVariable Long id) {
        sysRoleService.delete(id);
        return Result.success("成功删除角色数据");
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改角色接口")
    public Result update(@RequestBody SysRole sysRole) {
        sysRoleService.update(sysRole);
        return Result.success("成功修改角色信息");
    }


    /**
     * 前端的角色管理页面数据来源
     *
     * @param queryInfo 模糊查询角色信息
     * @return 模糊查询结果
     */
    @ApiOperation(value = "模糊查询角色接口")
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryInfo queryInfo) {
        List<SysRole> list = sysRoleService.findPage(queryInfo);
        return PageResult.pageResult(list.size(), list);
    }
}
