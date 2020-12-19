package com.samsung.android.app.watchmanager.setupwizard;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

public class VerticalSpaceItemDecoration extends RecyclerView.h {
    private final int verticalSpaceHeight;

    public VerticalSpaceItemDecoration(int i) {
        this.verticalSpaceHeight = i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.h
    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.s sVar) {
        if (recyclerView.f(view) != recyclerView.getAdapter().getItemCount() - 1) {
            rect.bottom = this.verticalSpaceHeight;
        }
    }
}
