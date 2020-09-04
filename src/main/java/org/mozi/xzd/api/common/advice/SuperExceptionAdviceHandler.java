package org.mozi.xzd.api.common.advice;

import com.google.common.base.Throwables;
import org.mozi.xzd.api.common.exception.SecurityException;
import org.mozi.xzd.api.common.http.HttpStatus;

import org.mozi.xzd.api.common.exception.LoginException;
import org.mozi.xzd.api.common.exception.ResultErrorData;
import org.mozi.xzd.api.common.exception.SignatureException;
import org.mozi.xzd.api.common.exception.TokenException;
import io.jsonwebtoken.io.SerializationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.DateTimeException;
import java.time.format.DateTimeParseException;

/**
 * @author xuzidong
 * @version V1.0.0

 * @description <p></p >
 * @since 2020/8/7 10:28
 */
@RestControllerAdvice
@Slf4j
public class SuperExceptionAdviceHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public ResultErrorData runtimeException(RuntimeException ex, HttpServletRequest request) {
        int code = 0;
        if (ex.getClass().equals(TokenException.class)) {
            code = HttpStatus.TOKEN_ERROR.value();
        } else if (ex.getClass().equals(SignatureException.class)) {
            code = HttpStatus.SIGNATURE_ERROR.value();
        } else if (ex.getClass().equals(LoginException.class)) {
            code = HttpStatus.LOGIN_ERROR.value();
        } else if (SecurityException.class.equals(ex.getClass())) {
            code = HttpStatus.SECURITY_EXCEPTION.value();
        } else if (DateTimeException.class.equals(ex.getClass()) || DateTimeParseException.class.equals(ex.getClass())) {
            code = HttpStatus.DATE_TIME_EXCEPTION.value();
        } else if (ex.getClass().toString().equals(NullPointerException.class.toString())) {
            code = HttpStatus.NULL_POINT_EREXCEPTION.value();
        } else if (ex.getClass().equals(ArithmeticException.class)) {
            code = HttpStatus.ARITHMETIC_EXCEPTION.value();
        } else if (ex.getClass().equals(ArrayIndexOutOfBoundsException.class)) {
            code = HttpStatus.ARRAY_INDEX_OUT_OF_BOUNDS_EXCEPTION.value();
        } else if (ex.getClass().equals(IllegalArgumentException.class)) {
            code = HttpStatus.ILLEGALARGUMENT_EXCEPTION.value();
        } else if (ex.getClass().equals(ClassCastException.class)) {
            code = HttpStatus.CLASS_CAST_EXCEPTION.value();
        } else if (ex.getClass().equals(SerializationException.class)) {
            code = HttpStatus.Serialization_Exception.value();
        } else {
            code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        }
        log.error(Throwables.getStackTraceAsString(ex));
        String errorLocalizedMsg = ex.getMessage();
        return ResultErrorData.builder().code(code)
                .retMsg(errorLocalizedMsg)
                .message(HttpStatus.METHOD_ARGUMENT_NOT_VALID_EXCEPTION.name())
                .path(request.getRequestURL().toString())
                .dateTime(new Timestamp(System.currentTimeMillis())).build();


    }

    @ExceptionHandler(value = Exception.class)
    public ResultErrorData exception(Exception ex, HttpServletRequest request) {


        int code = 0;
         if (ex.getClass().equals(IOException.class)) {
            code = HttpStatus.IO_EXCEPTION.value();
        } else if (ex.getClass().equals(ClassNotFoundException.class)) {
            code = HttpStatus.CLASS_NOT_FOUND_EXCEPTION.value();
        } else if (ex.getClass().equals(SQLException.class)) {
            code = HttpStatus.SQL_EXCEPTION.value();
        } else {
            code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        }

        String errorLocalizedMsg = ex.getMessage();
        log.error(Throwables.getStackTraceAsString(ex));
        return ResultErrorData.builder().code(code)
                .retMsg(errorLocalizedMsg)
                .message(HttpStatus.METHOD_ARGUMENT_NOT_VALID_EXCEPTION.name())
                .path(request.getRequestURL().toString())
                .dateTime(new Timestamp(System.currentTimeMillis()))
                .build();
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResultErrorData
      notValidExceptionHandler(MethodArgumentNotValidException ex, HttpServletRequest request) {

        int code=HttpStatus.METHOD_ARGUMENT_NOT_VALID_EXCEPTION.value();
        log.error(Throwables.getStackTraceAsString(ex));
        return ResultErrorData.builder().code(code)
                .retMsg(ex.getMessage())
                .message(HttpStatus.METHOD_ARGUMENT_NOT_VALID_EXCEPTION.name())
                .path(request.getRequestURL().toString())
                .dateTime(new Timestamp(System.currentTimeMillis()))
                .build();
    }
}
