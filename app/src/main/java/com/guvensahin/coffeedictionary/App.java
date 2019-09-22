package com.guvensahin.coffeedictionary;

import android.app.Application;
import com.google.android.gms.ads.MobileAds;
import com.guvensahin.coffeedictionary.Utils.AppUtil;

public class App extends Application {

    private static App mApp;

    @Override
    public void onCreate() {
        super.onCreate();

        // context
        mApp = this;

        // admob
        MobileAds.initialize(this, AppUtil.getProperty(this, "ADMOB_ID"));
    }

    public static App get() {
        return mApp;
    }
}