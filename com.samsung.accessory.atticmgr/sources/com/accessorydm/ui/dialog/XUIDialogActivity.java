package com.accessorydm.ui.dialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.accessorydm.interfaces.XDMDefInterface;
import com.accessorydm.ui.UIManager;
import com.samsung.android.fotaprovider.log.Log;

public class XUIDialogActivity extends AppCompatActivity {
    private static Activity m_DialogActivity;
    private static int m_DialogId;
    private boolean bIsDialog = false;

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        int i;
        super.onCreate(bundle);
        UIManager.getInstance().put(this);
        m_DialogActivity = this;
        String action = getIntent().getAction();
        if (action == null) {
            i = 0;
        } else {
            i = Integer.parseInt(action);
        }
        m_DialogId = i;
    }

    /* access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity
    public void onPause() {
        this.bIsDialog = false;
        super.onPause();
    }

    /* access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity
    public void onNewIntent(Intent intent) {
        int i;
        Log.I("");
        String action = intent.getAction();
        if (action == null) {
            i = 0;
        } else {
            i = Integer.parseInt(action);
        }
        int i2 = m_DialogId;
        if (i2 == i) {
            this.bIsDialog = true;
        } else {
            if (i2 > 0) {
                dismissDialogFragment(i2);
            }
            m_DialogId = i;
        }
        super.onNewIntent(intent);
    }

    /* access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity
    public void onResume() {
        Log.I("");
        try {
            if (getSupportFragmentManager().findFragmentById(m_DialogId) == null && !this.bIsDialog) {
                showDialogFragment(m_DialogId);
            }
        } catch (Exception e) {
            Log.E(e.toString());
        }
        super.onResume();
    }

    /* access modifiers changed from: protected */
    public void onUserLeaveHint() {
        xuiRemoveDialog();
        super.onUserLeaveHint();
    }

    /* access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity
    public void onDestroy() {
        Log.I("");
        UIManager.getInstance().remove(this);
        super.onDestroy();
    }

    private void showDialogFragment(int i) {
        Log.I("Show dialog id : " + XUIDialog.valueOf(i));
        XUIDialogFragment newInstance = XUIDialogFragment.newInstance(i);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        newInstance.show(supportFragmentManager, XDMDefInterface.XDM_DIALOG_TAG + i);
    }

    private void dismissDialogFragment(int i) {
        if (getFragmentManager() != null) {
            FragmentManager supportFragmentManager = getSupportFragmentManager();
            Fragment findFragmentByTag = supportFragmentManager.findFragmentByTag(XDMDefInterface.XDM_DIALOG_TAG + i);
            if (findFragmentByTag != null) {
                ((DialogFragment) findFragmentByTag).dismiss();
            }
        }
    }

    public static void xuiRemoveDialog() {
        try {
            if (m_DialogActivity != null) {
                m_DialogId = 0;
                Log.I("DialogActivity Remove and reset mDialogId");
                m_DialogActivity.finish();
            }
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    public static boolean xuiCheckDialog(int i) {
        if (m_DialogActivity == null || m_DialogId != i) {
            return false;
        }
        Log.I("XUIDialogActivity show dialog : " + i);
        return true;
    }
}
