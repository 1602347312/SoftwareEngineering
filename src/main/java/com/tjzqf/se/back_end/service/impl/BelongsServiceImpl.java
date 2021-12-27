package com.tjzqf.se.back_end.service.impl;

import com.tjzqf.se.back_end.entity.Belongs;
import com.tjzqf.se.back_end.mapper.BelongsMapper;
import com.tjzqf.se.back_end.service.BelongsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zqf
 * @since 2021-12-27
 */
@Service
public class BelongsServiceImpl extends ServiceImpl<BelongsMapper, Belongs> implements BelongsService {

    @Resource
    private BelongsMapper belongsMapper;

    public List<Belongs> getAllClasses(int user_id) {
        return belongsMapper.getAllClasses(user_id);
    }
}
