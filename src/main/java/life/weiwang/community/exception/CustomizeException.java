package life.weiwang.community.exception;

public class CustomizeException extends RuntimeException{
    private String message;
    private Integer code;

    public CustomizeException(ICustomizeErrorCode iCustomizeErrorCode ) {
        this.code = iCustomizeErrorCode.getCode();
        this.message = iCustomizeErrorCode.getMessage();
    }


    @Override
    public String getMessage() {

        return message;
    }

    public Integer getCode() {
        return code;
    }
}
