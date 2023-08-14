package com.google.personalhealthrecordingplateform.controller;

import com.google.personalhealthrecordingplateform.entity.SysUser;
import com.google.personalhealthrecordingplateform.service.SysUserService;
import com.google.personalhealthrecordingplateform.util.PageResult;
import com.google.personalhealthrecordingplateform.util.QiniuUtils;
import com.google.personalhealthrecordingplateform.util.QueryInfo;
import com.google.personalhealthrecordingplateform.util.Result;
import com.qiniu.common.QiniuException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/11/18 20:20
 */

@Api(tags = "用户数据接口")
@Slf4j
@RestController
@RequestMapping("/user")
public class SysUserController {

    private final QiniuUtils qiniuUtils;
    private final SysUserService sysUserService;

    @Autowired
    public SysUserController(QiniuUtils qiniuUtils, SysUserService sysUserService) {
        this.qiniuUtils = qiniuUtils;
        this.sysUserService = sysUserService;
    }

    @ApiOperation(value = "插入用户数据接口")
    @PostMapping("insert")
    public Result insert(@RequestBody SysUser sysUser) {
        return sysUserService.insert(sysUser);
    }

    @ApiOperation(value = "删除用户接口")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) throws QiniuException {
        try {
            qiniuUtils.delete(sysUserService.findAvatar(id));
        } catch (QiniuException e) {
            e.printStackTrace();
        } finally {
            sysUserService.delete(id);
        }
        return Result.success("删除成功");
    }

    @ApiOperation(value = "修改用户数据接口")
    @PutMapping("/update")
    public Result update(@RequestBody SysUser sysUser) {
        log.info("进入update用户信息方法");
        return sysUserService.update(sysUser);
    }


    @ApiOperation(value = "模糊查询用户接口")
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryInfo queryInfo) {
        List<SysUser> list = sysUserService.findPage(queryInfo);
        return PageResult.pageResult(list.size(), list);
    }
}
