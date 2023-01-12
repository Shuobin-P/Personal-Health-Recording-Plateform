package com.google.personalhealthrecordingplateform.util;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/12/16 21:35
 */
@Component
public class DateUtils {
    private final String YYYY_MM_DD = "yyyy-MM-dd";

    private final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 返回当前时间字符串 年月日时分秒
     *
     * @return
     */
    public String getCurrentDate() {
        SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        return format.format(new Date());
    }

    public String transformTimestampToDate(long timestamp) {
        Date date = new Date(timestamp * 1000);
        return parseDate(date, YYYY_MM_DD);
    }

    public String parseDate(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }


    /**
     * 根据日期获得今天是星期几
     *
     * @param date
     * @return
     */
    public String getDayOfWeek(Date date) {
        String[] weeks = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        return weeks[this.getDayIndexOfWeek(date)];
    }

    public int getDayIndexOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        return week_index;
    }

    public Date getDateBeforeORAfterToday(int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, n);
        return calendar.getTime();
    }

    public Date getDateBeforeORAfterSpecificDate(Date date, int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, n);
        return calendar.getTime();
    }
}
