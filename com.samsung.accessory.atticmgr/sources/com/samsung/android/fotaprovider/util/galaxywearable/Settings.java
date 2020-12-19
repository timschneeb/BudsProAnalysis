package com.samsung.android.fotaprovider.util.galaxywearable;

import android.net.Uri;
import com.samsung.android.fotaprovider.FotaProviderInitializer;
import com.samsung.android.fotaprovider.util.FotaProviderUtil;

public class Settings {
    private static Uri contentUri = generateContentUri();

    public interface GearPluginEventQuery {
        public static final String[] COLUMNS = {"keyField", "keyValue"};
        public static final int KEY_FIELD = 0;
        public static final int KEY_VALUE = 1;
    }

    private static Uri generateContentUri() {
        String packageName = FotaProviderInitializer.getContext().getPackageName();
        if (FotaProviderUtil.getSingleFotaProviderPackageName().equals(packageName)) {
            packageName = "com.samsung.android.gear2plugin";
        } else if ("com.samsung.android.gearfit2plugin".equals(packageName)) {
            packageName = "com.samsung.android.gearfit2plugin.gearfit2FT";
        }
        return Uri.parse("content://" + packageName + "/settings");
    }

    public static class System {
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0051, code lost:
            r1 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0052, code lost:
            if (r7 != null) goto L_0x0054;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
            r7.close();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x0058, code lost:
            r7 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0059, code lost:
            r0.addSuppressed(r7);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x005c, code lost:
            throw r1;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static int getInt(android.content.ContentResolver r7, java.lang.String r8, int r9) {
            /*
            // Method dump skipped, instructions count: 106
            */
            throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.fotaprovider.util.galaxywearable.Settings.System.getInt(android.content.ContentResolver, java.lang.String, int):int");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0052, code lost:
            r1 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0053, code lost:
            if (r8 != null) goto L_0x0055;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
            r8.close();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x0059, code lost:
            r8 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x005a, code lost:
            r0.addSuppressed(r8);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x005d, code lost:
            throw r1;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static int getInt(android.content.ContentResolver r8, java.lang.String r9) throws android.provider.Settings.SettingNotFoundException {
            /*
            // Method dump skipped, instructions count: 150
            */
            throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.fotaprovider.util.galaxywearable.Settings.System.getInt(android.content.ContentResolver, java.lang.String):int");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0088, code lost:
            r11 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x0089, code lost:
            if (r2 != null) goto L_0x008b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
            r2.close();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x008f, code lost:
            r12 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0090, code lost:
            r10.addSuppressed(r12);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x0093, code lost:
            throw r11;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static void putInt(android.content.ContentResolver r10, java.lang.String r11, int r12) {
            /*
            // Method dump skipped, instructions count: 154
            */
            throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.fotaprovider.util.galaxywearable.Settings.System.putInt(android.content.ContentResolver, java.lang.String, int):void");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0051, code lost:
            r1 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0052, code lost:
            if (r7 != null) goto L_0x0054;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
            r7.close();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x0058, code lost:
            r7 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0059, code lost:
            r0.addSuppressed(r7);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x005c, code lost:
            throw r1;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static java.lang.String getString(android.content.ContentResolver r7, java.lang.String r8, java.lang.String r9) {
            /*
            // Method dump skipped, instructions count: 103
            */
            throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.fotaprovider.util.galaxywearable.Settings.System.getString(android.content.ContentResolver, java.lang.String, java.lang.String):java.lang.String");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0051, code lost:
            r1 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0052, code lost:
            if (r7 != null) goto L_0x0054;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
            r7.close();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x0058, code lost:
            r7 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0059, code lost:
            r0.addSuppressed(r7);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x005c, code lost:
            throw r1;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static java.lang.String getString(android.content.ContentResolver r7, java.lang.String r8) {
            /*
                r0 = 1
                java.lang.String[] r5 = new java.lang.String[r0]
                r1 = 0
                r5[r1] = r8
                r8 = 0
                android.net.Uri r2 = com.samsung.android.fotaprovider.util.galaxywearable.Settings.access$000()     // Catch:{ Exception -> 0x005d }
                r3 = 0
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x005d }
                r4.<init>()     // Catch:{ Exception -> 0x005d }
                java.lang.String[] r6 = com.samsung.android.fotaprovider.util.galaxywearable.Settings.GearPluginEventQuery.COLUMNS     // Catch:{ Exception -> 0x005d }
                r1 = r6[r1]     // Catch:{ Exception -> 0x005d }
                r4.append(r1)     // Catch:{ Exception -> 0x005d }
                java.lang.String r1 = "=?"
                r4.append(r1)     // Catch:{ Exception -> 0x005d }
                java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x005d }
                r6 = 0
                r1 = r7
                android.database.Cursor r7 = r1.query(r2, r3, r4, r5, r6)     // Catch:{ Exception -> 0x005d }
                java.lang.String r1 = "succeeded to find content uri"
                com.samsung.android.fotaprovider.log.Log.D(r1)     // Catch:{ all -> 0x004f }
                if (r7 == 0) goto L_0x0044
                int r1 = r7.getCount()     // Catch:{ all -> 0x004f }
                if (r1 <= 0) goto L_0x0044
                r7.moveToFirst()     // Catch:{ all -> 0x004f }
                java.lang.String[] r1 = com.samsung.android.fotaprovider.util.galaxywearable.Settings.GearPluginEventQuery.COLUMNS     // Catch:{ all -> 0x004f }
                r0 = r1[r0]     // Catch:{ all -> 0x004f }
                int r0 = r7.getColumnIndex(r0)     // Catch:{ all -> 0x004f }
                java.lang.String r8 = r7.getString(r0)     // Catch:{ all -> 0x004f }
                goto L_0x0049
            L_0x0044:
                java.lang.String r0 = "Field Entry not present in the DB !!"
                com.samsung.android.fotaprovider.log.Log.W(r0)     // Catch:{ all -> 0x004f }
            L_0x0049:
                if (r7 == 0) goto L_0x0062
                r7.close()
                goto L_0x0062
            L_0x004f:
                r0 = move-exception
                throw r0     // Catch:{ all -> 0x0051 }
            L_0x0051:
                r1 = move-exception
                if (r7 == 0) goto L_0x005c
                r7.close()     // Catch:{ all -> 0x0058 }
                goto L_0x005c
            L_0x0058:
                r7 = move-exception
                r0.addSuppressed(r7)
            L_0x005c:
                throw r1
            L_0x005d:
                java.lang.String r7 = "failed to find content uri"
                com.samsung.android.fotaprovider.log.Log.D(r7)
            L_0x0062:
                return r8
            */
            throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.fotaprovider.util.galaxywearable.Settings.System.getString(android.content.ContentResolver, java.lang.String):java.lang.String");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0080, code lost:
            r11 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x0081, code lost:
            if (r2 != null) goto L_0x0083;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
            r2.close();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0087, code lost:
            r12 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0088, code lost:
            r10.addSuppressed(r12);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x008b, code lost:
            throw r11;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static void putString(android.content.ContentResolver r10, java.lang.String r11, java.lang.String r12) {
            /*
            // Method dump skipped, instructions count: 146
            */
            throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.fotaprovider.util.galaxywearable.Settings.System.putString(android.content.ContentResolver, java.lang.String, java.lang.String):void");
        }

        private static Uri getUriFor(String str) {
            return Uri.withAppendedPath(Settings.contentUri, str);
        }
    }
}
