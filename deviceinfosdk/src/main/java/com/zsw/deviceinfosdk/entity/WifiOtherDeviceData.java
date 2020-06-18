package com.zsw.deviceinfosdk.entity;

/**
 * @author ciba
 * @description 同一WiFi下其他Device信息
 * @date 2018/12/12
 */
public class WifiOtherDeviceData {
    private String ip;
    private String mac;
    private String flag;

    public WifiOtherDeviceData(String ip, String mac, String flag) {
        this.ip = ip;
        this.mac = mac;
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
