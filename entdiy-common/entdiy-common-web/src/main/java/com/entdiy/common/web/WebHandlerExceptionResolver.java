package com.entdiy.common.web;

import com.entdiy.common.auth.AuthDataHolder;
import com.entdiy.common.exception.ErrorCodeEnum;
import com.entdiy.common.exception.ErrorCodeException;
import com.entdiy.common.model.ExceptionViewResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.DataTruncation;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class WebHandlerExceptionResolver implements HandlerExceptionResolver {

    private static DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            // 解析date+time
            .appendPattern("yyyyMMddHHmmss")
            // 解析毫秒数
            .appendValue(ChronoField.MILLI_OF_SECOND, 3)
            .toFormatter();

    private static ThrowableAnalyzer throwableAnalyzer = new ThrowableAnalyzer();

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object aHandler, Exception e) {
        log.debug("Invoking CustomExceptionResolver.resolveException ...");
        ModelAndView mv = new ModelAndView();
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        view.setExtractValueFromSingleKeyModel(true);

        Map<String, Object> attributes = new HashMap(4);
        attributes.put("body", buildExceptionViewResult(request, e));
        view.setAttributesMap(attributes);
        mv.setView(view);
        return mv;
    }

    private ExceptionViewResult processExceptionViewResult(ExceptionViewResult exceptionViewResult, Exception e) {
        if (!exceptionViewResult.isSkipLog()) {
            String eid = "ERR" + DATE_TIME_FORMATTER.format(LocalDateTime.now()) + RandomStringUtils.randomNumeric(3);
            exceptionViewResult.setEid(eid);
            log.error(eid + ": " + exceptionViewResult.getMessage() + "\r\n" + AuthDataHolder.get(), e);
        }
        return exceptionViewResult;
    }


    public ExceptionViewResult buildExceptionViewResult(HttpServletRequest request, Exception e) {

        ExceptionViewResult exceptionViewResult = new ExceptionViewResult();
        exceptionViewResult.setSuccess(false);
        Throwable[] causeChain = throwableAnalyzer.determineCauseChain(e);
        ErrorCodeException ece = (ErrorCodeException) throwableAnalyzer.getFirstThrowableOfType(ErrorCodeException.class, causeChain);
        if (ece != null) {
            exceptionViewResult.setSkipLog(ece.isSkipLog());
            exceptionViewResult.setCode(ece.getErrorCode());
            exceptionViewResult.setMessage(ece.getMessage());
            return processExceptionViewResult(exceptionViewResult, e);
        }

//        ExpiredTokenException ete = (ExpiredTokenException) throwableAnalyzer.getFirstThrowableOfType(ExpiredTokenException.class, causeChain);
//        if (ete != null) {
//            apiDataBody.setCode(ete.getOAuth2ErrorCode());
//            apiDataBody.setMessage(ete.getMessage());
//            return apiResponseBody;
//        }
//
//        InvalidTokenException ite = (InvalidTokenException) throwableAnalyzer.getFirstThrowableOfType(InvalidTokenException.class, causeChain);
//        if (ite != null) {
//            /**
//             * @see CheckTokenEndpoint#checkToken(String)
//             * @see DefaultTokenServices#refreshAccessToken
//             */
//            if ("Token has expired".equals(ite.getMessage()) || ite.getMessage().startsWith("Invalid refresh token (expired):")) {
//                apiDataBody.setCode(ApiCommonError.ERROR_TOKEN_EXPIRED);
//                apiDataBody.setMessage(ite.getMessage());
//                return apiResponseBody;
//            }
//        }

        AccessDeniedException ade = (AccessDeniedException) throwableAnalyzer.getFirstThrowableOfType(AccessDeniedException.class, causeChain);
        if (ade != null) {
            exceptionViewResult.setSkipLog(false);
            exceptionViewResult.setCode(ErrorCodeEnum.AccessDenied.getCode());
            exceptionViewResult.setMessage(ade.getMessage());
            return processExceptionViewResult(exceptionViewResult, e);
        }

        DataTruncation dataTruncation = (DataTruncation) throwableAnalyzer.getFirstThrowableOfType(DataTruncation.class, causeChain);
        if (dataTruncation != null) {
            exceptionViewResult.setSkipLog(true);
            exceptionViewResult.setCode(ErrorCodeEnum.DataConstraint.getCode());
            exceptionViewResult.setMessage("提交数据内容过长，请检查修正");
            return processExceptionViewResult(exceptionViewResult, e);
        }

        SQLIntegrityConstraintViolationException sqle = (SQLIntegrityConstraintViolationException) throwableAnalyzer.getFirstThrowableOfType(SQLIntegrityConstraintViolationException.class, causeChain);
        if (sqle != null) {
            String message = sqle.getMessage();
            if (message.startsWith("Duplicate")) {
                exceptionViewResult.setSkipLog(false);
                exceptionViewResult.setCode(ErrorCodeEnum.DataConstraint.getCode());
                exceptionViewResult.setMessage("提交数据已存在，请检查修正");
                return processExceptionViewResult(exceptionViewResult, e);
            }
        }

        String message = log.isDebugEnabled() && StringUtils.isNotEmpty(e.getMessage()) ? e.getMessage() : "系统处理异常";
        exceptionViewResult.setSkipLog(false);
        exceptionViewResult.setCode(ErrorCodeEnum.DataConstraint.getCode());
        exceptionViewResult.setMessage(message);
        return processExceptionViewResult(exceptionViewResult, e);
    }
}

