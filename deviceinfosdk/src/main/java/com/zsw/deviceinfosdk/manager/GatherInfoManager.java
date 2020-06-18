package com.zsw.deviceinfosdk.manager;

import com.bun.miitmdid.core.MdidSdkHelper;
import com.bun.supplier.IIdentifierListener;
import com.bun.supplier.IdSupplier;
import com.zsw.deviceinfosdk.DeviceInfoSDK;

/**
 * @Description:
 * @Author: xianggu
 * @CreateDate: 2020-06-18 11:42
 */
public class GatherInfoManager {

    public static GatherInfoManager instance;

    public GatherInfoManager(){}

    public static GatherInfoManager getInstance(){
        if (instance == null) {
            synchronized (GatherInfoManager.class) {
                if (instance == null) {
                    instance = new GatherInfoManager();
                }
            }
        }
        return instance;
    }


    /**
     * 开始采集设备信息
     */
    public void startGatherDeviceInfo(){
        if (DeviceInfoSDK.getInstance().isOaidInitSuccess()){
            MdidSdkHelper.InitSdk(DeviceInfoSDK.getInstance().getContext(), true, new IIdentifierListener() {
                @Override
                public void OnSupport(boolean b, IdSupplier idSupplier) {
                    // idSupplier.isSupported() : 是否支持补充设备标 识符获取 ,true 为支持，false 为不支持
                    if (idSupplier != null && idSupplier.isSupported()){
                    }
                }
            });
        }

    }
}
