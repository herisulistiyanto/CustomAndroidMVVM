package com.android.indie.school.cleancodemvvm.testutils;

import android.support.test.InstrumentationRegistry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by herisulistiyanto on 3/9/17.
 */

public class AssetUtils {

    public AssetUtils() {
    }

    public static String readAsset(final String file) throws RuntimeException {
        StringBuilder builder = new StringBuilder();

        try {
            InputStream inputStream = InstrumentationRegistry.getContext().getAssets().open(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            String str;
            while (null != (str = reader.readLine())) {
                builder.append(str);
            }
            reader.close();
            return builder.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
