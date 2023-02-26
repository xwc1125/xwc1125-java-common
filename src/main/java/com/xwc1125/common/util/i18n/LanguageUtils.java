package com.xwc1125.common.util.i18n;

import com.xwc1125.common.util.string.StringUtils;
import java.util.HashMap;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @Description:
 * @Author: xwc1125
 * @Date: 2019-04-23 19:04
 * @Copyright Copyright@2019
 */
public class LanguageUtils {

    private static Logger log = LoggerFactory.getLogger(LanguageUtils.class);
    public static final String KEY_LANGUAGE_DIC = "OS:LANGUAGE:DIC";
    public static final String DEFAULT_LANGUAGE = "en";

    public static HashMap<String, String> hashMap = new HashMap<>();

    /**
     * Description: 初始化
     * </p>
     *
     * @param
     * @return void
     * @Author: xwc1125
     * @Date: 2019-04-23 19:22:53
     */
    public static void init() {
        hashMap = new HashMap<>();
    }

    /**
     * Description: 添加数据
     * </p>
     *
     * @param subKey
     * @param content
     * @return void
     * @Author: xwc1125
     * @Date: 2019-04-23 19:23:02
     */
    public static void put(String subKey, String content) {
        hashMap.put(subKey, content);
    }

    public static String getMessage(String bizCode) {
        Locale locale = LocaleContextHolder.getLocale();
        return getMessage(locale.getLanguage(), bizCode, null);
    }

    public static String getMessage(String bizCode, String defaultMessage) {
        log.debug("开始获取语言");
        Locale locale = LocaleContextHolder.getLocale();
        log.debug("结束获取语言");
        return getMessage(locale.getLanguage(), bizCode, defaultMessage);
    }

    /**
     * Description: 获取语言信息
     * </p>
     *
     * @param lang
     * @param bizCode
     * @param defaultMessage
     * @return java.lang.String
     * @Author: xwc1125
     * @Date: 2019-04-23 18:59:58
     */
    public static String getMessage(String lang, String bizCode, String defaultMessage) {
        String subKey = getSubKey(lang, bizCode);
        log.debug("结束获取subKey");
        String content = hashMap.get(subKey);
        log.debug("结束获取content");
        if (StringUtils.isEmpty(content)) {
            content = hashMap.get(getSubKey(DEFAULT_LANGUAGE, bizCode));
            log.debug("结束获取默认语言的content");
        }
        if (StringUtils.isEmpty(content) && StringUtils.isNotEmpty(defaultMessage)) {
            content = defaultMessage;
            log.debug("返回默认数据");
        }
        return content;
    }

    /**
     * Description: 获取subKey
     * </p>
     *
     * @param lang
     * @param bizCode
     * @return java.lang.String
     * @Author: xwc1125
     * @Date: 2019-04-23 19:09:16
     */
    public static String getSubKey(String lang, String bizCode) {
        if (StringUtils.isEmpty(lang)) {
            lang = DEFAULT_LANGUAGE;
        }
        String[] langArrays = lang.split("_");
        lang = langArrays[0];
        return lang.toLowerCase() + "_" + bizCode.toLowerCase();
    }
}
