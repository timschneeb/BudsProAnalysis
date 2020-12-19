package com.accessorydm.ui.installconfirm.scheduleinstall;

import android.app.Dialog;
import android.content.Context;
import androidx.picker.app.SeslTimePickerDialog;

public interface ScheduleInstallContract {

    public interface Presenter {
        void onCreate(Context context);

        Dialog onCreateDialog(Context context);

        void onTimeSet(Context context, int i, int i2);
    }

    public interface View extends SeslTimePickerDialog.OnTimeSetListener {
    }
}
