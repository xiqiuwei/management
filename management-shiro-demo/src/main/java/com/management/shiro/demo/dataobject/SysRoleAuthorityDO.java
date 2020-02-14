package com.management.shiro.demo.dataobject;

import lombok.Data;

/**
 * @Author xiqiuwei
 * @Date Created in 14:56 2019/12/14
 * @Description
 * @Modified By:
 * 角色菜单表
 */
@Data
public class SysRoleAuthorityDO {
    /*主键id*/
    private Long id;
    /*菜单编码*/
    private String menuCode;
    /*角色编码*/
    private String roleKey;
    /*菜单类型 1 导航 2 按钮*/
    private Integer menuType;
}
