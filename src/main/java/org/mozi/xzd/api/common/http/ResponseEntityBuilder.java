package org.mozi.xzd.api.common.http;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author xuzidong
 * @version V1.0.0

 * @description <p></p >
 * @since 2020/7/29 16:21
 */
public class ResponseEntityBuilder {


    private final static String MESSAGE_OK="操作成功！";
    public static ResponseEntity<Result<String>> bodyVoid( HttpStatus status,HttpHeaders headers,String message){

        return  body(status,headers,message,MESSAGE_OK);
    }

    public static  ResponseEntity<Result<String>> bodyVoid(HttpStatus status,HttpHeaders headers){
        return body(status,headers,null,MESSAGE_OK);
    }

    public static  ResponseEntity<Result<String>> success(){
        return body(null,null,null,MESSAGE_OK);
    }

    public static <T> ResponseEntity<Result<T>> body(HttpStatus status,HttpHeaders headers, String message, T body){
        Result<T> resultBody=new Result<>();
        resultBody.setData(body);
        if(Objects.isNull(status)) {
            status=HttpStatus.OK;
        }
        resultBody.setCode(status.value());
        if(!StringUtils.hasLength(message)){
            message=status.getReasonPhrase();
        }
        resultBody.setMessage(message);
        ResponseEntity.BodyBuilder builder = ResponseEntity.status(status.value());

        if(Objects.nonNull(headers)) {
            builder.headers(headers);
        }
        return builder.body(resultBody);
    }

    public static <T> ResponseEntity<Result<T>> body( HttpStatus status,HttpHeaders headers, T body){
        return body(status,headers,null,body);
    }

    public static <T> ResponseEntity<Result<T>> body( HttpHeaders headers, T body){
        return body(HttpStatus.OK,headers,null,body);
    }

    public static <T> ResponseEntity<Result<T>> body( T body){
        return body(HttpStatus.OK,null,null,body);
    }

    public static <T> ResponseEntity<Result<T>> body( HttpStatus status,T body){
        return body(status,null,null,body);
    }

    public static <T> ResponseEntity<Result<T>> body(String message, T body){
        return body(HttpStatus.OK,null,message,body);
    }

    public static <T> ResponseEntity<Result<T>> body(HttpStatus status,String message, T body){
        return body(status,null,message,body);
    }


    private static ResponseEntity.BodyBuilder resultBodyBuilder(HttpStatus status,HttpHeaders headers,MediaType mediaType){
        Assert.notNull(status,"状态不能为空！");
        ResponseEntity.BodyBuilder result = ResponseEntity.status(status.value());
        if(Objects.nonNull(headers)){
            result.headers(headers);
        }
        if(Objects.nonNull(mediaType)){
            result.contentType(mediaType);
        }

        return result;
    }

}
