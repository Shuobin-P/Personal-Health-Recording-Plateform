package com.google.personalhealthrecordingplateform.controller;


import com.google.personalhealthrecordingplateform.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "测试接口")
public class TestController {


    @ApiOperation(value = "测试Test")
    @GetMapping("/test")
    @PreAuthorize("hasAuthority('USER_DELETE')")
    public Result test() {
        return Result.success("成功", 1);
    }




}
