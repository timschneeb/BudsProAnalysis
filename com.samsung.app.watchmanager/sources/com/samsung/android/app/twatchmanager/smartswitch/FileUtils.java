package com.samsung.android.app.twatchmanager.smartswitch;

import android.os.StatFs;
import com.samsung.android.app.twatchmanager.log.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtils {
    private static final int BUFFER_SIZE = 4096;
    static final int STORAGE_FULL = 2;
    static final int SUCCESS = 0;
    private static final String TAG = ("tUHM:SmartSwitch:" + FileUtils.class.getSimpleName());
    static final int UNKNOWN_ERROR = 1;

    public static int copyDirectory(File file, File file2) {
        if (file.isDirectory()) {
            if (!file2.exists()) {
                file2.mkdir();
            }
            String[] list = file.list();
            if (list != null) {
                for (int i = 0; i < list.length; i++) {
                    copyDirectory(new File(file, list[i]), new File(file2, list[i]));
                }
            }
        } else if (copyFile(file, file2) == 2) {
            return 2;
        }
        return 0;
    }

    /* JADX WARNING: Removed duplicated region for block: B:100:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00aa A[SYNTHETIC, Splitter:B:57:0x00aa] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x00b2 A[Catch:{ IOException -> 0x00ae }] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x00b7 A[Catch:{ IOException -> 0x00ae }] */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x00bc A[Catch:{ IOException -> 0x00ae }] */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x00d0  */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x00d8  */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x00dd  */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x00e2  */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x00ee A[SYNTHETIC, Splitter:B:87:0x00ee] */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x00f6 A[Catch:{ IOException -> 0x00f2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x00fb A[Catch:{ IOException -> 0x00f2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:96:0x0100 A[Catch:{ IOException -> 0x00f2 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int copyFile(java.io.File r10, java.io.File r11) {
        /*
        // Method dump skipped, instructions count: 264
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.smartswitch.FileUtils.copyFile(java.io.File, java.io.File):int");
    }

    public static void deleteAllFiles(String str) {
        int length;
        File[] listFiles = new File(str).listFiles();
        if (listFiles != null && (length = listFiles.length) > 0) {
            for (int i = 0; i < length; i++) {
                if (listFiles[i].isFile()) {
                    listFiles[i].delete();
                }
            }
        }
    }

    public static boolean deleteDirectory(String str) {
        File file = new File(str);
        if (!file.exists()) {
            Log.e(TAG, "Directory is NOT existed !");
            return false;
        }
        File[] listFiles = file.listFiles();
        if (listFiles != null) {
            for (int i = 0; i < listFiles.length; i++) {
                if (listFiles[i].isDirectory()) {
                    deleteDirectory(listFiles[i].getPath());
                } else {
                    deleteFile(listFiles[i].getPath());
                }
            }
        }
        return file.delete();
    }

    public static boolean deleteFile(String str) {
        File file = new File(str);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:54:0x0093  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x009d  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x00af  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean fileUnZip(java.lang.String r7, java.lang.String r8) {
        /*
        // Method dump skipped, instructions count: 182
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.smartswitch.FileUtils.fileUnZip(java.lang.String, java.lang.String):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:43:0x0094  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x009c  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00a1  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00b8  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00be A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00ce A[SYNTHETIC, Splitter:B:58:0x00ce] */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x00d6 A[Catch:{ IOException -> 0x00d2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x00db A[Catch:{ IOException -> 0x00d2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x00e6 A[SYNTHETIC, Splitter:B:70:0x00e6] */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x00ee A[Catch:{ IOException -> 0x00ea }] */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x00f3 A[Catch:{ IOException -> 0x00ea }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean fileZip(java.lang.String r6, java.lang.String r7, java.lang.String r8) {
        /*
        // Method dump skipped, instructions count: 253
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.smartswitch.FileUtils.fileZip(java.lang.String, java.lang.String, java.lang.String):boolean");
    }

    private static boolean isStorageFull(String str, long j) {
        StatFs statFs = new StatFs(str);
        return statFs.getAvailableBlocksLong() * statFs.getBlockSizeLong() < j;
    }

    public static File makeDir(String str) {
        File file = new File(str);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x003b  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0046 A[SYNTHETIC, Splitter:B:35:0x0046] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean unZipEntry(java.util.zip.ZipInputStream r6, java.io.File r7) {
        /*
            r0 = 1
            r1 = 0
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x0035 }
            r2.<init>(r7)     // Catch:{ IOException -> 0x0035 }
            r7 = 4096(0x1000, float:5.74E-42)
            byte[] r7 = new byte[r7]     // Catch:{ IOException -> 0x0030, all -> 0x002d }
            r1 = 0
            r3 = 0
        L_0x000d:
            int r4 = r6.read(r7)     // Catch:{ IOException -> 0x0030, all -> 0x002d }
            r5 = -1
            if (r4 == r5) goto L_0x0029
            if (r4 != 0) goto L_0x0024
            int r3 = r3 + r0
            r5 = 50
            if (r3 != r5) goto L_0x0025
            r2.close()     // Catch:{ IOException -> 0x001f }
            goto L_0x0023
        L_0x001f:
            r6 = move-exception
            r6.printStackTrace()
        L_0x0023:
            return r1
        L_0x0024:
            r3 = 0
        L_0x0025:
            r2.write(r7, r1, r4)
            goto L_0x000d
        L_0x0029:
            r2.close()     // Catch:{ IOException -> 0x003f }
            goto L_0x0043
        L_0x002d:
            r6 = move-exception
            r1 = r2
            goto L_0x0044
        L_0x0030:
            r6 = move-exception
            r1 = r2
            goto L_0x0036
        L_0x0033:
            r6 = move-exception
            goto L_0x0044
        L_0x0035:
            r6 = move-exception
        L_0x0036:
            r6.printStackTrace()     // Catch:{ all -> 0x0033 }
            if (r1 == 0) goto L_0x0043
            r1.close()
            goto L_0x0043
        L_0x003f:
            r6 = move-exception
            r6.printStackTrace()
        L_0x0043:
            return r0
        L_0x0044:
            if (r1 == 0) goto L_0x004e
            r1.close()     // Catch:{ IOException -> 0x004a }
            goto L_0x004e
        L_0x004a:
            r7 = move-exception
            r7.printStackTrace()
        L_0x004e:
            goto L_0x0050
        L_0x004f:
            throw r6
        L_0x0050:
            goto L_0x004f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.smartswitch.FileUtils.unZipEntry(java.util.zip.ZipInputStream, java.io.File):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:35:0x007c  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0084  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x008f A[SYNTHETIC, Splitter:B:42:0x008f] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0097 A[Catch:{ IOException -> 0x0093 }] */
    /* JADX WARNING: Removed duplicated region for block: B:54:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void zipEntry(java.io.File r5, java.lang.String r6, java.util.zip.ZipOutputStream r7) {
        /*
        // Method dump skipped, instructions count: 161
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.smartswitch.FileUtils.zipEntry(java.io.File, java.lang.String, java.util.zip.ZipOutputStream):void");
    }

    public static void zipFiles(File[] fileArr, File file) {
        ZipOutputStream zipOutputStream;
        Throwable th;
        FileNotFoundException e;
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream fileOutputStream2 = new FileOutputStream(file);
            try {
                zipOutputStream = new ZipOutputStream(fileOutputStream2);
                try {
                    zipFilesInternal(fileArr, zipOutputStream);
                    try {
                        fileOutputStream2.close();
                    } catch (IOException | NullPointerException e2) {
                        e2.printStackTrace();
                    }
                    try {
                        zipOutputStream.close();
                    } catch (IOException | NullPointerException e3) {
                        e3.printStackTrace();
                    }
                } catch (FileNotFoundException e4) {
                    e = e4;
                    fileOutputStream = fileOutputStream2;
                    try {
                        e.printStackTrace();
                        try {
                            fileOutputStream.close();
                        } catch (IOException | NullPointerException e5) {
                            e5.printStackTrace();
                        }
                        zipOutputStream.close();
                    } catch (Throwable th2) {
                        th = th2;
                        try {
                            fileOutputStream.close();
                        } catch (IOException | NullPointerException e6) {
                            e6.printStackTrace();
                        }
                        try {
                            zipOutputStream.close();
                        } catch (IOException | NullPointerException e7) {
                            e7.printStackTrace();
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    fileOutputStream = fileOutputStream2;
                    fileOutputStream.close();
                    zipOutputStream.close();
                    throw th;
                }
            } catch (FileNotFoundException e8) {
                e = e8;
                zipOutputStream = null;
                fileOutputStream = fileOutputStream2;
                e.printStackTrace();
                fileOutputStream.close();
                zipOutputStream.close();
            } catch (Throwable th4) {
                th = th4;
                zipOutputStream = null;
                fileOutputStream = fileOutputStream2;
                fileOutputStream.close();
                zipOutputStream.close();
                throw th;
            }
        } catch (FileNotFoundException e9) {
            e = e9;
            zipOutputStream = null;
            e.printStackTrace();
            fileOutputStream.close();
            zipOutputStream.close();
        } catch (Throwable th5) {
            th = th5;
            zipOutputStream = null;
            fileOutputStream.close();
            zipOutputStream.close();
            throw th;
        }
    }

    private static void zipFilesInternal(File[] fileArr, ZipOutputStream zipOutputStream) {
        Throwable th;
        IOException e;
        byte[] bArr = new byte[128];
        for (File file : fileArr) {
            if (!file.isDirectory()) {
                ZipEntry zipEntry = new ZipEntry(file.getName());
                FileInputStream fileInputStream = null;
                try {
                    FileInputStream fileInputStream2 = new FileInputStream(file);
                    try {
                        zipOutputStream.putNextEntry(zipEntry);
                        while (true) {
                            int read = fileInputStream2.read(bArr);
                            if (read != -1) {
                                zipOutputStream.write(bArr, 0, read);
                            } else {
                                try {
                                    break;
                                } catch (IOException | NullPointerException e2) {
                                    e2.printStackTrace();
                                }
                            }
                        }
                        zipOutputStream.closeEntry();
                        try {
                            fileInputStream2.close();
                        } catch (IOException | NullPointerException e3) {
                            e3.printStackTrace();
                        }
                    } catch (IOException e4) {
                        e = e4;
                        fileInputStream = fileInputStream2;
                        try {
                            e.printStackTrace();
                            try {
                                zipOutputStream.closeEntry();
                            } catch (IOException | NullPointerException e5) {
                                e5.printStackTrace();
                            }
                            fileInputStream.close();
                        } catch (Throwable th2) {
                            th = th2;
                            try {
                                zipOutputStream.closeEntry();
                            } catch (IOException | NullPointerException e6) {
                                e6.printStackTrace();
                            }
                            try {
                                fileInputStream.close();
                            } catch (IOException | NullPointerException e7) {
                                e7.printStackTrace();
                            }
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        fileInputStream = fileInputStream2;
                        zipOutputStream.closeEntry();
                        fileInputStream.close();
                        throw th;
                    }
                } catch (IOException e8) {
                    e = e8;
                    e.printStackTrace();
                    zipOutputStream.closeEntry();
                    fileInputStream.close();
                }
            }
        }
    }
}
