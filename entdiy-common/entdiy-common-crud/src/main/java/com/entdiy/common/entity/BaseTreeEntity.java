package com.entdiy.common.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 实体对象基类
 */
@Data
@ExcelIgnoreUnannotated
@MappedSuperclass
public abstract class BaseTreeEntity extends BaseEntity {

    /**
     * 定义外键约束为none主要是为了方便删除表重建数据，为了避免意外删除大量数据，因此不支持递归删除方式,业务接口已限制只能删除子节点。
     * 由于采用了Nested Set数据模型，除非清楚模型数据规则，请勿随意删除数据，否则会导致lft和rgt等相关数据混乱
     */
    @ApiModelProperty(value = "上级节点主键")
    @TableField(value = "parent_id")
    private Long parentId;

    @Column(name = "lft", nullable = false)
    @TableField(value = "lft")
    private Integer lft = 0;

    @Column(name = "rgt", nullable = false)
    @TableField(value = "rgt")
    private Integer rgt = 0;

    @Column(name = "depth", nullable = false)
    @TableField(value = "depth")
    private Integer depth = 0;


    public void makeRoot() {
        Assert.isTrue(this.getLft() == null || this.getLft() <= 0, "Invalid entity");
        Assert.isTrue(this.getRgt() == null || this.getRgt() <= 0, "Invalid entity");
        this.setLft(1);
        this.setRgt(2);
        this.setDepth(0);
    }

    public boolean isRoot() {
        return this.getDepth() == 0;
    }

    public Boolean hasChildren() {
        return (this.getRgt() - this.getLft()) > 1;
    }
}
