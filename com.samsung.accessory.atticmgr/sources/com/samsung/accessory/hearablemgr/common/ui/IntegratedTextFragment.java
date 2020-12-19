package com.samsung.accessory.hearablemgr.common.ui;

import android.os.Bundle;

public class IntegratedTextFragment extends LayoutResourceFragment {
    public IntegratedTextFragment(int i) {
        super(i);
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        if (getView() != null) {
            getView().setContentDescription(UiUtil.getAllTextWithChildView(getView()));
        }
    }
}
