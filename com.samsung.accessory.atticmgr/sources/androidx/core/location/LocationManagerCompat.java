package androidx.core.location;

import android.location.LocationManager;
import android.os.Build;
import com.accessorydm.interfaces.XDBInterface;

public final class LocationManagerCompat {
    public static boolean isLocationEnabled(LocationManager locationManager) {
        if (Build.VERSION.SDK_INT >= 28) {
            return locationManager.isLocationEnabled();
        }
        return locationManager.isProviderEnabled(XDBInterface.XDB_NETWORK_TABLE) || locationManager.isProviderEnabled("gps");
    }

    private LocationManagerCompat() {
    }
}
