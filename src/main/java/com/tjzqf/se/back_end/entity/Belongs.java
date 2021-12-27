package com.tjzqf.se.back_end.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.tjzqf.se.back_end.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author zqf
 * @since 2021-12-27
 */
@Data
  @EqualsAndHashCode(callSuper = true)
    public class Belongs extends BaseEntity {

    private static final long serialVersionUID = 1L;

      private Integer studentId;

    private String classId;

  @TableField(exist = false)
    private List<Class> classes;


}
