package com.entdiy.common.web;

import com.entdiy.common.exception.ErrorCodeException;
import com.entdiy.common.model.ViewResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class GlobalWebExceptionResolver implements HandlerExceptionResolver {

    private static DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            // 解析date+time
            .appendPattern("yyyyMMddHHmmss")
            // 解析毫秒数
            .appendValue(ChronoField.MILLI_OF_SECOND, 3)
            .toFormatter();

    public static ThrowableAnalyzer throwableAnalyzer = new ThrowableAnalyzer();

    public static String buildEID() {
        // TODO 优先从链路跟踪系统取全局唯一ID，如果没有则动态创建随机ID
        String eid = "ERR" + DATE_TIME_FORMATTER.format(LocalDateTime.now()) + RandomStringUtils.randomNumeric(3);
        return eid;
    }

    /**
     * Web层全局异常处理
     *
     * @param request
     * @param response
     * @param aHandler
     * @param e
     * @return
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object aHandler, Exception e) {
        ModelAndView mv = new ModelAndView();
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        view.setExtractValueFromSingleKeyModel(true);

        Map<String, Object> attributes = new HashMap(4);
        attributes.put("body", buildResponseBody(request,response, e));
        view.setAttributesMap(attributes);
        mv.setView(view);
        return mv;
    }

    public static ViewResult buildResponseBody(HttpServletRequest request,HttpServletResponse response, Exception e) {
        Throwable[] causeChain = throwableAnalyzer.determineCauseChain(e);
        ViewResult viewResult = null;

        if (viewResult == null) {
            ErrorCodeException exception = (ErrorCodeException) throwableAnalyzer.getFirstThrowableOfType(ErrorCodeException.class, causeChain);
            if (exception != null) {
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                viewResult = ViewResult.error(exception.getCode(), exception.getMessage()).skipLog(exception.isSkipLog());
            }
        }

        if (viewResult == null) {
            InsufficientAuthenticationException exception = (InsufficientAuthenticationException) throwableAnalyzer.getFirstThrowableOfType(InsufficientAuthenticationException.class, causeChain);
            if (exception != null) {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                viewResult = ViewResult.error(HttpStatus.FORBIDDEN, "权限不足").skipLog(true);
            }
        }

        if (viewResult == null) {
            BadCredentialsException exception = (BadCredentialsException) throwableAnalyzer.getFirstThrowableOfType(BadCredentialsException.class, causeChain);
            if (exception != null) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                viewResult = ViewResult.error(HttpStatus.UNAUTHORIZED, "账号或密码不正确").skipLog(true);
            }
        }

        if (viewResult == null) {
            AccessDeniedException exception = (AccessDeniedException) throwableAnalyzer.getFirstThrowableOfType(AccessDeniedException.class, causeChain);
            if (exception != null) {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                viewResult = ViewResult.error(HttpStatus.FORBIDDEN, "访问拒绝").skipLog(true);
            }
        }

        if (viewResult == null) {
            DataTruncation exception = (DataTruncation) throwableAnalyzer.getFirstThrowableOfType(DataTruncation.class, causeChain);
            if (exception != null) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                viewResult = ViewResult.error(HttpStatus.BAD_REQUEST, "提交数据内容过长，请检查修正").skipLog(false);
            }
        }

        if (viewResult == null) {
            SQLIntegrityConstraintViolationException exception = (SQLIntegrityConstraintViolationException) throwableAnalyzer.getFirstThrowableOfType(SQLIntegrityConstraintViolationException.class, causeChain);
            if (exception != null) {
                String message = exception.getMessage();
                if (message.startsWith("Duplicate")) {
                    response.setStatus(HttpStatus.BAD_REQUEST.value());
                    viewResult = ViewResult.error(HttpStatus.BAD_REQUEST, "提交数据已存在，请检查修正").skipLog(false);
                }
            }
        }

        if (viewResult == null) {
            MethodArgumentNotValidException mane = (MethodArgumentNotValidException) throwableAnalyzer.getFirstThrowableOfType(MethodArgumentNotValidException.class, causeChain);
            if (mane != null) {
                List<FieldError> fieldErrors = mane.getBindingResult().getFieldErrors();
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                viewResult = ViewResult.error(HttpStatus.BAD_REQUEST,
                        fieldErrors.stream().map(fe -> fe.getField() + fe.getDefaultMessage()).collect(Collectors.joining(";"))).skipLog(true);
                return viewResult;
            }
        }

        if (viewResult == null) {
            String msg = log.isDebugEnabled() && StringUtils.isNotEmpty(e.getMessage()) ? e.getMessage() : "系统处理异常";
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            viewResult = ViewResult.error(HttpStatus.INTERNAL_SERVER_ERROR, msg).skipLog(false);
        }

        viewResult.setTimestamp(System.currentTimeMillis());
        viewResult.setPath(request.getRequestURI());
        if (!viewResult.skipLog()) {
            String eid = buildEID();
            viewResult.setTraceId(eid);
            log.error("Error Data: " + viewResult, e);
        } else if (log.isDebugEnabled()) {
            log.debug("Error Data: " + viewResult, e);
        }
        return viewResult;
    }

}
