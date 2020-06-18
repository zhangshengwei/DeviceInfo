package com.zsw.deviceinfo;

import android.app.Application;

import com.zsw.deviceinfosdk.DeviceInfoSDK;
import com.zsw.deviceinfosdk.config.DeviceInfoConfig;

/**
 * @Description:
 * @Author: xianggu
 * @CreateDate: 2020-06-18 10:57
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initDeviceInfoSDK();
    }



    private void initDeviceInfoSDK() {
        DeviceInfoSDK.getInstance().initSDK(this,new DeviceInfoConfig.Builder()
                .initOAID(true)
                .userLoacalIDStrategy(true)
                .gatherDeviceInfo(true)
                .gatherApkInstalledList(true)
                .build());
    }
}
