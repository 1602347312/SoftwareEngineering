package com.tjzqf.se.back_end.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tjzqf.se.back_end.entity.Student;
import com.tjzqf.se.back_end.entity.Takes;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zqf
 * @since 2021-12-26
 */
public interface TakesMapper extends BaseMapper<Takes> {
    Page<Takes> getAll(Page<Takes> iPage);

}
