package com.entdiy.common.service;

import com.entdiy.common.model.LabelValueBean;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 支持服务接口
 * </p>
 *
 * @author Li Xia
 * @since 2020-11-20
 */
public interface ISupportService {
    /**
     * 扫码所有实现了LabelValueBean接口的枚举类组装为字典结构数据并缓存
     * 返回给前端作为字典静态数据源
     *
     * @return
     */
    Map<String, List<LabelValueBean>> buildLabelValueEnumsCacheable();

    /**
     * 构建并缓存实体对象元数据，返回前端用于表单校验、数据类型等自动处理
     *
     * @param metaClass 需要进行元数据解析的类标识。
     *                  一种方式如果不介意前端代码暴露企业敏感信息可以直接传入完整包路径类名，
     *                  同时考虑到一些有意隐藏完整包名的需求，做了一定兼容设计传入类名及紧邻包名格式，如mdm.UserEditDto，
     *                  当然这种方式极端情况会存在重名冲突的情况，会检测抛出异常拒绝重名参数传入。
     * @return
     */
    ModelMetaData buildModelMetaDataCacheable(String metaClass);

    @Data
    class ModelMetaData {
        private String metaClass;
        private Class<?> modelClass;
        Object meteData;
    }
}
