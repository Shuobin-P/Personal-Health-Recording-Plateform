package com.google.personalhealthrecordingplateform.entity;

import lombok.Data;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/1/10 16:12
 */
@Data
public class WxRun {

    private String openid;

    private String time;

    private Integer step;

    public WxRun(String openid, String time, Integer step) {
        this.openid = openid;
        this.time = time;
        this.step = step;
    }
}
