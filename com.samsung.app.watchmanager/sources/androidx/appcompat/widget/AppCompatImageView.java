package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;
import androidx.core.widget.o;
import b.e.g.q;

public class AppCompatImageView extends ImageView implements q, o {

    /* renamed from: a  reason: collision with root package name */
    private final C0070o f320a;

    /* renamed from: b  reason: collision with root package name */
    private final C0073s f321b;

    public AppCompatImageView(Context context) {
        this(context, null);
    }

    public AppCompatImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AppCompatImageView(Context context, AttributeSet attributeSet, int i) {
        super(fa.a(context), attributeSet, i);
        this.f320a = new C0070o(this);
        this.f320a.a(attributeSet, i);
        this.f321b = new C0073s(this);
        this.f321b.a(attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        C0070o oVar = this.f320a;
        if (oVar != null) {
            oVar.a();
        }
        C0073s sVar = this.f321b;
        if (sVar != null) {
            sVar.a();
        }
    }

    @Override // b.e.g.q
    public ColorStateList getSupportBackgroundTintList() {
        C0070o oVar = this.f320a;
        if (oVar != null) {
            return oVar.b();
        }
        return null;
    }

    @Override // b.e.g.q
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        C0070o oVar = this.f320a;
        if (oVar != null) {
            return oVar.c();
        }
        return null;
    }

    @Override // androidx.core.widget.o
    public ColorStateList getSupportImageTintList() {
        C0073s sVar = this.f321b;
        if (sVar != null) {
            return sVar.b();
        }
        return null;
    }

    @Override // androidx.core.widget.o
    public PorterDuff.Mode getSupportImageTintMode() {
        C0073s sVar = this.f321b;
        if (sVar != null) {
            return sVar.c();
        }
        return null;
    }

    public boolean hasOverlappingRendering() {
        return this.f321b.d() && super.hasOverlappingRendering();
    }

    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        C0070o oVar = this.f320a;
        if (oVar != null) {
            oVar.a(drawable);
        }
    }

    public void setBackgroundResource(int i) {
        super.setBackgroundResource(i);
        C0070o oVar = this.f320a;
        if (oVar != null) {
            oVar.a(i);
        }
    }

    public void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
        C0073s sVar = this.f321b;
        if (sVar != null) {
            sVar.a();
        }
    }

    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        C0073s sVar = this.f321b;
        if (sVar != null) {
            sVar.a();
        }
    }

    public void setImageResource(int i) {
        C0073s sVar = this.f321b;
        if (sVar != null) {
            sVar.a(i);
        }
    }

    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        C0073s sVar = this.f321b;
        if (sVar != null) {
            sVar.a();
        }
    }

    @Override // b.e.g.q
    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        C0070o oVar = this.f320a;
        if (oVar != null) {
            oVar.b(colorStateList);
        }
    }

    @Override // b.e.g.q
    public void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        C0070o oVar = this.f320a;
        if (oVar != null) {
            oVar.a(mode);
        }
    }

    @Override // androidx.core.widget.o
    public void setSupportImageTintList(ColorStateList colorStateList) {
        C0073s sVar = this.f321b;
        if (sVar != null) {
            sVar.a(colorStateList);
        }
    }

    @Override // androidx.core.widget.o
    public void setSupportImageTintMode(PorterDuff.Mode mode) {
        C0073s sVar = this.f321b;
        if (sVar != null) {
            sVar.a(mode);
        }
    }
}
