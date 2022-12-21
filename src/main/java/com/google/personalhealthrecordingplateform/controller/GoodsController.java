package com.google.personalhealthrecordingplateform.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.personalhealthrecordingplateform.dto.GoodsDTO;
import com.google.personalhealthrecordingplateform.entity.Goods;
import com.google.personalhealthrecordingplateform.exception.GoodsException;
import com.google.personalhealthrecordingplateform.service.GoodsService;
import com.google.personalhealthrecordingplateform.util.PageResult;
import com.google.personalhealthrecordingplateform.util.QueryInfo;
import com.google.personalhealthrecordingplateform.util.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/12/16 22:25
 */
@Slf4j
@RestController
@RequestMapping("/goods")
public class GoodsController {
    private final GoodsService goodsService;

    @Autowired
    public GoodsController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @ApiOperation(value = "批量导入商品信息", httpMethod = "POST")
    @PostMapping(value = "/batchImport")
    public Result importBatchOfGoods(@RequestParam("goods") MultipartFile multipartFile) {
        try {
            goodsService.importGoodsExcel(multipartFile);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("服务器内部错误，插入数据失败");
        }
        return Result.success("已成功批量导入数据");
    }

    @ApiOperation(value = "导出商品信息", httpMethod = "GET")
    @GetMapping(value = "/batchExport")
    public void exportBatchOfGoods(HttpServletResponse response) throws GoodsException {
        try {
            goodsService.exportGoodsExcel(response);
        } catch (IOException e) {
            e.printStackTrace();
            throw new GoodsException("服务器内部错误，导出商品信息失败");
        }
    }

    @ApiOperation(value = "删除商品信息", httpMethod = "DELETE")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) throws GoodsException {
        if (id == null) {
            return Result.fail("商品ID不能为空");
        }
        try {
            goodsService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GoodsException("服务器内部错误，删除商品信息失败");
        }
        return Result.success("成功删除商品信息");
    }

    @ApiOperation(value = "更新商品信息", httpMethod = "POST")
    @PostMapping("/update")
    public Result update(@RequestBody Goods goods) throws GoodsException {
        try {
            goodsService.update(goods);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GoodsException("服务器内部错误，修改商品信息失败");
        }
        return Result.success("成功修改商品信息");
    }

    @ApiOperation(value = "分页查询商品信息", httpMethod = "POST")
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryInfo queryInfo) {
        PageHelper.startPage(queryInfo.getPageNumber(), queryInfo.getPageSize());
        Page<GoodsDTO> page = goodsService.findPage(queryInfo.getQueryString());
        return PageResult.pageResult(page.getTotal(), page.getResult());
    }
}
