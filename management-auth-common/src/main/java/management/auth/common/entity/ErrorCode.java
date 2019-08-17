package management.auth.common.entity;

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

    public static ErrorCode AuthorizationEmpty = new ErrorCode("请登录后再来",-1);
    public static ErrorCode TokenExpiration = new ErrorCode("token过期请重新登录",-2);
    public static ErrorCode OtherErrors = new ErrorCode("服务器报错",-3);

}
