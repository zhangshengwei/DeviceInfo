package com.zsw.deviceinfosdk.config;

import com.zsw.deviceinfosdk.DeviceInfoSDK;

/**
 * @Description:
 * @Author: xianggu
 * @CreateDate: 2020-06-18 11:01
 */
public class DeviceInfoConfig {

    /**
     * 是否初始化OAID，默认初始OAID
     */
    private boolean isInitOAID = true;

    /**
     * 是否启用本地ID 策略,默认不使用
     */
    private boolean isUserLocalIDStrategy = false;

    /**
     * 是否采集设备信息
     */
    private boolean isGatherDeviceInfo = true;

    /**
     * 是否收集apk 安装列表
     */
    private boolean isGatherApkInstalledList = true;


    public boolean isInitOAID() {
        return isInitOAID;
    }

    public boolean isUserLocalIDStrategy() {
        return isUserLocalIDStrategy;
    }

    public boolean isGatherDeviceInfo() {
        return isGatherDeviceInfo;
    }

    public boolean isGatherApkInstalledList() {
        return isGatherApkInstalledList;
    }

    /**
     * builder 模式
     */
    public static class Builder{
        private DeviceInfoConfig deviceInfoConfig = new DeviceInfoConfig();

        public Builder initOAID(boolean isInitOAID){
            deviceInfoConfig.isInitOAID = isInitOAID;
            return this;
        }

        public Builder userLoacalIDStrategy(boolean isUserLocalIDStrategy){
            deviceInfoConfig.isUserLocalIDStrategy = isUserLocalIDStrategy;
            return this;
        }

        public Builder gatherDeviceInfo(boolean isGatherDeviceInfo){
            deviceInfoConfig.isGatherDeviceInfo = isGatherDeviceInfo;
            return this;
        }

        public Builder gatherApkInstalledList(boolean isGatherApkInstalledList){
            deviceInfoConfig.isGatherApkInstalledList = isGatherApkInstalledList;
            return this;
        }


        public DeviceInfoConfig build(){
            return deviceInfoConfig;
        }
    }
}
