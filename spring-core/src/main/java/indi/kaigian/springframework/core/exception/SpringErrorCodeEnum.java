package indi.kaigian.springframework.core.exception;

/**
 * @author kaigian
 **/
public enum SpringErrorCodeEnum {

    //错误码
    SUCCESS_CODE("0", "成功"),
    ERROR_CODE("1", "失败"),
    SERVICE_NOT_SUPPORT("2", "暂不支持该功能");

    private final String code;
    private final String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    SpringErrorCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static SpringErrorCodeEnum getEnum(String code) {
        for (SpringErrorCodeEnum ele : SpringErrorCodeEnum.values()) {
            if (ele.getCode().equals(code)) {
                return ele;
            }
        }
        return null;
    }
}
