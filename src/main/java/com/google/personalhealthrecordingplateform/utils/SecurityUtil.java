package com.google.personalhealthrecordingplateform.utils;

import com.google.personalhealthrecordingplateform.model.SysUser;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static SysUser getUserInfo() {
        try {
            SysUser sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            sysUser.setPassword(null);
            return sysUser;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getUsername() {
        return getUserInfo().getUsername();
    }

    public static Long getUserId() {
        return getUserInfo().getId();
    }
}
