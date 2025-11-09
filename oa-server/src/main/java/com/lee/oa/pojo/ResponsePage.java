package com.lee.oa.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @ClassName ResponsePage
 * @Description 分页公共返回对象
 * @Author lihongliang
 * @Date 2025/11/7 19:00
 * @Version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "分页公共返回对象")
public class ResponsePage extends Response {

    @Schema(name = "总记录数")
    private Long total;


    public static ResponsePage success(Long total, Object data) {
        ResponsePage responsePage = new ResponsePage(total);
        responsePage.setCode(200);
        responsePage.setMessage("success");
        responsePage.setData(data);
        return responsePage;
    }

}
