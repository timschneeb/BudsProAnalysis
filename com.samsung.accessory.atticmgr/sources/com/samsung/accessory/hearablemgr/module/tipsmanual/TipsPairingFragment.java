package com.samsung.accessory.hearablemgr.module.tipsmanual;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.common.ui.LayoutResourceFragment;
import com.samsung.accessory.hearablemgr.common.ui.UiUtil;
import com.samsung.accessory.hearablemgr.common.ui.VIView;
import com.samsung.accessory.hearablemgr.module.tipsmanual.TipsAndUserManualActivity;

public class TipsPairingFragment extends LayoutResourceFragment implements TipsAndUserManualActivity.OnSelectedTipsFragment {
    private VIView mVIView;
    private final int quantity = 3;

    public TipsPairingFragment(int i) {
        super(i);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        ((TextView) view.findViewById(R.id.tips_pair_with_a_phone_or_tablet_desc)).setText(getResources().getQuantityString(R.plurals.tips_pair_with_a_phone_or_tablet_desc, 3, 3));
        this.mVIView = (VIView) view.findViewById(R.id.vi_view);
        this.mVIView.stop();
        if (getView() != null) {
            getView().setContentDescription(UiUtil.getAllTextWithChildView(getView()));
        }
    }

    @Override // com.samsung.accessory.hearablemgr.module.tipsmanual.TipsAndUserManualActivity.OnSelectedTipsFragment
    public void onSelected(boolean z) {
        VIView vIView = this.mVIView;
        if (vIView != null) {
            if (z) {
                vIView.start();
            } else {
                vIView.reset();
            }
        }
    }
}
