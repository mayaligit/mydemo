package com.cmic.attendance.exception;

import com.cmic.saas.base.web.RestException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 何荣
 * @create 2017-10-26 17:08
 **/
@RestControllerAdvice
public class RestExceptionHandler {
    private static final Map<String,String> msgMap = new HashMap<>();

    /**
     * 处理全局的系统异常
     */
    @ExceptionHandler(Exception.class)
    public Map<String,String> handleException(Exception e){
        e.printStackTrace();
        msgMap.put("msg","网路异常,请稍后再试");
        return msgMap;
    }

    /**
     * 自定义异常处理
     */
    @ExceptionHandler(RestException.class)
    public Map<String,String> handleRestException(RestException e){
        e.printStackTrace();
        msgMap.put("msg",e.getMessage());
        return msgMap;
    }

    /**
     * 处理SQL异常
     * @param e
     * @return
  */
    @ExceptionHandler(SQLException.class)
    public Map<String,String> handleSQLException(SQLException e){
        e.printStackTrace();
        msgMap.put("msg","数据查询语句异常");
        return msgMap;
    }

    /**
     * 字符串转数字格式化异常
     * @param e
     * @return
     */
    @ExceptionHandler(NumberFormatException.class)
    public Map<String,String> handleNumberFormatException(NumberFormatException e){
        e.printStackTrace();
        msgMap.put("msg","传递参数格式有误");
        return msgMap;
    }

    /**
     * 处理日期格式化异常
     * @param e
     * @return
     */
    @ExceptionHandler(ParseException.class)
    public Map<String,String> handleParseException(ParseException e){
        e.printStackTrace();
        msgMap.put("msg","日期格式错误");
        return msgMap;
    }
    /**
     * 处理日期格式化异常
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Map<String,String> handleJsonParseException(HttpMessageNotReadableException e){
        e.printStackTrace();
        msgMap.put("msg","Json数据格式错误");
        return msgMap;
    }

}
