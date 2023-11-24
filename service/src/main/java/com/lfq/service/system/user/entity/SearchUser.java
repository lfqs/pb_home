package com.lfq.service.system.user.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @作者 lfq
 * @DATE 2023-11-24
 * current year
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class SearchUser extends SysUserEntity{

    private String deptName;
    private String deptLeader;

}
