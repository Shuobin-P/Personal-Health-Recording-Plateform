package com.google.personalhealthrecordingplateform.service.impl;

import com.github.pagehelper.Page;
import com.google.personalhealthrecordingplateform.entity.SportNews;
import com.google.personalhealthrecordingplateform.mapper.SportNewsMapper;
import com.google.personalhealthrecordingplateform.service.SportNewsService;
import com.google.personalhealthrecordingplateform.util.DateUtils;
import com.google.personalhealthrecordingplateform.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/12/16 15:02
 */
@Service
public class SportNewsServiceImpl implements SportNewsService {
    private final SportNewsMapper sportNewsMapper;

    @Autowired
    public SportNewsServiceImpl(SportNewsMapper sportNewsMapper) {
        this.sportNewsMapper = sportNewsMapper;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void insert(SportNews sportNews) {
        sportNews.setCreateName(SecurityUtils.getUsername());
        sportNews.setCreateTime(DateUtils.getDateTime());
        sportNewsMapper.insert(sportNews);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void delete(Long id) {
        sportNewsMapper.delete(id);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(SportNews sportNews) {
        sportNews.setUpdateName(SecurityUtils.getUsername());
        sportNews.setUpdateTime(DateUtils.getDateTime());
        sportNewsMapper.update(sportNews);
    }

    @Override
    public Page<SportNews> findPage(String queryInfo) {
        return sportNewsMapper.findPage(queryInfo);
    }
}
