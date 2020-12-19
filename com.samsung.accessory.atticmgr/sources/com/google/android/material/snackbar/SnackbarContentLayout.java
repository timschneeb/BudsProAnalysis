package com.google.android.material.snackbar;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import com.google.android.material.color.MaterialColors;

public class SnackbarContentLayout extends LinearLayout implements ContentViewCallback {
    private static final boolean WIDGET_ONEUI_SNACKBAR = true;
    private Button actionView;
    private int mLastOrientation;
    private SnackbarContentLayout mSnackBarLayout;
    private int mWidthWtihAction;
    private int maxInlineActionWidth;
    private int maxWidth;
    private TextView messageView;

    public SnackbarContentLayout(Context context) {
        this(context, null);
    }

    public SnackbarContentLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mLastOrientation = 0;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SnackbarLayout);
        this.maxWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.SnackbarLayout_android_maxWidth, -1);
        this.maxInlineActionWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.SnackbarLayout_maxActionInlineWidth, -1);
        obtainStyledAttributes.recycle();
        this.mWidthWtihAction = (int) context.getResources().getFraction(R.dimen.sesl_config_prefSnackWidth, context.getResources().getDisplayMetrics().widthPixels, context.getResources().getDisplayMetrics().widthPixels);
        this.maxWidth = this.mWidthWtihAction;
        this.mSnackBarLayout = (SnackbarContentLayout) findViewById(R.id.snackbar_layout);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.messageView = (TextView) findViewById(R.id.snackbar_text);
        this.actionView = (Button) findViewById(R.id.snackbar_action);
    }

    public TextView getMessageView() {
        return this.messageView;
    }

    public Button getActionView() {
        return this.actionView;
    }

    /* access modifiers changed from: package-private */
    public void updateActionTextColorAlphaIfNeeded(float f) {
        if (f != 1.0f) {
            this.actionView.setTextColor(MaterialColors.layer(MaterialColors.getColor(this, R.attr.colorSurface), this.actionView.getCurrentTextColor(), f));
        }
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.mLastOrientation != configuration.orientation) {
            this.mWidthWtihAction = (int) getContext().getResources().getFraction(R.dimen.sesl_config_prefSnackWidth, getContext().getResources().getDisplayMetrics().widthPixels, getContext().getResources().getDisplayMetrics().widthPixels);
            this.maxWidth = this.mWidthWtihAction;
            this.mLastOrientation = configuration.orientation;
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int i3;
        super.onMeasure(i, i2);
        if (this.actionView.getVisibility() == 0) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(this.mWidthWtihAction, 1073741824), i2);
        } else if (this.maxWidth > 0 && getMeasuredWidth() > (i3 = this.maxWidth)) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(i3, 1073741824), i2);
        }
        getResources().getDimensionPixelSize(R.dimen.design_snackbar_padding_vertical_2lines);
        getResources().getDimensionPixelSize(R.dimen.design_snackbar_padding_vertical);
        boolean z = this.messageView.getLayout().getLineCount() > 1;
        float paddingLeft = (float) (this.mSnackBarLayout.getPaddingLeft() + this.mSnackBarLayout.getPaddingRight() + this.messageView.getMeasuredWidth() + this.actionView.getMeasuredWidth());
        if (this.maxInlineActionWidth != -1 || this.actionView.getVisibility() != 0) {
            return;
        }
        if (paddingLeft > ((float) this.mWidthWtihAction) || z) {
            this.mSnackBarLayout.setOrientation(1);
            this.actionView.setPadding(getResources().getDimensionPixelSize(R.dimen.sesl_design_snackbar_action_padding_left), getResources().getDimensionPixelSize(R.dimen.sesl_design_snackbar_action_padding_top), getResources().getDimensionPixelSize(R.dimen.sesl_design_snackbar_action_padding_right), 0);
            return;
        }
        this.mSnackBarLayout.setOrientation(0);
        this.actionView.setPadding(getResources().getDimensionPixelSize(R.dimen.sesl_design_snackbar_action_padding_left), 0, getResources().getDimensionPixelSize(R.dimen.sesl_design_snackbar_action_padding_right), 0);
    }

    private boolean updateViewsWithinLayout(int i, int i2, int i3) {
        boolean z;
        if (i != getOrientation()) {
            setOrientation(i);
            z = true;
        } else {
            z = false;
        }
        if (this.messageView.getPaddingTop() == i2 && this.messageView.getPaddingBottom() == i3) {
            return z;
        }
        updateTopBottomPadding(this.messageView, i2, i3);
        return true;
    }

    private static void updateTopBottomPadding(View view, int i, int i2) {
        if (ViewCompat.isPaddingRelative(view)) {
            ViewCompat.setPaddingRelative(view, ViewCompat.getPaddingStart(view), i, ViewCompat.getPaddingEnd(view), i2);
        } else {
            view.setPadding(view.getPaddingLeft(), i, view.getPaddingRight(), i2);
        }
    }

    @Override // com.google.android.material.snackbar.ContentViewCallback
    public void animateContentIn(int i, int i2) {
        this.messageView.setAlpha(0.0f);
        long j = (long) i2;
        long j2 = (long) i;
        this.messageView.animate().alpha(1.0f).setDuration(j).setStartDelay(j2).start();
        if (this.actionView.getVisibility() == 0) {
            this.actionView.setAlpha(0.0f);
            this.actionView.animate().alpha(1.0f).setDuration(j).setStartDelay(j2).start();
        }
    }

    @Override // com.google.android.material.snackbar.ContentViewCallback
    public void animateContentOut(int i, int i2) {
        this.messageView.setAlpha(1.0f);
        long j = (long) i2;
        long j2 = (long) i;
        this.messageView.animate().alpha(0.0f).setDuration(j).setStartDelay(j2).start();
        if (this.actionView.getVisibility() == 0) {
            this.actionView.setAlpha(1.0f);
            this.actionView.animate().alpha(0.0f).setDuration(j).setStartDelay(j2).start();
        }
    }
}
