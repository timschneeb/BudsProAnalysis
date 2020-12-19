package com.samsung.android.sdk.bixby2.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import c.b.a.a.a.a.b;
import c.b.a.a.a.c;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

public class CapsuleProvider extends ContentProvider {

    /* renamed from: a  reason: collision with root package name */
    private static final String f1893a = (CapsuleProvider.class.getSimpleName() + "_" + "1.0.7");

    /* renamed from: b  reason: collision with root package name */
    private static final boolean f1894b = "user".equals(Build.TYPE);

    /* renamed from: c  reason: collision with root package name */
    private static Signature f1895c = new Signature(Base64.decode("MIIE1DCCA7ygAwIBAgIJANIJlaecDarWMA0GCSqGSIb3DQEBBQUAMIGiMQswCQYDVQQGEwJLUjEUMBIGA1UECBMLU291dGggS29yZWExEzARBgNVBAcTClN1d29uIENpdHkxHDAaBgNVBAoTE1NhbXN1bmcgQ29ycG9yYXRpb24xDDAKBgNVBAsTA0RNQzEVMBMGA1UEAxMMU2Ftc3VuZyBDZXJ0MSUwIwYJKoZIhvcNAQkBFhZhbmRyb2lkLm9zQHNhbXN1bmcuY29tMB4XDTExMDYyMjEyMjUxMloXDTM4MTEwNzEyMjUxMlowgaIxCzAJBgNVBAYTAktSMRQwEgYDVQQIEwtTb3V0aCBLb3JlYTETMBEGA1UEBxMKU3V3b24gQ2l0eTEcMBoGA1UEChMTU2Ftc3VuZyBDb3Jwb3JhdGlvbjEMMAoGA1UECxMDRE1DMRUwEwYDVQQDEwxTYW1zdW5nIENlcnQxJTAjBgkqhkiG9w0BCQEWFmFuZHJvaWQub3NAc2Ftc3VuZy5jb20wggEgMA0GCSqGSIb3DQEBAQUAA4IBDQAwggEIAoIBAQDJhjhKPh8vsgZnDnjvIyIVwNJvRaInKNuZpE2hHDWsM6cf4HHEotaCWptMiLMz7ZbzxebGZtYPPulMSQiFq8+NxmD3B6q8d+rT4tDYrugQjBXNJg8uhQQsKNLyktqjxtoMe/I5HbeEGq3o/fDJ0N7893Ek5tLeCp4NLadGw2cOT/zchbcBu0dEhhuW/3MR2jYDxaEDNuVf+jS0NT7tyF9RAV4VGMZ+MJ45+HY5/xeBB/EJzRhBGmB38mlktuY/inC5YZ2wQwajI8Gh0jr4Z+GfFPVw/+Vz0OOgwrMGMqrsMXM4CZS+HjQeOpC9LkthVIH0bbOeqDgWRI7DX+sXNcHzAgEDo4IBCzCCAQcwHQYDVR0OBBYEFJMsOvcLYnoMdhC1oOdCfWz66j8eMIHXBgNVHSMEgc8wgcyAFJMsOvcLYnoMdhC1oOdCfWz66j8eoYGopIGlMIGiMQswCQYDVQQGEwJLUjEUMBIGA1UECBMLU291dGggS29yZWExEzARBgNVBAcTClN1d29uIENpdHkxHDAaBgNVBAoTE1NhbXN1bmcgQ29ycG9yYXRpb24xDDAKBgNVBAsTA0RNQzEVMBMGA1UEAxMMU2Ftc3VuZyBDZXJ0MSUwIwYJKoZIhvcNAQkBFhZhbmRyb2lkLm9zQHNhbXN1bmcuY29tggkA0gmVp5wNqtYwDAYDVR0TBAUwAwEB/zANBgkqhkiG9w0BAQUFAAOCAQEAMpYB/kDgNqSobMXUndjBtUFZmOcmN1OLDUMDaaxRUw9jqs6MAZoaZmFqLxuyxfq9bzEyYfOA40cWI/BT2ePFP1/W0ZZdewAOTcJEwbJ+L+mjI/8Hf1LEZ16GJHqoARhxN+MMm78BxWekKZ20vwslt9cQenuB7hAvcv9HlQFk4mdS4RTEL4udKkLnMIiX7GQOoZJO0Tq76dEgkSti9JJkk6htuUwLRvRMYWHVjC9kgWSJDFEt+yjULIVb9HDb7i2raWDK0E6B9xUl3tRs3Q81n5nEYNufAH2WzoO0shisLYLEjxJgjUaXM/BaM3VZRmnMv4pJVUTWxXAek2nAjIEBWA==", 0));

    /* renamed from: d  reason: collision with root package name */
    private static Map<String, c.b.a.a.a.a.a> f1896d = new HashMap();
    private static boolean e = false;
    private static boolean f = false;
    private static Object g = new Object();
    private static String h = null;
    private Object i = new Object();

    /* access modifiers changed from: private */
    public class a implements b {

        /* renamed from: a  reason: collision with root package name */
        private Bundle f1897a;

        /* renamed from: b  reason: collision with root package name */
        private boolean f1898b;

        /* renamed from: c  reason: collision with root package name */
        private boolean f1899c;

        public a() {
            this.f1897a = new Bundle();
            this.f1898b = false;
            this.f1899c = false;
            this.f1898b = false;
            this.f1899c = false;
        }

        public Bundle a() {
            return this.f1897a;
        }

