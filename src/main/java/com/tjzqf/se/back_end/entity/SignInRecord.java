package com.tjzqf.se.back_end.entity;

import com.tjzqf.se.back_end.entity.BaseEntity;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    public class SignInRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

      private Integer signInRecordId;

    private String signInRecordTitle;

    private LocalDateTime signInRecordLaunchTime;

    private LocalTime signInRecordDuration;

    private String signInRecordState;

    private String signInRecordClassId;


}
