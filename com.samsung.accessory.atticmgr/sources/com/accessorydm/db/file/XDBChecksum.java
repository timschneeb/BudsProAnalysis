package com.accessorydm.db.file;

import com.samsung.android.fotaprovider.log.Log;

public class XDBChecksum {
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0047 A[SYNTHETIC, Splitter:B:25:0x0047] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x006c A[SYNTHETIC, Splitter:B:33:0x006c] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getChecksum(java.lang.String r5) {
        /*
        // Method dump skipped, instructions count: 121
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.db.file.XDBChecksum.getChecksum(java.lang.String):java.lang.String");
    }

    public static String bytesToHex(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        if (bArr != null) {
            int length = bArr.length;
            for (int i = 0; i < length; i++) {
                sb.append(String.format("%02x", Byte.valueOf(bArr[i])));
            }
            return sb.toString();
        }
        Log.E("byte is null");
        return "";
    }
}
