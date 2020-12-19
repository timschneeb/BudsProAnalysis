package com.accessorydm.db.file;

import android.text.TextUtils;
import com.accessorydm.adapter.XDMTargetAdapter;
import com.accessorydm.interfaces.XDBInterface;
import com.accessorydm.interfaces.XDMDefInterface;
import com.accessorydm.interfaces.XDMInterface;
import com.accessorydm.interfaces.XFOTAInterface;
import com.accessorydm.interfaces.XTPInterface;
import com.accessorydm.tp.XTPHttpUtil;
import com.samsung.android.fotaprovider.log.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Locale;

public class XDBAdapter implements XDMDefInterface, XDMInterface, XTPInterface, XDBInterface, XFOTAInterface {
    public static int xdbFileFreeSizeCheck(long j) {
        long xdmGetAvailableStorageSize = XDMTargetAdapter.xdmGetAvailableStorageSize();
        long xdmGetTotalStorageSize = XDMTargetAdapter.xdmGetTotalStorageSize();
        Log.I(String.format(Locale.US, "Remain size : %d, Total size : %d and Required Size : %d bytes", Long.valueOf(xdmGetAvailableStorageSize), Long.valueOf(xdmGetTotalStorageSize), Long.valueOf(j)));
        if (!XDMTargetAdapter.xdmGetStorageAvailable() || j > xdmGetAvailableStorageSize) {
            return 4;
        }
        Log.I("Storage >>> XDB_FS_OK...");
        return 0;
    }

    public static boolean xdbFileExist(String str) {
        try {
            File file = new File(str);
            if (!file.exists() || !file.canRead()) {
                return false;
            }
            return true;
        } catch (Exception e) {
            Log.E(e.toString());
            return false;
        }
    }

    public int xdbFileExists(String str) {
        try {
            File file = new File(str);
            if (!file.exists() || !file.canRead()) {
                return -1;
            }
            return 0;
        } catch (Exception e) {
            Log.E(e.toString());
            return -1;
        }
    }

    public static boolean xdbFolderExist(String str) {
        try {
            if (new File(str).isDirectory()) {
                return true;
            }
            Log.I("Folder is not Exist!!");
            return false;
        } catch (Exception e) {
            Log.E(e.toString());
            return false;
        }
    }

    public static boolean xdbFolderCreate(String str) {
        try {
            File file = new File(str);
            if (file.isDirectory()) {
                return true;
            }
            if (!file.mkdirs()) {
                return false;
            }
            Log.H("make [" + str + "] folder");
            return true;
        } catch (Exception e) {
            Log.E(e.toString());
            return false;
        }
    }

