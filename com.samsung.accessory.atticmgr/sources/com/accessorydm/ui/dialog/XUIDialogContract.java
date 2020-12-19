package com.accessorydm.ui.dialog;

import com.accessorydm.ui.dialog.model.buttonstrategy.ButtonStrategy;

public interface XUIDialogContract {

    public interface Presenter {
        void onCreate();

        void onDismiss();
    }

    public interface View {
        void blockKeyEvents(int[] iArr);

        void setButton(ButtonStrategy buttonStrategy);

        void setCancelable(boolean z);

        void setDialogBody(String str);

        void setDialogTitle(String str);

        void setGravity(int i);
    }
}
