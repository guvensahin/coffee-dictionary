package com.guvensahin.coffeedictionary;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppUtil {
    private static final String TAG = AppUtil.class.getName();

    private static Properties getProperties(Context context) {
        try {
            Properties properties = new Properties();
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open("config.properties");
            properties.load(inputStream);
            return properties;
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Unable to find the config file: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "Failed to open config file.");
        }

        return null;
    }

    public static String getProperty(Context context, String key)
    {
        return AppUtil.getProperties(context).getProperty(key);
    }

    public static void clickSearchOn(Context context, String gameName, int pos)
    {
        String[] searchOnUrls = context.getResources().getStringArray(R.array.searchOnUrls);
        String url = searchOnUrls[pos];

        url = url.replace("%SEARCHTEXT%", gameName);


        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        context.startActivity(i);
    }
}
