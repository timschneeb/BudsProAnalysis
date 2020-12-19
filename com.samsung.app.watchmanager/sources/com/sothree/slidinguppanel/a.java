package com.sothree.slidinguppanel;

import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;
import androidx.recyclerview.widget.RecyclerView;

public class a {
    public int a(View view, boolean z) {
        if (view == null) {
            return 0;
        }
        if (!(view instanceof ScrollView)) {
            if (view instanceof ListView) {
                ListView listView = (ListView) view;
                if (listView.getChildCount() > 0) {
                    if (listView.getAdapter() == null) {
                        return 0;
                    }
                    if (z) {
                        View childAt = listView.getChildAt(0);
                        return (listView.getFirstVisiblePosition() * childAt.getHeight()) - childAt.getTop();
                    }
                    View childAt2 = listView.getChildAt(listView.getChildCount() - 1);
                    return ((((listView.getAdapter().getCount() - listView.getLastVisiblePosition()) - 1) * childAt2.getHeight()) + childAt2.getBottom()) - listView.getBottom();
                }
            }
            if (!(view instanceof RecyclerView)) {
                return 0;
            }
            RecyclerView recyclerView = (RecyclerView) view;
            if (recyclerView.getChildCount() <= 0) {
                return 0;
            }
            RecyclerView.i layoutManager = recyclerView.getLayoutManager();
            if (recyclerView.getAdapter() == null) {
                return 0;
            }
            if (z) {
                View childAt3 = recyclerView.getChildAt(0);
                return (recyclerView.g(childAt3) * layoutManager.getDecoratedMeasuredHeight(childAt3)) - layoutManager.getDecoratedTop(childAt3);
            }
            View childAt4 = recyclerView.getChildAt(recyclerView.getChildCount() - 1);
            return (((recyclerView.getAdapter().getItemCount() - 1) * layoutManager.getDecoratedMeasuredHeight(childAt4)) + layoutManager.getDecoratedBottom(childAt4)) - recyclerView.getBottom();
        } else if (z) {
            return view.getScrollY();
        } else {
            ScrollView scrollView = (ScrollView) view;
            return scrollView.getChildAt(0).getBottom() - (scrollView.getHeight() + scrollView.getScrollY());
        }
    }
}
