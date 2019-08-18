package com.gengyu.msgnotification.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Siegfried GENG
 * @date 2019/8/16 - 14:17
 */
public class StringUtil {

/*    public static List<String> generateRandomStr(int length, int num){
        String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        List<String> stringList = new ArrayList<>();

        for (int i = 0; i < num; i++) {
            StringBuffer sb = new StringBuffer();
            for (int j = 0; j < length; j++) {
                int number = random.nextInt(base.length());
                sb.append(base.charAt(number));
            }
            stringList.add(sb.toString());
        }
        return stringList;
    }*/

    public static String generateSingleStr(int length){
        String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int j = 0; j < length; j++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
