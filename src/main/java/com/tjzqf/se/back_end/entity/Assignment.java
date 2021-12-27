package com.tjzqf.se.back_end.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.tjzqf.se.back_end.entity.BaseEntity;
import java.time.LocalDate;
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
    public class Assignment extends BaseEntity {

    private static final long serialVersionUID = 1L;

      @TableId(value = "assignment_id", type = IdType.AUTO)
      private Integer assignmentId;

    private String assignmentTitle;

    private String assignmentRequirement;

    private LocalDate assignmentDeadline;

    private Integer assignmentTotalScore;

    private String assignmentClassId;


}
