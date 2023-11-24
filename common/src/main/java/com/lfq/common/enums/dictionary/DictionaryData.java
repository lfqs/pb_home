package com.lfq.common.enums.dictionary;

import com.lfq.common.enums.DictionaryEnum;
import lombok.Data;

/**
 * 字典模型类
 * @作者 lfq
 * @DATE 2023-11-23
 * current year
 **/
@Data
public class DictionaryData {

    private String label;
    private Integer value;
    private String cssTag;

    @SuppressWarnings("rawtypes")
    public DictionaryData(DictionaryEnum enumType) {
        if (enumType != null) {
            this.label = enumType.description();
            this.value = (Integer) enumType.getValue();
            this.cssTag = enumType.cssTag();
        }
    }

}
