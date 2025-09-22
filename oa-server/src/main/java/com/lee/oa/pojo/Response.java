package com.lee.oa.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response implements Serializable {

//    private static final long serialVersionUID = -8091879091924046844L;
    private long code;
    private String message;
    private Object data;
//    private String jwt;

    /**
     * 响应成功
     * @param message
     * @return
     */
    public static Response success(String message) {
        return new Response(200, message, null);
    }

    /**
     * 响应成功
     * @param message
     * @param data
     * @return
     */
    public static Response success(String message, Object data) {
        return new Response(200, message, data);
    }

    /**
     * 响应失败
     * @param message
     * @return
     */
    public static Response error(String message) {
        return new Response(500, message, null);
    }

    /**
     * 响应失败
     * @param message
     * @param data
     * @return
     */
    public static Response error(String message, Object data) {
        return new Response(500, message, data);
    }
}