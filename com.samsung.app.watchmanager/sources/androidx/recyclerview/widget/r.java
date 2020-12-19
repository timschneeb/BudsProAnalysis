package androidx.recyclerview.widget;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* access modifiers changed from: package-private */
public class r {

    /* renamed from: a  reason: collision with root package name */
    boolean f1117a = true;

    /* renamed from: b  reason: collision with root package name */
    int f1118b;

    /* renamed from: c  reason: collision with root package name */
    int f1119c;

    /* renamed from: d  reason: collision with root package name */
    int f1120d;
    int e;
    int f = 0;
    int g = 0;
    boolean h;
    boolean i;

    r() {
    }

    /* access modifiers changed from: package-private */
    public View a(RecyclerView.o oVar) {
        View d2 = oVar.d(this.f1119c);
        this.f1119c += this.f1120d;
        return d2;
    }

    /* access modifiers changed from: package-private */
    public boolean a(RecyclerView.s sVar) {
        int i2 = this.f1119c;
        return i2 >= 0 && i2 < sVar.a();
    }

    public String toString() {
        return "LayoutState{mAvailable=" + this.f1118b + ", mCurrentPosition=" + this.f1119c + ", mItemDirection=" + this.f1120d + ", mLayoutDirection=" + this.e + ", mStartLine=" + this.f + ", mEndLine=" + this.g + '}';
    }
}
