package com.guvensahin.coffeedictionary;

import android.app.Application;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {

    private static App mApp;

    @Override
    public void onCreate() {
        super.onCreate();

        // context
        mApp = this;

        // admob
        MobileAds.initialize(this);

        // test cihazları eklenir
        List<String> testDevices = new ArrayList<>();
        testDevices.add(AdRequest.DEVICE_ID_EMULATOR);
        testDevices.add("FDA3E6132CC71524BCE0F7BFA8A013FA"); // asus

        RequestConfiguration requestConfiguration
                = new RequestConfiguration.Builder()
                .setTestDeviceIds(testDevices)
                .build();
        MobileAds.setRequestConfiguration(requestConfiguration);
    }

    public static App get() {
        return mApp;
    }
}