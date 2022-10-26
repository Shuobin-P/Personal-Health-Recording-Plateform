package com.google.personalhealthrecordingplateform.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysRole implements Serializable {
    private Long id;
    private String label;
    private String code;
    private boolean status;

}
