package com.lfq.service.system.menu.command;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @作者 lfq
 * @DATE 2023-12-07
 * current year
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateMenuCommand extends AddMenuCommand {

    @NotNull
    private Long menuId;

}
