package com.samsung.accessory.hearablemgr.module.setupwizard;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.common.preference.PreferenceKey;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import com.samsung.accessory.hearablemgr.common.uhm.UhmFwUtil;
import com.samsung.accessory.hearablemgr.common.ui.OnSingleClickListener;
import com.samsung.accessory.hearablemgr.common.util.CompanionDeviceUtil;
import com.samsung.accessory.hearablemgr.common.util.ResponseCallback;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.core.bigdata.SA;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;
import com.samsung.accessory.hearablemgr.core.notification.NotificationUtil;
import com.samsung.accessory.hearablemgr.module.base.OrientationPolicyActivity;
import com.samsung.accessory.hearablemgr.module.base.PermissionCheckImpl;
import com.samsung.accessory.hearablemgr.module.home.HomeActivity;
import seccompat.android.util.Log;

public class TermsAndConditionsActivity extends OrientationPolicyActivity {
    private static final String TAG = "Attic_TermsAndConditionsActivity";
    private Button mButtonAgree;
    private CheckBox mCheckBoxReport;
    private ContinueState mContinueState = ContinueState.NONE;
    private Dialog mDialogAllowNotificationAccess;
    private PermissionCheckImpl mPermissionCheckImpl;

    /* access modifiers changed from: package-private */
    public enum ContinueState {
        NONE,
        START,
        CHECK_PERMISSIONS,
        CHECK_PERMISSIONS_ING,
        CHECK_PERMISSIONS_DONE,
        CHECK_CDM,
        CHECK_CDM_ING,
        CHECK_CDM_DONE,
        CHECK_NOTIFICATION_ACCESS,
        CHECK_NOTIFICATION_ACCESS_ING,
        CHECK_NOTIFICATION_ACCESS_DONE,
        STATE_DONE
    }

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, com.samsung.accessory.hearablemgr.module.base.OrientationPolicyActivity, androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        Log.d(TAG, "onCreate()");
        super.onCreate(bundle);
        setContentView(R.layout.activity_terms_and_conditions);
        this.mPermissionCheckImpl = new PermissionCheckImpl(this);
        this.mPermissionCheckImpl.setAutoFinish(false);
        this.mCheckBoxReport = (CheckBox) findViewById(R.id.checkbox_report);
        findViewById(R.id.layout_checkbox_report).setOnClickListener(new OnSingleClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.setupwizard.TermsAndConditionsActivity.AnonymousClass1 */

            @Override // com.samsung.accessory.hearablemgr.common.ui.OnSingleClickListener
            public void onSingleClick(View view) {
                TermsAndConditionsActivity.this.mCheckBoxReport.toggle();
            }
        });
        initTitle();
        this.mButtonAgree = (Button) findViewById(R.id.button_agree);
        this.mButtonAgree.setOnClickListener(new OnSingleClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.setupwizard.TermsAndConditionsActivity.AnonymousClass2 */

            @Override // com.samsung.accessory.hearablemgr.common.ui.OnSingleClickListener
            public void onSingleClick(View view) {
                TermsAndConditionsActivity.this.onClickButtonAgreePre();
            }
        });
        initLayout();
    }

    /* access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity
    public void onResume() {
        Log.d(TAG, "onResume()");
        super.onResume();
        updateUI();
        nextToOnClickButtonAgree();
    }

    private void initTitle() {
        String string = getString(R.string.congrats_on_your_new, new Object[]{getString(R.string.app_name)});
        ((TextView) findViewById(R.id.text_full_title)).setText(string);
        setTitle(string);
    }

    private void initText() {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        String countryIso = Util.getCountryIso();
        if (Util.isGDPRCountry(countryIso) || Util.isLGPDCountry(countryIso)) {
            LinkString linkString = new LinkString(getString(R.string.check_our_privacy_gdpr), 1);
            linkString.addLinkSpan(0, new StyleSpan(1));
            LinkString linkString2 = new LinkString(getString(R.string.by_continuing_gdpr), 1);
            linkString2.addLinkSpan(0, new StyleSpan(1));
            if (!Util.isTalkBackEnabled()) {
                linkString.addLinkSpan(0, new ClickableSpan() {
                    /* class com.samsung.accessory.hearablemgr.module.setupwizard.TermsAndConditionsActivity.AnonymousClass3 */

                    public void onClick(View view) {
                        TermsAndConditionsActivity.this.onClickPrivacyPolicy();
                    }
                });
                linkString2.addLinkSpan(0, new ClickableSpan() {
                    /* class com.samsung.accessory.hearablemgr.module.setupwizard.TermsAndConditionsActivity.AnonymousClass4 */

                    public void onClick(View view) {
                        TermsAndConditionsActivity.this.onClickEULA();
                    }
                });
            }
            spannableStringBuilder.append(linkString.toCharSequence()).append((CharSequence) "\n\n").append(linkString2.toCharSequence());
            this.mButtonAgree.setText(R.string._continue);
        } else {
            LinkString linkString3 = new LinkString(getString(R.string.to_continuing), 2);
            linkString3.addLinkSpan(0, new StyleSpan(1));
            linkString3.addLinkSpan(1, new StyleSpan(1));
            if (!Util.isTalkBackEnabled()) {
                linkString3.addLinkSpan(0, new ClickableSpan() {
                    /* class com.samsung.accessory.hearablemgr.module.setupwizard.TermsAndConditionsActivity.AnonymousClass5 */

                    public void onClick(View view) {
                        TermsAndConditionsActivity.this.onClickEULA();
                    }
                });
                linkString3.addLinkSpan(1, new ClickableSpan() {
                    /* class com.samsung.accessory.hearablemgr.module.setupwizard.TermsAndConditionsActivity.AnonymousClass6 */

                    public void onClick(View view) {
                        TermsAndConditionsActivity.this.onClickPrivacyPolicy();
                    }
                });
            }
            spannableStringBuilder.append(linkString3.toCharSequence());
            this.mButtonAgree.setText(R.string.agree);
        }
        TextView textView = (TextView) findViewById(R.id.text_description1);
        textView.setText(spannableStringBuilder);
        textView.setMovementMethod(Util.isTalkBackEnabled() ? null : LinkMovementMethod.getInstance());
        if (!Util.isTalkBackEnabled()) {
            findViewById(R.id.layout_accessibility_links_01).setVisibility(8);
        } else {
            TextView textView2 = (TextView) findViewById(R.id.text_link_privacy_policy);
            TextView textView3 = (TextView) findViewById(R.id.text_link_eula);
            textView2.setPaintFlags(textView2.getPaintFlags() | 8);
            textView3.setPaintFlags(textView3.getPaintFlags() | 8);
            textView2.setOnClickListener(new OnSingleClickListener() {
                /* class com.samsung.accessory.hearablemgr.module.setupwizard.TermsAndConditionsActivity.AnonymousClass7 */

                @Override // com.samsung.accessory.hearablemgr.common.ui.OnSingleClickListener
                public void onSingleClick(View view) {
                    TermsAndConditionsActivity.this.onClickPrivacyPolicy();
                }
            });
            textView3.setOnClickListener(new OnSingleClickListener() {
                /* class com.samsung.accessory.hearablemgr.module.setupwizard.TermsAndConditionsActivity.AnonymousClass8 */

                @Override // com.samsung.accessory.hearablemgr.common.ui.OnSingleClickListener
                public void onSingleClick(View view) {
                    TermsAndConditionsActivity.this.onClickEULA();
                }
            });
            findViewById(R.id.layout_accessibility_links_01).setVisibility(0);
        }
        LinkString linkString4 = new LinkString(getString(R.string.you_can_also_check_the_required_permissions), 1);
        linkString4.addLinkSpan(0, new StyleSpan(1));
        if (!Util.isTalkBackEnabled()) {
            linkString4.addLinkSpan(0, new ClickableSpan() {
                /* class com.samsung.accessory.hearablemgr.module.setupwizard.TermsAndConditionsActivity.AnonymousClass9 */

                public void onClick(View view) {
                    TermsAndConditionsActivity.this.onClickPermissions();
                }
            });
        }
        TextView textView4 = (TextView) findViewById(R.id.text_description2);
        textView4.setText(linkString4.toCharSequence());
        if (!Util.isTalkBackEnabled()) {
            textView4.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            textView4.setMovementMethod(null);
        }
        TextView textView5 = (TextView) findViewById(R.id.text_link_permissions);
        if (!Util.isTalkBackEnabled()) {
            textView5.setVisibility(8);
        } else {
            textView5.setVisibility(0);
            textView5.setPaintFlags(textView5.getPaintFlags() | 8);
            textView5.setOnClickListener(new OnSingleClickListener() {
                /* class com.samsung.accessory.hearablemgr.module.setupwizard.TermsAndConditionsActivity.AnonymousClass10 */

                @Override // com.samsung.accessory.hearablemgr.common.ui.OnSingleClickListener
                public void onSingleClick(View view) {
                    TermsAndConditionsActivity.this.onClickPermissions();
                }
            });
        }
        String string = getString(R.string.report_diagnostic_info);
        String string2 = getString(R.string.optional, new Object[]{string});
        SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(string2);
        int indexOf = string2.indexOf(string);
        int length = string.length() + indexOf;
        spannableStringBuilder2.setSpan(new StyleSpan(1), indexOf, length, 33);
        if (!Util.isTalkBackEnabled()) {
            spannableStringBuilder2.setSpan(new ClickableSpan() {
                /* class com.samsung.accessory.hearablemgr.module.setupwizard.TermsAndConditionsActivity.AnonymousClass11 */

                public void onClick(View view) {
                    TermsAndConditionsActivity.this.onClickReportDiagnosticInfo();
                }
            }, indexOf, length, 33);
        } else {
            spannableStringBuilder2.setSpan(new UnderlineSpan(), indexOf, length, 33);
        }
        TextView textView6 = (TextView) findViewById(R.id.text_diagnostic_info);
        textView6.setText(spannableStringBuilder2);
        if (!Util.isTalkBackEnabled()) {
            textView6.setMovementMethod(LinkMovementMethod.getInstance());
            return;
        }
        textView6.setMovementMethod(null);
        textView6.setOnClickListener(new OnSingleClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.setupwizard.TermsAndConditionsActivity.AnonymousClass12 */

            @Override // com.samsung.accessory.hearablemgr.common.ui.OnSingleClickListener
            public void onSingleClick(View view) {
                TermsAndConditionsActivity.this.onClickReportDiagnosticInfo();
            }
        });
    }

    private void initLayout() {
        Log.d(TAG, "initLayout()");
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_accessibility_links_01);
        String countryIso = Util.getCountryIso();
        if (Util.isGDPRCountry(countryIso) || Util.isLGPDCountry(countryIso)) {
            View[] viewArr = new View[linearLayout.getChildCount()];
            for (int i = 0; i < linearLayout.getChildCount(); i++) {
                viewArr[i] = linearLayout.getChildAt(i);
            }
            linearLayout.removeAllViews();
            for (int length = viewArr.length - 1; length >= 0; length--) {
                linearLayout.addView(viewArr[length]);
            }
        }
    }

    private void updateUI() {
        Log.d(TAG, "updateUI()");
        initText();
        if (Preferences.getBoolean(PreferenceKey.SETUP_WIZARD_REPORT_DIAGNOSTIC_INFO, false)) {
            this.mCheckBoxReport.setChecked(true);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onClickPrivacyPolicy() {
        Log.d(TAG, "onClickPrivacyPolicy()");
        if ("kr".equalsIgnoreCase(Util.getCountryIso())) {
            PrivacyNotice.startOnlinePage(this);
        } else {
            startActivity(new Intent(this, NoticePrivacyPolicyActivity.class));
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onClickEULA() {
        Log.d(TAG, "onClickEULA()");
        startActivity(new Intent(this, NoticeEULAActivity.class));
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onClickPermissions() {
        Log.d(TAG, "onClickPermissions()");
        startActivity(new Intent(this, PermissionsActivity.class));
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onClickReportDiagnosticInfo() {
        Log.d(TAG, "onClickReportDiagnosticInfo()");
        startActivity(new Intent(this, NoticeDiagnosticInfoActivity.class));
    }

    private boolean isAllPermissionsAllowed() {
        boolean isAllPermissionGranted = this.mPermissionCheckImpl.isAllPermissionGranted();
        Log.d(TAG, "isAllPermissionsAllowed() : " + isAllPermissionGranted);
        return isAllPermissionGranted;
    }

    private void checkRuntimePermissions() {
        Log.d(TAG, "checkRuntimePermissions()");
        this.mPermissionCheckImpl.checkPermission();
    }

    private boolean isCompanionDeviceSupport() {
        boolean z = CompanionDeviceUtil.isSupport() && Build.VERSION.SDK_INT >= 30;
        Log.d(TAG, "isCompanionDeviceSupport() : " + z);
        return z;
    }

    private void makeCompanionDevice() {
        Log.d(TAG, "makeCompanionDevice()");
        this.mContinueState = ContinueState.CHECK_CDM_ING;
        CompanionDeviceUtil.associate(this, UhmFwUtil.getLastLaunchDeviceId(), new ResponseCallback() {
            /* class com.samsung.accessory.hearablemgr.module.setupwizard.TermsAndConditionsActivity.AnonymousClass13 */

            @Override // com.samsung.accessory.hearablemgr.common.util.ResponseCallback
            public void onResponse(String str) {
                Log.d(TermsAndConditionsActivity.TAG, "onResponse() : " + str);
            }
        });
    }

    private boolean isCompanionDevice() {
        boolean isCompanionDevice = CompanionDeviceUtil.isCompanionDevice(UhmFwUtil.getLastLaunchDeviceId());
        Log.d(TAG, "isCompanionDevice() : " + isCompanionDevice);
        return isCompanionDevice;
    }

    private boolean hasNotificationAccessPermission() {
        boolean isAccessibilityON = NotificationUtil.isAccessibilityON();
        Log.d(TAG, "hasNotificationAccessPermission() : " + isAccessibilityON);
        return isAccessibilityON;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void startNotificationListenerSettings() {
        Log.d(TAG, "startNotificationListenerSettings()");
        try {
            Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            intent.addFlags(268435456);
            startActivity(intent);
        } catch (Exception e) {
            Log.e(TAG, "startNotificationListenerSettings() : Exception : " + e);
        }
    }

    private void showAllowNotificationAccessDialog() {
        Log.d(TAG, "showAllowNotificationAccessDialog()");
        Dialog dialog = this.mDialogAllowNotificationAccess;
        if (dialog != null && dialog.isShowing()) {
            this.mDialogAllowNotificationAccess.cancel();
            this.mDialogAllowNotificationAccess = null;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.turn_on_notifications_dialog_title, new Object[]{getString(R.string.app_name)}));
        builder.setMessage(getString(R.string.turn_on_notifications_dialog_content, new Object[]{getString(R.string.app_name)}));
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.setupwizard.TermsAndConditionsActivity.AnonymousClass14 */

            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(TermsAndConditionsActivity.TAG, "showAllowNotificationAccessDialog() : OK");
                dialogInterface.dismiss();
                TermsAndConditionsActivity.this.startNotificationListenerSettings();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.setupwizard.TermsAndConditionsActivity.AnonymousClass15 */

            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(TermsAndConditionsActivity.TAG, "showAllowNotificationAccessDialog() : cancel");
                dialogInterface.cancel();
            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            /* class com.samsung.accessory.hearablemgr.module.setupwizard.TermsAndConditionsActivity.AnonymousClass16 */

            public void onDismiss(DialogInterface dialogInterface) {
                Log.d(TermsAndConditionsActivity.TAG, "showAllowNotificationAccessDialog() : onDismiss()");
                TermsAndConditionsActivity.this.mDialogAllowNotificationAccess = null;
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            /* class com.samsung.accessory.hearablemgr.module.setupwizard.TermsAndConditionsActivity.AnonymousClass17 */

            public void onCancel(DialogInterface dialogInterface) {
                Log.d(TermsAndConditionsActivity.TAG, "showAllowNotificationAccessDialog() : onCancel()");
                TermsAndConditionsActivity.this.nextToOnClickButtonAgree();
            }
        });
        builder.setCancelable(false);
        this.mDialogAllowNotificationAccess = builder.create();
        this.mDialogAllowNotificationAccess.show();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onClickButtonAgreePre() {
        Log.d(TAG, "onClickButtonAgreePre()");
        this.mContinueState = ContinueState.START;
        nextToOnClickButtonAgree();
    }

    private void setContinueState(ContinueState continueState) {
        Log.d(TAG, "setContinueState() : " + continueState);
        this.mContinueState = continueState;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void nextToOnClickButtonAgree() {
        Log.d(TAG, "nextToOnClickButtonAgree() : " + this.mContinueState);
        switch (this.mContinueState) {
            case START:
                if (isAllPermissionsAllowed()) {
                    setContinueState(ContinueState.CHECK_PERMISSIONS_DONE);
                } else {
                    setContinueState(ContinueState.CHECK_PERMISSIONS);
                }
                nextToOnClickButtonAgree();
                return;
            case CHECK_PERMISSIONS:
                setContinueState(ContinueState.CHECK_PERMISSIONS_ING);
                checkRuntimePermissions();
                return;
            case CHECK_PERMISSIONS_ING:
                if (isAllPermissionsAllowed()) {
                    setContinueState(ContinueState.CHECK_PERMISSIONS_DONE);
                } else {
                    setContinueState(ContinueState.NONE);
                }
                nextToOnClickButtonAgree();
                return;
            case CHECK_PERMISSIONS_DONE:
                if (!isCompanionDeviceSupport() || isCompanionDevice()) {
                    setContinueState(ContinueState.CHECK_CDM_DONE);
                } else {
                    setContinueState(ContinueState.CHECK_CDM);
                }
                nextToOnClickButtonAgree();
                return;
            case CHECK_CDM:
                setContinueState(ContinueState.CHECK_CDM_ING);
                makeCompanionDevice();
                return;
            case CHECK_CDM_ING:
                if (isCompanionDevice()) {
                    setContinueState(ContinueState.CHECK_CDM_DONE);
                } else {
                    setContinueState(ContinueState.NONE);
                }
                nextToOnClickButtonAgree();
                return;
            case CHECK_CDM_DONE:
                if (hasNotificationAccessPermission()) {
                    setContinueState(ContinueState.CHECK_NOTIFICATION_ACCESS_DONE);
                } else {
                    setContinueState(ContinueState.CHECK_NOTIFICATION_ACCESS);
                }
                nextToOnClickButtonAgree();
                return;
            case CHECK_NOTIFICATION_ACCESS:
                setContinueState(ContinueState.CHECK_NOTIFICATION_ACCESS_ING);
                if (Util.isSamsungDevice()) {
                    NotificationUtil.enableNotificationService(true);
                }
                if (hasNotificationAccessPermission()) {
                    setContinueState(ContinueState.CHECK_NOTIFICATION_ACCESS_DONE);
                    nextToOnClickButtonAgree();
                    return;
                }
                showAllowNotificationAccessDialog();
                return;
            case CHECK_NOTIFICATION_ACCESS_ING:
                if (hasNotificationAccessPermission()) {
                    setContinueState(ContinueState.CHECK_NOTIFICATION_ACCESS_DONE);
                } else {
                    setContinueState(ContinueState.NONE);
                }
                nextToOnClickButtonAgree();
                return;
            case CHECK_NOTIFICATION_ACCESS_DONE:
                setContinueState(ContinueState.STATE_DONE);
                onClickButtonAgree();
                return;
            default:
                return;
        }
    }

    @Override // androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback, androidx.fragment.app.FragmentActivity
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        Log.d(TAG, "onRequestPermissionsResult()");
        this.mPermissionCheckImpl.onRequestPermissionsResult(i, strArr, iArr);
    }

    private void onClickButtonAgree() {
        Log.d(TAG, "onClickButtonAgree()");
        SamsungAnalyticsUtil.setReportDiagnosticInfo(this.mCheckBoxReport.isChecked());
        SamsungAnalyticsUtil.sendPage(SA.Screen.TERMS_AND_CONDITIONS);
        SamsungAnalyticsUtil.sendEvent(SA.Event.T_AND_C_AGREE, SA.Screen.TERMS_AND_CONDITIONS, this.mCheckBoxReport.isChecked() ? "1" : "0");
        startActivityForResult(new Intent(this, YouAreAllSetActivity.class), 0);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
    }

    /* access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        Log.d(TAG, "onActivityResult() : requestCode=" + i + ", resultCode=" + i2);
        if (i == 0 && i2 == -1) {
            setResult(-1);
            startHomeActivity();
            finish();
        }
    }

    private void startHomeActivity() {
        Log.d(TAG, "startHomeActivity()");
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra(HomeActivity.EXTRA_AUTO_CONNECT, true);
        intent.putExtra(HomeActivity.EXTRA_FROM_SETUPWIZARD, true);
        startActivity(intent);
    }

    /* access modifiers changed from: package-private */
    public static class LinkString {
        private final SpannableString mSpannableString;

        public LinkString(String str, int i) {
            Object[] objArr = new Object[(i * 2)];
            for (int i2 = 0; i2 < objArr.length; i2++) {
                objArr[i2] = i2 % 2 == 0 ? "<u>" : "</u>";
            }
            this.mSpannableString = new SpannableString(Html.fromHtml(String.format(str, objArr)));
        }

        public void addLinkSpan(int i, Object obj) {
            SpannableString spannableString = this.mSpannableString;
            Object[] spans = spannableString.getSpans(0, spannableString.length(), Object.class);
            if (spans != null && i < spans.length) {
                Object obj2 = spans[i];
                SpannableString spannableString2 = this.mSpannableString;
                spannableString2.setSpan(obj, spannableString2.getSpanStart(obj2), this.mSpannableString.getSpanEnd(obj2), 33);
            }
        }

        public CharSequence toCharSequence() {
            return this.mSpannableString;
        }
    }
}
