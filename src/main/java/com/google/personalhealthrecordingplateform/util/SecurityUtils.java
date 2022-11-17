package com.google.personalhealthrecordingplateform.util;

import com.google.personalhealthrecordingplateform.entity.SysUser;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author W&F
 */
public class SecurityUtils {
    public static SysUser getUserInfo() {
        try {
            SysUser sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            sysUser.setPassword(null);
            sysUser.setName(sysUser.getUsername());
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
