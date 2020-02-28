package com.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND(2001, "问题不存在"),
    TARGET_PARAM_NOT_FOUND(2002, "未选中任何问题或评论进行回复"),
    NO_LOGIN(2003,"用户未登录"),
    SYS_ERROR(2004,"服务器异常"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"评论不存在"),
    COMMENT_IS_EMPTY(2007,"输入内容不能为空"),
    READ_NOTIFICATION_FAIL(2008,"用户id错误"),
    NOTIFICATION_NOT_FOUND(2009,"消息未找到"),
    FILE_UPLOAD_FAIL(2010,"图片上传失败")
    ;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    private Integer code;
    private String  message;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
