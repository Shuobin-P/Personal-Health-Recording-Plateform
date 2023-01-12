package com.google.personalhealthrecordingplateform;

import com.google.personalhealthrecordingplateform.entity.WxRun;
import com.google.personalhealthrecordingplateform.mapper.WxRunMapper;
import com.google.personalhealthrecordingplateform.util.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/1/10 16:33
 */
public class DateUtilsTest {
    @Autowired
    WxRunMapper wxRunMapper;

    @Test
    public void test() {
        DateUtils dateUtils = new DateUtils();
        int days = dateUtils.getDayIndexOfWeek(new Date());
        List<WxRun> thisWeek = new ArrayList<>();
        for (int i = days; i >= 0; i--) {
            thisWeek.add(
                    wxRunMapper.find("ouUrt5PzNnDh8SiBBr_WyA3_myq0",
                    dateUtils.parseDate(dateUtils.getDateBeforeORAfterToday(-i), "yyyy-MM-dd"))
            );
        }
    }
}
