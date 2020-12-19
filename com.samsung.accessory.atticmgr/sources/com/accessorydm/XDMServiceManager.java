package com.accessorydm;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import com.accessorydm.adapter.XDMFeature;
import com.accessorydm.adapter.XDMTargetAdapter;
import com.accessorydm.agent.XDMAgent;
import com.accessorydm.dmstarter.XDMInitExecutor;
import com.accessorydm.network.XDMNetworkCallback;
import com.accessorydm.receiver.XDMDynamicReceivers;
import com.accessorydm.service.XDMJobService;
import com.accessorydm.service.XDMService;
import com.accessorydm.tp.XTPAdapter;
import com.accessorydm.ui.notification.XUINotificationManager;
import com.samsung.android.fotaagent.FotaServiceJobId;
import com.samsung.android.fotaprovider.FotaProviderInitializer;
import com.samsung.android.fotaprovider.log.Log;
import java.util.Observable;
import java.util.Observer;

public class XDMServiceManager extends Observable implements Observer {
    private static final XDMServiceManager instance = new XDMServiceManager();
    private boolean isServiceBound;
    private ServiceConnection mServiceConnection;

    public static XDMServiceManager getInstance() {
        return instance;
    }

    private XDMServiceManager() {
        Log.I("");
        if (Build.VERSION.SDK_INT < 26) {
            this.mServiceConnection = new ServiceConnection() {
                /* class com.accessorydm.XDMServiceManager.AnonymousClass1 */

                public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    Log.I("XDMService onServiceConnected");
                    XDMServiceManager.this.isServiceBound = true;
                }

                public void onServiceDisconnected(ComponentName componentName) {
                    Log.I("XDMService onServiceDisconnected");
                    XDMServiceManager.this.isServiceBound = false;
                }
            };
        }
    }

    public void update(Observable observable, Object obj) {
        if (observable != null && (obj instanceof Integer) && ((Integer) obj).intValue() == 1) {
            Log.I("Notify Service Observer");
            xdmDoStopInitService();
        }
    }

    public void xdmDoStartInitService() {
        Log.I("");
        if (Build.VERSION.SDK_INT >= 26) {
            xdmStartJobService(FotaServiceJobId.INSTANCE.DM_SERVICE_JOB_ID);
            return;
        }
        Intent intent = new Intent(getContext(), XDMService.class);
        getContext().startService(intent);
        xdmBindService(intent);
    }

    private void xdmDoStopInitService() {
        Log.I("");
        if (Build.VERSION.SDK_INT >= 26) {
            xdmStopJobService(FotaServiceJobId.INSTANCE.DM_SERVICE_JOB_ID);
        } else {
            xdmUnbindService();
        }
    }

    private void xdmBindService(Intent intent) {
        if (!this.isServiceBound) {
            try {
                Log.I("xdmBindService");
                getContext().bindService(intent, this.mServiceConnection, 1);
            } catch (Exception e) {
                Log.E(e.toString());
            }
        }
    }

    private void xdmUnbindService() {
        if (this.isServiceBound) {
            try {
                getContext().unbindService(this.mServiceConnection);
                this.isServiceBound = false;
                Log.I("xdmUnbindService");
                getContext().stopService(new Intent(getContext(), XDMService.class));
            } catch (Exception e) {
                Log.E(e.toString());
            }
        }
    }

    public void xdmInitializeService() {
        Log.I("");
        try {
            xdmTaskInitialize();
            XDMFeature.xdmInitialize();
            XDMFeature.xdmSetPrefConfig();
            XDMTargetAdapter.xdmInitStorageState();
            XUINotificationManager.getInstance().xuiRemoveAllNotification();
            if (!XDMDynamicReceivers.getInstance().xdmIsRegisterReceiver()) {
                XDMDynamicReceivers.getInstance().xdmRegisterReceiver();
            }
            XDMNetworkCallback.getInstance().register();
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    private void xdmTaskInitialize() {
        XTPAdapter.xtpAdpSetIsConnected(false);
        XDMAgent.xdmAgentTpSetRetryCount(0);
        XDMAgent.xdmAgentSetSyncMode(0);
        XDMAgent.xdmAgentSetSvcState("");
    }

    public void xdmStopService() {
        Log.I("");
        XDMInitExecutor.getInstance().initExecutorStopIfRunning();
        if (XDMDynamicReceivers.getInstance().xdmIsRegisterReceiver()) {
            XDMDynamicReceivers.getInstance().xdmUnregisterReceiver();
        }
        XDMNetworkCallback.getInstance().unregister();
        XDMSecReceiverApiCall.getInstance().xdmAccessorySetRegistrationMode(0);
        getInstance().xdmNotifyObserver(1);
    }

    private void xdmStartJobService(int i) {
        Log.I("");
        JobScheduler jobScheduler = getJobScheduler();
        if (jobScheduler.getPendingJob(i) == null) {
            JobInfo.Builder builder = new JobInfo.Builder(i, new ComponentName(getContext(), XDMJobService.class));
            builder.setOverrideDeadline(0);
            if (jobScheduler.schedule(builder.build()) == 1) {
                Log.I("JobScheduler is Success!!");
                return;
            }
            Log.I("JobScheduler is fail!! Reschedule");
            jobScheduler.schedule(builder.build());
        }
    }

    private void xdmStopJobService(int i) {
        JobScheduler jobScheduler = getJobScheduler();
        if (jobScheduler != null) {
            Log.I("JobService cancel");
            jobScheduler.cancel(i);
        }
    }

    public void xdmAddObserver() {
        Log.I("");
        if (countObservers() == 0) {
            addObserver(this);
        }
    }

    public void xdmDeleteObserver() {
        Log.I("");
        if (countObservers() > 0) {
            deleteObserver(this);
        }
        Log.I("xdmDeleteObserver count : " + countObservers());
    }

    public void xdmNotifyObserver(int i) {
        Log.I("xdmNotifyObserver count : " + countObservers());
        if (countObservers() > 0) {
            setChanged();
            notifyObservers(Integer.valueOf(i));
        }
    }

    private JobScheduler getJobScheduler() {
        return (JobScheduler) getContext().getSystemService("jobscheduler");
    }

    private static Context getContext() {
        return FotaProviderInitializer.getContext();
    }
}
