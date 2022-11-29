package com.google.personalhealthrecordingplateform.util;

import java.security.SecureRandom;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public class StringUtils {
    /**
     * 字符串判断
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return str != null && !"".equals(str);
    }

    /**
     * 字符串判断
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

    /**
     * 集合判断
     *
     * @param collection
     * @return
     */
    public static boolean isNotEmpty(Map collection) {
        return collection != null && collection.size() > 0;
    }

    /**
     * 集合判断
     *
     * @param collection
     * @return
     */
    public static boolean isEmpty(Map collection) {
        return collection == null || collection.size() < 1;
    }

    /**
     * 集合判断
     *
     * @param collection
     * @return
     */
    public static boolean isNotEmpty(Collection collection) {
        return collection != null && collection.size() > 0;
    }

    /**
     * 集合判断
     *
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.size() < 1;
    }

    /**
     * 对象判断
     *
     * @param obj
     * @return
     */
    public static boolean isNotEmpty(Object obj) {
        return obj != null;
    }

    /**
     * 对象判断
     *
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj) {
        return obj == null;
    }

    /**
     * @Description: 生成唯一图片名称
     * @Param: fileName
     * @return: 保存到云服务器中的fileName
     */
    public static String getRandomImgName(String fileName) {

        int index = fileName.lastIndexOf(".");

        if (fileName.isEmpty() || index == -1) {
            throw new IllegalArgumentException();
        }
        // 获取文件后缀
        String suffix = fileName.substring(index).toLowerCase();
        // 生成UUID
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        // 生成上传至云服务器的路径
        return "userAvatar:" + uuid + suffix;
    }

    /**
     * 生成随机密码
     *
     * @param len 指定密码的长度
     * @return 随机生成的密码
     */
    public static String generateRandomPassword(int len) {
        // ASCII 范围 – 字母数字 (0-9, a-z, A-Z)
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        // 循环的每次迭代从给定的字符中随机选择一个字符
        // ASCII 范围并将其附加到 `StringBuilder` 实例

        for (int i = 0; i < len; i++) {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }

        return sb.toString();
    }
}
