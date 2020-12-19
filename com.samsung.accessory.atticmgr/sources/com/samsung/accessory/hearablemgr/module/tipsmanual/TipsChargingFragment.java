package com.samsung.accessory.hearablemgr.module.tipsmanual;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.common.ui.UiUtil;

public class TipsChargingFragment extends Fragment {
    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_tips_charging, viewGroup, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        ((TextView) view.findViewById(R.id.text_tips_charging_desc_2)).setText("1. " + getResources().getString(R.string.tips_charging_with_your_phone_desc_2));
        ((TextView) view.findViewById(R.id.text_tips_charging_desc_3)).setText("2. " + getResources().getString(R.string.tips_charging_with_your_phone_desc_3));
        view.setContentDescription(UiUtil.getAllTextWithChildView(getView()));
    }
}
