package com.google.personalhealthrecordingplateform.model;

import lombok.Data;

@Data
public class SysMenu {
    private Long id;

    private Long parent_id;

    private String title;

    private String path;

    private String icon;
}
