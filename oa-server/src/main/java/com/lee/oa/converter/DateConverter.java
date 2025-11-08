package com.lee.oa.converter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @ClassName DateConverter
 * @Description 日期转换
 * @Author lihongliang
 * @Date 2025/11/7 19:17
 * @Version 1.0
 */
@Component
public class DateConverter implements Converter<String, LocalDate> {


    private static final Logger logger = LoggerFactory.getLogger(DateConverter.class);

    /**
     * 日期转换 String -> LocalDate
     * @param source 源字符串
     * @return LocalDate
     */
    @Override
    public LocalDate convert(String source) {
        try {
            return LocalDate.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e) {
            logger.error("日期转换错误: " + e);
        }
        return null;
    }
}
