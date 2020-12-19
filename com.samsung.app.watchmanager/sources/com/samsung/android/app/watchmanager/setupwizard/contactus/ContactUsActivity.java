package com.samsung.android.app.watchmanager.setupwizard.contactus;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.watchmanager.R;
import com.samsung.android.app.watchmanager.setupwizard.SetupWizardWelcomeActivity;

public class ContactUsActivity extends Activity {
    String pluginName = "Gear Manager";

    public static String getMCC(Context context) {
        Log.d("ContactUsActivity", "getMCC()");
        TelephonyManager telephonyManager = (TelephonyManager) context.getApplicationContext().getSystemService("phone");
        if (telephonyManager != null) {
            String simOperator = telephonyManager.getSimOperator();
            Log.d("ContactUsActivity", " getMCC() networkOperator:" + simOperator);
            if (simOperator != null && simOperator.length() >= 3) {
                return simOperator.substring(0, 3);
            }
        }
        return "404";
    }

    /* access modifiers changed from: protected */
    public void initActionBar() {
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.show();
            if (Build.VERSION.SDK_INT >= 21) {
                actionBar.setElevation(0.0f);
            }
            actionBar.setTitle("CONTACT US");
            actionBar.setHomeAsUpIndicator(R.drawable.tw_ic_ab_back_mtrl);
        }
    }

    public void loadAskQuestions(long j) {
        FragmentTransaction beginTransaction = getFragmentManager().beginTransaction();
        AskandErrorReportFragment askandErrorReportFragment = new AskandErrorReportFragment();
        Bundle bundle = new Bundle();
        if (j != -1) {
            bundle.putLong(AskandErrorReportFragment.FEEDBACKID, j);
        }
        bundle.putBoolean(AskandErrorReportFragment.ASK_QUESTIONS, true);
        bundle.putString("pluginName", this.pluginName);
        askandErrorReportFragment.setArguments(bundle);
        beginTransaction.replace(R.id.fragment_container, askandErrorReportFragment, "askquestions");
        beginTransaction.addToBackStack("askquestions");
        beginTransaction.commit();
    }

    public void loadErrorReport(long j, boolean z) {
        FragmentTransaction beginTransaction = getFragmentManager().beginTransaction();
        AskandErrorReportFragment askandErrorReportFragment = new AskandErrorReportFragment();
        Bundle bundle = new Bundle();
        if (j != -1) {
            bundle.putLong(AskandErrorReportFragment.FEEDBACKID, j);
        }
        bundle.putString("pluginName", this.pluginName);
        bundle.putBoolean("checkbokChecked", z);
        askandErrorReportFragment.setArguments(bundle);
        beginTransaction.replace(R.id.fragment_container, askandErrorReportFragment, "errorreport");
        beginTransaction.addToBackStack("errorreport");
        beginTransaction.commit();
    }

    public void loadFeedBackDetailFragment(long j) {
        FragmentTransaction beginTransaction = getFragmentManager().beginTransaction();
        FeedBackDetailFragment feedBackDetailFragment = new FeedBackDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(AskandErrorReportFragment.FEEDBACKID, j);
        feedBackDetailFragment.setArguments(bundle);
        beginTransaction.replace(R.id.fragment_container, feedBackDetailFragment, "feedbackdetail");
        beginTransaction.addToBackStack("feedbackdetail");
        beginTransaction.commit();
    }

    public void loadFeedBackListFragment() {
        FragmentTransaction beginTransaction = getFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.fragment_container, new FeedBackListFragment(), AskandErrorReportFragment.FEEDBACKLIST);
        beginTransaction.addToBackStack(AskandErrorReportFragment.FEEDBACKLIST);
        beginTransaction.commit();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        android.util.Log.d("ContactUsActivity", "onCreate");
        super.onCreate(bundle);
        setContentView(R.layout.activity_contact_us);
        Intent intent = getIntent();
        if (intent.hasExtra(SetupWizardWelcomeActivity.EXTRA_IS_FROM_PLUGIN) && intent.getBooleanExtra(SetupWizardWelcomeActivity.EXTRA_IS_FROM_PLUGIN, false)) {
            this.pluginName = intent.getStringExtra("pluginName");
        }
        initActionBar();
        FragmentTransaction beginTransaction = getFragmentManager().beginTransaction();
        beginTransaction.add(R.id.fragment_container, new ContactUsfragment(), "contactus");
        beginTransaction.commit();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        android.util.Log.d("ContactUsActivity", "onDestroy");
        super.onDestroy();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        onBackPressed();
        return true;
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        android.util.Log.d("ContactUsActivity", "onPause");
        super.onPause();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        android.util.Log.d("ContactUsActivity", "onResume");
        super.onResume();
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        android.util.Log.d("ContactUsActivity", "onStart");
        super.onStart();
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        android.util.Log.d("ContactUsActivity", "onStop");
        super.onStop();
    }
}
