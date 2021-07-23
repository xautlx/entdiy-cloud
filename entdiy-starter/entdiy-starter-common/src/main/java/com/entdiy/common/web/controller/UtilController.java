package com.entdiy.common.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.entdiy.common.core.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class UtilController {

    @GetMapping("/pub/ping")
    public String ping(@RequestParam(value = "et", required = false) String et) {
        if ("1".equals(et)) {
            throw new CustomException("预定义异常演示消息");
        } else if ("2".equals(et)) {
            throw new RuntimeException("系统异常演示消息");
        }
        return "Pong at " + LocalDateTime.now();
    }

    @GetMapping("/pub/build-info")
    public Map<String, Object> getVersionInfo() {
        InputStream inputStream = null;
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            inputStream = classLoader.getResourceAsStream("build-info.json");
            // 读取文件内容，自定义一个方法实现即可
            String versionJson = IOUtils.toString(inputStream, "UTF-8");
            JSONObject jsonObject = JSON.parseObject(versionJson);
            Set<Map.Entry<String, Object>> entrySet = jsonObject.entrySet();
            if (CollectionUtils.isNotEmpty(entrySet)) {
                return entrySet.stream().collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (o, n) -> n));
            }
        } catch (Exception e) {
            log.error("get git version info fail", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return new HashMap<>();
    }
}
