package com.entdiy.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/**
 * 定制分页结果响应数据结构对象，便于细粒度控制序列化输出属性
 *
 * @param <T>
 */
@JsonIgnoreProperties({"empty", "pageable"})
@ApiModel("分页查询响应结构")
public class PageResult<T> implements Page<T> {

    @JsonIgnore
    private Page<T> page;

    public PageResult(Page<T> page) {
        this.page = page;
    }

    @ApiModelProperty(value = "分页当前页码")
    public int getPageNumber() {
        return page.getPageable().getPageNumber();
    }

    @Override
    @ApiModelProperty(value = "总页数")
    public int getTotalPages() {
        return page.getTotalPages();
    }

    @Override
    @ApiModelProperty(value = "总记录数")
    public long getTotalElements() {
        return page.getTotalElements();
    }

    @Override
    @JsonIgnore
    public int getNumber() {
        return page.getNumber();
    }

    @Override
    @JsonIgnore
    public int getSize() {
        return page.getSize();
    }

    @Override
    @JsonIgnore
    public int getNumberOfElements() {
        return page.getNumberOfElements();
    }

    @Override
    @ApiModelProperty(value = "分页数据")
    public List<T> getContent() {
        return page.getContent();
    }

    @Override
    @JsonIgnore
    public boolean hasContent() {
        return page.hasContent();
    }

    @Override
    @JsonIgnore
    public Sort getSort() {
        return page.getSort();
    }

    @Override
    @JsonIgnore
    public boolean isFirst() {
        return page.isFirst();
    }

    @Override
    @JsonIgnore
    public boolean isLast() {
        return page.isLast();
    }

    @Override
    @JsonIgnore
    public boolean hasNext() {
        return page.hasNext();
    }

    @Override
    @JsonIgnore
    public boolean hasPrevious() {
        return page.hasPrevious();
    }

    @Override
    @JsonIgnore
    public Pageable nextPageable() {
        return page.nextPageable();
    }

    @Override
    @JsonIgnore
    public Pageable previousPageable() {
        return page.previousPageable();
    }

    @Override
    @JsonIgnore
    public <U> Page<U> map(Function<? super T, ? extends U> function) {
        return page.map(function);
    }

    @Override
    @JsonIgnore
    public Iterator<T> iterator() {
        return page.iterator();
    }
}
