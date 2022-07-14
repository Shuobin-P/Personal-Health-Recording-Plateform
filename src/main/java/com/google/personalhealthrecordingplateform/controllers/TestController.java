package com.google.personalhealthrecordingplateform.controllers;


import com.google.personalhealthrecordingplateform.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/test")
    public Result test() {
        return Result.success("成功访问", "你好");
    }
}
