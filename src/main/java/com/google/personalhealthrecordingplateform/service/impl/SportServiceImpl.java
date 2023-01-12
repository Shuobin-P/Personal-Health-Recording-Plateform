package com.google.personalhealthrecordingplateform.service.impl;

import com.github.pagehelper.Page;
import com.google.personalhealthrecordingplateform.entity.Sport;
import com.google.personalhealthrecordingplateform.mapper.SportMapper;
import com.google.personalhealthrecordingplateform.service.SportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/12/21 21:41
 */
@Service
public class SportServiceImpl implements SportService {
    private final SportMapper sportMapper;

    @Autowired
    public SportServiceImpl(SportMapper sportMapper) {
        this.sportMapper = sportMapper;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void insert(Sport sport) {
        sportMapper.insert(sport);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void delete(Long id) {
        sportMapper.delete(id);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(Sport sport) {
        sportMapper.update(sport);
    }

    @Override
    public Sport findExplicitSportInfo(Long id) {
        return sportMapper.findExplicitSportInfo(id);
    }

    @Override
    public Page<Sport> findPage(String queryString) {
        return sportMapper.findPage(queryString);
    }
}
