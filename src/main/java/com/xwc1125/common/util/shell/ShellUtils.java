package com.xwc1125.common.util.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description:
 * @Author: xwc1125
 * @Date: 2019-05-14 17:33
 * @Copyright Copyright@2019
 */
public class ShellUtils {

    private static Logger log = LoggerFactory.getLogger(ShellUtils.class);
    /**
     * Description: shell命令
     * </p>
     *
     * @param command
     * @return java.lang.String 返回执行的结果
     * @Author: xwc1125
     * @Date: 2019-05-14 17:38:54
     */
    public static String exec(String[] command) throws IOException {
        String returnString = "";
        Process pid = null;
        Runtime runTime = Runtime.getRuntime();
        if (runTime == null) {
            throw new RuntimeException("Create runtime false!");
        }
//        String[] cmd = {"/bin/sh", "-c", command};
        pid = runTime.exec(command);
        BufferedReader input = new BufferedReader(new InputStreamReader(pid.getInputStream()));
        PrintWriter output = new PrintWriter(new OutputStreamWriter(pid.getOutputStream()));
        String line;
        while ((line = input.readLine()) != null) {
            returnString = returnString + line + "\n";
        }
        input.close();
        output.close();
        pid.destroy();
        return returnString;
    }

    /**
     * Description: 调用shell脚本，判断是否正常执行，如果正常结束，Process的waitFor()方法返回0
     * </p>
     *
     * @param command
     * @return boolean
     * @Author: xwc1125
     * @Date: 2019-05-14 18:30:16
     */
    public static boolean callShell(String command) throws InterruptedException, IOException {
        String[] cmd = {"/bin/sh", "-c", command};
        Process pid = Runtime.getRuntime().exec(command);
        int exitValue = pid.waitFor();
        if (0 != exitValue) {
            log.info("call shell failed. error code is :" + exitValue);
        }
        return true;
    }

}
