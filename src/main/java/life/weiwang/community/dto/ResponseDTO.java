package life.weiwang.community.dto;

import life.weiwang.community.exception.CustomizeErrorCode;
import life.weiwang.community.exception.CustomizeException;
import lombok.Data;

@Data
public class ResponseDTO {
    private String message;
    private Integer code;

    //扩展，错误源自
    public static ResponseDTO errorOf(Integer code, String message){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(code);
        responseDTO.setMessage(message);
        return responseDTO;

    }

    public static ResponseDTO errorOf(CustomizeErrorCode errorCode) {

        return errorOf(errorCode.getCode(), errorCode.getMessage());
    }

    public static ResponseDTO errorOf(CustomizeException e) {
        System.out.println("hhh");
        return errorOf(e.getCode(), e.getMessage());
    }

    public static ResponseDTO okOf(){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(200);
        responseDTO.setMessage("请求成功");
        return responseDTO;

    }
}
