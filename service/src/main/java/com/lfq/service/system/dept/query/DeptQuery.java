package com.lfq.service.system.dept.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lfq.common.core.page.AbstractQuery;
import com.lfq.service.system.dept.entity.SysDeptEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @作者 lfq
 * @DATE 2023-12-07
 * current year
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class DeptQuery extends AbstractQuery<SysDeptEntity> {

    private Long deptId;

    private Long parentId;


    @Override
    public QueryWrapper<SysDeptEntity> addQueryCondition() {
        // TODO parentId 这个似乎没使用
        return new QueryWrapper<SysDeptEntity>()
//            .eq(status != null, "status", status)
                .eq(parentId != null, "parent_id", parentId);
//            .like(StrUtil.isNotEmpty(deptName), "dept_name", deptName);
//            .and(deptId != null && isExcludeCurrentDept, o ->
//                o.ne("dept_id", deptId)
//                    .or()
//                    .apply("FIND_IN_SET (dept_id , ancestors)")
//            );
    }
}
