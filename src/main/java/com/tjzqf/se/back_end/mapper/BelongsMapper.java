package com.tjzqf.se.back_end.mapper;

import com.tjzqf.se.back_end.entity.Belongs;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zqf
 * @since 2021-12-27
 */
public interface BelongsMapper extends BaseMapper<Belongs> {

    List<Belongs> getAllClasses(int user_id);

}
