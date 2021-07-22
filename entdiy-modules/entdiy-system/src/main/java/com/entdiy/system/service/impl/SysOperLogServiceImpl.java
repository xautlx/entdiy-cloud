package com.entdiy.system.service.impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entdiy.system.api.domain.SysOperLog;
import com.entdiy.system.mapper.SysOperLogMapper;
import com.entdiy.system.service.ISysOperLogService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 操作日志 服务层处理
 *
 *
 */
@Service
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog> implements ISysOperLogService {

    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     */
    @Override
    public boolean insertOperlog(SysOperLog operLog) {
        operLog.setOperTime(new Date());
        return save(operLog);
    }

    /**
     * 查询系统操作日志集合
     *
     * @param operLog 操作日志对象
     * @return 操作日志集合
     */
    @Override
    public List<SysOperLog> selectOperLogList(SysOperLog operLog) {
        Map<String, Object> params = operLog.getParams();
        return list(new LambdaQueryWrapper<SysOperLog>()
                .like(StrUtil.isNotBlank(operLog.getTitle()),SysOperLog::getTitle,operLog.getTitle())
                .eq(operLog.getBusinessType() != null && operLog.getBusinessType() > 0,
                        SysOperLog::getBusinessType,operLog.getBusinessType())
                .func(f -> {
                    if (ArrayUtil.isNotEmpty(operLog.getBusinessTypes())){
                        f.in(SysOperLog::getBusinessType, Arrays.asList(operLog.getBusinessTypes()));
                    }
                })
                .eq(operLog.getStatus() != null && operLog.getStatus() > 0,
                        SysOperLog::getStatus,operLog.getStatus())
                .like(StrUtil.isNotBlank(operLog.getOperName()),SysOperLog::getOperName,operLog.getOperName())
                .apply(Validator.isNotEmpty(params.get("beginTime")),
                        "date_format(oper_time,'%y%m%d') >= date_format({0},'%y%m%d')",
                        params.get("beginTime"))
                .apply(Validator.isNotEmpty(params.get("endTime")),
                        "date_format(oper_time,'%y%m%d') <= date_format({0},'%y%m%d')",
                        params.get("endTime"))
                .orderByDesc(SysOperLog::getOperId));
    }

    /**
     * 批量删除系统操作日志
     *
     * @param operIds 需要删除的操作日志ID
     * @return 结果
     */
    @Override
    public int deleteOperLogByIds(Long[] operIds) {
        return baseMapper.deleteBatchIds(Arrays.asList(operIds));
    }

    /**
     * 查询操作日志详细
     *
     * @param operId 操作ID
     * @return 操作日志对象
     */
    @Override
    public SysOperLog selectOperLogById(Long operId) {
        return getById(operId);
    }

    /**
     * 清空操作日志
     */
    @Override
    public void cleanOperLog() {
        remove(new LambdaQueryWrapper<>());
    }
}
