package com.lfq.service.system.menu.dto;

import lombok.Data;

/**
 * @作者 lfq
 * @DATE 2023-12-06
 * current year
 **/
@Data
public class ExtraIconDTO {
    // 是否是svg
    private boolean svg;
    // iconfont名称，目前只支持iconfont，后续拓展
    private String name;

}
