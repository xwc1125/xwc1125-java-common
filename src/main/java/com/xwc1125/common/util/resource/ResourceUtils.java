package com.xwc1125.common.util.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * @Description:
 * @Author: xwc1125
 * @Date: 2020/12/10 11:30
 * @Copyright Copyright@2020
 */
public class ResourceUtils {
    public static final String CLASSPATH_PREFIX = "classpath:";
    public static final String URL_PREFIX = "url:";
    public static final String FILE_PREFIX = "file:";
    private static final Logger log = LoggerFactory.getLogger(ResourceUtils.class);

    private ResourceUtils() {
    }

    public static boolean hasResourcePrefix(String resourcePath) {
        return resourcePath != null && (resourcePath.startsWith("classpath:") || resourcePath.startsWith("url:") || resourcePath.startsWith("file:"));
    }

    public static boolean resourceExists(String resourcePath) {
        InputStream stream = null;
        boolean exists = false;

        try {
            stream = getInputStreamForPath(resourcePath);
            exists = true;
        } catch (IOException var12) {
            stream = null;
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException var11) {
                }
            }

        }

        return exists;
    }

    public static InputStream getInputStreamForPath(String resourcePath) throws IOException {
        InputStream is;
        if (resourcePath.startsWith(CLASSPATH_PREFIX)) {
            is = loadFromClassPath(stripPrefix(resourcePath));
        } else if (resourcePath.startsWith(URL_PREFIX)) {
            is = loadFromUrl(stripPrefix(resourcePath));
        } else if (resourcePath.startsWith(FILE_PREFIX)) {
            is = loadFromFile(stripPrefix(resourcePath));
        } else {
            is = loadFromFile(resourcePath);
        }

        if (is == null) {
            throw new IOException("Resource [" + resourcePath + "] could not be found.");
        } else {
            return is;
        }
    }

    public static Resource getResourceFromPath(String resourcePath) throws Exception {
        try {
            File file;
            URL url = ResourceUtils.class.getResource(resourcePath);
            if (url == null) {
                file = new File(resourcePath);
            } else {
                file = new File(url.getFile());
            }
            ClassPathResource resource=new ClassPathResource(resourcePath);
//            FileSystemResource resource = new FileSystemResource(file);
            return resource;
        } catch (Exception e) {
            throw new Exception("getResourceFromPath err", e);
        }
    }

    private static InputStream loadFromFile(String path) throws IOException {
        if (log.isDebugEnabled()) {
            log.debug("Opening file [" + path + "]...");
        }

        return new FileInputStream(path);
    }

    private static InputStream loadFromUrl(String urlPath) throws IOException {
        log.debug("Opening url {}", urlPath);
        URL url = new URL(urlPath);
        return url.openStream();
    }

    private static InputStream loadFromClassPath(String path) {
        log.debug("Opening resource from class path [{}]", path);
        return ClassUtils.getResourceAsStream(path);
    }

    private static String stripPrefix(String resourcePath) {
        return resourcePath.substring(resourcePath.indexOf(":") + 1);
    }

    public static void close(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException var2) {
                log.warn("Error closing input stream.", var2);
            }
        }

    }
}

