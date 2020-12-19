package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.appcompat.view.menu.w;
import androidx.appcompat.widget.ia;
import b.a.a;
import b.a.f;
import b.a.g;
import b.a.j;
import b.e.g.t;

public class ListMenuItemView extends LinearLayout implements w.a, AbsListView.SelectionBoundsAdjuster {

    /* renamed from: a  reason: collision with root package name */
    private p f214a;

    /* renamed from: b  reason: collision with root package name */
    private ImageView f215b;

    /* renamed from: c  reason: collision with root package name */
    private RadioButton f216c;

    /* renamed from: d  reason: collision with root package name */
    private TextView f217d;
    private CheckBox e;
    private TextView f;
    private ImageView g;
    private ImageView h;
    private LinearLayout i;
    private Drawable j;
    private int k;
    private Context l;
    private boolean m;
    private Drawable n;
    private boolean o;
    private int p;
    private LayoutInflater q;
    private boolean r;

    public ListMenuItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, a.listMenuViewStyle);
    }

    public ListMenuItemView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet);
        ia a2 = ia.a(getContext(), attributeSet, j.MenuView, i2, 0);
        this.j = a2.b(j.MenuView_android_itemBackground);
        this.k = a2.g(j.MenuView_android_itemTextAppearance, -1);
        this.m = a2.a(j.MenuView_preserveIconSpacing, false);
        this.l = context;
        this.n = a2.b(j.MenuView_subMenuArrow);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(null, new int[]{16843049}, a.dropDownListViewStyle, 0);
        this.o = obtainStyledAttributes.hasValue(0);
        a2.a();
        obtainStyledAttributes.recycle();
    }

    private void a() {
        this.e = (CheckBox) getInflater().inflate(g.abc_list_menu_item_checkbox, (ViewGroup) this, false);
        a(this.e);
    }

    private void a(View view) {
        a(view, -1);
    }

    private void a(View view, int i2) {
        LinearLayout linearLayout = this.i;
        if (linearLayout != null) {
            linearLayout.addView(view, i2);
        } else {
            addView(view, i2);
        }
    }

    private void b() {
        this.f215b = (ImageView) getInflater().inflate(g.abc_list_menu_item_icon, (ViewGroup) this, false);
        a(this.f215b, 0);
    }

    private void d() {
        this.f216c = (RadioButton) getInflater().inflate(g.abc_list_menu_item_radio, (ViewGroup) this, false);
        a(this.f216c);
    }

    private LayoutInflater getInflater() {
        if (this.q == null) {
            this.q = LayoutInflater.from(getContext());
        }
        return this.q;
    }

    private void setSubMenuArrowVisible(boolean z) {
        ImageView imageView = this.g;
        if (imageView != null) {
            imageView.setVisibility(z ? 0 : 8);
        }
    }

    @Override // androidx.appcompat.view.menu.w.a
    public void a(p pVar, int i2) {
        this.f214a = pVar;
        this.p = i2;
        setVisibility(pVar.isVisible() ? 0 : 8);
        setTitle(pVar.a(this));
        setCheckable(pVar.isCheckable());
        setShortcut(pVar.m(), pVar.d());
        setIcon(pVar.getIcon());
        setEnabled(pVar.isEnabled());
        setSubMenuArrowVisible(pVar.hasSubMenu());
        setContentDescription(pVar.getContentDescription());
    }

    public void adjustListItemSelectionBounds(Rect rect) {
        ImageView imageView = this.h;
        if (imageView != null && imageView.getVisibility() == 0) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.h.getLayoutParams();
            rect.top += this.h.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
        }
    }

    @Override // androidx.appcompat.view.menu.w.a
    public boolean c() {
        return false;
    }

    @Override // androidx.appcompat.view.menu.w.a
    public p getItemData() {
        return this.f214a;
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        t.a(this, this.j);
        this.f217d = (TextView) findViewById(f.title);
        int i2 = this.k;
        if (i2 != -1) {
            this.f217d.setTextAppearance(this.l, i2);
        }
        this.f = (TextView) findViewById(f.shortcut);
        this.g = (ImageView) findViewById(f.submenuarrow);
        ImageView imageView = this.g;
        if (imageView != null) {
            imageView.setImageDrawable(this.n);
        }
        this.h = (ImageView) findViewById(f.group_divider);
        this.i = (LinearLayout) findViewById(f.content);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        if (this.f215b != null && this.m) {
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.f215b.getLayoutParams();
            if (layoutParams.height > 0 && layoutParams2.width <= 0) {
                layoutParams2.width = layoutParams.height;
            }
        }
        super.onMeasure(i2, i3);
    }

    public void setCheckable(boolean z) {
        CompoundButton compoundButton;
        CompoundButton compoundButton2;
        if (z || this.f216c != null || this.e != null) {
            if (this.f214a.i()) {
                if (this.f216c == null) {
                    d();
                }
                compoundButton2 = this.f216c;
                compoundButton = this.e;
            } else {
                if (this.e == null) {
                    a();
                }
                compoundButton2 = this.e;
                compoundButton = this.f216c;
            }
            if (z) {
                compoundButton2.setChecked(this.f214a.isChecked());
                if (compoundButton2.getVisibility() != 0) {
                    compoundButton2.setVisibility(0);
                }
                if (compoundButton != null && compoundButton.getVisibility() != 8) {
                    compoundButton.setVisibility(8);
                    return;
                }
                return;
            }
            CheckBox checkBox = this.e;
            if (checkBox != null) {
                checkBox.setVisibility(8);
            }
            RadioButton radioButton = this.f216c;
            if (radioButton != null) {
                radioButton.setVisibility(8);
            }
        }
    }

    public void setChecked(boolean z) {
        CompoundButton compoundButton;
        if (this.f214a.i()) {
            if (this.f216c == null) {
                d();
            }
            compoundButton = this.f216c;
        } else {
            if (this.e == null) {
                a();
            }
            compoundButton = this.e;
        }
        compoundButton.setChecked(z);
    }

    public void setForceShowIcon(boolean z) {
        this.r = z;
        this.m = z;
    }

    public void setGroupDividerEnabled(boolean z) {
        ImageView imageView = this.h;
        if (imageView != null) {
            imageView.setVisibility((this.o || !z) ? 8 : 0);
        }
    }

    public void setIcon(Drawable drawable) {
        boolean z = this.f214a.l() || this.r;
        if (!z && !this.m) {
            return;
        }
        if (this.f215b != null || drawable != null || this.m) {
            if (this.f215b == null) {
                b();
            }
            if (drawable != null || this.m) {
                ImageView imageView = this.f215b;
                if (!z) {
                    drawable = null;
                }
                imageView.setImageDrawable(drawable);
                if (this.f215b.getVisibility() != 0) {
                    this.f215b.setVisibility(0);
                    return;
                }
                return;
            }
            this.f215b.setVisibility(8);
        }
    }

    public void setShortcut(boolean z, char c2) {
        int i2 = (!z || !this.f214a.m()) ? 8 : 0;
        if (i2 == 0) {
            this.f.setText(this.f214a.e());
        }
        if (this.f.getVisibility() != i2) {
            this.f.setVisibility(i2);
        }
    }

    public void setTitle(CharSequence charSequence) {
        TextView textView;
        int i2;
        if (charSequence != null) {
            this.f217d.setText(charSequence);
            if (this.f217d.getVisibility() != 0) {
                textView = this.f217d;
                i2 = 0;
            } else {
                return;
            }
        } else {
            i2 = 8;
            if (this.f217d.getVisibility() != 8) {
                textView = this.f217d;
            } else {
                return;
            }
        }
        textView.setVisibility(i2);
    }
}