    public long xdbFileGetSize(String str) {
        try {
            File file = new File(str);
            if (file.exists()) {
                return file.length();
            }
            Log.E("file is not exist : " + str);
            return 0;
        } catch (Exception e) {
            Log.E(e.toString());
            return 0;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0032 A[SYNTHETIC, Splitter:B:21:0x0032] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0042 A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0046 A[SYNTHETIC, Splitter:B:30:0x0046] */
    /* JADX WARNING: Removed duplicated region for block: B:35:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean xdbFileRead(java.lang.String r5, byte[] r6, int r7, int r8) {
        /*
            r4 = this;
            r0 = 0
            if (r8 > 0) goto L_0x0004
            return r0
        L_0x0004:
            r1 = 0
            java.io.DataInputStream r2 = new java.io.DataInputStream     // Catch:{ Exception -> 0x0028 }
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0028 }
            r3.<init>(r5)     // Catch:{ Exception -> 0x0028 }
            r2.<init>(r3)     // Catch:{ Exception -> 0x0028 }
            int r5 = r2.read(r6, r7, r8)     // Catch:{ Exception -> 0x0023, all -> 0x0020 }
            r2.close()     // Catch:{ Exception -> 0x0017 }
            goto L_0x003f
        L_0x0017:
            r6 = move-exception
            java.lang.String r6 = r6.toString()
            com.samsung.android.fotaprovider.log.Log.E(r6)
            goto L_0x003f
        L_0x0020:
            r5 = move-exception
            r1 = r2
            goto L_0x0044
        L_0x0023:
            r5 = move-exception
            r1 = r2
            goto L_0x0029
        L_0x0026:
            r5 = move-exception
            goto L_0x0044
        L_0x0028:
            r5 = move-exception
        L_0x0029:
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0026 }
            com.samsung.android.fotaprovider.log.Log.E(r5)     // Catch:{ all -> 0x0026 }
            if (r1 == 0) goto L_0x003e
            r1.close()     // Catch:{ Exception -> 0x0036 }
            goto L_0x003e
        L_0x0036:
            r5 = move-exception
            java.lang.String r5 = r5.toString()
            com.samsung.android.fotaprovider.log.Log.E(r5)
        L_0x003e:
            r5 = r0
        L_0x003f:
            r6 = -1
            if (r5 == r6) goto L_0x0043
            r0 = 1
        L_0x0043:
            return r0
        L_0x0044:
            if (r1 == 0) goto L_0x0052
            r1.close()     // Catch:{ Exception -> 0x004a }
            goto L_0x0052
        L_0x004a:
            r6 = move-exception
            java.lang.String r6 = r6.toString()
            com.samsung.android.fotaprovider.log.Log.E(r6)
        L_0x0052:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.db.file.XDBAdapter.xdbFileRead(java.lang.String, byte[], int, int):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0033 A[SYNTHETIC, Splitter:B:18:0x0033] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0042 A[SYNTHETIC, Splitter:B:24:0x0042] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean xdbFileWrite(java.lang.String r4, int r5, java.lang.Object r6) {
        /*
            byte[] r6 = (byte[]) r6
            byte[] r6 = (byte[]) r6
            r0 = 0
            r1 = 0
            java.io.DataOutputStream r2 = new java.io.DataOutputStream     // Catch:{ Exception -> 0x0029 }
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0029 }
            r3.<init>(r4)     // Catch:{ Exception -> 0x0029 }
            r2.<init>(r3)     // Catch:{ Exception -> 0x0029 }
            r2.write(r6, r0, r5)     // Catch:{ Exception -> 0x0024, all -> 0x0021 }
            r2.close()     // Catch:{ Exception -> 0x0017 }
            goto L_0x001f
        L_0x0017:
            r4 = move-exception
            java.lang.String r4 = r4.toString()
            com.samsung.android.fotaprovider.log.Log.E(r4)
        L_0x001f:
            r4 = 1
            return r4
        L_0x0021:
            r4 = move-exception
            r1 = r2
            goto L_0x0040
        L_0x0024:
            r4 = move-exception
            r1 = r2
            goto L_0x002a
        L_0x0027:
            r4 = move-exception
            goto L_0x0040
        L_0x0029:
            r4 = move-exception
        L_0x002a:
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0027 }
            com.samsung.android.fotaprovider.log.Log.E(r4)     // Catch:{ all -> 0x0027 }
            if (r1 == 0) goto L_0x003f
            r1.close()     // Catch:{ Exception -> 0x0037 }
            goto L_0x003f
        L_0x0037:
            r4 = move-exception
            java.lang.String r4 = r4.toString()
            com.samsung.android.fotaprovider.log.Log.E(r4)
        L_0x003f:
            return r0
        L_0x0040:
            if (r1 == 0) goto L_0x004e
            r1.close()     // Catch:{ Exception -> 0x0046 }
            goto L_0x004e
        L_0x0046:
            r5 = move-exception
            java.lang.String r5 = r5.toString()
            com.samsung.android.fotaprovider.log.Log.E(r5)
        L_0x004e:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.db.file.XDBAdapter.xdbFileWrite(java.lang.String, int, java.lang.Object):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0032 A[SYNTHETIC, Splitter:B:19:0x0032] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0041 A[SYNTHETIC, Splitter:B:25:0x0041] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean xdbFileWrite(java.lang.String r3, java.lang.Object r4) {
        /*
            r0 = 0
            java.io.ObjectOutputStream r1 = new java.io.ObjectOutputStream     // Catch:{ Exception -> 0x0027 }
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0027 }
            r2.<init>(r3)     // Catch:{ Exception -> 0x0027 }
            r1.<init>(r2)     // Catch:{ Exception -> 0x0027 }
            r1.reset()     // Catch:{ Exception -> 0x0022, all -> 0x001f }
            r1.writeObject(r4)     // Catch:{ Exception -> 0x0022, all -> 0x001f }
            r1.close()     // Catch:{ Exception -> 0x0015 }
            goto L_0x001d
        L_0x0015:
            r3 = move-exception
            java.lang.String r3 = r3.toString()
            com.samsung.android.fotaprovider.log.Log.E(r3)
        L_0x001d:
            r3 = 1
            return r3
        L_0x001f:
            r3 = move-exception
            r0 = r1
            goto L_0x003f
        L_0x0022:
            r3 = move-exception
            r0 = r1
            goto L_0x0028
        L_0x0025:
            r3 = move-exception
            goto L_0x003f
        L_0x0027:
            r3 = move-exception
        L_0x0028:
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0025 }
            com.samsung.android.fotaprovider.log.Log.E(r3)     // Catch:{ all -> 0x0025 }
            r3 = 0
            if (r0 == 0) goto L_0x003e
            r0.close()     // Catch:{ Exception -> 0x0036 }
            goto L_0x003e
        L_0x0036:
            r4 = move-exception
            java.lang.String r4 = r4.toString()
            com.samsung.android.fotaprovider.log.Log.E(r4)
        L_0x003e:
            return r3
        L_0x003f:
            if (r0 == 0) goto L_0x004d
            r0.close()     // Catch:{ Exception -> 0x0045 }
            goto L_0x004d
        L_0x0045:
            r4 = move-exception
            java.lang.String r4 = r4.toString()
            com.samsung.android.fotaprovider.log.Log.E(r4)
        L_0x004d:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.db.file.XDBAdapter.xdbFileWrite(java.lang.String, java.lang.Object):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x002f A[SYNTHETIC, Splitter:B:19:0x002f] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x003e A[SYNTHETIC, Splitter:B:25:0x003e] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean xdbFileCreateWrite(java.lang.String r4, byte[] r5) {
        /*
            r3 = this;
            r0 = 0
            java.io.DataOutputStream r1 = new java.io.DataOutputStream     // Catch:{ Exception -> 0x0024 }
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0024 }
            r2.<init>(r4)     // Catch:{ Exception -> 0x0024 }
            r1.<init>(r2)     // Catch:{ Exception -> 0x0024 }
            r1.write(r5)     // Catch:{ Exception -> 0x001f, all -> 0x001c }
            r1.close()     // Catch:{ Exception -> 0x0012 }
            goto L_0x001a
        L_0x0012:
            r4 = move-exception
            java.lang.String r4 = r4.toString()
            com.samsung.android.fotaprovider.log.Log.E(r4)
        L_0x001a:
            r4 = 1
            return r4
        L_0x001c:
            r4 = move-exception
            r0 = r1
            goto L_0x003c
        L_0x001f:
            r4 = move-exception
            r0 = r1
            goto L_0x0025
        L_0x0022:
            r4 = move-exception
            goto L_0x003c
        L_0x0024:
            r4 = move-exception
        L_0x0025:
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0022 }
            com.samsung.android.fotaprovider.log.Log.E(r4)     // Catch:{ all -> 0x0022 }
            r4 = 0
            if (r0 == 0) goto L_0x003b
            r0.close()     // Catch:{ Exception -> 0x0033 }
            goto L_0x003b
        L_0x0033:
            r5 = move-exception
            java.lang.String r5 = r5.toString()
            com.samsung.android.fotaprovider.log.Log.E(r5)
        L_0x003b:
            return r4
        L_0x003c:
            if (r0 == 0) goto L_0x004a
            r0.close()     // Catch:{ Exception -> 0x0042 }
            goto L_0x004a
        L_0x0042:
            r5 = move-exception
            java.lang.String r5 = r5.toString()
            com.samsung.android.fotaprovider.log.Log.E(r5)
        L_0x004a:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.db.file.XDBAdapter.xdbFileCreateWrite(java.lang.String, byte[]):boolean");
    }

    public boolean xdbFileDeltaCreateWrite(FileOutputStream fileOutputStream, byte[] bArr, int i) {
        Log.I("");
        try {
            fileOutputStream.write(bArr, 0, i);
            fileOutputStream.flush();
            fileOutputStream.getFD().sync();
            return true;
        } catch (Exception e) {
            Log.E(e.toString());
            return false;
        }
    }

    public static boolean xdbFileDelete(String str) {
        try {
            File file = new File(str);
            if (!file.exists() || file.delete()) {
                Log.I("xdbFileDelete true");
                return true;
            }
            Log.I("xdbFileDelete false");
            return false;
        } catch (Exception e) {
            Log.E(e.toString());
            return false;
        }
    }

    public int xdbFileRemove(String str) {
        try {
            File file = new File(str);
            if (!file.exists() || file.delete()) {
                return 0;
            }
            return -1;
        } catch (Exception e) {
            Log.E(e.toString());
            return -1;
        }
    }

    public static int xdbGetConnectType(int i) {
        if (i == 0) {
            String xdbGetServerProtocol = XDBProfileAdp.xdbGetServerProtocol();
            if (!TextUtils.isEmpty(xdbGetServerProtocol)) {
                return XTPHttpUtil.xtpHttpExchangeProtocolType(xdbGetServerProtocol);
            }
            return 2;
        } else if (i != 1) {
            return 2;
        } else {
            String xdbGetFUMOProtocol = XDBFumoAdp.xdbGetFUMOProtocol();
            if (TextUtils.isEmpty(xdbGetFUMOProtocol)) {
                return 2;
            }
            Log.H(String.format("Protool [%s]", xdbGetFUMOProtocol));
            return XTPHttpUtil.xtpHttpExchangeProtocolType(xdbGetFUMOProtocol);
        }
    }

    public static String xdbGetNotiDigest(int i, String str, int i2, byte[] bArr, int i3) {
        if (TextUtils.isEmpty(str)) {
            Log.E("pServerID is NULL");
            return null;
        } else if (i == 0) {
            return XDB.xdbGetNotiDigest(str, i2, bArr, i3);
        } else {
            Log.E("Not Support Application :" + i);
            return null;
        }
    }

    public static String xdbCheckOMADDURL(String str) {
        if (str.indexOf(38) <= 0) {
            return str;
        }
        return str.replaceAll("&amp;", "&");
    }
}
