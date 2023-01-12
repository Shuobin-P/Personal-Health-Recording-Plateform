package com.google.personalhealthrecordingplateform.mapper;

import com.google.personalhealthrecordingplateform.entity.WxRun;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/1/10 16:57
 */
@Mapper
public interface WxRunMapper {

    WxRun find(String openID, String date);

    void update(WxRun wxStep);

    void insert(WxRun wxStep);

    void batchInsert(List<WxRun> list);
}
