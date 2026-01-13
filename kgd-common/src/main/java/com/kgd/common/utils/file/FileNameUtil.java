package com.kgd.common.utils.file;

import org.springframework.stereotype.Component;

import java.io.File;


@Component
public class FileNameUtil {

    /**
     * 取“纯文件名”——去掉路径、去掉扩展名
     * 例：  ./uploadPath/upload/1762480671270_1-11-1-01.xlsx  ->  1762480671270_1-11-1-01
     */
    public static String getNameWithoutExt(String fullPath) {
        if (fullPath == null) return "";
        // 1. 去掉路径
        String name = new File(fullPath).getName();
        // 2. 去掉扩展名
        int dot = name.lastIndexOf('.');
        return dot == -1 ? name : name.substring(0, dot);
    }

    /**
     * 再进一步：去掉“时间戳_”前缀，得到最干净的编号
     * 规则：第一个下划线之后的所有内容
     * 例： 1762480671270_1-11-1-01  ->  1-11-1-01
     * 如果没有下划线，则原样返回
     */
    public static String getPureCode(String fullPath) {
        String s = getNameWithoutExt(fullPath);
        int firstUnder = s.indexOf('_');
        return firstUnder == -1 ? s : s.substring(firstUnder + 1);
    }
}
