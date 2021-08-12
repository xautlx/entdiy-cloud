package com.entdiy.system.api;

import com.entdiy.common.core.domain.R;
import com.entdiy.system.api.constant.FileFeignConstants;
import com.entdiy.system.api.domain.SysFile;
import com.entdiy.system.api.factory.RemoteFileFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件服务
 */
@FeignClient(contextId = "remoteFileService",
        value = FileFeignConstants.SERVICE_ID,
        url = FileFeignConstants.SERVICE_URL,
        fallbackFactory = RemoteFileFallbackFactory.class)
public interface RemoteFileService {
    /**
     * 上传文件
     *
     * @param file 文件信息
     * @return 结果
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R<SysFile> upload(@RequestPart(value = "file") MultipartFile file);
}
