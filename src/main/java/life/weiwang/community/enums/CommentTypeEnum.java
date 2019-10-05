package life.weiwang.community.enums;

public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);
    private Integer Type;

    public Integer getType() {
        return Type;
    }

    CommentTypeEnum(Integer type) {
        this.Type = type;
    }

    public static boolean isExist(Integer type) {
        //CommentTypeEnum.values()可以拿到它的枚举值
        for (CommentTypeEnum value : CommentTypeEnum.values()) {
            if (type == value.Type) {
                return true;
            }

        }
        return false;
    }

}

