package com.tjzqf.se.back_end.mapper;

import com.tjzqf.se.back_end.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zqf
 * @since 2021-12-26
 */
public interface CourseMapper extends BaseMapper<Course> {
    @Select("select * from course where student_id=#{studentId}")
    List<Course> selectByStudentId(Integer studentId);

}
