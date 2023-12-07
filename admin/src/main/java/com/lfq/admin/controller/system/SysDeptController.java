package com.lfq.admin.controller.system;

import com.lfq.common.core.base.BaseController;
import com.lfq.common.core.response.ResponseVO;
import com.lfq.service.system.dept.DeptApplicationService;
import com.lfq.service.system.dept.dto.DeptDTO;
import com.lfq.service.system.dept.query.DeptQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 部门控制层
 * @作者 lfq
 * @DATE 2023-12-07
 * current year
 **/
@RestController
@RequestMapping("/system")
@Validated
@RequiredArgsConstructor
public class SysDeptController extends BaseController {

    private final DeptApplicationService deptApplicationService;

    /**
     * 获取部门列表
     */
    @PreAuthorize("@permission.has('system:dept:list')")
    @GetMapping("/depts")
    public ResponseVO<List<DeptDTO>> list(DeptQuery query) {
        List<DeptDTO> deptList = deptApplicationService.getDeptList(query);
        return ResponseVO.ok(deptList);
    }



}
