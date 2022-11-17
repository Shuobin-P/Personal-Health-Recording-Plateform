package com.google.personalhealthrecordingplateform;

import com.google.personalhealthrecordingplateform.entity.SysUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest

public class SysUserMapperTest {
    @Autowired
    private com.google.personalhealthrecordingplateform.mapper.SysUserMapper sysUserMapper;

    @Test
    public void testFindUserByUserName() {
        SysUser sysUser = sysUserMapper.findUserByUserName("admin");
        System.out.println(sysUser.getUsername());
        System.out.println(sysUser.getPassword());
        System.out.println(sysUser.getStatus());
    }

    @Test
    public void test() {
        List list  = sysUserMapper.findChildrenMenus((long) 1, (long) 2);
        System.out.println(list.size());

    }
}
