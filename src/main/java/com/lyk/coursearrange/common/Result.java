package com.lyk.coursearrange.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author: 15760
 * @Date: 2020/3/13
 * @Descripe: 响应信息
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    // 响应码
    private int code;

    // 信息
    private String message;

    // 数据
    private T data;


    private Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @JsonIgnore
    public boolean isSuccess() {
        // 0成功，1失败
        return this.code == ResponseCode.SUCCESS.getCode();
    }


    public static <T> Result<T> ofSuccess() {
        return new Result<T>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDesc());
    }


    public static <T> Result<T> ofSuccess(T obj) {
        return new Result<T>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDesc(), obj);
    }


    public static <T> Result<T> ofSuccess(int code, String msg, T obj) {
        return new Result<T>(code, msg, obj);
    }


    public static <T> Result<T> ofSuccess(String msg) {
        return new Result<T>(ResponseCode.SUCCESS.getCode(), msg);
    }


    public static <T> Result<T> ofSuccess(String msg, T obj) {
        return new Result<T>(ResponseCode.SUCCESS.getCode(), msg, obj);
    }


    public static <T> Result<T> ofError(int code, String msg, T obj) {
        return new Result<T>(code, msg, obj);
    }


    public static <T> Result<T> ofError() {
        return new Result<T>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
    }


    public static <T> Result<T> ofError(String msg) {
        return new Result<T>(ResponseCode.ERROR.getCode(), msg);
    }


    public static <T> Result<T> ofError(T obj) {
        return new Result<T>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc(), obj);
    }


    public static <T> Result<T> ofError(String msg, T obj) {
        return new Result<T>(ResponseCode.ERROR.getCode(), msg, obj);
    }
}
