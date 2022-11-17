package com.google.personalhealthrecordingplateform.controller;

import com.google.personalhealthrecordingplateform.entity.SysMenu;
import com.google.personalhealthrecordingplateform.service.SysMenuService;
import com.google.personalhealthrecordingplateform.util.QueryInfo;
import com.google.personalhealthrecordingplateform.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author W&F
 */
@Api(tags = "菜单数据接口")
@RestController
@RequestMapping("/menu")
public class SysMenuController {
    private SysMenuService sysMenuService;

    @Autowired
    public SysMenuController(SysMenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }

    @PostMapping("/insert")
    @ApiOperation(value = "增加菜单接口")
    public Result insert(@RequestBody SysMenu sysMenu) {
        sysMenuService.insert(sysMenu);
        return Result.success("成功插入菜单数据");
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除菜单接口")
    public Result delete(@PathVariable Long id) {
        sysMenuService.delete(id);
        return Result.success("成功删除菜单数据");
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改菜单接口")
    public Result update(@RequestBody SysMenu sysMenu) {
        sysMenuService.update(sysMenu);
        return Result.success("成功修改菜单信息");
    }

    @ApiOperation(value = "查询所有的父级菜单及其子菜单")
    @GetMapping("/findParent")
    public Result findParents() {
        return sysMenuService.findParents();
    }


    /**
     * 前端的菜单管理页面数据来源
     * @param queryInfo
     * @return
     */
    @ApiOperation(value = "模糊查询菜单接口")
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryInfo queryInfo) {
        return sysMenuService.findPage(queryInfo);
    }
}
