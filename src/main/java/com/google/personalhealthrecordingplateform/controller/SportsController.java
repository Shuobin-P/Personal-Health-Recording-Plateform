package com.google.personalhealthrecordingplateform.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.personalhealthrecordingplateform.entity.Sport;
import com.google.personalhealthrecordingplateform.exception.SportException;
import com.google.personalhealthrecordingplateform.service.SportService;
import com.google.personalhealthrecordingplateform.util.PageResult;
import com.google.personalhealthrecordingplateform.util.QueryInfo;
import com.google.personalhealthrecordingplateform.util.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/12/21 21:08
 */
@Slf4j
@RestController
@RequestMapping("/motion")
public class SportsController {
    private final SportService sportService;

    @Autowired
    public SportsController(SportService sportService) {
        this.sportService = sportService;
    }

    @PostMapping("/add")
    public Result insert(@RequestBody Sport sport) throws SportException {
        try {
            sportService.insert(sport);
        } catch (Exception e) {
            throw new SportException("服务器内部错误，插入运动项目失败");
        }
        return Result.success("成功插入运动项目");
    }

    @DeleteMapping("delete")
    public Result delete(Long id) throws SportException {
        try {
            sportService.delete(id);
        } catch (Exception e) {
            throw new SportException("服务器内部错误，删除运动项目失败");
        }
        return Result.success("成功删除运动项目");
    }

    @PostMapping("/edit")
    public Result update(@RequestBody Sport sport) throws SportException {
        try {
            sportService.update(sport);
        } catch (Exception e) {
            throw new SportException("服务器内部错误，修改运动项目失败");
        }
        return Result.success("成功修改运动项目");
    }

    @GetMapping("/{id}")
    public Result findExplicitSportInfo(@PathVariable Long id) {
        return Result.success("成功查询运动信息", sportService.findExplicitSportInfo(id));
    }

    @ApiOperation(value = "分页查询运动项目信息", httpMethod = "POST")
    @PostMapping("/findPage")
    Result findPage(@RequestBody QueryInfo queryInfo) {
        PageHelper.startPage(queryInfo.getPageNumber(), queryInfo.getPageSize());
        Page<Sport> page = sportService.findPage(queryInfo.getQueryString());
        return PageResult.pageResult(page.getTotal(), page.getResult());
    }
}
