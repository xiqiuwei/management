package com.management.shiro.demo.dataobject;

import lombok.Data;

/**
 * @Author xiqiuwei
 * @Date Created in 14:51 2019/12/14
 * @Description
 * @Modified By:
 * 角色表
 */
@Data
public class SysRoleDO {
    /*主键*/
    private Integer roleId;
    /*角色编码*/
    private String roleKey;
    /*创建时间*/
    private String createTime;
    /*描述*/
    private String description;
    /*角色名称*/
    private String roleValue;
    /*公司编号*/
    private Long companyId;
}
