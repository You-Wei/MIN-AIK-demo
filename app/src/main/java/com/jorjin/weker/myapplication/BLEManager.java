package com.jorjin.weker.myapplication;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothManager;

/**
 * Created by weker on 8/24/2016.
 */
public class BLEManager {

    public static String HEART_RATE_MEASUREMENT = "00002a37";

    private BluetoothManager mbluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothGatt mBluetoothGatt;
    private Integer mConnectionState;
    private final Integer REQUEST_ENABLE_BT = 1;
    private static final long SCAN_PERIOD = 10000;
    private boolean mScanning;
    private boolean mConnected;


}

