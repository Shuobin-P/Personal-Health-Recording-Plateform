package com.google.personalhealthrecordingplateform;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SysUserTest {
    @Autowired
    private com.google.personalhealthrecordingplateform.mapper.SysUserMapper sysUserMapper;

    @Test
    public void testSysUserMapper() {
        List list = sysUserMapper.findChildrenMenus((long) 1, (long) 2);
        System.out.println(list.size());
    }
}
