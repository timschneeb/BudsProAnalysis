package com.samsung.accessory.hearablemgr.core.appwidget.base;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.core.appwidget.util.WidgetInfo;
import com.samsung.accessory.hearablemgr.core.appwidget.util.WidgetInfoManager;
import com.samsung.accessory.hearablemgr.core.appwidget.util.WidgetUtil;

public abstract class WidgetSettingBaseActivity extends AppCompatActivity {
    private SeekBar mBackgroundAlphaSeekBar;
    private TextView mBackgroundAlphaTextView;
    private TextView mCancelButton;
    private TextView mPositiveButton;
    private LinearLayout mWidgetDarkModeLayout;
    private SwitchCompat mWidgetDarkModeSwitch;
    private TextView mWidgetDarkModeText;
    private int mWidgetId;
    private WidgetInfo mWidgetInfo;
    private FrameLayout mWidgetPreview;
    private RadioButton mWidgetStyleRadioBlack;
    private RadioGroup mWidgetStyleRadioGroup;
    private RadioButton mWidgetStyleRadioWhite;

    public abstract Class getProviderClass();

    public abstract void onUpdatedAlpha(int i);

    public abstract void onUpdatedColor(int i);

    public abstract void onUpdatedDarkMode(boolean z);

    public abstract void updateWidget(int i);

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_widget_setting);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        init();
        initView();
        initListener();
    }

    private void init() {
        this.mWidgetPreview = (FrameLayout) findViewById(R.id.view_widget_preview);
        this.mWidgetStyleRadioGroup = (RadioGroup) findViewById(R.id.radio_group_widget_background_color_type);
        this.mWidgetStyleRadioWhite = (RadioButton) findViewById(R.id.radio_widget_setting_background_color_white);
        this.mWidgetStyleRadioBlack = (RadioButton) findViewById(R.id.radio_widget_setting_background_color_black);
        this.mBackgroundAlphaTextView = (TextView) findViewById(R.id.text_widget_background_alpha);
        this.mBackgroundAlphaSeekBar = (SeekBar) findViewById(R.id.seekbar_widget_background_alpha);
        this.mWidgetDarkModeLayout = (LinearLayout) findViewById(R.id.layout_widget_base_dark_mode);
        this.mWidgetDarkModeSwitch = (SwitchCompat) findViewById(R.id.switch_widget_base_dark_mode);
        this.mWidgetDarkModeText = (TextView) findViewById(R.id.text_widget_dark_mode);
        this.mCancelButton = (TextView) findViewById(R.id.btn_cancel);
        this.mPositiveButton = (TextView) findViewById(R.id.btn_ok);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.mWidgetId = extras.getInt("appWidgetId", 0);
        }
        this.mWidgetInfo = new WidgetInfoManager(this, getProviderClass()).getWidgetInfo(this.mWidgetId);
    }

    private void initListener() {
        this.mBackgroundAlphaSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /* class com.samsung.accessory.hearablemgr.core.appwidget.base.WidgetSettingBaseActivity.AnonymousClass1 */

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                int i2 = i * 10;
                WidgetSettingBaseActivity.this.mWidgetInfo.alpha = i2;
                WidgetSettingBaseActivity.this.mBackgroundAlphaTextView.setText(i2 + "%");
                WidgetSettingBaseActivity.this.onUpdatedAlpha(i2);
                WidgetUtil.setTextShadowWithChildren(WidgetSettingBaseActivity.this.mWidgetPreview, i2 == 0 && WidgetUtil.getWidgetColor(WidgetSettingBaseActivity.this.getApplicationContext(), WidgetSettingBaseActivity.this.mWidgetInfo) == -16711423);
            }
        });
        this.mWidgetStyleRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            /* class com.samsung.accessory.hearablemgr.core.appwidget.base.$$Lambda$WidgetSettingBaseActivity$nzpTtt1Qx_SqPbHakf1bnYVafW0 */

            public final void onCheckedChanged(RadioGroup radioGroup, int i) {
                WidgetSettingBaseActivity.this.lambda$initListener$0$WidgetSettingBaseActivity(radioGroup, i);
            }
        });
        this.mWidgetDarkModeLayout.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.core.appwidget.base.$$Lambda$WidgetSettingBaseActivity$7Tj6R7456p0glRtCwY1_5ygOwzs */

            public final void onClick(View view) {
                WidgetSettingBaseActivity.this.lambda$initListener$1$WidgetSettingBaseActivity(view);
            }
        });
        this.mWidgetDarkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /* class com.samsung.accessory.hearablemgr.core.appwidget.base.$$Lambda$WidgetSettingBaseActivity$oGu735dJDZijiUvsls6iOe4NMPo */

            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                WidgetSettingBaseActivity.this.lambda$initListener$2$WidgetSettingBaseActivity(compoundButton, z);
            }
        });
        this.mCancelButton.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.core.appwidget.base.$$Lambda$WidgetSettingBaseActivity$3qVkMeLXG8sE_EcrFNIopJk8b4 */

            public final void onClick(View view) {
                WidgetSettingBaseActivity.this.lambda$initListener$3$WidgetSettingBaseActivity(view);
            }
        });
        this.mPositiveButton.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.core.appwidget.base.$$Lambda$WidgetSettingBaseActivity$_TB3h7OMMiXIjUVQ6WQWWX36bYk */

            public final void onClick(View view) {
                WidgetSettingBaseActivity.this.lambda$initListener$4$WidgetSettingBaseActivity(view);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$WidgetSettingBaseActivity(RadioGroup radioGroup, int i) {
        int i2 = -16711423;
        switch (i) {
            case R.id.radio_widget_setting_background_color_black /*{ENCODED_INT: 2131231298}*/:
                this.mWidgetInfo.color = -16711423;
                onUpdatedColor(-16711423);
                return;
            case R.id.radio_widget_setting_background_color_white /*{ENCODED_INT: 2131231299}*/:
                this.mWidgetInfo.color = WidgetInfo.WIDGET_COLOR_WHITE;
                if (!(WidgetUtil.isDeviceDarkMode(getApplicationContext()) && this.mWidgetDarkModeSwitch.isChecked())) {
                    i2 = -328966;
                }
                onUpdatedColor(i2);
                return;
            default:
                return;
        }
    }

    public /* synthetic */ void lambda$initListener$1$WidgetSettingBaseActivity(View view) {
        this.mWidgetDarkModeSwitch.performClick();
    }

    public /* synthetic */ void lambda$initListener$2$WidgetSettingBaseActivity(CompoundButton compoundButton, boolean z) {
        this.mWidgetInfo.darkmode = z;
        if (WidgetUtil.isDeviceDarkMode(getApplicationContext())) {
            this.mWidgetStyleRadioWhite.setEnabled(!z);
            this.mWidgetStyleRadioBlack.setEnabled(!z);
            this.mBackgroundAlphaSeekBar.setEnabled(!z);
            this.mBackgroundAlphaSeekBar.setAlpha(!z ? 1.0f : 0.4f);
            onUpdatedDarkMode(z);
        }
    }

    public /* synthetic */ void lambda$initListener$3$WidgetSettingBaseActivity(View view) {
        finish();
    }

    public /* synthetic */ void lambda$initListener$4$WidgetSettingBaseActivity(View view) {
        saveAndFinish();
    }

    private void initView() {
        if (this.mWidgetInfo.color == -328966) {
            this.mWidgetStyleRadioWhite.setChecked(true);
            this.mWidgetStyleRadioBlack.setChecked(false);
        } else {
            this.mWidgetStyleRadioWhite.setChecked(false);
            this.mWidgetStyleRadioBlack.setChecked(true);
        }
        this.mBackgroundAlphaTextView.setText(this.mWidgetInfo.alpha + "%");
        this.mBackgroundAlphaSeekBar.setProgress(this.mWidgetInfo.alpha / 10);
        this.mWidgetDarkModeSwitch.setChecked(this.mWidgetInfo.darkmode);
        if (WidgetUtil.isDeviceDarkMode(getApplicationContext())) {
            this.mWidgetStyleRadioWhite.setEnabled(!this.mWidgetInfo.darkmode);
            this.mWidgetStyleRadioBlack.setEnabled(!this.mWidgetInfo.darkmode);
            this.mBackgroundAlphaSeekBar.setEnabled(!this.mWidgetInfo.darkmode);
            this.mBackgroundAlphaSeekBar.setAlpha(!this.mWidgetInfo.darkmode ? 1.0f : 0.4f);
        }
        initDarkModeView();
        updateStatusBarTextColor();
    }

    private void initDarkModeView() {
        this.mWidgetDarkModeLayout.setVisibility(WidgetUtil.isSupportedDarkMode() ? 0 : 8);
        this.mWidgetDarkModeText.setText(WidgetUtil.getStringDarkMode(getApplicationContext()));
    }

    private void updateStatusBarTextColor() {
        if (!WidgetUtil.isDeviceDarkMode(this)) {
            getWindow().getDecorView().setSystemUiVisibility(8192);
        }
    }

    /* access modifiers changed from: protected */
    public WidgetInfo getWidgetInfo() {
        return this.mWidgetInfo;
    }

    /* access modifiers changed from: protected */
    public FrameLayout getWidgetPreview() {
        return this.mWidgetPreview;
    }

    /* access modifiers changed from: protected */
    public void setWidgetPreview(int i) {
        LayoutInflater.from(this).inflate(i, this.mWidgetPreview);
        initPreview();
    }

    private void initPreview() {
        WidgetUtil.setTextShadowWithChildren(this.mWidgetPreview, this.mWidgetInfo.alpha == 0 && WidgetUtil.getWidgetColor(getApplicationContext(), this.mWidgetInfo) == -16711423);
    }

    /* access modifiers changed from: protected */
    public void setPreviewWidth(int i) {
        ViewGroup.LayoutParams layoutParams = this.mWidgetPreview.getLayoutParams();
        layoutParams.width = i;
        this.mWidgetPreview.setLayoutParams(layoutParams);
    }

    @Override // androidx.appcompat.app.AppCompatActivity
    public boolean onSupportNavigateUp() {
        saveAndFinish();
        return super.onSupportNavigateUp();
    }

    private void saveAndFinish() {
        saveWidgetInfo();
        updateWidget(this.mWidgetId);
        finish();
    }

    private void saveWidgetInfo() {
        new WidgetInfoManager(this, getProviderClass()).setWidgetInfo(this.mWidgetId, this.mWidgetInfo);
        Intent intent = new Intent();
        intent.putExtra("appWidgetId", this.mWidgetId);
        setResult(-1, intent);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.clear();
        if (getResources().getConfiguration().orientation != 2) {
            return true;
        }
        getMenuInflater().inflate(R.menu.menu_widget_settings_toolbar, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.cancel) {
            finish();
            return true;
        } else if (itemId != R.id.save) {
            return super.onOptionsItemSelected(menuItem);
        } else {
            saveAndFinish();
            return true;
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        recreate();
    }
}
