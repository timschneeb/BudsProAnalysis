package com.sec.android.diagmonagent.log.provider.utils;

public class ZipHelper {
    private static final int BUFFER_SIZE = 2048;
    private static final int COMPRESSION_LEVEL = 8;

    /* JADX WARNING: Removed duplicated region for block: B:26:0x004d  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0052  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0057  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String zip(java.lang.String r4, java.lang.String r5) throws java.lang.Exception {
        /*
            java.io.File r0 = new java.io.File
            r0.<init>(r4)
            boolean r4 = r0.isFile()
            if (r4 != 0) goto L_0x001a
            boolean r4 = r0.isDirectory()
            if (r4 == 0) goto L_0x0012
            goto L_0x001a
        L_0x0012:
            java.lang.Exception r4 = new java.lang.Exception
            java.lang.String r5 = "not found"
            r4.<init>(r5)
            throw r4
        L_0x001a:
            r4 = 0
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ all -> 0x0048 }
            r1.<init>(r5)     // Catch:{ all -> 0x0048 }
            java.io.BufferedOutputStream r2 = new java.io.BufferedOutputStream     // Catch:{ all -> 0x0045 }
            r2.<init>(r1)     // Catch:{ all -> 0x0045 }
            java.util.zip.ZipOutputStream r3 = new java.util.zip.ZipOutputStream     // Catch:{ all -> 0x0043 }
            r3.<init>(r2)     // Catch:{ all -> 0x0043 }
            r4 = 8
            r3.setLevel(r4)     // Catch:{ all -> 0x003f }
            zipEntry(r0, r3)     // Catch:{ all -> 0x003f }
            r3.finish()     // Catch:{ all -> 0x003f }
            r3.close()
            r2.close()
            r1.close()
            return r5
        L_0x003f:
            r4 = move-exception
            r5 = r4
            r4 = r3
            goto L_0x004b
        L_0x0043:
            r5 = move-exception
            goto L_0x004b
        L_0x0045:
            r5 = move-exception
            r2 = r4
            goto L_0x004b
        L_0x0048:
            r5 = move-exception
            r1 = r4
            r2 = r1
        L_0x004b:
            if (r4 == 0) goto L_0x0050
            r4.close()
        L_0x0050:
            if (r2 == 0) goto L_0x0055
            r2.close()
        L_0x0055:
            if (r1 == 0) goto L_0x005a
            r1.close()
        L_0x005a:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sec.android.diagmonagent.log.provider.utils.ZipHelper.zip(java.lang.String, java.lang.String):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x007a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void zipEntry(java.io.File r5, java.util.zip.ZipOutputStream r6) throws java.lang.Exception {
        /*
        // Method dump skipped, instructions count: 126
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sec.android.diagmonagent.log.provider.utils.ZipHelper.zipEntry(java.io.File, java.util.zip.ZipOutputStream):void");
    }
}
