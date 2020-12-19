package com.samsung.accessory.hearablemgr.module.aboutmenu;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.common.preference.PreferenceKey;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;
import com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity;
import com.samsung.accessory.hearablemgr.module.setupwizard.AssetString;
import com.samsung.accessory.hearablemgr.module.setupwizard.NoticeDiagnosticInfoActivity;
import com.samsung.accessory.hearablemgr.module.setupwizard.PrivacyNotice;
import seccompat.android.util.Log;

public class LegalnformationActivity extends PermissionCheckActivity {
    private static final String TAG = "Attic_LegalnformationActivity";
    private Button button;
    private boolean isCheckedAgree;
    private Context mContext;
    private String openSourceLicenseMessage;
    LinearLayout reportDiagnosticInfoLayout;
    SwitchCompat reportDiagnosticInfoSwitch;
    private String samsungLegalMessage;

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, com.samsung.accessory.hearablemgr.module.base.OrientationPolicyActivity, androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = this;
        setContentView(R.layout.activity_legal_information);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(this.mContext.getString(R.string.about_earbuds_legal_information));
        prepareMessage();
        this.reportDiagnosticInfoSwitch = (SwitchCompat) findViewById(R.id.switch_report_diagnostic_info);
        this.reportDiagnosticInfoLayout = (LinearLayout) findViewById(R.id.layout_report_diagnostic_info_switch);
        this.reportDiagnosticInfoSwitch.setChecked(Preferences.getBoolean(PreferenceKey.SETUP_WIZARD_REPORT_DIAGNOSTIC_INFO, false));
        findViewById(R.id.menu_open_source_license).setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.aboutmenu.LegalnformationActivity.AnonymousClass1 */

