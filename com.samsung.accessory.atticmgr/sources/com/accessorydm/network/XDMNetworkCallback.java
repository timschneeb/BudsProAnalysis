package com.accessorydm.network;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import com.accessorydm.XDMDmUtils;
import com.accessorydm.resume.XDMResumeStarter;
import com.samsung.android.fotaprovider.FotaProviderInitializer;
import com.samsung.android.fotaprovider.log.Log;
import com.samsung.android.fotaprovider.util.NetworkUtil;

public class XDMNetworkCallback extends ConnectivityManager.NetworkCallback {
    private static XDMNetworkCallback instance = new XDMNetworkCallback();
    private final NetworkRequest networkRequest = new NetworkRequest.Builder().addTransportType(0).addTransportType(1).addCapability(12).removeCapability(15).build();

    private XDMNetworkCallback() {
    }

    public static XDMNetworkCallback getInstance() {
        return instance;
    }

    public void register() {
        Log.I("");
        ((ConnectivityManager) FotaProviderInitializer.getContext().getSystemService("connectivity")).registerNetworkCallback(this.networkRequest, this);
    }

    public void unregister() {
        Log.I("");
        try {
            ((ConnectivityManager) FotaProviderInitializer.getContext().getSystemService("connectivity")).unregisterNetworkCallback(this);
        } catch (IllegalArgumentException e) {
            Log.W(e.toString());
        }
    }

    public void onAvailable(Network network) {
        Log.I("");
        if (NetworkUtil.isAnyNetworkConnected(FotaProviderInitializer.getContext())) {
            if (XDMDmUtils.getInstance().xdmGetResumeStatus() != 0) {
                Log.I("Run Intent ResumeStatus Operation");
                XDMResumeStarter.INTENT_RESUME.resumeExecute();
            } else if (XDMDmUtils.getInstance().xdmGetWaitWifiConnectMode() != 0) {
                Log.I("Run Intent WifiConnectMode Operation");
                XDMResumeStarter.INTENT_RESUME.resumeExecute();
            }
        }
        super.onAvailable(network);
    }
}
