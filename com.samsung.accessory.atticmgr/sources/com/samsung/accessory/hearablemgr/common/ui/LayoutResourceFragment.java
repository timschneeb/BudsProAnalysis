package com.samsung.accessory.hearablemgr.common.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

public class LayoutResourceFragment extends Fragment {
    private int mLayoutResource;

    public LayoutResourceFragment(int i) {
        this.mLayoutResource = i;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(this.mLayoutResource, viewGroup, false);
    }
}
