package com.google.personalhealthrecordingplateform.controllers;

import com.google.personalhealthrecordingplateform.services.SysUserService;
import com.google.personalhealthrecordingplateform.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/login")
    public Result Result() {
        System.out.println("进入LoginController");
        return Result.success("成功", sysUserService.findAll());
    }
}
