package com.google.personalhealthrecordingplateform.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class SysUser implements UserDetails {
    private Long id;
    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "昵称")
    private String nickName;
    @ApiModelProperty(value = "性别")
    private Byte sex;
    @ApiModelProperty(value = "微信获取用户头像")
    private String avatar;
    @ApiModelProperty(value = "微信获取用户地址")
    private String address;
    @ApiModelProperty(value = "微信小程序用户唯一标识")
    private String openID;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "是否为管理员")
    private Byte admin;
    @ApiModelProperty(value = "用户状态")
    private Byte status;
    @ApiModelProperty(value = "电话号码")
    private String phoneNumber;
    @ApiModelProperty(value = "角色列表")
    private List<SysRole> roles;
    @ApiModelProperty(value = "用户对应的菜单列表")
    private List<SysMenu> menus;
    @ApiModelProperty(value = "权限")
    private List<SysPermission> permissions;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList();
        if (roles != null && roles.size() > 0) {
            roles.forEach(item -> list.add(new SimpleGrantedAuthority("ROLE_" + item.getCode())));
        }
        if (permissions != null && permissions.size() > 0) {
            permissions.forEach(item -> list.add(new SimpleGrantedAuthority(item.getCode())));
        }
        return list;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return status == 0 ? false : true;
    }

    public boolean isAdmin() {
        return this.admin == 1 ? true : false;
    }
}
