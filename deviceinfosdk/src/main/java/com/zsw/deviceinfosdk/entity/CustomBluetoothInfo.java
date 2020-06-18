package com.zsw.deviceinfosdk.entity;

import java.util.List;

/**
 * @author ciba
 * @description
 * @date 2019/2/15
 */
public class CustomBluetoothInfo {
    private CustomBluetoothDevice bluetoothDevice;
    private List<CustomBluetoothDevice> bondedDevices;

    public CustomBluetoothDevice getBluetoothDevice() {
        return bluetoothDevice;
    }

    public void setBluetoothDevice(CustomBluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }

    public List<CustomBluetoothDevice> getBondedDevices() {
        return bondedDevices;
    }

    public void setBondedDevices(List<CustomBluetoothDevice> bondedDevices) {
        this.bondedDevices = bondedDevices;
    }

    public static class CustomBluetoothDevice{
        private String name;
        private String mac;
        private String type;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMac() {
            return mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
