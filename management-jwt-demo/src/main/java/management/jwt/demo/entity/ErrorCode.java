package management.jwt.demo.entity;

import lombok.Data;

/**
 * @Author xiqiuwei
 * @Date Created in 14:35 2019/8/13
 * @Description
 * @Modified By:
 */
@Data
public class ErrorCode {
    private String msg;
    private int errorCode;
    public ErrorCode (String msg,int errorCode) {
        this.msg = msg;
        this.errorCode = errorCode;
    }

    public static ErrorCode AuthorizationEmpty = new ErrorCode("头部信息不符合JWT规范",-1);

    public static ErrorCode Bearer = new ErrorCode("Token信息没有带上Bearer ",-1);
}
