package com.lee.oa.execption;

import com.lee.oa.pojo.Response;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @ClassName GlobalException
 * @Description 全局异常处理
 * @Author lihongliang
 * @Date 2025/10/27 16:54
 * @Version 1.0
 */
@RestControllerAdvice // 控制器的增强类, 如果出现异常并符合定义的错误处理逻辑, 则会进入此方法进行处理
public class GlobalException {
    @ExceptionHandler(SQLException.class)
    public Response customSqlException(SQLException e) {
        if (e instanceof SQLIntegrityConstraintViolationException) {
            return Response.error("该数据有关联数据, 操作失败!");
        }
        return Response.error("数据库异常, 操作失败!");
    }
}
