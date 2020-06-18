package com.zsw.deviceinfosdk.utils;

import android.os.Build;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.UUID;

/**
 * @author ciba
 * @description 描述
 * @date 2018/12/4
 */
public class OtherDataUtil {
    /**
     * 获取cid
     */
    public static String getCid() {
        String str1 = null;
        FileReader fileReader1 = null;
        FileReader fileReader2 = null;
        BufferedReader bufferedReader1 = null;
        BufferedReader bufferedReader2 = null;
        try {
            fileReader1 = new FileReader("/sys/block/mmcblk0/device/type");
            bufferedReader1 = new BufferedReader(fileReader1);
            boolean localOb = bufferedReader1.readLine().toLowerCase().contentEquals("mmc");
            if (localOb) {
                str1 = "/sys/block/mmcblk0/device/";
            }
            // nand ID
            fileReader2 = new FileReader(str1 + "cid");
            bufferedReader2 = new BufferedReader(fileReader2);
            str1 = bufferedReader2.readLine();
        } catch (Exception e) {
           e.printStackTrace();
        } finally {
            if (fileReader1 != null) {
                try {
                    fileReader1.close();
                    fileReader1 = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileReader2 != null) {
                try {
                    fileReader2.close();
                    fileReader2 = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedReader1 != null) {
                try {
                    bufferedReader1.close();
                    bufferedReader1 = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedReader2 != null) {
                try {
                    bufferedReader2.close();
                    bufferedReader2 = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return str1;
    }

    public static String getUniquePsuedoID() {
        String serial = null;
        String deviceId = null;
        try {
            deviceId = "35" +
                    Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +

                    Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +

                    Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +

                    Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +

                    Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +

                    Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +

                    Build.USER.length() % 10; //13 位
            serial = android.os.Build.class.getField("SERIAL").get(null).toString();
            //API>=9 使用serial号
            return new UUID(deviceId.hashCode(), serial.hashCode()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            //serial需要一个初始化
            // 随便一个初始化
            serial = "serial";
        }
        if (deviceId == null) {
            deviceId = "";
        }
        //使用硬件信息拼凑出来的15位号码
        return new UUID(deviceId.hashCode(), serial.hashCode()).toString();
    }
}
