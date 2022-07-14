package com.google.personalhealthrecordingplateform.utils;

import lombok.Data;

@Data
public class Result {
    private boolean flag;
    private String message;
    private Object data;

    public Result() {
    }

    public Result(boolean flag, String message, Object data) {
        this.flag = flag;
        this.message = message;
        this.data = data;
    }

    public static Result success(String message, Object data) {
        return new Result(true, message, data);
    }
}
