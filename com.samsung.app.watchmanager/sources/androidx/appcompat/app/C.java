package androidx.appcompat.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import androidx.core.content.b;
import java.util.Calendar;

class C {

    /* renamed from: a  reason: collision with root package name */
    private static C f147a;

    /* renamed from: b  reason: collision with root package name */
    private final Context f148b;

    /* renamed from: c  reason: collision with root package name */
    private final LocationManager f149c;

    /* renamed from: d  reason: collision with root package name */
    private final a f150d = new a();

    /* access modifiers changed from: private */
    public static class a {

        /* renamed from: a  reason: collision with root package name */
        boolean f151a;

        /* renamed from: b  reason: collision with root package name */
        long f152b;

        /* renamed from: c  reason: collision with root package name */
        long f153c;

        /* renamed from: d  reason: collision with root package name */
        long f154d;
        long e;
        long f;

        a() {
        }
    }

    C(Context context, LocationManager locationManager) {
        this.f148b = context;
        this.f149c = locationManager;
    }

    private Location a(String str) {
        try {
            if (this.f149c.isProviderEnabled(str)) {
                return this.f149c.getLastKnownLocation(str);
            }
            return null;
        } catch (Exception e) {
            Log.d("TwilightManager", "Failed to get last known location", e);
            return null;
        }
    }

    static C a(Context context) {
        if (f147a == null) {
            Context applicationContext = context.getApplicationContext();
            f147a = new C(applicationContext, (LocationManager) applicationContext.getSystemService("location"));
        }
        return f147a;
    }

    private void a(Location location) {
        long j;
        a aVar = this.f150d;
        long currentTimeMillis = System.currentTimeMillis();
        B a2 = B.a();
        a2.a(currentTimeMillis - 86400000, location.getLatitude(), location.getLongitude());
        long j2 = a2.f144b;
        a2.a(currentTimeMillis, location.getLatitude(), location.getLongitude());
        boolean z = a2.f146d == 1;
        long j3 = a2.f145c;
        long j4 = a2.f144b;
        a2.a(86400000 + currentTimeMillis, location.getLatitude(), location.getLongitude());
        long j5 = a2.f145c;
        if (j3 == -1 || j4 == -1) {
            j = 43200000 + currentTimeMillis;
        } else {
            j = (currentTimeMillis > j4 ? 0 + j5 : currentTimeMillis > j3 ? 0 + j4 : 0 + j3) + 60000;
        }
        aVar.f151a = z;
        aVar.f152b = j2;
        aVar.f153c = j3;
        aVar.f154d = j4;
        aVar.e = j5;
        aVar.f = j;
    }

    @SuppressLint({"MissingPermission"})
    private Location b() {
        Location location = null;
        Location a2 = b.a(this.f148b, "android.permission.ACCESS_COARSE_LOCATION") == 0 ? a("network") : null;
        if (b.a(this.f148b, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            location = a("gps");
        }
        return (location == null || a2 == null) ? location != null ? location : a2 : location.getTime() > a2.getTime() ? location : a2;
    }

    private boolean c() {
        return this.f150d.f > System.currentTimeMillis();
    }

    /* access modifiers changed from: package-private */
    public boolean a() {
        a aVar = this.f150d;
        if (c()) {
            return aVar.f151a;
        }
        Location b2 = b();
        if (b2 != null) {
            a(b2);
            return aVar.f151a;
        }
        Log.i("TwilightManager", "Could not get last known location. This is probably because the app does not have any location permissions. Falling back to hardcoded sunrise/sunset values.");
        int i = Calendar.getInstance().get(11);
        return i < 6 || i >= 22;
    }
}