        @Override // c.b.a.a.a.a.b
        public void a(String str) {
            synchronized (CapsuleProvider.this.i) {
                if (!this.f1899c) {
                    if (!this.f1898b) {
                        this.f1897a.putInt("status_code", 0);
                        this.f1897a.putString("result", str);
                        String str2 = CapsuleProvider.f1893a;
                        StringBuilder sb = new StringBuilder();
                        sb.append("action result: ");
                        if (str == null) {
                            str = null;
                        }
                        sb.append(str);
                        c.b.a.a.a.b.a(str2, sb.toString());
                        this.f1898b = true;
                        CapsuleProvider.this.i.notify();
                    }
                }
            }
        }

        public void a(boolean z) {
            this.f1899c = z;
        }
    }

    private Bundle a(int i2, String str) {
        Bundle bundle = new Bundle();
        bundle.putInt("status_code", i2);
        if (TextUtils.isEmpty(str) && i2 == -1) {
            str = "Failed to execute action.";
            c.b.a.a.a.b.b(f1893a, str);
        }
        bundle.putString("status_message", str);
        return bundle;
    }

    private synchronized Bundle a(String str, Bundle bundle) {
        try {
            c.b.a.a.a.a.a a2 = a(str);
            if (a2 == null) {
                c.b.a.a.a.b.b(f1893a, "Handler not found!!..");
                return a(-2, "Action handler not found");
            } else if (bundle == null || !bundle.containsKey(c.b.a.a.a.a.a.ACTION_TYPE)) {
                return a(-1, "params missing..");
            } else {
                a aVar = new a();
                Thread thread = new Thread(new b(this, a2, str, bundle, aVar));
                thread.start();
                synchronized (this.i) {
                    if (!aVar.f1898b) {
                        this.i.wait(30000);
                    }
                    if (aVar.f1898b) {
                        Bundle a3 = aVar.a();
                        if (a3 != null) {
                            return a3;
                        }
                    } else {
                        c.b.a.a.a.b.a(f1893a, "timeout occurred..");
                        aVar.a(true);
                        thread.interrupt();
                    }
                    return a(-1, "action execution timed out");
                }
            }
        } catch (Exception e2) {
            String str2 = f1893a;
            c.b.a.a.a.b.b(str2, "Unable to execute action." + e2.toString());
            e2.printStackTrace();
            return a(-1, e2.toString());
        }
    }

    private c.b.a.a.a.a.a a(String str) {
        c.b.a.a.a.a.a aVar = f1896d.get(str);
        synchronized (g) {
            if (aVar == null) {
                if (f) {
                    h = str;
                    g.wait(3000);
                    aVar = f1896d.get(str);
                }
            }
        }
        return aVar;
    }

    public static void a(String str, c.b.a.a.a.a.a aVar) {
        synchronized (g) {
            if (f1896d.get(str) == null) {
                f1896d.put(str, aVar);
                if (h != null && h.equals(str)) {
                    String str2 = f1893a;
                    c.b.a.a.a.b.c(str2, "handler added: " + str);
                    g.notify();
                }
            }
        }
    }

    public static void b(boolean z) {
        synchronized (g) {
            if (!e && z) {
                e = z;
                c.b.a.a.a.b.c(f1893a, "releasing initialize wait lock.");
                g.notify();
            }
        }
        new Timer().schedule(new a(), 3000);
    }

    private boolean b() {
        if (!f1894b) {
            return true;
        }
        int callingUid = Binder.getCallingUid();
        PackageManager packageManager = getContext().getPackageManager();
        String[] packagesForUid = packageManager.getPackagesForUid(callingUid);
        if (packagesForUid == null) {
            c.b.a.a.a.b.b(f1893a, "packages is null");
            return false;
        }
        for (String str : packagesForUid) {
            if ("com.samsung.android.bixby.agent".equals(str)) {
                try {
                    Signature[] signatureArr = packageManager.getPackageInfo(str, 64).signatures;
                    if (signatureArr != null && signatureArr.length > 0 && f1895c.equals(signatureArr[0])) {
                        return true;
                    }
                } catch (PackageManager.NameNotFoundException e2) {
                    e2.printStackTrace();
                }
            }
        }
        c.b.a.a.a.b.b(f1893a, "Not allowed to access capsule provider. package (s): " + Arrays.toString(packagesForUid));
        return false;
    }

    private void c() {
        synchronized (g) {
            if (!e) {
                try {
                    g.wait(3000);
                } catch (InterruptedException e2) {
                    c.b.a.a.a.b.b(f1893a, "interrupted exception");
                    e2.printStackTrace();
                }
            }
        }
    }

    public Bundle call(String str, String str2, Bundle bundle) {
        String str3 = f1893a;
        StringBuilder sb = new StringBuilder();
        sb.append("call(): method --> ");
        sb.append(str);
        sb.append(" args --> ");
        sb.append(str2);
        sb.append(" extras --> ");
        sb.append(bundle != null ? bundle.toString() : null);
        c.b.a.a.a.b.a(str3, sb.toString());
        if (!b()) {
            throw new SecurityException("not allowed to access capsule provider.");
        } else if (!TextUtils.isEmpty(str)) {
            c();
            if (!e) {
                c.b.a.a.a.b.b(f1893a, "App initialization error.");
                return a(-1, "Initialization Failure..");
            } else if (str.equals("getAppContext")) {
                c.b();
                String a2 = c.c().a(getContext());
                if (a2 == null) {
                    return null;
                }
                Bundle bundle2 = new Bundle();
                bundle2.putString("appContext", a2);
                return bundle2;
            } else if (bundle != null) {
                return a(str, bundle);
            } else {
                throw new IllegalArgumentException("action params are EMPTY.");
            }
        } else {
            throw new IllegalArgumentException("method is null or empty. pass valid action name.");
        }
    }

    public int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    public String getType(Uri uri) {
        return "actionUri";
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    public boolean onCreate() {
        c.b.a.a.a.b.a(f1893a, "onCreate");
        f = true;
        return true;
    }

    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return null;
    }

    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }
}
