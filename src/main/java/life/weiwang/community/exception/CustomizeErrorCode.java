package life.weiwang.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND("你找的问题三儿找不到了，要不换个试试～～");

    private String message;


    CustomizeErrorCode(String message) {

        this.message = message;
    }


    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
