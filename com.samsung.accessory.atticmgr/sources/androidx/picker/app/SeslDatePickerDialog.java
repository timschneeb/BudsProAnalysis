package androidx.picker.app;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.util.SeslMisc;
import androidx.picker.R;
import androidx.picker.widget.SeslDatePicker;

public class SeslDatePickerDialog extends AlertDialog implements DialogInterface.OnClickListener, SeslDatePicker.OnDateChangedListener {
    private static final String DAY = "day";
    private static final String MONTH = "month";
    private static final String YEAR = "year";
    private final View.OnFocusChangeListener mBtnFocusChangeListener;
    private final SeslDatePicker mDatePicker;
    private final OnDateSetListener mDateSetListener;
    private InputMethodManager mImm;
    private final SeslDatePicker.ValidationCallback mValidationCallback;

    public interface OnDateSetListener {
        void onDateSet(SeslDatePicker seslDatePicker, int i, int i2, int i3);
    }

    @Override // androidx.picker.widget.SeslDatePicker.OnDateChangedListener
    public void onDateChanged(SeslDatePicker seslDatePicker, int i, int i2, int i3) {
    }

    public SeslDatePickerDialog(Context context, OnDateSetListener onDateSetListener, int i, int i2, int i3) {
        this(context, 0, onDateSetListener, i, i2, i3);
    }

    public SeslDatePickerDialog(Context context, int i, OnDateSetListener onDateSetListener, int i2, int i3, int i4) {
        super(context, resolveDialogTheme(context, i));
        this.mBtnFocusChangeListener = new View.OnFocusChangeListener() {
            /* class androidx.picker.app.SeslDatePickerDialog.AnonymousClass1 */

            public void onFocusChange(View view, boolean z) {
                if (SeslDatePickerDialog.this.mDatePicker.isEditTextMode() && z) {
                    SeslDatePickerDialog.this.mDatePicker.setEditTextMode(false);
                }
            }
        };
        this.mValidationCallback = new SeslDatePicker.ValidationCallback() {
            /* class androidx.picker.app.SeslDatePickerDialog.AnonymousClass2 */

            @Override // androidx.picker.widget.SeslDatePicker.ValidationCallback
            public void onValidationChanged(boolean z) {
                Button button = SeslDatePickerDialog.this.getButton(-1);
                if (button != null) {
                    button.setEnabled(z);
                }
            }
        };
        Context context2 = getContext();
        View inflate = LayoutInflater.from(context2).inflate(R.layout.sesl_date_picker_dialog, (ViewGroup) null);
        setView(inflate);
        setButton(-1, context2.getString(R.string.sesl_picker_done), this);
        setButton(-2, context2.getString(R.string.sesl_picker_cancel), this);
        this.mDatePicker = (SeslDatePicker) inflate.findViewById(R.id.sesl_datePicker);
        this.mDatePicker.init(i2, i3, i4, this);
        this.mDatePicker.setValidationCallback(this.mValidationCallback);
        this.mDatePicker.setDialogWindow(getWindow());
        this.mDatePicker.setDialogPaddingVertical(inflate.getPaddingTop() + inflate.getPaddingBottom());
        this.mDateSetListener = onDateSetListener;
        this.mImm = (InputMethodManager) context2.getSystemService("input_method");
    }

    static int resolveDialogTheme(Context context, int i) {
        if (i == 0) {
            return SeslMisc.isLightTheme(context) ? R.style.Theme_AppCompat_Light_PickerDialog_DatePicker : R.style.Theme_AppCompat_PickerDialog_DatePicker;
        }
        return i;
    }

    /* access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AlertDialog, androidx.appcompat.app.AppCompatDialog
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getButton(-1).setOnFocusChangeListener(this.mBtnFocusChangeListener);
        getButton(-2).setOnFocusChangeListener(this.mBtnFocusChangeListener);
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        InputMethodManager inputMethodManager = this.mImm;
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
        if (i == -2) {
            cancel();
        } else if (i == -1 && this.mDateSetListener != null) {
            this.mDatePicker.clearFocus();
            OnDateSetListener onDateSetListener = this.mDateSetListener;
            SeslDatePicker seslDatePicker = this.mDatePicker;
            onDateSetListener.onDateSet(seslDatePicker, seslDatePicker.getYear(), this.mDatePicker.getMonth(), this.mDatePicker.getDayOfMonth());
        }
    }

    public SeslDatePicker getDatePicker() {
        return this.mDatePicker;
    }

    public void updateDate(int i, int i2, int i3) {
        this.mDatePicker.updateDate(i, i2, i3);
    }

    public Bundle onSaveInstanceState() {
        Bundle onSaveInstanceState = super.onSaveInstanceState();
        onSaveInstanceState.putInt(YEAR, this.mDatePicker.getYear());
        onSaveInstanceState.putInt(MONTH, this.mDatePicker.getMonth());
        onSaveInstanceState.putInt(DAY, this.mDatePicker.getDayOfMonth());
        return onSaveInstanceState;
    }

    public void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        this.mDatePicker.init(bundle.getInt(YEAR), bundle.getInt(MONTH), bundle.getInt(DAY), this);
    }
}
