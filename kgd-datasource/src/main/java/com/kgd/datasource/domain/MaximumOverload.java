package com.kgd.datasource.domain;

import com.kgd.common.annotation.Excel;
import com.kgd.common.core.domain.BaseEntity;

import java.math.BigDecimal;

public class MaximumOverload extends BaseEntity {
    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * 主观权重
     */
    @Excel(name = "主观权重")
    private String time;

    @Excel(name = "主观权重")
    private String overload;


}
