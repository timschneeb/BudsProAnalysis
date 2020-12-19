package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.ActionProvider;
import android.view.CollapsibleActionView;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.FrameLayout;
import b.e.g.AbstractC0112b;
import java.lang.reflect.Method;

public class q extends AbstractC0053c<b.e.b.a.b> implements MenuItem {
    private Method e;

    /* access modifiers changed from: package-private */
    public class a extends AbstractC0112b {

        /* renamed from: d  reason: collision with root package name */
        final ActionProvider f266d;

        public a(Context context, ActionProvider actionProvider) {
            super(context);
            this.f266d = actionProvider;
        }

        @Override // b.e.g.AbstractC0112b
        public void a(SubMenu subMenu) {
            this.f266d.onPrepareSubMenu(q.this.a(subMenu));
        }

        @Override // b.e.g.AbstractC0112b
        public boolean a() {
            return this.f266d.hasSubMenu();
        }

        @Override // b.e.g.AbstractC0112b
        public View c() {
            return this.f266d.onCreateActionView();
        }

        @Override // b.e.g.AbstractC0112b
        public boolean d() {
            return this.f266d.onPerformDefaultAction();
        }
    }

    static class b extends FrameLayout implements b.a.d.c {

        /* renamed from: a  reason: collision with root package name */
        final CollapsibleActionView f267a;

        b(View view) {
            super(view.getContext());
            this.f267a = (CollapsibleActionView) view;
            addView(view);
        }

        /* access modifiers changed from: package-private */
        public View a() {
            return (View) this.f267a;
        }

        @Override // b.a.d.c
        public void onActionViewCollapsed() {
            this.f267a.onActionViewCollapsed();
        }

        @Override // b.a.d.c
        public void onActionViewExpanded() {
            this.f267a.onActionViewExpanded();
        }
    }

    private class c extends C0054d<MenuItem.OnActionExpandListener> implements MenuItem.OnActionExpandListener {
        c(MenuItem.OnActionExpandListener onActionExpandListener) {
            super(onActionExpandListener);
        }

        public boolean onMenuItemActionCollapse(MenuItem menuItem) {
            return this.f229a.onMenuItemActionCollapse(q.this.a(menuItem));
        }

        public boolean onMenuItemActionExpand(MenuItem menuItem) {
            return this.f229a.onMenuItemActionExpand(q.this.a(menuItem));
        }
    }

    private class d extends C0054d<MenuItem.OnMenuItemClickListener> implements MenuItem.OnMenuItemClickListener {
        d(MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
            super(onMenuItemClickListener);
        }

        public boolean onMenuItemClick(MenuItem menuItem) {
            return this.f229a.onMenuItemClick(q.this.a(menuItem));
        }
    }

    q(Context context, b.e.b.a.b bVar) {
        super(context, bVar);
    }

    /* access modifiers changed from: package-private */
    public a a(ActionProvider actionProvider) {
        return new a(this.f226b, actionProvider);
    }

    public void a(boolean z) {
        try {
            if (this.e == null) {
                this.e = this.f229a.getClass().getDeclaredMethod("setExclusiveCheckable", Boolean.TYPE);
            }
            this.e.invoke(this.f229a, Boolean.valueOf(z));
        } catch (Exception e2) {
            Log.w("MenuItemWrapper", "Error while calling setExclusiveCheckable", e2);
        }
    }

    public boolean collapseActionView() {
        return this.f229a.collapseActionView();
    }

    public boolean expandActionView() {
        return this.f229a.expandActionView();
    }

    public ActionProvider getActionProvider() {
        AbstractC0112b a2 = this.f229a.a();
        if (a2 instanceof a) {
            return ((a) a2).f266d;
        }
        return null;
    }

    public View getActionView() {
        View actionView = this.f229a.getActionView();
        return actionView instanceof b ? ((b) actionView).a() : actionView;
    }

    public int getAlphabeticModifiers() {
        return this.f229a.getAlphabeticModifiers();
    }

    public char getAlphabeticShortcut() {
        return this.f229a.getAlphabeticShortcut();
    }

    public CharSequence getContentDescription() {
        return this.f229a.getContentDescription();
    }

    public int getGroupId() {
        return this.f229a.getGroupId();
    }

    public Drawable getIcon() {
        return this.f229a.getIcon();
    }

    public ColorStateList getIconTintList() {
        return this.f229a.getIconTintList();
    }

    public PorterDuff.Mode getIconTintMode() {
        return this.f229a.getIconTintMode();
    }

    public Intent getIntent() {
        return this.f229a.getIntent();
    }

    public int getItemId() {
        return this.f229a.getItemId();
    }

