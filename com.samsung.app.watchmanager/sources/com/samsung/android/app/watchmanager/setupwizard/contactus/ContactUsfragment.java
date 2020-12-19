package com.samsung.android.app.watchmanager.setupwizard.contactus;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import com.samsung.android.app.twatchmanager.TWatchManagerApplication;
import com.samsung.android.app.twatchmanager.uiitems.SetupwizardCheckboxLayout;
import com.samsung.android.app.twatchmanager.util.CommonDialog;
import com.samsung.android.app.twatchmanager.util.HostManagerUtils;
import com.samsung.android.app.twatchmanager.util.HostManagerUtilsNetwork;
import com.samsung.android.app.watchmanager.R;
import com.samsung.android.app.watchmanager.setupwizard.ContactUs;
import com.samsung.android.app.watchmanager.setupwizard.contactus.connection.AsyncHttpRequest;
import com.samsung.android.app.watchmanager.setupwizard.contactus.connection.GlobalConst;
import com.samsung.android.app.watchmanager.setupwizard.contactus.connection.SAWebViewActivity;
import java.util.concurrent.ExecutionException;
import org.json.JSONException;
import org.json.JSONObject;

public class ContactUsfragment extends Fragment {
    private static final int LEGAL_DIALOG_SENSITIVE_PRIVACY_CONSENT = 0;
    private static final int LEGAL_DIALOG_TRANSFER_PRIVACY_CONSENT = 1;
    private static String TAG = "ContactUsfragment";
    private LinearLayout askQuestions = null;
    private LinearLayout errorReport = null;
    private LinearLayout faq = null;
    private LinearLayout feedBackList = null;
    private Activity mActivity;
    private CommonDialog mLegalDialog;
    private LinearLayout mNextButton;
    private TextView mNextTV;
    private boolean mNonSecChnPrivacyConsent;
    private BroadcastReceiver mSAResponseReceiver = new BroadcastReceiver() {
        /* class com.samsung.android.app.watchmanager.setupwizard.contactus.ContactUsfragment.AnonymousClass14 */

        public void onReceive(Context context, Intent intent) {
            String str = ContactUsfragment.TAG;
            Log.d(str, "onReceive() action : " + intent.getAction());
            if (intent.getAction() == SAWebViewActivity.ACTION_SA_WEBVIEW_LOGIN_SUCCESS) {
                try {
                    String csc = HostManagerUtilsNetwork.getCSC();
                    String mcc = ContactUsActivity.getMCC(ContactUsfragment.this.mActivity);
                    SharedPreferences.Editor edit = ContactUsfragment.this.mActivity.getSharedPreferences(GlobalConst.PREF_SM, 0).edit();
                    edit.putString(GlobalConst.AUTH_SERVER_URL, intent.getStringExtra(GlobalConst.AUTH_SERVER_URL));
                    edit.putString(GlobalConst.USER_ID, intent.getStringExtra(GlobalConst.USER_ID));
                    edit.putString(GlobalConst.TOKEN_SA, intent.getStringExtra(GlobalConst.TOKEN_SA));
                    edit.putString("refresh_token_sa", intent.getStringExtra("refresh_token_sa"));
                    edit.apply();
                    try {
                        String str2 = (String) new AsyncHttpRequest(null).execute(1, mcc, csc).get();
                        if (str2 != null) {
                            JSONObject jSONObject = new JSONObject(str2);
                            String string = jSONObject.getString("access_token");
                            String string2 = jSONObject.getString("refresh_token");
                            edit.putString(GlobalConst.TOKEN_SM, string);
                            edit.putString("refresh_token_sa", string2);
                            if (ContactUsfragment.this.mActivity != null && !ContactUsfragment.this.mActivity.isDestroyed() && !ContactUsfragment.this.mActivity.isFinishing()) {
                                Log.d(ContactUsfragment.TAG, "onReceive() try to open item...");
                                ContactUsfragment.this.openItem();
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e2) {
                        e2.printStackTrace();
                    }
                } catch (JSONException e3) {
                    e3.printStackTrace();
                }
            }
        }
    };
    private SetupwizardCheckboxLayout mSensitiveConsent;
    private SetupwizardCheckboxLayout mTransferConsent;
    View rootView;
    int screenToBeLaunched;

    /* access modifiers changed from: package-private */
    public interface ValidateTokenCallBack {
        void istokenValid(boolean z);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void openItem() {
        int i = this.screenToBeLaunched;
        if (i == 1) {
            ((ContactUsActivity) this.mActivity).loadAskQuestions(-1);
        } else if (i != 2) {
            if (i == 3) {
                ((ContactUsActivity) this.mActivity).loadFeedBackListFragment();
            }
        } else if (!this.mActivity.getSharedPreferences(GlobalConst.PREF_SM, 0).getBoolean("collect_system_logs_always", false)) {
            showRadioButtonDialog();
        } else {
            ((ContactUsActivity) this.mActivity).loadErrorReport(-1, true);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void showLegalDialog(int i) {
        String str;
        int i2;
        CommonDialog commonDialog = this.mLegalDialog;
        if (commonDialog != null) {
            commonDialog.dismiss();
            this.mLegalDialog = null;
        }
        this.mLegalDialog = new CommonDialog(this.mActivity, 1, 0, 1);
        String str2 = "";
        if (i == 0) {
            str2 = getString(R.string.sensitive_privacy_text);
            i2 = R.string.sensitive_privacy_legal_text;
        } else if (i != 1) {
            str = str2;
            this.mLegalDialog.createDialog();
            this.mLegalDialog.setTitle(str2);
            this.mLegalDialog.setMessage(str);
            this.mLegalDialog.setOkBtnListener(new View.OnClickListener() {
                /* class com.samsung.android.app.watchmanager.setupwizard.contactus.ContactUsfragment.AnonymousClass12 */

                public void onClick(View view) {
                    if (ContactUsfragment.this.mLegalDialog != null) {
                        ContactUsfragment.this.mLegalDialog.dismiss();
                    }
                }
            });
        } else {
            str2 = getString(R.string.transfer_privacy_text);
            i2 = R.string.transfer_privacy_legal_text;
        }
        str = getString(i2);
        this.mLegalDialog.createDialog();
        this.mLegalDialog.setTitle(str2);
        this.mLegalDialog.setMessage(str);
        this.mLegalDialog.setOkBtnListener(new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.contactus.ContactUsfragment.AnonymousClass12 */

            public void onClick(View view) {
                if (ContactUsfragment.this.mLegalDialog != null) {
                    ContactUsfragment.this.mLegalDialog.dismiss();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void showRadioButtonDialog() {
        final Dialog dialog = new Dialog(this.mActivity);
        dialog.setContentView(R.layout.systemlog_popup);
        dialog.setTitle(this.mActivity.getResources().getString(R.string.send_system_log_data_popup));
        ((TextView) dialog.findViewById(R.id.explantion)).setText(String.format(this.mActivity.getString(R.string.sysytemlogpopupdec), "5MB"));
        final RadioButton radioButton = (RadioButton) dialog.findViewById(R.id.alwaystake);
        final RadioButton radioButton2 = (RadioButton) dialog.findViewById(R.id.justthistime);
        ((TextView) dialog.findViewById(R.id.ok_btn)).setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.contactus.ContactUsfragment.AnonymousClass15 */

            /* JADX WARNING: Code restructure failed: missing block: B:4:0x002a, code lost:
                if (r3.isChecked() != false) goto L_0x002c;
             */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void onClick(android.view.View r4) {
                /*
                    r3 = this;
                    android.widget.RadioButton r4 = r2
                    boolean r4 = r4.isChecked()
                    r0 = 0
                    r1 = 1
                    if (r4 == 0) goto L_0x0024
                    com.samsung.android.app.watchmanager.setupwizard.contactus.ContactUsfragment r4 = com.samsung.android.app.watchmanager.setupwizard.contactus.ContactUsfragment.this
                    android.app.Activity r4 = com.samsung.android.app.watchmanager.setupwizard.contactus.ContactUsfragment.access$000(r4)
                    java.lang.String r2 = "samsung_members"
                    android.content.SharedPreferences r4 = r4.getSharedPreferences(r2, r0)
                    android.content.SharedPreferences$Editor r4 = r4.edit()
                    java.lang.String r0 = "collect_system_logs_always"
                    android.content.SharedPreferences$Editor r4 = r4.putBoolean(r0, r1)
                    r4.apply()
                    goto L_0x002c
                L_0x0024:
                    android.widget.RadioButton r4 = r3
                    boolean r4 = r4.isChecked()
                    if (r4 == 0) goto L_0x002d
                L_0x002c:
                    r0 = 1
                L_0x002d:
                    android.app.Dialog r4 = r0
                    r4.dismiss()
                    com.samsung.android.app.watchmanager.setupwizard.contactus.ContactUsfragment r4 = com.samsung.android.app.watchmanager.setupwizard.contactus.ContactUsfragment.this
                    android.app.Activity r4 = com.samsung.android.app.watchmanager.setupwizard.contactus.ContactUsfragment.access$000(r4)
                    com.samsung.android.app.watchmanager.setupwizard.contactus.ContactUsActivity r4 = (com.samsung.android.app.watchmanager.setupwizard.contactus.ContactUsActivity) r4
                    r1 = -1
                    r4.loadErrorReport(r1, r0)
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.watchmanager.setupwizard.contactus.ContactUsfragment.AnonymousClass15.onClick(android.view.View):void");
            }
        });
        ((TextView) dialog.findViewById(R.id.cancel_btn)).setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.contactus.ContactUsfragment.AnonymousClass16 */

            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void isSignedIn(Context context, final ValidateTokenCallBack validateTokenCallBack) {
        final SharedPreferences sharedPreferences = this.mActivity.getSharedPreferences(GlobalConst.PREF_SM, 0);
        String string = sharedPreferences.getString(GlobalConst.TOKEN_SA, null);
        if (string == null || string.isEmpty()) {
            validateTokenCallBack.istokenValid(false);
            return;
        }
        final ProgressDialog progressDialog = new ProgressDialog(this.mActivity);
        progressDialog.setProgressStyle(0);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        new AsyncHttpRequest(new AsyncHttpRequest.AsyncResponse() {
            /* class com.samsung.android.app.watchmanager.setupwizard.contactus.ContactUsfragment.AnonymousClass13 */

            @Override // com.samsung.android.app.watchmanager.setupwizard.contactus.connection.AsyncHttpRequest.AsyncResponse
            public void processFinish(String str) {
                String str2 = ContactUsfragment.TAG;
                Log.d(str2, "istokenValid:" + str);
                if ("true".equals(str)) {
                    progressDialog.cancel();
                    validateTokenCallBack.istokenValid(true);
                    return;
                }
                new AsyncHttpRequest(new AsyncHttpRequest.AsyncResponse() {
                    /* class com.samsung.android.app.watchmanager.setupwizard.contactus.ContactUsfragment.AnonymousClass13.AnonymousClass1 */

                    @Override // com.samsung.android.app.watchmanager.setupwizard.contactus.connection.AsyncHttpRequest.AsyncResponse
                    public void processFinish(String str) {
                        ValidateTokenCallBack validateTokenCallBack;
                        boolean z;
                        String str2 = ContactUsfragment.TAG;
                        Log.d(str2, "str:" + str);
                        progressDialog.cancel();
                        if (str != null) {
                            validateTokenCallBack = validateTokenCallBack;
                            z = true;
                        } else {
                            Log.d(ContactUsfragment.TAG, "Couldn't login into samsung account");
                            validateTokenCallBack = validateTokenCallBack;
                            z = false;
                        }
                        validateTokenCallBack.istokenValid(z);
                    }
                }).execute(10, sharedPreferences.getString("refresh_token_sa", null), sharedPreferences);
            }
        }).execute(9, string, sharedPreferences.getString(GlobalConst.AUTH_SERVER_URL, null));
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        String str = TAG;
        Log.d(str, "SCS::UI::REQUEST_CODE_SA_SIGNIN::onActivityResult() data : " + intent);
        if (i == 1998) {
            if (i2 == -1) {
                Log.d(TAG, "onActivityResult() login success");
            }
        } else if (555 == i && i2 != -1) {
            Log.d(TAG, "onActivityResult() permission denied");
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Log.d(TAG, "onCreateView");
        this.mActivity = getActivity();
        this.mNonSecChnPrivacyConsent = true;
        SharedPreferences sharedPreferences = this.mActivity.getSharedPreferences(GlobalConst.PREF_SM, 0);
        if (!HostManagerUtils.isSamsungDevice() && HostManagerUtils.isChinaPhone(this.mActivity)) {
            this.mNonSecChnPrivacyConsent = false;
            if (this.mActivity != null) {
                this.mNonSecChnPrivacyConsent = sharedPreferences.getBoolean(GlobalConst.CONTACT_US_CHN_PRIVACY_CONSENT, false);
            }
        }
        this.rootView = layoutInflater.inflate(this.mNonSecChnPrivacyConsent ? R.layout.fragment_contact_us : R.layout.fragment_contact_us_agreement_chn, (ViewGroup) null);
        setHasOptionsMenu(true);
        return this.rootView;
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
        try {
            TWatchManagerApplication.getAppContext().unregisterReceiver(this.mSAResponseReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
    }

    public void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
    }

    public void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
    }

    public void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
    }

    public void onViewCreated(View view, Bundle bundle) {
        Log.d(TAG, "onViewCreated");
        super.onViewCreated(view, bundle);
        this.mActivity = getActivity();
        if (this.mNonSecChnPrivacyConsent) {
            this.errorReport = (LinearLayout) this.rootView.findViewById(R.id.errorReportButton);
            this.mActivity.getActionBar().setTitle(this.mActivity.getResources().getString(R.string.welcome_to_samsung_gear_promotion_contact_us).toUpperCase());
            this.errorReport.setOnClickListener(new View.OnClickListener() {
                /* class com.samsung.android.app.watchmanager.setupwizard.contactus.ContactUsfragment.AnonymousClass1 */

                public void onClick(View view) {
                    if (!HostManagerUtilsNetwork.isNetworkAvailable(ContactUsfragment.this.mActivity)) {
                        Toast.makeText(ContactUsfragment.this.mActivity, ContactUsfragment.this.mActivity.getResources().getString(R.string.no_internet_access), 0).show();
                        return;
                    }
                    AnonymousClass1 r3 = new ValidateTokenCallBack() {
                        /* class com.samsung.android.app.watchmanager.setupwizard.contactus.ContactUsfragment.AnonymousClass1.AnonymousClass1 */

                        @Override // com.samsung.android.app.watchmanager.setupwizard.contactus.ContactUsfragment.ValidateTokenCallBack
                        public void istokenValid(boolean z) {
                            if (z) {
                                if (!ContactUsfragment.this.mActivity.getSharedPreferences(GlobalConst.PREF_SM, 0).getBoolean("collect_system_logs_always", false)) {
                                    ContactUsfragment.this.showRadioButtonDialog();
                                } else {
                                    ((ContactUsActivity) ContactUsfragment.this.mActivity).loadErrorReport(-1, true);
                                }
                            } else if (!HostManagerUtilsNetwork.isNetworkAvailable(ContactUsfragment.this.mActivity)) {
                                Toast.makeText(ContactUsfragment.this.mActivity, ContactUsfragment.this.mActivity.getResources().getString(R.string.no_internet_access), 0).show();
                            } else {
                                ContactUsfragment contactUsfragment = ContactUsfragment.this;
                                contactUsfragment.screenToBeLaunched = 2;
                                ContactUsfragment.this.startActivityForResult(new Intent(contactUsfragment.mActivity, SAWebViewActivity.class), GlobalConst.SCS_REQUEST_CODE_SA_SIGNIN_WEBVIEW);
                            }
                        }
                    };
                    ContactUsfragment contactUsfragment = ContactUsfragment.this;
                    contactUsfragment.isSignedIn(contactUsfragment.mActivity.getApplicationContext(), r3);
                }
            });
            this.askQuestions = (LinearLayout) this.rootView.findViewById(R.id.askButton);
            this.askQuestions.setOnClickListener(new View.OnClickListener() {
                /* class com.samsung.android.app.watchmanager.setupwizard.contactus.ContactUsfragment.AnonymousClass2 */

                public void onClick(View view) {
                    if (!HostManagerUtilsNetwork.isNetworkAvailable(ContactUsfragment.this.mActivity)) {
                        Toast.makeText(ContactUsfragment.this.mActivity, ContactUsfragment.this.mActivity.getResources().getString(R.string.no_internet_access), 0).show();
                        return;
                    }
                    AnonymousClass1 r3 = new ValidateTokenCallBack() {
                        /* class com.samsung.android.app.watchmanager.setupwizard.contactus.ContactUsfragment.AnonymousClass2.AnonymousClass1 */

                        @Override // com.samsung.android.app.watchmanager.setupwizard.contactus.ContactUsfragment.ValidateTokenCallBack
                        public void istokenValid(boolean z) {
                            if (z) {
                                ((ContactUsActivity) ContactUsfragment.this.mActivity).loadAskQuestions(-1);
                            } else if (!HostManagerUtilsNetwork.isNetworkAvailable(ContactUsfragment.this.mActivity)) {
                                Toast.makeText(ContactUsfragment.this.mActivity, ContactUsfragment.this.mActivity.getResources().getString(R.string.no_internet_access), 0).show();
                            } else {
                                ContactUsfragment contactUsfragment = ContactUsfragment.this;
                                contactUsfragment.screenToBeLaunched = 1;
                                ContactUsfragment.this.startActivityForResult(new Intent(contactUsfragment.mActivity, SAWebViewActivity.class), GlobalConst.SCS_REQUEST_CODE_SA_SIGNIN_WEBVIEW);
                            }
                        }
                    };
                    ContactUsfragment contactUsfragment = ContactUsfragment.this;
                    contactUsfragment.isSignedIn(contactUsfragment.mActivity.getApplicationContext(), r3);
                }
            });
            this.feedBackList = (LinearLayout) this.rootView.findViewById(R.id.feedbackList);
            this.feedBackList.setOnClickListener(new View.OnClickListener() {
                /* class com.samsung.android.app.watchmanager.setupwizard.contactus.ContactUsfragment.AnonymousClass3 */

                public void onClick(View view) {
                    if (!HostManagerUtilsNetwork.isNetworkAvailable(ContactUsfragment.this.mActivity)) {
                        Toast.makeText(ContactUsfragment.this.mActivity, ContactUsfragment.this.mActivity.getResources().getString(R.string.no_internet_access), 0).show();
                        return;
                    }
                    AnonymousClass1 r3 = new ValidateTokenCallBack() {
                        /* class com.samsung.android.app.watchmanager.setupwizard.contactus.ContactUsfragment.AnonymousClass3.AnonymousClass1 */

                        @Override // com.samsung.android.app.watchmanager.setupwizard.contactus.ContactUsfragment.ValidateTokenCallBack
                        public void istokenValid(boolean z) {
                            if (z) {
                                ((ContactUsActivity) ContactUsfragment.this.mActivity).loadFeedBackListFragment();
                                return;
                            }
                            ContactUsfragment contactUsfragment = ContactUsfragment.this;
                            contactUsfragment.screenToBeLaunched = 3;
                            ContactUsfragment.this.startActivityForResult(new Intent(contactUsfragment.mActivity, SAWebViewActivity.class), GlobalConst.SCS_REQUEST_CODE_SA_SIGNIN_WEBVIEW);
                        }
                    };
                    ContactUsfragment contactUsfragment = ContactUsfragment.this;
                    contactUsfragment.isSignedIn(contactUsfragment.mActivity.getApplicationContext(), r3);
                }
            });
            this.faq = (LinearLayout) this.rootView.findViewById(R.id.faqButton);
            this.faq.setOnClickListener(new View.OnClickListener() {
                /* class com.samsung.android.app.watchmanager.setupwizard.contactus.ContactUsfragment.AnonymousClass4 */

                public void onClick(View view) {
                    ContactUsfragment.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(new ContactUs(ContactUsfragment.this.mActivity).getApplicationURI())));
                }
            });
        } else {
            this.mSensitiveConsent = (SetupwizardCheckboxLayout) this.rootView.findViewById(R.id.sensitive_personal_layout);
            this.mSensitiveConsent.setOnClickTitleLayout(new View.OnClickListener() {
                /* class com.samsung.android.app.watchmanager.setupwizard.contactus.ContactUsfragment.AnonymousClass5 */

                public void onClick(View view) {
                    ContactUsfragment.this.mSensitiveConsent.setChecked(!ContactUsfragment.this.mSensitiveConsent.isChecked());
                }
            });
            this.mSensitiveConsent.setOnClickLearnMoreButton(new View.OnClickListener() {
                /* class com.samsung.android.app.watchmanager.setupwizard.contactus.ContactUsfragment.AnonymousClass6 */

                public void onClick(View view) {
                    ContactUsfragment.this.showLegalDialog(0);
                }
            });
            this.mSensitiveConsent.setOnCheckboxChanged(new CompoundButton.OnCheckedChangeListener() {
                /* class com.samsung.android.app.watchmanager.setupwizard.contactus.ContactUsfragment.AnonymousClass7 */

                public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    boolean z2;
                    ContactUsfragment contactUsfragment;
                    if (!ContactUsfragment.this.mTransferConsent.isChecked() || !ContactUsfragment.this.mSensitiveConsent.isChecked()) {
                        contactUsfragment = ContactUsfragment.this;
                        z2 = false;
                    } else {
                        contactUsfragment = ContactUsfragment.this;
                        z2 = true;
                    }
                    contactUsfragment.setNextButtonEnabled(z2);
                }
            });
            this.mTransferConsent = (SetupwizardCheckboxLayout) this.rootView.findViewById(R.id.transer_personal_layout);
            this.mTransferConsent.setOnClickTitleLayout(new View.OnClickListener() {
                /* class com.samsung.android.app.watchmanager.setupwizard.contactus.ContactUsfragment.AnonymousClass8 */

                public void onClick(View view) {
                    ContactUsfragment.this.mTransferConsent.setChecked(!ContactUsfragment.this.mTransferConsent.isChecked());
                }
            });
            this.mTransferConsent.setOnClickLearnMoreButton(new View.OnClickListener() {
                /* class com.samsung.android.app.watchmanager.setupwizard.contactus.ContactUsfragment.AnonymousClass9 */

                public void onClick(View view) {
                    ContactUsfragment.this.showLegalDialog(1);
                }
            });
            this.mTransferConsent.setOnCheckboxChanged(new CompoundButton.OnCheckedChangeListener() {
                /* class com.samsung.android.app.watchmanager.setupwizard.contactus.ContactUsfragment.AnonymousClass10 */

                public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    boolean z2;
                    ContactUsfragment contactUsfragment;
                    if (!ContactUsfragment.this.mTransferConsent.isChecked() || !ContactUsfragment.this.mSensitiveConsent.isChecked()) {
                        contactUsfragment = ContactUsfragment.this;
                        z2 = false;
                    } else {
                        contactUsfragment = ContactUsfragment.this;
                        z2 = true;
                    }
                    contactUsfragment.setNextButtonEnabled(z2);
                }
            });
            this.mNextButton = (LinearLayout) this.rootView.findViewById(R.id.next_button_items_layout);
            this.mNextTV = (TextView) this.rootView.findViewById(R.id.next_button_tv);
            this.mNextButton.setOnClickListener(new View.OnClickListener() {
                /* class com.samsung.android.app.watchmanager.setupwizard.contactus.ContactUsfragment.AnonymousClass11 */

                public void onClick(View view) {
                    SharedPreferences.Editor edit = ContactUsfragment.this.mActivity.getSharedPreferences(GlobalConst.PREF_SM, 0).edit();
                    edit.putBoolean(GlobalConst.CONTACT_US_CHN_PRIVACY_CONSENT, true);
                    edit.apply();
                    ContactUsfragment.this.mActivity.finish();
                    ContactUsfragment.this.mActivity.startActivity(new Intent(ContactUsfragment.this.getActivity(), ContactUsActivity.class));
                }
            });
            setNextButtonEnabled(false);
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SAWebViewActivity.ACTION_SA_WEBVIEW_LOGIN_SUCCESS);
        TWatchManagerApplication.getAppContext().registerReceiver(this.mSAResponseReceiver, intentFilter);
    }

    public void setNextButtonEnabled(boolean z) {
        this.mNextButton.setEnabled(z);
        TypedValue typedValue = new TypedValue();
        getResources().getValue(R.dimen.oobe_bottom_btn_text_opacity, typedValue, true);
        this.mNextTV.setAlpha(z ? 0.85f : typedValue.getFloat());
    }
}
