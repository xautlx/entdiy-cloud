## 常见问题

### MyBatis Plus 环境下 @TableField 字段别名使用注意事项

有时Java项目为了兼容遗留数据库设计，比如遗留数据库是 is_abc 字段，Java属性一般定义为 Boolean abc，此时按照MyBatis Plus的语法，可以使用@TableField做映射定义：


```
    @TableField("is_abc")
    private Boolean abc;
```

