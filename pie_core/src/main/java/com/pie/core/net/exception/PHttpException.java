package com.pie.core.net.exception;

import com.pie.core.net.model.HttpCode;

import retrofit2.HttpException;

/**
 * @author:zjh
 * @date:2018/8/27
 * @Description：API异常统一管理
 */
public class PHttpException extends Exception{

    private final int CODE;
    private String MESSAGE;

    public PHttpException(Throwable throwable, int code) {
        CODE = code;
        MESSAGE = throwable.getMessage();
    }


    public static PHttpException handlerException(Throwable throwable){
        PHttpException ex;
        if (throwable instanceof HttpException){
            HttpException httpException = (HttpException) throwable;
        }
        return null;
    }


    public int getCode() {
        return CODE;
    }

    public String getMessage() {
        return MESSAGE;
    }
}
