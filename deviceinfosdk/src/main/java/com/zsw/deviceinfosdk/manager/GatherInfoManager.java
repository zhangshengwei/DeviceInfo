package com.zsw.deviceinfosdk.manager;

import android.os.Build;

import com.bun.miitmdid.core.MdidSdkHelper;
import com.bun.supplier.IIdentifierListener;
import com.bun.supplier.IdSupplier;
import com.zsw.deviceinfosdk.DeviceInfoSDK;
import com.zsw.deviceinfosdk.config.Constant;
import com.zsw.deviceinfosdk.entity.CustomBluetoothInfo;
import com.zsw.deviceinfosdk.entity.CustomPhoneState;
import com.zsw.deviceinfosdk.entity.CustomWifiInfo;
import com.zsw.deviceinfosdk.entity.DeviceData;
import com.zsw.deviceinfosdk.entity.WifiOtherDeviceData;
import com.zsw.deviceinfosdk.utils.AdvertisingUtil;
import com.zsw.deviceinfosdk.utils.BatteryUtil;
import com.zsw.deviceinfosdk.utils.BlueToothUtil;
import com.zsw.deviceinfosdk.utils.CpuUtil;
import com.zsw.deviceinfosdk.utils.NetworkUtil;
import com.zsw.deviceinfosdk.utils.OtherDataUtil;
import com.zsw.deviceinfosdk.utils.PhoneStateUtil;
import com.zsw.deviceinfosdk.utils.ScreenUtils;
import com.zsw.deviceinfosdk.utils.WifiUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * @Description:
 * @Author: xianggu
 * @CreateDate: 2020-06-18 11:42
 */
public class GatherInfoManager {

    public static GatherInfoManager instance;

    private DeviceData deviceData;

    public GatherInfoManager(){
        if (deviceData == null){
            deviceData = new DeviceData();
        }
    }

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


    public DeviceData getDeviceData() {
        return deviceData;
    }

    /**
     * 开始采集设备信息
     */
    public void startGatherDeviceInfo(){
        if (deviceData == null){
            deviceData = new DeviceData();
        }
        if (DeviceInfoSDK.getInstance().isOaidInitSuccess()){
            // 如果OAID 已经初始化了，则去获取OAID 相关信息
            MdidSdkHelper.InitSdk(DeviceInfoSDK.getInstance().getContext(), true, new IIdentifierListener() {
                @Override
                public void OnSupport(boolean b, IdSupplier idSupplier) {
                    // idSupplier.isSupported() : 是否支持补充设备标 识符获取 ,true 为支持，false 为不支持
                    if (idSupplier != null && idSupplier.isSupported()){
                        deviceData.setOAID(idSupplier.getOAID());
                        deviceData.setAAID(idSupplier.getAAID());
                        deviceData.setVAID(idSupplier.getVAID());
                    }
                }
            });
        }

        if (DeviceInfoSDK.getInstance().getDeviceInfoConfig().isGatherApkInstalledList()){
            // 获取设备上的安装列表
        }

        if (DeviceInfoSDK.getInstance().getDeviceInfoConfig().isGatherDeviceInfo()){

            gatherDeviceInfo(deviceData);

            gatherLocalInfo(deviceData);

            gatherNetworkData(deviceData);

            gatherBluetoothData(deviceData);

            gatherOtherData(deviceData);
        }

        if (DeviceInfoSDK.getInstance().getDeviceInfoConfig().isUserLocalIDStrategy()){

        }
    }


    /**
     * 获取设备相关信息
     * @param deviceData
     */
    private void gatherDeviceInfo(DeviceData deviceData) {
        // 获取设备宽高，分辨率
        deviceData.setScreenWidth(ScreenUtils.getScreenWidth());
        deviceData.setScreenHeight(ScreenUtils.getScreenHeight());
        deviceData.setSdScreendpi(String.valueOf(ScreenUtils.getDensityDpi()));

        // 获取设备操作系统版本，产商，手机型号
        deviceData.setOsVersion(Build.VERSION.RELEASE);
        deviceData.setVendor(Build.MANUFACTURER);
        deviceData.setModelNo(Build.MODEL);

        // 获取设备的imei，mac，这些信息
        CustomPhoneState phoneState = PhoneStateUtil.getPhoneState();
        deviceData.setImsi(phoneState.getImsi());
        deviceData.setImei(phoneState.getImei());
        deviceData.setMeid(phoneState.getMeid());
        deviceData.setIccid(phoneState.getIccid());
        deviceData.setMcc(phoneState.getMcc());
        deviceData.setRoaming(phoneState.getIsNetworkRoaming());
        deviceData.setAndroidId(phoneState.getAndroidId());
        deviceData.setDeviceType(phoneState.getDeviceType());

        // 手机与电池相关的数据
        deviceData.setBattery(BatteryUtil.getBatteryLevel());
    }


    private void gatherLocalInfo(DeviceData deviceData) {

    }


    /**
     * 收集网络相关数据
     */
    private static void gatherNetworkData(DeviceData deviceData) {
        deviceData.setNetworkType(NetworkUtil.getCurrentNetType(Constant.GET_DATA_NULL));
        deviceData.setIp(NetworkUtil.getHostIP());
        deviceData.setNetworkAddress(NetworkUtil.getMacAddress());

        CustomWifiInfo wifiInfo = WifiUtil.getWifiInfo();
        deviceData.setNetwkId(wifiInfo.getNetworkId());
        deviceData.setBssId(wifiInfo.getBssid());
        deviceData.setSsid(wifiInfo.getSsid());
        deviceData.setLksd(wifiInfo.getLinkSpeed());
        deviceData.setRssi(wifiInfo.getRssi());

        // 将其他连接WIFI的设备的mac地址以逗号分隔
        List<WifiOtherDeviceData> wifiOtherDeviceDataList = WifiUtil.datagramPacket(deviceData.getIp(), wifiInfo.getBssid());
        if (wifiOtherDeviceDataList != null && wifiOtherDeviceDataList.size() > 0) {
            try {
                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < wifiOtherDeviceDataList.size(); i++) {
                    WifiOtherDeviceData wifiOtherDeviceData = wifiOtherDeviceDataList.get(i);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("ip", wifiOtherDeviceData.getIp());
                    jsonObject.put("mac", wifiOtherDeviceData.getMac());
                    jsonArray.put(jsonObject);
                }
                wifiOtherDeviceDataList.clear();
                deviceData.setNd(jsonArray.toString());
                jsonArray = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void gatherBluetoothData(DeviceData deviceData) {
        CustomBluetoothInfo bluetoothInfo = BlueToothUtil.getBluetoothInfo();
        deviceData.setBtmac(bluetoothInfo.getBluetoothDevice() == null ? "" : bluetoothInfo.getBluetoothDevice().getMac());
        deviceData.setBluetoothInfo(bluetoothInfo);
    }



    private void gatherOtherData(DeviceData deviceData) {
        deviceData.setMachineType(1);
        deviceData.setUa("");
        deviceData.setIdfa("");
        deviceData.setIdfv("");
        deviceData.setOpenUdid("");
        deviceData.setCid(OtherDataUtil.getCid());
        deviceData.setPdunid(OtherDataUtil.getUniquePsuedoID());
        deviceData.setCputy(CpuUtil.getCpuHardware());

        deviceData.setAdvertisingId(AdvertisingUtil.getGoogleAdId());
    }
}
