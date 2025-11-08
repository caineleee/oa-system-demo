package com.lee.oa.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @ClassName Nation
 * @Description 民族实体类
 * @Author lihongliang
 * @Date 2025/11/8 10:09
 * @Version 1.0
 */
@Data
@TableName("t_nation")
@Schema(description = "民族")
public class Nation {

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "民族")
    @TableField(value = "name")
    private String name;
}
