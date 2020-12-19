package androidx.recyclerview.widget;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* access modifiers changed from: package-private */
public class I {
    static int a(RecyclerView.s sVar, x xVar, View view, View view2, RecyclerView.i iVar, boolean z) {
        if (iVar.getChildCount() == 0 || sVar.a() == 0 || view == null || view2 == null) {
            return 0;
        }
        if (!z) {
            return Math.abs(iVar.getPosition(view) - iVar.getPosition(view2)) + 1;
        }
        return Math.min(xVar.g(), xVar.a(view2) - xVar.d(view));
    }

    static int a(RecyclerView.s sVar, x xVar, View view, View view2, RecyclerView.i iVar, boolean z, boolean z2) {
        if (iVar.getChildCount() == 0 || sVar.a() == 0 || view == null || view2 == null) {
            return 0;
        }
        int max = z2 ? Math.max(0, (sVar.a() - Math.max(iVar.getPosition(view), iVar.getPosition(view2))) - 1) : Math.max(0, Math.min(iVar.getPosition(view), iVar.getPosition(view2)));
        if (!z) {
            return max;
        }
        return Math.round((((float) max) * (((float) Math.abs(xVar.a(view2) - xVar.d(view))) / ((float) (Math.abs(iVar.getPosition(view) - iVar.getPosition(view2)) + 1)))) + ((float) (xVar.f() - xVar.d(view))));
    }

    static int b(RecyclerView.s sVar, x xVar, View view, View view2, RecyclerView.i iVar, boolean z) {
        if (iVar.getChildCount() == 0 || sVar.a() == 0 || view == null || view2 == null) {
            return 0;
        }
        if (!z) {
            return sVar.a();
        }
        return (int) ((((float) (xVar.a(view2) - xVar.d(view))) / ((float) (Math.abs(iVar.getPosition(view) - iVar.getPosition(view2)) + 1))) * ((float) sVar.a()));
    }
}
