package com.samsung.android.app.twatchmanager.plugininfoservice;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.plugindatamanager.PluginDataManager;
import com.samsung.android.app.twatchmanager.plugininfoservice.MessageConfig;

public class PluginInfoProvideService extends Service {
    private static final String TAG = ("tUHM:" + PluginInfoProvideService.class.getSimpleName());
    private Context mContext;
    @SuppressLint({"HandlerLeak"})
    private final Handler mMessageHandler = new Handler() {
        /* class com.samsung.android.app.twatchmanager.plugininfoservice.PluginInfoProvideService.AnonymousClass1 */

        public void handleMessage(Message message) {
            PluginInfoProvideService pluginInfoProvideService;
            Messenger messenger;
            int i;
            Bundle installedPluginListResult;
            int i2 = message.what;
            if (i2 == MessageConfig.Type.SUPPORTED_API_LIST.REQUEST_ID) {
                Log.d(PluginInfoProvideService.TAG, "handleMessage: MSG_WHAT_GETTING_SUPPORTED_API_LIST");
                pluginInfoProvideService = PluginInfoProvideService.this;
                messenger = message.replyTo;
                i = MessageConfig.Type.SUPPORTED_API_LIST.RESPONSE_ID;
                installedPluginListResult = PluginDataManager.getInstance().getSupportedApiList();
            } else if (i2 == MessageConfig.Type.INSTALLED_PLUGIN_LIST.REQUEST_ID) {
                Log.d(PluginInfoProvideService.TAG, "handleMessage: MSG_WHAT_GETTING_WATCH_PLUGIN_LIST_INFO");
                pluginInfoProvideService = PluginInfoProvideService.this;
                messenger = message.replyTo;
                i = MessageConfig.Type.INSTALLED_PLUGIN_LIST.RESPONSE_ID;
                installedPluginListResult = PluginDataManager.getInstance().getInstalledPluginListResult(PluginInfoProvideService.this.mContext);
            } else {
                return;
            }
            pluginInfoProvideService.sendResult(messenger, i, installedPluginListResult);
        }
    };

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void sendResult(Messenger messenger, int i, Bundle bundle) {
        if (messenger != null) {
            Message obtain = Message.obtain((Handler) null, i);
            obtain.setData(bundle);
            try {
                messenger.send(obtain);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return new Messenger(this.mMessageHandler).getBinder();
    }

    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        this.mContext = this;
    }
}
