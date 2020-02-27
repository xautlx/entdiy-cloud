## 常见问题

### MyBatis Plus 环境下 @TableField 字段别名使用注意事项

有时Java项目为了兼容遗留数据库设计，比如遗留数据库是 is_abc 字段，Java属性一般定义为 Boolean abc，此时按照MyBatis Plus的语法，可以使用@TableField做映射定义：


```
    @TableField("is_abc")
    private Boolean abc;
```

此种方式使用MyBatis Plus框架基类的标准CURD方法没有问题。如果是在Mapper层面以@Select注解SQL或者SQL Mapper XML中定义以“select is_abc from ...” 无法正常绑定is_abc字段值到abc属性上面。

 **解决方法参考：** 当然最彻底的办法是把数据库字段按照Java规范要求重命名为映射一致的字段名称。如果数据库结构没法动，还有种变通方法，用as语法把自定义SQL改写成“select is_abc as abc from ...”。