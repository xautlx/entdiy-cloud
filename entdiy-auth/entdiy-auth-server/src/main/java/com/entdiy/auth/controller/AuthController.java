package com.entdiy.auth.controller;

import cn.hutool.core.util.RandomUtil;
import com.entdiy.common.model.ViewResult;
import com.entdiy.common.util.RandImageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
@Api(tags = "用户登录")
@Slf4j
public class AuthController {

    private static final String BASE_CHECK_CODES = "qwertyuiplkjhgfdsazxcvbnmQWERTYUPLKJHGFDSAZXCVBNM1234567890";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private String buildPersistKey(String key, String code) {
        String lowerCaseCode = code.toLowerCase();
        return lowerCaseCode + key;
    }

    /**
     * 后台生成图形验证码 ：有效
     */
    @ApiOperation("获取验证码")
    @GetMapping(value = "/pub/captcha/{key}")
    public ViewResult<String> captchaImage(@PathVariable String key) throws IOException {
        String code = RandomUtil.randomString(BASE_CHECK_CODES, 4);
        String cacheKey = Md5Crypt.md5Crypt(buildPersistKey(key, code).getBytes());
        redisTemplate.opsForValue().set(cacheKey, code, 60, TimeUnit.SECONDS);
        String base64 = RandImageUtil.generate(code);
        return ViewResult.success(base64);
    }

    @GetMapping("/user")
    public String user() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