    public ContextMenu.ContextMenuInfo getMenuInfo() {
        return this.f229a.getMenuInfo();
    }

    public int getNumericModifiers() {
        return this.f229a.getNumericModifiers();
    }

    public char getNumericShortcut() {
        return this.f229a.getNumericShortcut();
    }

    public int getOrder() {
        return this.f229a.getOrder();
    }

    public SubMenu getSubMenu() {
        return a(this.f229a.getSubMenu());
    }

    public CharSequence getTitle() {
        return this.f229a.getTitle();
    }

    public CharSequence getTitleCondensed() {
        return this.f229a.getTitleCondensed();
    }

    public CharSequence getTooltipText() {
        return this.f229a.getTooltipText();
    }

    public boolean hasSubMenu() {
        return this.f229a.hasSubMenu();
    }

    public boolean isActionViewExpanded() {
        return this.f229a.isActionViewExpanded();
    }

    public boolean isCheckable() {
        return this.f229a.isCheckable();
    }

    public boolean isChecked() {
        return this.f229a.isChecked();
    }

    public boolean isEnabled() {
        return this.f229a.isEnabled();
    }

    public boolean isVisible() {
        return this.f229a.isVisible();
    }

    public MenuItem setActionProvider(ActionProvider actionProvider) {
        this.f229a.a(actionProvider != null ? a(actionProvider) : null);
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setActionView(int i) {
        this.f229a.setActionView(i);
        View actionView = this.f229a.getActionView();
        if (actionView instanceof CollapsibleActionView) {
            this.f229a.setActionView(new b(actionView));
        }
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setActionView(View view) {
        if (view instanceof CollapsibleActionView) {
            view = new b(view);
        }
        this.f229a.setActionView(view);
        return this;
    }

    public MenuItem setAlphabeticShortcut(char c2) {
        this.f229a.setAlphabeticShortcut(c2);
        return this;
    }

    public MenuItem setAlphabeticShortcut(char c2, int i) {
        this.f229a.setAlphabeticShortcut(c2, i);
        return this;
    }

    public MenuItem setCheckable(boolean z) {
        this.f229a.setCheckable(z);
        return this;
    }

    public MenuItem setChecked(boolean z) {
        this.f229a.setChecked(z);
        return this;
    }

    public MenuItem setContentDescription(CharSequence charSequence) {
        this.f229a.setContentDescription(charSequence);
        return this;
    }

    public MenuItem setEnabled(boolean z) {
        this.f229a.setEnabled(z);
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setIcon(int i) {
        this.f229a.setIcon(i);
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setIcon(Drawable drawable) {
        this.f229a.setIcon(drawable);
        return this;
    }

    public MenuItem setIconTintList(ColorStateList colorStateList) {
        this.f229a.setIconTintList(colorStateList);
        return this;
    }

    public MenuItem setIconTintMode(PorterDuff.Mode mode) {
        this.f229a.setIconTintMode(mode);
        return this;
    }

    public MenuItem setIntent(Intent intent) {
        this.f229a.setIntent(intent);
        return this;
    }

    public MenuItem setNumericShortcut(char c2) {
        this.f229a.setNumericShortcut(c2);
        return this;
    }

    public MenuItem setNumericShortcut(char c2, int i) {
        this.f229a.setNumericShortcut(c2, i);
        return this;
    }

    public MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener onActionExpandListener) {
        this.f229a.setOnActionExpandListener(onActionExpandListener != null ? new c(onActionExpandListener) : null);
        return this;
    }

    public MenuItem setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
        this.f229a.setOnMenuItemClickListener(onMenuItemClickListener != null ? new d(onMenuItemClickListener) : null);
        return this;
    }

    public MenuItem setShortcut(char c2, char c3) {
        this.f229a.setShortcut(c2, c3);
        return this;
    }

    public MenuItem setShortcut(char c2, char c3, int i, int i2) {
        this.f229a.setShortcut(c2, c3, i, i2);
        return this;
    }

    public void setShowAsAction(int i) {
        this.f229a.setShowAsAction(i);
    }

    public MenuItem setShowAsActionFlags(int i) {
        this.f229a.setShowAsActionFlags(i);
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setTitle(int i) {
        this.f229a.setTitle(i);
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setTitle(CharSequence charSequence) {
        this.f229a.setTitle(charSequence);
        return this;
    }

    public MenuItem setTitleCondensed(CharSequence charSequence) {
        this.f229a.setTitleCondensed(charSequence);
        return this;
    }

    public MenuItem setTooltipText(CharSequence charSequence) {
        this.f229a.setTooltipText(charSequence);
        return this;
    }

    public MenuItem setVisible(boolean z) {
        return this.f229a.setVisible(z);
    }
}
