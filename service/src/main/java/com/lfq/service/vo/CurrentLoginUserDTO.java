package com.lfq.service.vo;

import java.util.Set;

import com.lfq.service.system.user.dto.UserDTO;
import lombok.Data;


@Data
public class CurrentLoginUserDTO {

    private UserDTO userInfo;
    private String roleKey;
    private Set<String> permissions;


}
