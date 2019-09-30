package life.weiwang.community.advice;

import life.weiwang.community.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class CustomizeExceptionHandle {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable E, Model model) {
        if (E instanceof CustomizeException){
            model.addAttribute("message", E.getMessage());
        }else {
            model.addAttribute("message", "三儿有点顶不住，要不稍后再试试！！！");
        }
        return new ModelAndView("error");
    }


}
