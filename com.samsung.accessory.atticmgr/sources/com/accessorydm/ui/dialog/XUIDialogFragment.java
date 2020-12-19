package com.accessorydm.ui.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.accessorydm.ui.dialog.XUIDialogContract;
import com.accessorydm.ui.dialog.model.buttonstrategy.ButtonStrategy;
import com.samsung.android.fotaprovider.log.Log;

public class XUIDialogFragment extends DialogFragment implements XUIDialogContract.View {
    private XUIDialogContract.Presenter presenter;

    static /* synthetic */ boolean lambda$blockKeyEvents$1(int i, DialogInterface dialogInterface, int i2, KeyEvent keyEvent) {
        return i2 == i;
    }

    public static XUIDialogFragment newInstance(int i) {
        XUIDialogFragment xUIDialogFragment = new XUIDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", i);
        xUIDialogFragment.setArguments(bundle);
        return xUIDialogFragment;
    }

    @Override // androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle bundle) {
        Log.I("");
        if (getActivity() != null) {
            return new AlertDialog.Builder(getActivity()).create();
        }
        return super.onCreateDialog(bundle);
    }

    @Override // androidx.fragment.app.Fragment, androidx.fragment.app.DialogFragment
    public void onActivityCreated(Bundle bundle) {
        Log.I("");
        if (getArguments() != null) {
            this.presenter = new XUIDialogPresenter(this, XUIDialog.valueOf(getArguments().getInt("id")));
            this.presenter.onCreate();
        }
        super.onActivityCreated(bundle);
    }

    @Override // com.accessorydm.ui.dialog.XUIDialogContract.View
    public void setDialogTitle(String str) {
        if (getDialog() != null && str != null) {
            getDialog().setTitle(str);
        }
    }

    @Override // com.accessorydm.ui.dialog.XUIDialogContract.View
    public void setDialogBody(String str) {
        if (getDialog() != null && str != null) {
            ((AlertDialog) getDialog()).setMessage(str);
        }
    }

    @Override // com.accessorydm.ui.dialog.XUIDialogContract.View
    public void setButton(ButtonStrategy buttonStrategy) {
        if (getDialog() != null && buttonStrategy != ButtonStrategy.NONE) {
            ((AlertDialog) getDialog()).setButton(buttonStrategy.getId(), buttonStrategy.getText(), new DialogInterface.OnClickListener() {
                /* class com.accessorydm.ui.dialog.$$Lambda$XUIDialogFragment$A1Myc49PvinWK2tA17Sp_4IDI_A */

                public final void onClick(DialogInterface dialogInterface, int i) {
                    ButtonStrategy.this.onClick();
                }
            });
        }
    }

    @Override // com.accessorydm.ui.dialog.XUIDialogContract.View
    public void setGravity(int i) {
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setGravity(i);
        }
    }

    @Override // com.accessorydm.ui.dialog.XUIDialogContract.View
    public void blockKeyEvents(int[] iArr) {
        if (!(getDialog() == null || iArr == null)) {
            for (int i : iArr) {
                getDialog().setOnKeyListener(new DialogInterface.OnKeyListener(i) {
                    /* class com.accessorydm.ui.dialog.$$Lambda$XUIDialogFragment$zEyxC8ttFrFLg6WG7Z6t2MxUVI */
                    private final /* synthetic */ int f$0;

                    {
                        this.f$0 = r1;
                    }

                    public final boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                        return XUIDialogFragment.lambda$blockKeyEvents$1(this.f$0, dialogInterface, i, keyEvent);
                    }
                });
            }
        }
    }

    @Override // com.accessorydm.ui.dialog.XUIDialogContract.View, androidx.fragment.app.DialogFragment
    public void setCancelable(boolean z) {
        if (getDialog() != null) {
            getDialog().setCanceledOnTouchOutside(z);
            getDialog().setCancelable(z);
        }
    }

    @Override // androidx.fragment.app.DialogFragment
    public void onDismiss(DialogInterface dialogInterface) {
        Log.I("");
        XUIDialogContract.Presenter presenter2 = this.presenter;
        if (presenter2 != null) {
            presenter2.onDismiss();
        }
        finish();
        super.onDismiss(dialogInterface);
    }

    @Override // androidx.fragment.app.DialogFragment
    public void onCancel(DialogInterface dialogInterface) {
        Log.I("");
        super.onCancel(dialogInterface);
    }

    private void finish() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }
}
