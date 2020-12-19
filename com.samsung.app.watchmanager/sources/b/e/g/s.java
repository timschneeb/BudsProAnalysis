package b.e.g;

import android.view.View;
import android.view.WindowInsets;

/* access modifiers changed from: package-private */
public class s implements View.OnApplyWindowInsetsListener {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ o f1426a;

    s(o oVar) {
        this.f1426a = oVar;
    }

    public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
        return (WindowInsets) D.a(this.f1426a.a(view, D.a(windowInsets)));
    }
}
