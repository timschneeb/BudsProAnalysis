package com.accessorydm.agent;

import com.accessorydm.XDMDmUtils;
import com.accessorydm.adapter.XDMCommonUtils;
import com.accessorydm.adapter.XDMDevinfAdapter;
import com.accessorydm.adapter.XDMTargetAdapter;
import com.accessorydm.db.file.XDBAESCrypt;
import com.accessorydm.db.file.XDBFumoAdp;
import com.accessorydm.eng.core.XDMBase64;
import com.accessorydm.interfaces.XDMInterface;
import com.accessorydm.ui.handler.XDMToastHandler;
import com.samsung.android.fotaprovider.log.Log;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Locale;

public class XDMDebug implements XDMInterface {
    private static boolean DEBUG_WBXML_FILE = false;
    private static boolean bBooting = true;
    public static boolean bSessionRuning = false;
    private static final String bootinglogfile = "/dm_booting.log";
    private static final String bootstraplogfile = "/dm_bootstrap.log";
    public static int curFileIndex = 1;
    private static ByteArrayOutputStream logTemp = new ByteArrayOutputStream();
    private static FileOutputStream logfileStream = null;
    private static final String sessionlogfile = "/dm_session";

    static {
        if (logTemp != null) {
            try {
                Date date = new Date();
                logTemp.write((">> time : " + date.toString() + "\n").getBytes(Charset.defaultCharset()));
            } catch (Exception e) {
                Log.E(e.toString());
            }
        }
    }

    public static void xdmSetDebugWbxmlFile(boolean z) {
        DEBUG_WBXML_FILE = z;
        XDMToastHandler.xdmShowToast("WBXML_FILE : " + DEBUG_WBXML_FILE, 1);
        Log.I("WBXML_FILE : " + DEBUG_WBXML_FILE);
    }

