package com.sec.android.diagmonagent.log.provider.a;

public class c {
    /* JADX WARNING: Removed duplicated region for block: B:26:0x004c  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0051  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0056  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(java.lang.String r5, java.lang.String r6) {
        /*
            java.io.File r0 = new java.io.File
            r0.<init>(r5)
            boolean r1 = r0.isFile()
            if (r1 != 0) goto L_0x001a
            boolean r1 = r0.isDirectory()
            if (r1 == 0) goto L_0x0012
            goto L_0x001a
        L_0x0012:
            java.lang.Exception r5 = new java.lang.Exception
            java.lang.String r6 = "not found"
            r5.<init>(r6)
            throw r5
        L_0x001a:
            r1 = 0
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ all -> 0x0047 }
            r2.<init>(r6)     // Catch:{ all -> 0x0047 }
            java.io.BufferedOutputStream r3 = new java.io.BufferedOutputStream     // Catch:{ all -> 0x0044 }
            r3.<init>(r2)     // Catch:{ all -> 0x0044 }
            java.util.zip.ZipOutputStream r4 = new java.util.zip.ZipOutputStream     // Catch:{ all -> 0x0042 }
            r4.<init>(r3)     // Catch:{ all -> 0x0042 }
            r1 = 8
            r4.setLevel(r1)     // Catch:{ all -> 0x003f }
            a(r0, r5, r4)     // Catch:{ all -> 0x003f }
            r4.finish()     // Catch:{ all -> 0x003f }
            r4.close()
            r3.close()
            r2.close()
            return r6
        L_0x003f:
            r5 = move-exception
            r1 = r4
            goto L_0x004a
        L_0x0042:
            r5 = move-exception
            goto L_0x004a
        L_0x0044:
            r5 = move-exception
            r3 = r1
            goto L_0x004a
        L_0x0047:
            r5 = move-exception
            r2 = r1
            r3 = r2
        L_0x004a:
            if (r1 == 0) goto L_0x004f
            r1.close()
        L_0x004f:
            if (r3 == 0) goto L_0x0054
            r3.close()
        L_0x0054:
            if (r2 == 0) goto L_0x0059
            r2.close()
        L_0x0059:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sec.android.diagmonagent.log.provider.a.c.a(java.lang.String, java.lang.String):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x007a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void a(java.io.File r4, java.lang.String r5, java.util.zip.ZipOutputStream r6) {
        /*
        // Method dump skipped, instructions count: 128
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sec.android.diagmonagent.log.provider.a.c.a(java.io.File, java.lang.String, java.util.zip.ZipOutputStream):void");
    }
}
