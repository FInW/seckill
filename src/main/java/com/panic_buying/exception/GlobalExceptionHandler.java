package com.panic_buying.exception;

import com.panic_buying.utils.CodeMsg;
import com.panic_buying.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest request, Exception e) {
        logger.error(e.getMessage());
        if (e instanceof GlobalException) {
            GlobalException globalException = (GlobalException) e;
            return Result.error(globalException.getCodeMsg());
        }else if(e instanceof BindException) {
            BindException bindException = (BindException) e;
            List<ObjectError> errors = bindException.getAllErrors();
            StringBuilder stringBuilder = new StringBuilder();
            for (ObjectError error : errors) {
                stringBuilder.append(error.getDefaultMessage()).append("|");
            }
            return Result.error(CodeMsg.BIND_ERROR.fillArgs(stringBuilder.toString()));
        } else {
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }
}
