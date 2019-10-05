package life.weiwang.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{
    //enum之间要用逗号隔开
    QUESTION_NOT_FOUND(1997, "你找的问题三儿找不到了，要不换个试试～～"),
    TARGET_PARAM_NOT_FOUND(1998, "您未选中任何问题和评论"),
    NOT_LOGIN(1999, "没有登录哦，请先登录~~"),
    SYSTEM_ERROR(2000,"三儿有点顶不住，要不稍后再试试！！！"),
    COMMENT_TYPE_WRONG(2001,"评论类型有误（对问题评论还是对回复评论）"),
    COMMENT_NOT_FOUND(2002,"您回复的这个评论已经不存在了"),
    ;


    private String message;
    private Integer code;


    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
