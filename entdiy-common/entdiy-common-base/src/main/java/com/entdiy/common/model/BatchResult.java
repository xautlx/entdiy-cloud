package com.entdiy.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ApiModel("Restful JSON 批量操作响应结构")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BatchResult<T> {

    @ApiModelProperty(value = "处理记录数")
    private int processCount = 0;

    @ApiModelProperty(value = "失败记录数")
    private int failureCount = 0;

    @ApiModelProperty(value = "处理结果行项集合")
    private List<BatchResultItem> items = Lists.newArrayList();

    private void appendItem(BatchResultItem item) {
        this.items.add(item);
        processCount = 0;
        failureCount = 0;
        for (BatchResultItem one : this.items) {
            processCount++;
            if (!one.getSuccess()) {
                failureCount++;
            }
        }
    }

    public void appendSuccessItem(Serializable id) {
        BatchResultItem item = new BatchResultItem();
        item.setId(id);
        item.setSuccess(true);
        appendItem(item);
    }

    public void appendFailureItem(Serializable id, String message) {
        BatchResultItem item = new BatchResultItem();
        item.setId(id);
        item.setSuccess(false);
        item.setMessage(message);
        appendItem(item);
    }

    @Setter
    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class BatchResultItem<T> {

        @ApiModelProperty(value = "主键标识")
        private Serializable id;

        @ApiModelProperty(value = "批处理业务对象标识")
        private T data;

        @ApiModelProperty(value = "成功标识")
        private Boolean success;

        @ApiModelProperty(value = "结果消息", notes = "异常情况对于代码的补充消息说明")
        private String message;

    }
}
