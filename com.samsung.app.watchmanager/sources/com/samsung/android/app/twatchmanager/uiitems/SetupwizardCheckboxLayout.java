package com.samsung.android.app.twatchmanager.uiitems;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.samsung.android.app.watchmanager.R;

public class SetupwizardCheckboxLayout extends LinearLayout {
    public static final String TAG = "SetupwizardCheckboxLayout";
    private CheckBox mCheckbox;
    private TextView mDescText;
    private boolean mDescTextVisible = false;
    private TextView mLearnMoreButton;
    private boolean mLearnMoreVisible = false;
    private LinearLayout mLinkLayout;
    private RelativeLayout mTitleLayout;
    private TextView mTitleText;

    public SetupwizardCheckboxLayout(Context context) {
        super(context);
        initView(context);
    }

    public SetupwizardCheckboxLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
        getAttrs(attributeSet);
    }

    public SetupwizardCheckboxLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet);
        initView(context);
        getAttrs(attributeSet, i);
    }

    private void getAttrs(AttributeSet attributeSet) {
        setTypeArray(getContext().obtainStyledAttributes(attributeSet, R.styleable.setupwizard_checkbox_attr));
    }

    private void getAttrs(AttributeSet attributeSet, int i) {
        setTypeArray(getContext().obtainStyledAttributes(attributeSet, R.styleable.setupwizard_checkbox_attr, i, 0));
    }

    private void initView(Context context) {
        addView(((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.setupwizard_checkbox_layout, (ViewGroup) this, false));
        this.mTitleLayout = (RelativeLayout) findViewById(R.id.item_title_layout);
        this.mCheckbox = (CheckBox) findViewById(R.id.item_checkbox);
        this.mTitleText = (TextView) findViewById(R.id.item_title_text);
        this.mDescText = (TextView) findViewById(R.id.item_desc_text);
        this.mLearnMoreButton = (TextView) findViewById(R.id.item_learnmore_button);
        this.mLinkLayout = (LinearLayout) findViewById(R.id.item_link_layout);
        TextView textView = this.mLearnMoreButton;
        textView.setPaintFlags(textView.getPaintFlags() | 8);
    }

    private void setTypeArray(TypedArray typedArray) {
        setTitleText(typedArray.getString(4));
        setDescText(typedArray.getString(0));
        setVisible(typedArray.getBoolean(5, true));
        this.mDescTextVisible = typedArray.getBoolean(1, false);
        int i = 8;
        this.mDescText.setVisibility(this.mDescTextVisible ? 0 : 8);
        this.mLearnMoreVisible = typedArray.getBoolean(2, false);
        TextView textView = this.mLearnMoreButton;
        if (this.mLearnMoreVisible) {
            i = 0;
        }
        textView.setVisibility(i);
        this.mCheckbox.setChecked(typedArray.getBoolean(3, false));
        typedArray.recycle();
    }

    public CheckBox getCheckbox() {
        return this.mCheckbox;
    }

    public LinearLayout getLinkAreaLayout() {
        return this.mLinkLayout;
    }

    public boolean isChecked() {
        return this.mCheckbox.isChecked();
    }

    public void setChecked(boolean z) {
        this.mCheckbox.setChecked(z);
    }

    public void setDescText(String str) {
        if (TextUtils.isEmpty(str)) {
            this.mDescText.setText("");
        } else {
            this.mDescText.setText(str);
        }
    }

    public void setEnabled(boolean z) {
        super.setEnabled(z);
        this.mTitleLayout.setClickable(z);
        this.mTitleLayout.setFocusable(z);
        this.mLearnMoreButton.setClickable(z);
        this.mLearnMoreButton.setFocusable(z);
    }

    public void setOnCheckboxChanged(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.mCheckbox.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    public void setOnClickCheckBoxListener(View.OnClickListener onClickListener) {
        this.mCheckbox.setOnClickListener(onClickListener);
    }

    public void setOnClickLearnMoreButton(View.OnClickListener onClickListener) {
        this.mLearnMoreButton.setOnClickListener(onClickListener);
    }

    public void setOnClickTitleLayout(View.OnClickListener onClickListener) {
        this.mTitleLayout.setOnClickListener(onClickListener);
    }

    public void setSubCheckBoxText(int i, View.OnClickListener onClickListener) {
        this.mTitleLayout.setClickable(false);
        this.mTitleLayout.setFocusable(false);
        this.mTitleText.setText(i);
        this.mTitleText.setTextColor(getResources().getColor(R.color.learn_more_link_color));
        this.mTitleText.setTypeface(Typeface.create("sec-roboto-regular", 0));
        this.mTitleText.setOnClickListener(onClickListener);
        this.mTitleText.setFocusable(true);
        this.mTitleText.setClickable(true);
        TextView textView = this.mTitleText;
        textView.setPaintFlags(textView.getPaintFlags() | 8);
        this.mTitleText.setTextSize(0, getResources().getDimension(R.dimen.list_second_line_desc_text_size));
        this.mTitleText.setBackgroundResource(R.xml.xml_listitem_selector);
    }

    public void setTitleText(String str) {
        if (TextUtils.isEmpty(str)) {
            this.mTitleText.setText("");
        } else {
            this.mTitleText.setText(str);
        }
    }

    public void setVisible(boolean z) {
        setVisibility(z ? 0 : 8);
    }
}
