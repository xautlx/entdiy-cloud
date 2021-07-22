package com.entdiy.system.api.factory;

import com.entdiy.system.api.RemoteFileService;
import com.entdiy.system.api.domain.SysFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.entdiy.common.core.domain.R;
import feign.hystrix.FallbackFactory;

/**
 * 文件服务降级处理
 *
 * 
 */
@Component
public class RemoteFileFallbackFactory implements FallbackFactory<RemoteFileService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteFileFallbackFactory.class);

    @Override
    public RemoteFileService create(Throwable throwable)
    {
        log.error("文件服务调用失败:{}", throwable);
        return new RemoteFileService()
        {
            @Override
            public R<SysFile> upload(MultipartFile file)
            {
                return R.fail("上传文件失败:" + throwable.getMessage());
            }
        };
    }
}
