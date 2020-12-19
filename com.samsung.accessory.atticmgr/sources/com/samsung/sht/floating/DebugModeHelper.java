package com.samsung.sht.floating;

import android.content.Context;
import com.samsung.sht.log.ShtLog;
import java.io.File;

public class DebugModeHelper {
    private static final String DEBUG_FILENAME = "debug.txt";

    public static boolean checkDebugMode(Context context) {
        File file = new File(context.getExternalFilesDir(null).getAbsolutePath() + "/" + DEBUG_FILENAME);
        if (file.exists()) {
            ShtLog.d("Debug mode is enabled");
        }
        return file.exists();
    }
}
