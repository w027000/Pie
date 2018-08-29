package com.pie.core.net.exception;

import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.pie.core.net.model.HttpCode;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import javax.net.ssl.SSLHandshakeException;

import retrofit2.HttpException;

/**
 * @author:zjh
 * @date:2018/8/27
 * @Description：API异常统一管理
 */
public class PHttpException extends Exception{

    private final int code;
    private String message;

    public PHttpException(Throwable throwable, int code) {
        this.code = code;
        this.message = throwable.getMessage();
    }


    public static PHttpException handleException(Throwable throwable){
        PHttpException ex;
        if (throwable instanceof HttpException){
            HttpException httpException = (HttpException) throwable;
            ex = new PHttpException(throwable,HttpCode.Request.HTTP_ERROR);
            switch (httpException.code()){
                case HttpCode.Http.UNAUTHORIZED:
                case HttpCode.Http.FORBIDDEN:
                case HttpCode.Http.NOT_FOUND:
                case HttpCode.Http.REQUEST_TIMEOUT:
                case HttpCode.Http.GATEWAY_TIMEOUT:
                case HttpCode.Http.INTERNAL_SERVER_ERROR:
                case HttpCode.Http.BAD_GATEWAY:
                case HttpCode.Http.SERVICE_UNAVAILABLE:
                default:
                    ex.message = "NETWORK_ERROR";
                    break;
            }
            return ex;
        }else if(throwable instanceof JsonParseException || throwable instanceof JSONException || throwable instanceof ParseException){
            ex = new PHttpException(throwable, HttpCode.Request.PARSE_ERROR);
            ex.message = "PARSE_ERROR";
            return ex;
        }else if(throwable instanceof ConnectException){
            ex = new PHttpException(throwable, HttpCode.Request.NETWORK_ERROR);
            ex.message = "NETWORK_ERROR";
            return ex;
        }else if(throwable instanceof SSLHandshakeException){
            ex = new PHttpException(throwable, HttpCode.Request.SSL_ERROR);
            ex.message = "SSL_ERROR";
            return ex;
        }else if(throwable instanceof SocketTimeoutException){
            ex = new PHttpException(throwable, HttpCode.Request.TIMEOUT_ERROR);
            ex.message = "TIMEOUT_ERROR";
            return ex;
        }else{
            ex = new PHttpException(throwable, HttpCode.Request.UNKNOWN);
            ex.message = "UNKNOWN";
            return ex;
        }
    }


    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
