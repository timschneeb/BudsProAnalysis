package com.samsung.sht.audio;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import com.samsung.sht.log.ShtLog;

public class SoundAliveHelper {
    public static void setSpatialAudioOn(Context context) {
        setSpatialAudioValue(context, 1);
    }

    public static void setSpatialAudioff(Context context) {
        setSpatialAudioValue(context, 0);
    }

    private static void setSpatialAudioValue(Context context, Integer num) {
        ShtLog.i("setSpatialAudioValue : " + num);
        ContentValues contentValues = new ContentValues();
        contentValues.put("SPATIAL_AUDIO", num);
        context.getContentResolver().insert(Uri.parse("content://com.sec.android.app.soundalive.compatibility.SAContentProvider"), contentValues);
    }
}
