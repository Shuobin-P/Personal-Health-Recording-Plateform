package com.google.personalhealthrecordingplateform.security.service;

import com.google.personalhealthrecordingplateform.mapper.MiniUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/12/30 15:59
 */
@Service("miniUserDetailsServiceImp")
public class MiniUserDetailsServiceImpl implements UserDetailsService {
    private MiniUserMapper miniUserMapper;

    @Autowired
    public MiniUserDetailsServiceImpl(MiniUserMapper miniUserMapper) {
        this.miniUserMapper = miniUserMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return miniUserMapper.findUserByOpenID(username);
    }
}
