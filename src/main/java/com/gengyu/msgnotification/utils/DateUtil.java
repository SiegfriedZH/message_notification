package com.gengyu.msgnotification.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Siegfried GENG
 * @date 2019/8/13 - 15:36
 */
@Slf4j
public class DateUtil {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Date convertStrToDate(String dateStr) throws ParseException {
        Date date = sdf.parse(dateStr);
        return date;
    }

    public static Date validateDateTime(Date dateExpected){

        Date dateNow = new Date();
        Date dateResult;
        /// 传入的参数为邮件的期待首发时间，应该大于等于当前时间。
        long dateExpectedStamp = dateExpected.getTime();
        long dateNowStamp = System.currentTimeMillis();
        long gap = dateExpectedStamp - dateNowStamp;
        log.info("期待的首发时间，当前时间，分别为：{}, {}", dateExpectedStamp, dateNowStamp);
        if (gap < 0){
            log.info("=======设置的邮件首发时间已过，现改为从当前时间首发！");
            dateResult = dateNow;
        } else {
            log.info("=======设置的邮件首发时间未过，正常！");
            dateResult = dateExpected;
        }
        /// 再加上5秒延缓时间，作为邮件首发时间
        dateResult.setTime(dateResult.getTime() + 5000);
        return dateResult;
    }

    public static String getDateStr(){
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    public static String getDateTimeStr(){
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

}
