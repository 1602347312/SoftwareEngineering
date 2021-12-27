package com.tjzqf.se.back_end.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.tjzqf.se.back_end.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author zqf
 * @since 2021-12-26
 */
@Data
  @EqualsAndHashCode(callSuper = true)
    public class Student extends BaseEntity {

    private static final long serialVersionUID = 1L;

      @TableId(value = "user_id", type = IdType.AUTO)
      private Integer userId;

    private Integer studentId;

    private String studentName;

    private String password;


}
