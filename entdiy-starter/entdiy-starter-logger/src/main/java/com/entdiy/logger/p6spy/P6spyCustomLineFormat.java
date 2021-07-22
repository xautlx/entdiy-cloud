package com.entdiy.logger.p6spy;

import com.p6spy.engine.common.P6Util;
import com.p6spy.engine.spy.P6SpyOptions;
import com.p6spy.engine.spy.appender.CustomLineFormat;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import com.p6spy.engine.spy.appender.SingleLineFormat;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @see CustomLineFormat
 */
public class P6spyCustomLineFormat extends CustomLineFormat {

    private static final MessageFormattingStrategy FALLBACK_FORMATTING_STRATEGY = new SingleLineFormat();

    /**
     * Formats a log message for the logging module
     *
     * @param connectionId the id of the connection
     * @param now          the current ime expressing in milliseconds
     * @param elapsed      the time in milliseconds that the operation took to complete
     * @param category     the category of the operation
     * @param prepared     the SQL statement with all bind variables replaced with actual values
     * @param sql          the sql statement executed
     * @param url          the database url where the sql statement executed
     * @return the formatted log message
     */
    @Override
    public String formatMessage(final int connectionId, final String now, final long elapsed, final String category, final String prepared, final String sql, final String url) {

        String customLogMessageFormat = P6SpyOptions.getActiveInstance().getCustomLogMessageFormat();

        if (customLogMessageFormat == null) {
            // Someone forgot to configure customLogMessageFormat: fall back to built-in
            return FALLBACK_FORMATTING_STRATEGY.formatMessage(connectionId, now, elapsed, category, prepared, sql, url);
        }

        return customLogMessageFormat
                .replaceAll(Pattern.quote(CONNECTION_ID), Integer.toString(connectionId))
                .replaceAll(Pattern.quote(CURRENT_TIME), now)
                .replaceAll(Pattern.quote(EXECUTION_TIME), Long.toString(elapsed))
                .replaceAll(Pattern.quote(CATEGORY), category)
                .replaceAll(Pattern.quote(EFFECTIVE_SQL), Matcher.quoteReplacement(prepared))
                .replaceAll(Pattern.quote(EFFECTIVE_SQL_SINGLELINE), Matcher.quoteReplacement(P6Util.singleLine(prepared)))
                .replaceAll(Pattern.quote(SQL), Matcher.quoteReplacement(sql))
                .replaceAll(Pattern.quote(SQL_SINGLE_LINE), Matcher.quoteReplacement(P6Util.singleLine(sql)))
                //提取关键URL部分日志显示
                .replaceAll(Pattern.quote(URL), StringUtils.substringAfter(StringUtils.indexOf(url, "?") > -1 ? StringUtils.substringBefore(url, "?") : url, "//"));
    }
}
