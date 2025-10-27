package com.lee.oa.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @ClassName Position
 * @Description 职位实体类
 * @Author lihongliang
 * @Date 2025/10/25 19:05
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_position")
@Schema(name = "职位", description = "职位信息实体类")
public class Position {

    @Schema(name = "id", description = "职位ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(name = "name", description = "职位名称")
    private String name;

    @Schema(name = "createDate", description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createDate;

    @Schema(name = "enabled", description = "是否启用")
    private Boolean enabled;
}
