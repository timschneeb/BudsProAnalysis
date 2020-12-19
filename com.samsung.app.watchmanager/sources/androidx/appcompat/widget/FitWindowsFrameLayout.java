package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import androidx.appcompat.widget.G;

public class FitWindowsFrameLayout extends FrameLayout implements G {

    /* renamed from: a  reason: collision with root package name */
    private G.a f359a;

    public FitWindowsFrameLayout(Context context) {
        super(context);
    }

    public FitWindowsFrameLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    /* access modifiers changed from: protected */
    public boolean fitSystemWindows(Rect rect) {
        G.a aVar = this.f359a;
        if (aVar != null) {
            aVar.a(rect);
        }
        return super.fitSystemWindows(rect);
    }

    @Override // androidx.appcompat.widget.G
    public void setOnFitSystemWindowsListener(G.a aVar) {
        this.f359a = aVar;
    }
}
