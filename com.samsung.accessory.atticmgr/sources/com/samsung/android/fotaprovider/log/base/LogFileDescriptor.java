package com.samsung.android.fotaprovider.log.base;

import android.content.Context;
import com.samsung.android.fotaprovider.log.Log;
import com.samsung.android.fotaprovider.log.base.LogDescriptor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LogFileDescriptor extends LogDescriptor.Stream {
    private List<String> logFilenameList;

    public LogFileDescriptor(Context context, String str, String str2, int i) {
        setLogFilenameList(makeLogFilenameList(context, str, str2, i));
    }

    public static List<String> makeLogFilenameList(Context context, String str, String str2, int i) {
        ArrayList arrayList = new ArrayList();
        File dir = context.getDir(str, 0);
        for (int i2 = 0; i2 < i; i2++) {
            arrayList.add(new File(dir, String.format(Locale.US, str2, Integer.valueOf(i2))).getAbsolutePath());
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    @Override // com.samsung.android.fotaprovider.log.base.LogDescriptor.Stream
    public long size() {
        return new File(getLogFilename()).length();
    }

    @Override // com.samsung.android.fotaprovider.log.base.LogDescriptor
    public void shift() {
        for (int size = getLogFilenameList().size() - 1; size > 0; size--) {
            shift(getLogFilenameList().get(size - 1), getLogFilenameList().get(size));
        }
    }

    @Override // com.samsung.android.fotaprovider.log.base.LogDescriptor.Stream
    public OutputStream getOutputStream() throws FileNotFoundException {
        return new FileOutputStream(getLogFilename(), true);
    }

    private String getLogFilename() {
        return getLogFilenameList().get(0);
    }

    /* access modifiers changed from: protected */
    public List<String> getLogFilenameList() {
        return this.logFilenameList;
    }

    /* access modifiers changed from: protected */
    public void setLogFilenameList(List<String> list) {
        this.logFilenameList = list;
    }

    private void shift(String str, String str2) {
        File file = new File(str);
        File file2 = new File(str2);
        try {
            file2.deleteOnExit();
        } catch (SecurityException e) {
            Log.printStackTrace(e);
        }
        try {
            if (!file.renameTo(file2)) {
                Log.D("Logger: failed to rename");
            }
        } catch (SecurityException e2) {
            Log.printStackTrace(e2);
        }
    }
}
