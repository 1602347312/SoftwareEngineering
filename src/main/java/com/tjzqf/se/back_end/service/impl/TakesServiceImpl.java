package com.tjzqf.se.back_end.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tjzqf.se.back_end.entity.Student;
import com.tjzqf.se.back_end.entity.Takes;
import com.tjzqf.se.back_end.mapper.StudentMapper;
import com.tjzqf.se.back_end.mapper.TakesMapper;
import com.tjzqf.se.back_end.service.TakesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zqf
 * @since 2021-12-26
 */
@Service
public class TakesServiceImpl extends ServiceImpl<TakesMapper, Takes> implements TakesService {
    @Resource
    TakesMapper takesMapper;

    public Page<Takes> getAll(Page<Takes> iPage) {
        return takesMapper.getAll(iPage);

    }

}
