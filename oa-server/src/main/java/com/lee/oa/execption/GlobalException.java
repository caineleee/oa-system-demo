package com.lee.oa.execption;

import com.lee.oa.pojo.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(GlobalException.class);

    @ExceptionHandler(SQLException.class)
    public Response customSqlException(SQLException e) {
        if (e instanceof SQLIntegrityConstraintViolationException) {
            logger.info("SQLIntegrityConstraintViolationException: " + e.getMessage());
            return Response.error("该数据有关联数据, 操作失败!");
        }
        logger.info("SQLException: " + e.getMessage());
        return Response.error("数据库异常, 操作失败!");
    }
}
