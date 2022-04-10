package org.example.test.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ExceptionAdvice {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final static String DEFAULT_ERROR_PAGE = "error";

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public String handle(Exception ex) {
        logger.error("에러 발생: ", ex);
        
        return DEFAULT_ERROR_PAGE;
    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public String handleNoHandlerFoundException(NoHandlerFoundException ex) {
        if(!ex.getRequestURL().equals("/favicon.ico"))
            logger.debug("페이지를 찾을 수 없습니다.", ex);

        return DEFAULT_ERROR_PAGE;
    }
}
