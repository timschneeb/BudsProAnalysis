package com.accessorydm.ui.installconfirm.scheduleinstall;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.picker.widget.SeslTimePicker;
import com.accessorydm.ui.installconfirm.scheduleinstall.ScheduleInstallContract;
import com.samsung.android.fotaprovider.log.Log;

public class ScheduleInstallDialog extends DialogFragment implements ScheduleInstallContract.View {
    public static final String TAG = "DIALOG_FOR_SCHEDULE_INSTALL";
    private ScheduleInstallContract.Presenter presenter;

    @Override // androidx.fragment.app.Fragment, androidx.fragment.app.DialogFragment
    public void onCreate(Bundle bundle) {
        Log.I("");
        this.presenter = new ScheduleInstallPresenter(this, ScheduleInstallModel.getInstance());
        super.onCreate(bundle);
        this.presenter.onCreate(getActivity());
    }

    @Override // androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle bundle) {
        Log.I("");
        return this.presenter.onCreateDialog(getActivity());
    }

    @Override // androidx.picker.app.SeslTimePickerDialog.OnTimeSetListener
    public void onTimeSet(SeslTimePicker seslTimePicker, int i, int i2) {
        Log.I("");
        if (getActivity() != null) {
            this.presenter.onTimeSet(seslTimePicker.getContext(), i, i2);
            getActivity().finish();
        }
    }

    @Override // androidx.fragment.app.DialogFragment
    public void onCancel(DialogInterface dialogInterface) {
        Log.I("");
        super.onCancel(dialogInterface);
    }
}
