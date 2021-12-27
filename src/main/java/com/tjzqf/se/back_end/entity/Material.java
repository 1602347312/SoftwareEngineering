package com.tjzqf.se.back_end.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.tjzqf.se.back_end.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.sql.Blob;
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
    public class Material extends BaseEntity {

    private static final long serialVersionUID = 1L;

      @TableId(value = "material_id", type = IdType.AUTO)
      private Integer materialId;

    private String materialTitle;

    private LocalDateTime materialUploadTime;

    private Double materialSize;

    private Integer materialUploader;

    private String materialContent;

    private String materialClassId;


}
