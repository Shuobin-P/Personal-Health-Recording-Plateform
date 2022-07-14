package com.google.personalhealthrecordingplateform.services;

import com.google.personalhealthrecordingplateform.utils.Result;

public interface SysUserService {

    Result findAll();

    Result selectByID(Integer id);
}
