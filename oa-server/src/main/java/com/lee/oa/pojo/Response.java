package com.lee.oa.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;

/**
 * 统一响应结果封装类
 * 用于封装所有HTTP接口的返回结果，保证接口返回格式的一致性
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response implements Serializable {

//    private static final long serialVersionUID = -8091879091924046844L;
    /**
     * 响应状态码，200表示成功，500表示失败
     */
    private long code;
    
    /**
     * 响应消息，描述操作结果
     */
    private String message;
    
    /**
     * 响应数据，包含具体的业务数据
     */
    private Object data;
//    private String jwt;

    /**
     * 成功响应（无数据）
     * @param message 响应消息
     * @return Response对象
     */
    public static Response success(String message) {
        return new Response(200, message, null);
    }

    /**
     * 成功响应（含数据）
     * @param message 响应消息
     * @param data 响应数据
     * @return Response对象
     */
    public static Response success(String message, Object data) {
        return new Response(200, message, data);
    }

    /**
     * 错误响应（无数据）
     * @param message 错误消息
     * @return Response对象
     */
    public static Response error(String message) {
        return new Response(500, message, null);
    }

    /**
     * 错误响应（含数据）
     * @param message 错误消息
     * @param data 错误数据
     * @return Response对象
     */
    public static Response error(String message, Object data) {
        return new Response(500, message, data);
    }
}