            public void onClick(View view) {
                LegalnformationActivity.this.alertOpenSourceDialog();
            }
        });
        findViewById(R.id.menu_samsung_legal).setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.aboutmenu.LegalnformationActivity.AnonymousClass2 */

            public void onClick(View view) {
                LegalnformationActivity.this.alertDialog();
            }
        });
        findViewById(R.id.menu_privacy_policy).setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.aboutmenu.LegalnformationActivity.AnonymousClass3 */

            public void onClick(View view) {
                if ("kr".equalsIgnoreCase(Util.getCountryIso())) {
                    PrivacyNotice.startOnlinePage(LegalnformationActivity.this);
                } else {
                    LegalnformationActivity.this.showPrivacyNoticeDialog();
                }
            }
        });
        this.reportDiagnosticInfoSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /* class com.samsung.accessory.hearablemgr.module.aboutmenu.LegalnformationActivity.AnonymousClass4 */

            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (Preferences.getBoolean(PreferenceKey.SETUP_WIZARD_REPORT_DIAGNOSTIC_INFO, false)) {
                    if (!z) {
                        SamsungAnalyticsUtil.setReportDiagnosticInfo(false);
                    }
                } else if (z) {
                    LegalnformationActivity.this.reportDiagnosticInfoSwitch.setChecked(false);
                    LegalnformationActivity.this.diagnosticDialog();
                }
            }
        });
        this.reportDiagnosticInfoLayout.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.aboutmenu.LegalnformationActivity.AnonymousClass5 */

            public void onClick(View view) {
                LegalnformationActivity.this.reportDiagnosticInfoSwitch.setChecked(!LegalnformationActivity.this.reportDiagnosticInfoSwitch.isChecked());
            }
        });
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void alertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(this.mContext.getString(R.string.legal_info_samsung_legal));
        builder.setMessage(this.samsungLegalMessage);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.aboutmenu.LegalnformationActivity.AnonymousClass6 */

            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setCancelable(true);
        builder.show();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void showPrivacyNoticeDialog() {
        Log.d(TAG, "showPrivacyNoticeDialog()");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(this.mContext.getString(R.string.privacy_policy));
        builder.setMessage(PrivacyNotice.getString());
        builder.setCancelable(true);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.aboutmenu.LegalnformationActivity.AnonymousClass7 */

            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(LegalnformationActivity.TAG, "showPrivacyNoticeDialog() : OK");
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void prepareMessage() {
        this.samsungLegalMessage = AssetString.getStringEULA();
        this.openSourceLicenseMessage = AssetString.getStringFromPath("S-20181214-DU-Galaxy_Buds-1.0-1_Announcement.txt");
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void diagnosticDialog() {
        String string = getString(R.string.optional, new Object[]{getString(R.string.report_diagnostic_info_agree_description)});
        this.isCheckedAgree = Preferences.getBoolean(PreferenceKey.SETUP_WIZARD_REPORT_DIAGNOSTIC_INFO, false);
        ScrollView scrollView = (ScrollView) View.inflate(this, R.layout.check_box_diagnostic_info, null);
        CheckBox checkBox = (CheckBox) scrollView.findViewById(R.id.checkbox_agree);
        checkBox.setText(string);
        checkBox.setChecked(this.isCheckedAgree);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /* class com.samsung.accessory.hearablemgr.module.aboutmenu.LegalnformationActivity.AnonymousClass8 */

            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                LegalnformationActivity.this.isCheckedAgree = z;
                if (LegalnformationActivity.this.isCheckedAgree) {
                    LegalnformationActivity.this.button.setEnabled(true);
                } else {
                    LegalnformationActivity.this.button.setEnabled(false);
                }
            }
        });
        TextView textView = (TextView) scrollView.findViewById(R.id.text_diagnostic_data);
        textView.setPaintFlags(textView.getPaintFlags() | 8);
        textView.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.aboutmenu.LegalnformationActivity.AnonymousClass9 */

            public void onClick(View view) {
                LegalnformationActivity.this.onClickReportDiagnosticInfo();
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getApplicationContext().getString(R.string.send_diagnostic_data));
        builder.setCancelable(false);
        builder.setView(scrollView);
        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            /* class com.samsung.accessory.hearablemgr.module.aboutmenu.LegalnformationActivity.AnonymousClass10 */

            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i != 4) {
                    return false;
                }
                dialogInterface.dismiss();
                return true;
            }
        });
        builder.setNegativeButton(getApplicationContext().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.aboutmenu.LegalnformationActivity.AnonymousClass11 */

            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setPositiveButton(getApplicationContext().getString(R.string.ok), new DialogInterface.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.aboutmenu.LegalnformationActivity.AnonymousClass12 */

            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                SamsungAnalyticsUtil.setReportDiagnosticInfo(LegalnformationActivity.this.isCheckedAgree);
                if (LegalnformationActivity.this.isCheckedAgree) {
                    LegalnformationActivity.this.reportDiagnosticInfoSwitch.setChecked(true);
                }
            }
        });
        final AlertDialog create = builder.create();
        create.setOnShowListener(new DialogInterface.OnShowListener() {
            /* class com.samsung.accessory.hearablemgr.module.aboutmenu.LegalnformationActivity.AnonymousClass13 */

            public void onShow(DialogInterface dialogInterface) {
                LegalnformationActivity.this.button = create.getButton(-1);
                if (LegalnformationActivity.this.button == null) {
                    return;
                }
                if (LegalnformationActivity.this.isCheckedAgree) {
                    LegalnformationActivity.this.button.setEnabled(true);
                } else {
                    LegalnformationActivity.this.button.setEnabled(false);
                }
            }
        });
        create.show();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onClickReportDiagnosticInfo() {
        Log.d(TAG, "onClickReportDiagnosticInfo()");
        startActivity(new Intent(this, NoticeDiagnosticInfoActivity.class));
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void alertOpenSourceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(this.mContext.getString(R.string.legal_info_open_source_licenses));
        builder.setMessage(this.openSourceLicenseMessage);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.aboutmenu.LegalnformationActivity.AnonymousClass14 */

            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setCancelable(true);
        builder.show();
    }

    @Override // androidx.appcompat.app.AppCompatActivity
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
