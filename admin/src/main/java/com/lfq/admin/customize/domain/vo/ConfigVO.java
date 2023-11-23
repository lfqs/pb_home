package com.lfq.admin.customize.domain.vo;

import com.lfq.common.enums.dictionary.DictionaryData;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @作者 lfq
 * @DATE 2023-11-23
 * current year
 **/
@Data
public class ConfigVO {

    private Boolean isCaptchaOn;

    private Map<String, List<DictionaryData>> dictionary;

}
