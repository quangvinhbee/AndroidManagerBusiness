package com.nhom1.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class BitmapHelper {
    public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("DEBUG_MSG",src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY);
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("DEBUG_MSG","returned");
            return myBitmap;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }


}
