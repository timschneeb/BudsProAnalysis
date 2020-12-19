package androidx.picker.app;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.util.SeslMisc;
import androidx.picker.R;
import androidx.picker.util.SeslAnimationListener;
import androidx.picker.widget.SeslTimePicker;

public class SeslTimePickerDialog extends AlertDialog implements DialogInterface.OnClickListener, SeslTimePicker.OnTimeChangedListener {
    private static final String HOUR = "hour";
    private static final String IS_24_HOUR = "is24hour";
    private static final String MINUTE = "minute";
    private final View.OnFocusChangeListener mBtnFocusChangeListener;
    private InputMethodManager mImm;
    private final int mInitialHourOfDay;
    private final int mInitialMinute;
    private final boolean mIs24HourView;
    private boolean mIsStartAnimation;
    private final SeslTimePicker mTimePicker;
    private final OnTimeSetListener mTimeSetListener;

    public interface OnTimeSetListener {
        void onTimeSet(SeslTimePicker seslTimePicker, int i, int i2);
    }

    @Override // androidx.picker.widget.SeslTimePicker.OnTimeChangedListener
    public void onTimeChanged(SeslTimePicker seslTimePicker, int i, int i2) {
    }

    public SeslTimePickerDialog(Context context, OnTimeSetListener onTimeSetListener, int i, int i2, boolean z) {
        this(context, 0, onTimeSetListener, i, i2, z);
    }

    static int resolveDialogTheme(Context context, int i) {
        if (i == 0) {
            return SeslMisc.isLightTheme(context) ? R.style.Theme_AppCompat_Light_PickerDialog_TimePicker : R.style.Theme_AppCompat_PickerDialog_TimePicker;
        }
        return i;
    }

    public SeslTimePickerDialog(Context context, int i, OnTimeSetListener onTimeSetListener, int i2, int i3, boolean z) {
        super(context, resolveDialogTheme(context, i));
        this.mBtnFocusChangeListener = new View.OnFocusChangeListener() {
            /* class androidx.picker.app.SeslTimePickerDialog.AnonymousClass1 */

            public void onFocusChange(View view, boolean z) {
                if (SeslTimePickerDialog.this.mTimePicker.isEditTextMode() && z) {
                    SeslTimePickerDialog.this.mTimePicker.setEditTextMode(false);
                }
            }
        };
        this.mTimeSetListener = onTimeSetListener;
        this.mInitialHourOfDay = i2;
        this.mInitialMinute = i3;
        this.mIs24HourView = z;
        Context context2 = getContext();
        View inflate = LayoutInflater.from(context2).inflate(R.layout.sesl_time_picker_spinner_dialog, (ViewGroup) null);
        setView(inflate);
        setButton(-1, context2.getString(R.string.sesl_picker_done), this);
        setButton(-2, context2.getString(R.string.sesl_picker_cancel), this);
        this.mTimePicker = (SeslTimePicker) inflate.findViewById(R.id.timePicker);
        this.mTimePicker.setIs24HourView(Boolean.valueOf(this.mIs24HourView));
        this.mTimePicker.setHour(this.mInitialHourOfDay);
        this.mTimePicker.setMinute(this.mInitialMinute);
        this.mTimePicker.setOnTimeChangedListener(this);
        setTitle(R.string.sesl_time_picker_set_title);
        this.mImm = (InputMethodManager) getContext().getSystemService("input_method");
    }

    @Override // androidx.appcompat.app.AlertDialog, androidx.appcompat.app.AppCompatDialog
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getButton(-1).setOnFocusChangeListener(this.mBtnFocusChangeListener);
        getButton(-2).setOnFocusChangeListener(this.mBtnFocusChangeListener);
        this.mIsStartAnimation = true;
        this.mTimePicker.startAnimation(283, new SeslAnimationListener() {
            /* class androidx.picker.app.SeslTimePickerDialog.AnonymousClass2 */

            @Override // androidx.picker.util.SeslAnimationListener
            public void onAnimationEnd() {
                SeslTimePickerDialog.this.mIsStartAnimation = false;
            }
        });
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        if (i == -2) {
            InputMethodManager inputMethodManager = this.mImm;
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
            }
            cancel();
        } else if (i == -1 && !this.mIsStartAnimation) {
            if (this.mTimeSetListener != null) {
                this.mTimePicker.clearFocus();
                OnTimeSetListener onTimeSetListener = this.mTimeSetListener;
                SeslTimePicker seslTimePicker = this.mTimePicker;
                onTimeSetListener.onTimeSet(seslTimePicker, seslTimePicker.getHour(), this.mTimePicker.getMinute());
            }
            InputMethodManager inputMethodManager2 = this.mImm;
            if (inputMethodManager2 != null) {
                inputMethodManager2.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
            }
            dismiss();
        }
    }

    public void updateTime(int i, int i2) {
        this.mTimePicker.setHour(i);
        this.mTimePicker.setMinute(i2);
    }

    public Bundle onSaveInstanceState() {
        Bundle onSaveInstanceState = super.onSaveInstanceState();
        onSaveInstanceState.putInt(HOUR, this.mTimePicker.getHour());
        onSaveInstanceState.putInt(MINUTE, this.mTimePicker.getMinute());
        onSaveInstanceState.putBoolean(IS_24_HOUR, this.mTimePicker.is24HourView());
        return onSaveInstanceState;
    }

    public void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        int i = bundle.getInt(HOUR);
        int i2 = bundle.getInt(MINUTE);
        this.mTimePicker.setIs24HourView(Boolean.valueOf(bundle.getBoolean(IS_24_HOUR)));
        this.mTimePicker.setHour(i);
        this.mTimePicker.setMinute(i2);
    }
}
