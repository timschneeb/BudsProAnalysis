package com.samsung.accessory.hearablemgr.core.appwidget.base;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import com.samsung.accessory.hearablemgr.core.appwidget.util.WidgetInfoManager;
import java.util.ArrayList;

public abstract class WidgetBaseProvider extends AppWidgetProvider {
    /* access modifiers changed from: protected */
    public abstract RemoteViews getRemoteView(Context context, int i);

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] iArr) {
        updateUI(context, appWidgetManager, iArr);
    }

    public void onDeleted(Context context, int[] iArr) {
        super.onDeleted(context, iArr);
        WidgetInfoManager widgetInfoManager = new WidgetInfoManager(context, getClass());
        for (int i : iArr) {
            widgetInfoManager.removeWidgetInfo(i);
        }
    }

    public void updateUI(Context context) {
        AppWidgetManager instance = AppWidgetManager.getInstance(context);
        updateUI(context, instance, instance.getAppWidgetIds(new ComponentName(context, getClass())));
    }

    /* access modifiers changed from: protected */
    public void updateUI(final Context context, final int i) {
        new Thread(new Runnable() {
            /* class com.samsung.accessory.hearablemgr.core.appwidget.base.WidgetBaseProvider.AnonymousClass1 */

            public void run() {
                AppWidgetManager instance = AppWidgetManager.getInstance(context);
                int i = i;
                instance.updateAppWidget(i, WidgetBaseProvider.this.getRemoteView(context, i));
            }
        }).start();
    }

    private void updateUI(final Context context, final AppWidgetManager appWidgetManager, final int[] iArr) {
        new Thread(new Runnable() {
            /* class com.samsung.accessory.hearablemgr.core.appwidget.base.WidgetBaseProvider.AnonymousClass2 */

            public void run() {
                ArrayList arrayList = new ArrayList();
                for (int i : iArr) {
                    arrayList.add(WidgetBaseProvider.this.getRemoteView(context, i));
                }
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    appWidgetManager.updateAppWidget(iArr[i2], (RemoteViews) arrayList.get(i2));
                }
            }
        }).start();
    }

    /* access modifiers changed from: protected */
    public PendingIntent getPendingIntent(Context context, String str) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(str);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }
}