    public static boolean xdmIsDebugWbxmlFile() {
        return DEBUG_WBXML_FILE;
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0024  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0033 A[SYNTHETIC, Splitter:B:20:0x0033] */
    /* JADX WARNING: Removed duplicated region for block: B:26:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void xdmWriteFile(java.lang.String r3, byte[] r4) {
        /*
            r0 = 0
            java.io.DataOutputStream r1 = new java.io.DataOutputStream     // Catch:{ Exception -> 0x001a }
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x001a }
            r2.<init>(r3)     // Catch:{ Exception -> 0x001a }
            r1.<init>(r2)     // Catch:{ Exception -> 0x001a }
            r1.write(r4)     // Catch:{ Exception -> 0x0015, all -> 0x0012 }
            r1.close()     // Catch:{ Exception -> 0x0028 }
            goto L_0x0030
        L_0x0012:
            r3 = move-exception
            r0 = r1
            goto L_0x0031
        L_0x0015:
            r3 = move-exception
            r0 = r1
            goto L_0x001b
        L_0x0018:
            r3 = move-exception
            goto L_0x0031
        L_0x001a:
            r3 = move-exception
        L_0x001b:
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0018 }
            com.samsung.android.fotaprovider.log.Log.I(r3)     // Catch:{ all -> 0x0018 }
            if (r0 == 0) goto L_0x0030
            r0.close()
            goto L_0x0030
        L_0x0028:
            r3 = move-exception
            java.lang.String r3 = r3.toString()
            com.samsung.android.fotaprovider.log.Log.E(r3)
        L_0x0030:
            return
        L_0x0031:
            if (r0 == 0) goto L_0x003f
            r0.close()     // Catch:{ Exception -> 0x0037 }
            goto L_0x003f
        L_0x0037:
            r4 = move-exception
            java.lang.String r4 = r4.toString()
            com.samsung.android.fotaprovider.log.Log.E(r4)
        L_0x003f:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.agent.XDMDebug.xdmWriteFile(java.lang.String, byte[]):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x00af  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00be A[SYNTHETIC, Splitter:B:35:0x00be] */
    /* JADX WARNING: Removed duplicated region for block: B:43:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void xdmSaveBootStrapLog(java.lang.String r8, java.lang.String r9) {
        /*
        // Method dump skipped, instructions count: 204
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.agent.XDMDebug.xdmSaveBootStrapLog(java.lang.String, java.lang.String):void");
    }

    private static void xdmSaveDevInfoLog() {
        if (logfileStream != null) {
            StringBuilder sb = new StringBuilder();
            Date date = new Date();
            bSessionRuning = false;
            sb.append(">>>> time :");
            sb.append(date.toString());
            sb.append("\n");
            sb.append("//////////////// Device infomation\n");
            sb.append("Release Version : ");
            sb.append(XDMDevinfAdapter.xdmDevAdpGetAppVersion());
            sb.append("\n");
            sb.append("Model : ");
            sb.append(XDMDevinfAdapter.xdmDevAdpGetModel());
            sb.append("\n");
            sb.append("CSC : ");
            sb.append(XDMDevinfAdapter.xdmDevAdpGetSalesCode());
            sb.append("\n");
            String xdmDevAdpGetDeviceID = XDMDevinfAdapter.xdmDevAdpGetDeviceID();
            String xdmBase64Encode = XDMBase64.xdmBase64Encode(XDBAESCrypt.xdbEncryptor("DeviceID :" + xdmDevAdpGetDeviceID));
            sb.append("\t");
            sb.append(xdmBase64Encode);
            sb.append("\n");
            sb.append("Kb /data/data: ");
            sb.append(XDMTargetAdapter.xdmGetAvailableStorageSize() / 1024);
            sb.append("Kb\n");
            sb.append("////////////////\n\n");
            try {
                logfileStream.write(sb.toString().getBytes(Charset.defaultCharset()));
                try {
                    logfileStream.close();
                } catch (IOException e) {
                    Log.E(e.toString());
                }
            } catch (Exception e2) {
                Log.E(e2.toString());
                logfileStream.close();
            } catch (Throwable th) {
                try {
                    logfileStream.close();
                } catch (IOException e3) {
                    Log.E(e3.toString());
                }
                throw th;
            }
            bSessionRuning = true;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:0x00dd  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00ec A[SYNTHETIC, Splitter:B:43:0x00ec] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void xdmSetBooting(boolean r8) {
        /*
        // Method dump skipped, instructions count: 252
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.agent.XDMDebug.xdmSetBooting(boolean):void");
    }

    public static void xdmSetSessionRuning(boolean z) {
        File dir;
        File dir2;
        if (XDBFumoAdp.xdbGetFUMOStatus() == 0 || !z) {
            if (bSessionRuning && z) {
                if (!(XDMDmUtils.getContext() == null || (dir2 = XDMDmUtils.getContext().getDir(Log.LOGFILE_PATH, 0)) == null || !dir2.exists())) {
                    Locale locale = Locale.US;
                    String format = String.format(locale, "%s%d.log", dir2.getPath() + sessionlogfile, Integer.valueOf(curFileIndex));
                    try {
                        File file = new File(format);
                        if (file.exists()) {
                            if (logfileStream != null) {
                                logfileStream.close();
                                logfileStream = null;
                            }
                            if (file.delete()) {
                                logfileStream = new FileOutputStream(format, true);
                            } else {
                                return;
                            }
                        }
                    } catch (Exception e) {
                        Log.E(e.toString());
                    }
                }
                xdmSaveDevInfoLog();
            } else if (!bSessionRuning && z) {
                if (!(XDMDmUtils.getContext() == null || (dir = XDMDmUtils.getContext().getDir(Log.LOGFILE_PATH, 0)) == null || !dir.exists())) {
                    Locale locale2 = Locale.US;
                    String format2 = String.format(locale2, "%s%d.log", dir.getPath() + sessionlogfile, Integer.valueOf(curFileIndex));
                    try {
                        File file2 = new File(format2);
                        if (!file2.exists() || file2.delete()) {
                            logfileStream = new FileOutputStream(format2, true);
                        } else {
                            return;
                        }
                    } catch (Exception e2) {
                        Log.E(e2.toString());
                    }
                }
                xdmSaveDevInfoLog();
            } else if (bSessionRuning && !z) {
                try {
                    if (logfileStream != null) {
                        logfileStream.close();
                        logfileStream = null;
                    }
                } catch (Exception e3) {
                    Log.E(e3.toString());
                }
                int i = curFileIndex;
                if (i >= 3) {
                    curFileIndex = 1;
                } else {
                    curFileIndex = i + 1;
                }
            }
            bSessionRuning = z;
            if (XDMDmUtils.getContext() != null) {
                XDMCommonUtils.xdmSavelogflag();
            }
        }
    }
}
