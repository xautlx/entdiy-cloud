package com.entdiy.auth.web;

import com.entdiy.common.model.ViewResult;
import com.entdiy.common.web.GlobalWebExceptionResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class OAuth2ExceptionTranslator implements WebResponseExceptionTranslator<OAuth2Exception> {

    public static ViewResult buildResponseBody(HttpServletRequest request, HttpServletResponse response, Exception e) {

        ThrowableAnalyzer throwableAnalyzer = GlobalWebExceptionResolver.throwableAnalyzer;
        Throwable[] causeChain = throwableAnalyzer.determineCauseChain(e);
        ViewResult viewResult = null;

        if (viewResult == null) {
            InvalidTokenException exception = (InvalidTokenException) throwableAnalyzer.getFirstThrowableOfType(InvalidTokenException.class, causeChain);
            if (exception != null) {
                /**
                 * @see CheckTokenEndpoint#checkToken(String)
                 * @see DefaultTokenServices#refreshAccessToken
                 */
                if ("Token has expired".equals(exception.getMessage()) || exception.getMessage().startsWith("Invalid refresh token (expired):")) {
                    viewResult = ViewResult.error("ERROR_TOKEN_EXPIRED", exception.getMessage()).skipLog(true);
                }
            }
        }

        if (viewResult == null) {
            InvalidGrantException exception = (InvalidGrantException) throwableAnalyzer.getFirstThrowableOfType(InvalidGrantException.class, causeChain);
            if (exception != null) {
                /**
                 * @see DefaultTokenServices#refreshAccessToken
                 */
                if (exception.getMessage().startsWith("Invalid refresh token")) {
                    viewResult = ViewResult.error("ERROR_TOKEN_EXPIRED", exception.getMessage()).skipLog(true);
                }
            }
        }

        if (viewResult == null) {
            viewResult = GlobalWebExceptionResolver.buildResponseBody(request, response, e);
        }

        return viewResult;
    }

    /**
     * OAuth2异常定制处理，保持与 ViewResult 一致的输出属性和顺序
     *
     * @param e
     * @return
     * @see ViewResult
     */
    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getResponse();
        ViewResult exceptionViewResult = buildResponseBody(request, response, e);
        CustomOAuth2Exception oAuth2Exception = CustomOAuth2Exception.valueOf(exceptionViewResult);
        return new ResponseEntity(oAuth2Exception, HttpStatus.OK);
    }
}
