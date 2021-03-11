package com.entdiy.feign.support;

import com.entdiy.common.exception.ErrorCodeException;
import com.entdiy.common.model.ViewResult;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.HttpMessageConverterExtractor;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * 对约定结构把success=false情况转换为异常抛出
 *
 * @see org.springframework.cloud.openfeign.support.SpringDecoder
 */
@Slf4j
public class FeignSpringDecoder implements Decoder {

    private ObjectFactory<HttpMessageConverters> messageConverters;

    public FeignSpringDecoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        this.messageConverters = messageConverters;
    }

    @Override
    public Object decode(final Response response, Type type)
            throws IOException, FeignException {
        log.debug("FeignSpringDecoder.decode...");
        if (type instanceof Class || type instanceof ParameterizedType
                || type instanceof WildcardType) {
            @SuppressWarnings({"unchecked", "rawtypes"})
            HttpMessageConverterExtractor<?> extractor = new HttpMessageConverterExtractor(
                    type, this.messageConverters.getObject().getConverters());

            //读取服务端响应文本进行判断处理
            String body = Util.toString(response.body().asReader());
            if (log.isInfoEnabled()) {
                StringBuilder sb = new StringBuilder();
                sb.append("\n - FeignClient Request: \n" + response.request());
                sb.append("\n\n - FeignClient Response: \n\n" + body);
                log.info("FeignClient Decoder: {}", sb);
            }
            //失败情况，无法转换为接口业务对象，因此直接转换为异常抛出
            if (body != null && body.startsWith("{\"" + ViewResult.SUCCESS_KEY_NAME + "\":false")) {
                String code = StringUtils.substringBefore(StringUtils.substringAfter(body, "\"code\":\""), "\",\"");
                String message = StringUtils.substringBefore(StringUtils.substringAfter(body, "\"message\":\""), "\",\"");
                ErrorCodeException ece = new ErrorCodeException(message).code(code);
                log.error("Feign response error", ece);
                throw ece;
            }

            //当前response已经读取使用，需要克隆创建新的response实例供后续读取使用
            Response copiedResponse = response.toBuilder().body(body.getBytes()).build();
            return extractor.extractData(new FeignResponseAdapter(copiedResponse));
        }

        throw new DecodeException(response.status(),
                "type is not an instance of Class or ParameterizedType: " + type,
                response.request());
    }

    private final class FeignResponseAdapter implements ClientHttpResponse {

        private final Response response;

        private FeignResponseAdapter(Response response) {
            this.response = response;
        }

        @Override
        public HttpStatus getStatusCode() throws IOException {
            return HttpStatus.valueOf(this.response.status());
        }

        @Override
        public int getRawStatusCode() throws IOException {
            return this.response.status();
        }

        @Override
        public String getStatusText() throws IOException {
            return this.response.reason();
        }

        @Override
        public void close() {
            try {
                this.response.body().close();
            } catch (IOException ex) {
                // Ignore exception on close...
            }
        }

        @Override
        public InputStream getBody() throws IOException {
            return this.response.body().asInputStream();
        }

        @Override
        public HttpHeaders getHeaders() {
            Map<String, Collection<String>> headers = this.response.headers();
            HttpHeaders httpHeaders = new HttpHeaders();
            for (Map.Entry<String, Collection<String>> entry : headers.entrySet()) {
                httpHeaders.put(entry.getKey(), new ArrayList<>(entry.getValue()));
            }
            return httpHeaders;
        }
    }
}
