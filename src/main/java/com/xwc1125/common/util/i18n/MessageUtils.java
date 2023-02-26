package com.xwc1125.common.util.i18n;

import com.xwc1125.common.util.spring.SpringUtils;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @Description: 获取i18n资源文件
 * @Author: xwc1125
 * @Date: 2019-03-09 21:46
 * @Copyright Copyright@2019
 */
public class MessageUtils {

    private static Logger log = LoggerFactory.getLogger(MessageUtils.class);
    /**
     * 国际化信息
     */
    private static final Map<String, ResourceBundle> MESSAGES = new HashMap<>();

    /**
     * Description: 获取语言版本
     * </p>
     *
     * @param language
     * @return java.util.Locale
     * @Author: xwc1125
     * @Date: 2019-04-18 18:04:51
     */
    public static Locale getLocale(String language) {
        try {
            return new Locale(language);
        } catch (Exception e) {
        }
        try {
            String[] split = language.split("_");
            return new Locale(split[0], split[1]);
        } catch (Exception e) {
            return new Locale(language);
        }
    }

    /**
     * 根据消息键和参数 获取消息 委托给spring messageSource
     *
     * @param code 消息键
     * @param args 参数
     * @return
     */
    public static String message(String code, Object... args) {
        return message(LocaleContextHolder.getLocale(), code, args);
    }

    public static String messageByLanguage(String language, String code, String defaultMsg, Object... args) {
        return message(getLocale(language), code, defaultMsg, args);
    }

    public static String messageByLanguage(String language, String code, Object... args) {
        return message(getLocale(language), code, null, args);
    }

    /**
     * 根据消息键和参数 获取消息 委托给spring messageSource
     *
     * @param locale
     * @param code   消息键
     * @param args   参数
     * @return
     */
    public static String message(Locale locale, String code, Object... args) {
        return message(locale, code, null, args);
    }

    /**
     * 根据消息键和参数 获取消息 委托给spring messageSource
     *
     * @param locale
     * @param code
     * @param defaultMsg
     * @param args
     * @return
     * @throws Exception
     */
    public static String message(Locale locale, String code, String defaultMsg, Object... args) {
        return message(locale, null, code, defaultMsg, args);
    }

    /**
     * Description: 根据消息键和参数 获取消息 委托给spring messageSource
     * </p>
     *
     * @param locale
     * @param defaultLocale
     * @param code
     * @param defaultMsg
     * @param args
     * @return java.lang.String
     * @Author: xwc1125
     * @Date: 2019-04-18 17:50:46
     */
    public static String message(Locale locale, Locale defaultLocale, String code, String defaultMsg, Object... args) {
        MessageSource messageSource = SpringUtils.getBean(MessageSource.class);
        if (locale != null) {
            try {
                return messageSource.getMessage(code, args, locale);
            } catch (Exception e) {
            }
        }
        if (defaultLocale != null) {
            try {
                return messageSource.getMessage(code, args, defaultLocale);
            } catch (Exception e1) {
            }
        }
        return defaultMsg;
    }

    /**
     * 国际化信息
     */
    public static String getMessage(String key, Object... params) {
        Locale locale = LocaleContextHolder.getLocale();
        ResourceBundle message = MESSAGES.get(locale.getLanguage());
        if (message == null) {
            synchronized (MESSAGES) {
                message = MESSAGES.get(locale.getLanguage());
                if (message == null) {
                    message = ResourceBundle.getBundle("i18n/messages", locale);
                    MESSAGES.put(locale.getLanguage(), message);
                }
            }
        }
        if (params != null && params.length > 0) {
            return String.format(message.getString(key), params);
        }
        return message.getString(key);
    }

    /**
     * 清除国际化信息
     */
    public static void flushMessage() {
        MESSAGES.clear();
    }

    /***
     * 根据路径获取资源
     * @param messagesBaseName 资源路径
     * @param locale
     * @param code
     * @param args
     * @return
     */
    public static String message(String messagesBaseName, Locale locale, String code, Object... args) throws Exception {
        ResourceBundle bundle = ResourceBundle.getBundle(messagesBaseName, locale);
        String content = bundle.getString(code);
        if (args != null) {
            content = new MessageFormat(content).format(args);
        }
        return content;
    }
}
