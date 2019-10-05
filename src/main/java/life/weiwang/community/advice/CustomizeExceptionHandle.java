package life.weiwang.community.advice;

import com.alibaba.fastjson.JSON;
import life.weiwang.community.dto.ResponseDTO;
import life.weiwang.community.exception.CustomizeErrorCode;
import life.weiwang.community.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandle {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable E, Model model,
                        HttpServletRequest request,
                          HttpServletResponse response) {

        if (request.getContentType().equals("application/json")) {
            /**
             * 这里没办法返回两个对象，所以用最原始的方法手写response
             */
            //返回json
            ResponseDTO responseDTO =null;
            if (E instanceof CustomizeException){
                responseDTO = ResponseDTO.errorOf((CustomizeException)E);
            }else {
                responseDTO = ResponseDTO.errorOf(CustomizeErrorCode.SYSTEM_ERROR);
            }

            try {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json");
                response.setStatus(200);
                PrintWriter writer = response.getWriter();
                //记住对象转json的方式
                writer.write(JSON.toJSONString(responseDTO));
                writer.close();
            } catch (IOException e) {

            }
            return null;

        }else {
            //返回html错误页面
            if (E instanceof CustomizeException){
                model.addAttribute("message", E.getMessage());
            }else {
                model.addAttribute("message", CustomizeErrorCode.SYSTEM_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }


    }


}
