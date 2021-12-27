package com.tjzqf.se.back_end.entity;

import com.tjzqf.se.back_end.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
    public class SignIn extends BaseEntity {

    private static final long serialVersionUID = 1L;

      private Integer studentId;

    private Integer signInRecordId;

    private Boolean signInState;


}
