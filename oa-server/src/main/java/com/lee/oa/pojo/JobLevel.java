package com.lee.oa.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName JobLevel
 * @Description 职称实体类
 * @Author lihongliang
 * @Date 2025/10/27 19:03
 * @Version 1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("t_job_level")
@Schema(name = "JobLevel对象实体类", description = "职称实体类")
public class JobLevel implements Serializable {
    @Schema(name = "id", description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(name = "name", description = "职称名称")
    private String name;

    @Schema(name = "titleLevel", description = "职称等级")
    private String titleLevel;

    @Schema(name = "createDate", description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDateTime createDate;

    @Schema(name = "enabled", description = "是否启用")
    private Boolean enabled;
}
