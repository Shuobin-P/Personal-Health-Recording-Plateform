package com.google.personalhealthrecordingplateform.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.personalhealthrecordingplateform.entity.SportNews;
import com.google.personalhealthrecordingplateform.exception.SportNewsException;
import com.google.personalhealthrecordingplateform.service.SportNewsService;
import com.google.personalhealthrecordingplateform.util.PageResult;
import com.google.personalhealthrecordingplateform.util.QueryInfo;
import com.google.personalhealthrecordingplateform.util.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/12/16 12:12
 */
@RestController
@RequestMapping("/sport")
public class SportNewsController {
    private SportNewsService sportNewsService;

    @Autowired
    public SportNewsController(SportNewsService sportNewsService) {
        this.sportNewsService = sportNewsService;
    }

    @ApiOperation(value = "添加运动咨询信息")
    @PostMapping("/insert")
    public Result insert(@RequestBody SportNews sportNews) throws SportNewsException {
        try {
            sportNewsService.insert(sportNews);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SportNewsException("服务器内部错误，添加运动咨询信息失败");
        }
        return Result.success("成功添加运动咨询信息");
    }

    @ApiOperation(value = "删除运动咨询信息")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Long id) throws SportNewsException {
        try {
            if (id == null) {
                throw new SportNewsException("请提供待删除的运动ID");
            }
            sportNewsService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SportNewsException("服务器内部错误，删除运动咨询信息失败");
        }
        return Result.success("成功删除运动咨询信息");
    }

    @ApiOperation(value = "修改运动咨询信息")
    @PutMapping("/update")
    public Result update(@RequestBody SportNews sportNews) throws SportNewsException {
        try {
            sportNewsService.update(sportNews);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SportNewsException("服务器内部错误，修改运动咨询信息失败");
        }
        return Result.success("成功修改运动咨询信息");
    }

    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryInfo queryInfo) {
        PageHelper.startPage(queryInfo.getPageNumber(), queryInfo.getPageSize());
        Page<SportNews> page = sportNewsService.findPage(queryInfo.getQueryString());
        return PageResult.pageResult(page.getTotal(), page.getResult());
    }
}
