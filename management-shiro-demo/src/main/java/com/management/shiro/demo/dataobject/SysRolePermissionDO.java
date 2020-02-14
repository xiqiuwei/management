package com.management.shiro.demo.dataobject;

import lombok.Data;

/**
 * @Author xiqiuwei
 * @Date Created in 15:01 2019/12/14
 * @Description
 * @Modified By:
 * 角色按钮权限表
 */
@Data
public class SysRolePermissionDO {
    /*角色主键编号*/
    private Integer roleId;
    /*按钮权限*/
    private String permissions;
}
