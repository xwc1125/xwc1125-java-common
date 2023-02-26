package com.xwc1125.common.util.io;

import java.io.*;

/**
 * Description: 文件处理工具类
 * </p>
 *
 * @Author: xwc1125
 * @Date: 2019-03-09 22:08:23
 * @Copyright Copyright@2019
 */
public class FileUtils {
    public static String FILENAME_PATTERN = "[a-zA-Z0-9_\\-\\|\\.\\u4e00-\\u9fa5]+";

    /**
     * 输出指定文件的byte数组
     *
     * @param filePath 文件路径
     * @param os       输出流
     * @return
     */
    public static void writeBytes(String filePath, OutputStream os) throws IOException {
        FileInputStream fis = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new FileNotFoundException(filePath);
            }
            fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            int length;
            while ((length = fis.read(b)) > 0) {
                os.write(b, 0, length);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除文件
     *
     * @param filePath 文件
     * @return
     */
    public static boolean deleteFile(String filePath) {
        boolean flag = false;
        File file = new File(filePath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 文件名称验证
     *
     * @param filename 文件名称
     * @return true 正常 false 非法
     */
    public static boolean isValidFilename(String filename) {
        return filename.matches(FILENAME_PATTERN);
    }

    /**
     * WEB-INF/upload/1/3 打散存储目录
     *
     * @param storePath
     * @param fileName
     * @return
     */
    public static String makeDir(String storePath, String fileName) {
        int hashCode = fileName.hashCode();// 得到文件名的hashcode码
        int dir1 = hashCode & 0xf;// 取hashCode的低4位 0~15
        int dir2 = (hashCode & 0xf0) >> 4;// 取hashCode的高4位 0~15
        String path = storePath + "\\" + dir1 + "\\" + dir2;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }
}
