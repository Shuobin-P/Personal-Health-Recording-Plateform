package com.google.personalhealthrecordingplateform;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.personalhealthrecordingplateform.mapper.SysUserMapper;
import com.google.personalhealthrecordingplateform.entity.SysUser;
import com.google.personalhealthrecordingplateform.util.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class PersonalhealthrecordingplateformApplicationTests {
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    SysUserMapper sysUserMapper;

    @Test
    public void test() throws JsonProcessingException {
        System.out.println(new ObjectMapper().writeValueAsString(Result.fail("失败")));
    }

    @Test
    public void test1() {
        List<SysUser> list = sysUserMapper.findAll();
        for (SysUser l :
                list) {
            System.out.println(l);
        }

    }

    @Test
    public void test2() {
        String str = "Bearer eyJhbGciOiJIUzUxMiJ9.e";
        System.out.println(str.startsWith(tokenHead));
    }


}
