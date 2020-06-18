package com.zsw.deviceinfosdk;

/**
 * @Description:
 * @Author: xianggu
 * @CreateDate: 2020-06-18 10:14
 */
public class DeviceInfoSDK {

    private static DeviceInfoSDK instance;

    private DeviceInfoSDK(){}

    public static DeviceInfoSDK getInstance() {
        if (instance == null){
            synchronized (DeviceInfoSDK.class){
                if (instance == null){
                    instance = new DeviceInfoSDK();
                }
            }
        }
        return instance;
    }


}
