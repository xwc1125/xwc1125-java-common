package com.xwc1125.common.util.excel;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: CVS导入导出
 * @Author: xwc1125
 * @Date: 2019-03-13 22:13
 * @Copyright Copyright@2019
 */
public class CSVUtils {

    /**
     * 解析csv文件并转成bean
     *
     * @param file  csv文件
     * @param clazz 类
     * @param <T>   泛型
     * @return 泛型bean集合
     */
    public static <T> List<T> importCsv(MultipartFile file, Class<T> clazz) throws IOException {
        InputStreamReader in = new InputStreamReader(file.getInputStream(), "gbk");
        HeaderColumnNameMappingStrategy<T> strategy = new HeaderColumnNameMappingStrategy<>();
        strategy.setType(clazz);

        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(in)
                .withSeparator(',')
                .withQuoteChar('\'')
                .withMappingStrategy(strategy).build();
        return csvToBean.parse();
    }

    /**
     * 解析csv文件并转成bean
     *
     * @param file  csv文件
     * @param clazz 类
     * @param <T>   泛型
     * @return 泛型bean集合
     */
    public static <T> List<T> importCsv(File file, Class<T> clazz) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(file));
        HeaderColumnNameMappingStrategy<T> strategy = new HeaderColumnNameMappingStrategy<>();
        strategy.setType(clazz);

        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(in)
                .withSeparator(',')
                .withQuoteChar('\'')
                .withMappingStrategy(strategy).build();
        return csvToBean.parse();
    }

    /**
     * 导入
     *
     * @param file csv文件(路径+文件)
     * @return
     */
    public static List importCsv(File file, RowCallback rowCallback) throws IOException {
        List dataList = new ArrayList();
        BufferedReader br = null;
        br = new BufferedReader(new FileReader(file));
        String line = "";
        while ((line = br.readLine()) != null) {
            if (rowCallback != null) {
                Object row = rowCallback.row(line);
                if (row != null) {
                    dataList.add(row);
                }
            } else {
                dataList.add(line);
            }
        }
        if (br != null) {
            try {
                br.close();
                br = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return dataList;
    }

    /**
     * 导出
     *
     * @param file     csv文件(路径+文件名)，csv文件不存在会自动创建
     * @param dataList 数据
     * @return
     */
    public static boolean exportCsv(File file, List<String> dataList) {
        boolean isSucess = false;

        FileOutputStream out = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try {
            out = new FileOutputStream(file);
            osw = new OutputStreamWriter(out);
            bw = new BufferedWriter(osw);
            if (dataList != null && !dataList.isEmpty()) {
                for (String data : dataList) {
                    bw.append(data).append("\r");
                }
            }
            isSucess = true;
        } catch (Exception e) {
            isSucess = false;
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                    bw = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (osw != null) {
                try {
                    osw.close();
                    osw = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                    out = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return isSucess;
    }

}
