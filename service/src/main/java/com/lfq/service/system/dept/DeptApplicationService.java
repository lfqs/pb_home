package com.lfq.service.system.dept;

import com.lfq.service.system.dept.dto.DeptDTO;
import com.lfq.service.system.dept.entity.SysDeptEntity;
import com.lfq.service.system.dept.query.DeptQuery;
import com.lfq.service.system.dept.service.SysDeptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @作者 lfq
 * @DATE 2023-12-07
 * current year
 **/
@Service
@RequiredArgsConstructor
public class DeptApplicationService {

    private final SysDeptService deptService;



    public List<DeptDTO> getDeptList(DeptQuery query) {
        List<SysDeptEntity> list = deptService.list(query.toQueryWrapper());
        return list.stream().map(DeptDTO::new).collect(Collectors.toList());
    }

}
