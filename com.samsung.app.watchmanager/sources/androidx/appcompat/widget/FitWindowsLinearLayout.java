package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import androidx.appcompat.widget.G;

public class FitWindowsLinearLayout extends LinearLayout implements G {

    /* renamed from: a  reason: collision with root package name */
    private G.a f360a;

    public FitWindowsLinearLayout(Context context) {
        super(context);
    }

    public FitWindowsLinearLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    /* access modifiers changed from: protected */
    public boolean fitSystemWindows(Rect rect) {
        G.a aVar = this.f360a;
        if (aVar != null) {
            aVar.a(rect);
        }
        return super.fitSystemWindows(rect);
    }

    @Override // androidx.appcompat.widget.G
    public void setOnFitSystemWindowsListener(G.a aVar) {
        this.f360a = aVar;
    }
}
