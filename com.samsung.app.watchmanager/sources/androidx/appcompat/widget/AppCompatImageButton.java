package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageButton;
import androidx.core.widget.o;
import b.a.a;
import b.e.g.q;

public class AppCompatImageButton extends ImageButton implements q, o {

    /* renamed from: a  reason: collision with root package name */
    private final C0070o f318a;

    /* renamed from: b  reason: collision with root package name */
    private final C0073s f319b;

    public AppCompatImageButton(Context context) {
        this(context, null);
    }

    public AppCompatImageButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, a.imageButtonStyle);
    }

    public AppCompatImageButton(Context context, AttributeSet attributeSet, int i) {
        super(fa.a(context), attributeSet, i);
        this.f318a = new C0070o(this);
        this.f318a.a(attributeSet, i);
        this.f319b = new C0073s(this);
        this.f319b.a(attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        C0070o oVar = this.f318a;
        if (oVar != null) {
            oVar.a();
        }
        C0073s sVar = this.f319b;
        if (sVar != null) {
            sVar.a();
        }
    }

    @Override // b.e.g.q
    public ColorStateList getSupportBackgroundTintList() {
        C0070o oVar = this.f318a;
        if (oVar != null) {
            return oVar.b();
        }
        return null;
    }

    @Override // b.e.g.q
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        C0070o oVar = this.f318a;
        if (oVar != null) {
            return oVar.c();
        }
        return null;
    }

    @Override // androidx.core.widget.o
    public ColorStateList getSupportImageTintList() {
        C0073s sVar = this.f319b;
        if (sVar != null) {
            return sVar.b();
        }
        return null;
    }

    @Override // androidx.core.widget.o
    public PorterDuff.Mode getSupportImageTintMode() {
        C0073s sVar = this.f319b;
        if (sVar != null) {
            return sVar.c();
        }
        return null;
    }

    public boolean hasOverlappingRendering() {
        return this.f319b.d() && super.hasOverlappingRendering();
    }

    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        C0070o oVar = this.f318a;
        if (oVar != null) {
            oVar.a(drawable);
        }
    }

    public void setBackgroundResource(int i) {
        super.setBackgroundResource(i);
        C0070o oVar = this.f318a;
        if (oVar != null) {
            oVar.a(i);
        }
    }

    public void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
        C0073s sVar = this.f319b;
        if (sVar != null) {
            sVar.a();
        }
    }

    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        C0073s sVar = this.f319b;
        if (sVar != null) {
            sVar.a();
        }
    }

    public void setImageResource(int i) {
        this.f319b.a(i);
    }

    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        C0073s sVar = this.f319b;
        if (sVar != null) {
            sVar.a();
        }
    }

    @Override // b.e.g.q
    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        C0070o oVar = this.f318a;
        if (oVar != null) {
            oVar.b(colorStateList);
        }
    }

    @Override // b.e.g.q
    public void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        C0070o oVar = this.f318a;
        if (oVar != null) {
            oVar.a(mode);
        }
    }

    @Override // androidx.core.widget.o
    public void setSupportImageTintList(ColorStateList colorStateList) {
        C0073s sVar = this.f319b;
        if (sVar != null) {
            sVar.a(colorStateList);
        }
    }

    @Override // androidx.core.widget.o
    public void setSupportImageTintMode(PorterDuff.Mode mode) {
        C0073s sVar = this.f319b;
        if (sVar != null) {
            sVar.a(mode);
        }
    }
}
