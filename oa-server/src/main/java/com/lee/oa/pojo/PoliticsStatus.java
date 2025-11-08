package com.lee.oa.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @ClassName PoliticsStatus
 * @Description 政治面貌实体类
 * @Author lihongliang
 * @Date 2025/11/8 10:21
 * @Version 1.0
 */
@Data
@TableName("t_politics_status")
@Schema(description = "政治面貌")
public class PoliticsStatus {
    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "政治面貌")
    @TableField("name")
    private String name;
}
