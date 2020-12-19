package androidx.appcompat.app;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListAdapter;
import androidx.appcompat.app.AlertController;

public class k extends z implements DialogInterface {

    /* renamed from: c  reason: collision with root package name */
    final AlertController f188c = new AlertController(getContext(), this, getWindow());

    public static class a {

        /* renamed from: a  reason: collision with root package name */
        private final AlertController.a f189a;

        /* renamed from: b  reason: collision with root package name */
        private final int f190b;

        public a(Context context) {
            this(context, k.a(context, 0));
        }

        public a(Context context, int i) {
            this.f189a = new AlertController.a(new ContextThemeWrapper(context, k.a(context, i)));
            this.f190b = i;
        }

        public a a(DialogInterface.OnKeyListener onKeyListener) {
            this.f189a.u = onKeyListener;
            return this;
        }

        public a a(Drawable drawable) {
            this.f189a.f114d = drawable;
            return this;
        }

        public a a(View view) {
            this.f189a.g = view;
            return this;
        }

        public a a(ListAdapter listAdapter, DialogInterface.OnClickListener onClickListener) {
            AlertController.a aVar = this.f189a;
            aVar.w = listAdapter;
            aVar.x = onClickListener;
            return this;
        }

        public a a(CharSequence charSequence) {
            this.f189a.f = charSequence;
            return this;
        }

        public k a() {
            k kVar = new k(this.f189a.f111a, this.f190b);
            this.f189a.a(kVar.f188c);
            kVar.setCancelable(this.f189a.r);
            if (this.f189a.r) {
                kVar.setCanceledOnTouchOutside(true);
            }
            kVar.setOnCancelListener(this.f189a.s);
            kVar.setOnDismissListener(this.f189a.t);
            DialogInterface.OnKeyListener onKeyListener = this.f189a.u;
            if (onKeyListener != null) {
                kVar.setOnKeyListener(onKeyListener);
            }
            return kVar;
        }

        public Context b() {
            return this.f189a.f111a;
        }

        public a b(View view) {
            AlertController.a aVar = this.f189a;
            aVar.z = view;
            aVar.y = 0;
            aVar.E = false;
            return this;
        }
    }

    protected k(Context context, int i) {
        super(context, a(context, i));
    }

    static int a(Context context, int i) {
        if (((i >>> 24) & 255) >= 1) {
            return i;
        }
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(b.a.a.alertDialogTheme, typedValue, true);
        return typedValue.resourceId;
    }

    /* access modifiers changed from: protected */
    @Override // androidx.appcompat.app.z
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.f188c.a();
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (this.f188c.a(i, keyEvent)) {
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (this.f188c.b(i, keyEvent)) {
            return true;
        }
        return super.onKeyUp(i, keyEvent);
    }

    @Override // android.app.Dialog, androidx.appcompat.app.z
    public void setTitle(CharSequence charSequence) {
        super.setTitle(charSequence);
        this.f188c.b(charSequence);
    }
}
