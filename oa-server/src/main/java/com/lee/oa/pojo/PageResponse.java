package com.lee.oa.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName PageResponse
 * @Description 分页公共返回对象
 * @Author lihongliang
 * @Date 2025/11/7 19:00
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "分页公共返回对象")
public class PageResponse {

    private Long total;

    private List<?> data;
}
