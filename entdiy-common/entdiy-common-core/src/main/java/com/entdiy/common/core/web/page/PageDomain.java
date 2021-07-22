package com.entdiy.common.core.web.page;

import com.entdiy.common.core.utils.StringUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 分页数据
 *
 *
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PageDomain
{
    /** 当前记录起始索引 */
    private Integer pageNum;

    /** 每页显示记录数 */
    private Integer pageSize;

    /** 排序列 */
    private String orderByColumn;

    /** 排序的方向desc或者asc */
    private String isAsc = "asc";

    public String getOrderBy()
    {
        if (StringUtils.isEmpty(orderByColumn))
        {
            return "";
        }
        return StringUtils.toUnderScoreCase(orderByColumn) + " " + isAsc;
    }
}
