package cn.zack.vo;

/**
 * 响应体Code枚举类
 */
public enum ResponseCodeEnum {

    OK(1000, "请求成功"),
    FORBIDDEN(1001, "无效的请求"),
    ;
    private int code;
    private String message;

    ResponseCodeEnum() {
    }

    ResponseCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
