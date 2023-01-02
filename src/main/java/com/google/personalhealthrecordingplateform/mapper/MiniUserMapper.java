package com.google.personalhealthrecordingplateform.mapper;

import com.google.personalhealthrecordingplateform.entity.MiniUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/1/1 16:55
 */
@Mapper
public interface MiniUserMapper {

    /**
     * 根据open_id找到用户相关信息
     *
     * @param open_id
     * @return
     */
    MiniUser findUserByOpenID(String open_id);
}
