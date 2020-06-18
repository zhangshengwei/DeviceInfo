package com.zsw.deviceinfosdk;

import android.app.Application;
import android.content.Context;
import android.os.Looper;

import com.bun.miitmdid.core.JLibrary;
import com.zsw.deviceinfosdk.config.DeviceInfoConfig;
import com.zsw.deviceinfosdk.entity.DeviceData;
import com.zsw.deviceinfosdk.manager.GatherInfoManager;

import androidx.annotation.NonNull;

/**
 * @Description:
 * @Author: xianggu
 * @CreateDate: 2020-06-18 10:14
 */
public class DeviceInfoSDK {

    private Context context;
    private static DeviceInfoSDK instance;
    private DeviceInfoConfig deviceInfoConfig;
    private boolean oaidInitSuccess;


    private DeviceInfoSDK() {
    }

    public static DeviceInfoSDK getInstance() {
        if (instance == null) {
            synchronized (DeviceInfoSDK.class) {
                if (instance == null) {
                    instance = new DeviceInfoSDK();
                }
            }
        }
        return instance;
    }

    public void initSDK(Context context, @NonNull DeviceInfoConfig deviceInfoConfig) {
        if (Looper.getMainLooper() != Looper.myLooper()){
            throw new RuntimeException("Must call in the main thread!!");
        }
        this.context = context.getApplicationContext();
        this.deviceInfoConfig = deviceInfoConfig;

        if (getContext() instanceof Application){
            // 是否初始化OAID
            if (deviceInfoConfig.isInitOAID()){
                initOAID();
            }

            // TODO: 2020-06-18 开始收集普通的设备信息，例如设备版本，产商等
            GatherInfoManager.getInstance().startGatherDeviceInfo();
        }
    }

    /**
     * 初始化OAID
     */
    private void initOAID() {
        try {
            JLibrary.InitEntry(getContext());
            oaidInitSuccess = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Context getContext() {
        return context;
    }

    public DeviceInfoConfig getDeviceInfoConfig() {
        return deviceInfoConfig;
    }

    public boolean isOaidInitSuccess() {
        return oaidInitSuccess;
    }

    /**
     * 返回设备信息
     * @return
     */
    public DeviceData getDeviceData() {
        return GatherInfoManager.getInstance().getDeviceData();
    }
}
