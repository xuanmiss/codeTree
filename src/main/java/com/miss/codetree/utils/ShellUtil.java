package com.miss.codetree.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ShellUtil {
    public static String runShell(String shStr) throws Exception {
        // 判断是否为Windows系统
        boolean isWindows = isWindows();

        // 根据操作系统选择执行命令的方式
        String[] cmd;
        if (isWindows) {
            cmd = new String[]{"cmd.exe", "/c", shStr};
        } else {
            cmd = new String[]{"/bin/sh", "-c", shStr};
        }

        Process process = Runtime.getRuntime().exec(cmd);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append(System.lineSeparator());
            }
            // 等待命令执行完成
            process.waitFor();
            return result.toString();
        }
    }

    // 判断当前系统是否为Windows
    private static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }
}