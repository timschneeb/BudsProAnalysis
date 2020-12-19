package com.accessorydm.ui.installconfirm.scheduleinstall;

import android.app.Dialog;
import android.content.Context;
import android.text.format.DateFormat;
import androidx.picker.app.SeslTimePickerDialog;
import com.accessorydm.ui.handler.XDMToastHandler;
import com.accessorydm.ui.installconfirm.InstallCountdown;
import com.accessorydm.ui.installconfirm.scheduleinstall.ScheduleInstallContract;
import com.samsung.accessory.fotaprovider.AccessoryController;
import com.samsung.accessory.fotaprovider.controller.NotificationController;
import com.samsung.android.fotaprovider.log.Log;
import com.sec.android.fotaprovider.R;

public class ScheduleInstallPresenter implements ScheduleInstallContract.Presenter {
    private ScheduleInstallModel model;
    private ScheduleInstallContract.View view;

    ScheduleInstallPresenter(ScheduleInstallContract.View view2, ScheduleInstallModel scheduleInstallModel) {
        this.view = view2;
        this.model = scheduleInstallModel;
    }

    @Override // com.accessorydm.ui.installconfirm.scheduleinstall.ScheduleInstallContract.Presenter
    public void onCreate(Context context) {
        Log.I("");
    }

    @Override // com.accessorydm.ui.installconfirm.scheduleinstall.ScheduleInstallContract.Presenter
    public Dialog onCreateDialog(Context context) {
        Log.I("");
        return new SeslTimePickerDialog(context, R.style.FotaProviderTheme_Dialog_TimePicker, this.view, this.model.getDefaultHour(), this.model.getDefaultMinute(), DateFormat.is24HourFormat(context));
    }

    @Override // com.accessorydm.ui.installconfirm.scheduleinstall.ScheduleInstallContract.Presenter
    public void onTimeSet(Context context, int i, int i2) {
        Log.I("");
        InstallCountdown.getInstance().stop();
        AccessoryController.getInstance().getNotificationController().removeAccessoryNotification(new NotificationController.NotificationCallback(i, i2) {
            /* class com.accessorydm.ui.installconfirm.scheduleinstall.$$Lambda$ScheduleInstallPresenter$GtEYbdq0UlgSC_PI7qNovj8A9YE */
            private final /* synthetic */ int f$1;
            private final /* synthetic */ int f$2;

            {
                this.f$1 = r2;
                this.f$2 = r3;
            }

            @Override // com.samsung.accessory.fotaprovider.controller.NotificationController.NotificationCallback
            public final void onResponse() {
                ScheduleInstallPresenter.this.lambda$onTimeSet$0$ScheduleInstallPresenter(this.f$1, this.f$2);
            }
        });
    }

    public /* synthetic */ void lambda$onTimeSet$0$ScheduleInstallPresenter(int i, int i2) {
        Log.I("");
        this.model.scheduleInstallByTimePicker(i, i2);
        XDMToastHandler.xdmShowToast(this.model.getSetTimeToastText(), 0);
    }
}
