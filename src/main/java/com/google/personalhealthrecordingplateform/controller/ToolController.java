package com.google.personalhealthrecordingplateform.controller;

import com.google.personalhealthrecordingplateform.util.QiniuUtils;
import com.google.personalhealthrecordingplateform.util.Result;
import com.google.personalhealthrecordingplateform.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/11/18 21:42
 */
@Slf4j
@Api(tags = "工具接口")
@RestController
@RequestMapping("/tool")
public class ToolController {
    private final QiniuUtils qiniuUtils;

    @Autowired
    public ToolController(QiniuUtils qiniuUtils) {
        this.qiniuUtils = qiniuUtils;
    }

    @ApiOperation(value = "七牛云文件上传")
    @PostMapping("/upload")
    public Result uploadAvatar(@RequestBody MultipartFile file) throws IOException {
        //头像文件上传到后台，并存储到数据库中
        //那边上传不应该是上传到前端吗？，点击确定之后才是把前端的数据上传到数据库。
        //这个RequestBody是指请求体
        //MultipartFile只代表一个文件，那这里的变量名为file，则说明是指名为file的这个文件

        String url = qiniuUtils.upload((FileInputStream) file.getInputStream(),
                StringUtils.getRandomImgName(file.getOriginalFilename()));

        return Result.success("成功上传头像", url);
    }
}
