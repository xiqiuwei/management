package com.management.shiro.demo.dataobject;

import lombok.Data;

/**
 * @Author xiqiuwei
 * @Date Created in 15:04 2019/12/14
 * @Description
 * @Modified By:
 */
@Data
public class SysUserDO {
    /*主键*/
    private Long userId;
    /*登录账号*/
    private String loginAccount;
    /*登录密码*/
    private String loginPass;
    /*用户名字*/
    private String userName;
    /*用户头像*/
    private String userHead;
    /*用户手机*/
    private String userPhone;
    /*用户邮箱*/
    private String userEmail;
    /*用户性别*/
    private Integer userSex;
    /*用户生日*/
    private String userBirthday;
    /*注册时间*/
    private String registerTime;
    /*部门编号*/
    private String departmentKey;
}
