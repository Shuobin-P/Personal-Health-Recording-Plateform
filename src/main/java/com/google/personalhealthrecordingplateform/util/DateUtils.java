package com.google.personalhealthrecordingplateform.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/12/16 21:35
 */
public class DateUtils {
    private static final String YYYY_MM_DD = "yyyy-MM-dd";

    private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 返回当前时间字符串 年月日时分秒
     *
     * @return
     */
    public static String getDateTime() {
        SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        return format.format(new Date());
    }
}
