package com.sast.atSast.exception;

import com.sast.atSast.enums.CustomError;
import com.sast.atSast.util.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Xander
 * @Date: 2021/4/20 14:45
 * @Description: 全局错误处理，将错误返回给前端
 **/
@ControllerAdvice
@Controller
public class AllExceptionHandler implements ErrorController {
    @Override
    public String getErrorPath() {
        return "/error";
    }
    private Logger logger = LoggerFactory.getLogger("ERROR");

    @ResponseStatus(code = HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    @RequestMapping("/error")
    @ResponseBody
    public HttpResponse<Void> handleException(Exception uncaughtException){
        if (uncaughtException == null){
            return HttpResponse.failure("No found exception!", CustomError.INTERNAL_ERROR.getCode());
        }
        uncaughtException.printStackTrace();
        if (uncaughtException instanceof LocalRuntimeException){
            logger.error(((LocalRuntimeException) uncaughtException).getError().getErrMsg());
            LocalRuntimeException localRuntimeException = (LocalRuntimeException) uncaughtException;
            return HttpResponse.failure(localRuntimeException.getError().getErrMsg(),localRuntimeException.getError().getCode());
        }else{
            logger.error("error message",uncaughtException);
            return HttpResponse.failure(uncaughtException.getMessage(),CustomError.UNKNOWN_ERROR.getCode());
        }
    }
}
