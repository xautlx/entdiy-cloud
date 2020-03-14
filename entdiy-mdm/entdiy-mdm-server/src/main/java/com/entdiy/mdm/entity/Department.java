package com.entdiy.mdm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.entdiy.common.entity.BaseTreeEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Data
@TableName("mdm_department")
@Entity
@Table(name = "mdm_department",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_mdm_department_code",
                        columnNames = {"code"})
        }
)
@ApiModel(value = "部门")
public class Department extends BaseTreeEntity {

    @Column(length = 32, nullable = false)
    private String code;

    private String mobile;

    private String email;
}
