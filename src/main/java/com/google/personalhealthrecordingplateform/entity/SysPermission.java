package com.google.personalhealthrecordingplateform.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 31204
 */
@Data
public class SysPermission implements Serializable {
    private Long id;

    private String label;

    private String code;

    private boolean status;
}
