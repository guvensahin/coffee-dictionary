package com.guvensahin.coffeedictionary;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;

public class CoffeeDictionaryApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        MobileAds.initialize(getApplicationContext(), AppUtil.getProperty(this, "ADMOB_ID"));
    }
}
