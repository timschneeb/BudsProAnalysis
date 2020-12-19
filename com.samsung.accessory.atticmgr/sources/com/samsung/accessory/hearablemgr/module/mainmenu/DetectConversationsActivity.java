package com.samsung.accessory.hearablemgr.module.mainmenu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.appcompat.widget.SeslSwitchBar;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.ui.UiUtil;
import com.samsung.accessory.hearablemgr.core.bigdata.SA;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;
import com.samsung.accessory.hearablemgr.core.service.message.MsgID;
import com.samsung.accessory.hearablemgr.core.service.message.MsgSimple;
import com.samsung.accessory.hearablemgr.module.base.ConnectionActivity;
import seccompat.android.util.Log;

public class DetectConversationsActivity extends ConnectionActivity {
    public static final int DEFAULT_INDEX = 1;
    public static final int[] DETECT_SECONDS = {5, 10, 15};
    private static final String TAG = (Application.TAG_ + DetectConversationsActivity.class.getSimpleName());
    private SeslSwitchBar mSwitchBar;
    LinearLayout optionContainer;

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, com.samsung.accessory.hearablemgr.module.base.OrientationPolicyActivity, androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onCreate(Bundle bundle) {
        Log.i(TAG, "onCreate()");
        super.onCreate(bundle);
        setContentView(R.layout.activity_detect_conversations);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        init();
        initView();
        initListener();
    }

    /* access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity, com.samsung.accessory.hearablemgr.module.base.ConnectionActivity
    public void onResume() {
        super.onResume();
        SamsungAnalyticsUtil.sendPage(SA.Screen.CONVERSATION_AWARE);
    }

    private void init() {
        this.mSwitchBar = (SeslSwitchBar) findViewById(R.id.switch_bar);
        this.optionContainer = (LinearLayout) findViewById(R.id.layout_voice_detect_option);
        initOption();
    }

    private void initOption() {
        this.optionContainer.removeAllViews();
        for (int i = 0; i < DETECT_SECONDS.length; i++) {
            int indexToSeconds = indexToSeconds(i);
            addOption(this.optionContainer, getResources().getQuantityString(R.plurals.detect_conversations_about_seconds, indexToSeconds, Integer.valueOf(indexToSeconds)), i);
        }
    }

    private LinearLayout addOption(LinearLayout linearLayout, String str, int i) {
        LinearLayout linearLayout2 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.view_radio_detect_conversations, (ViewGroup) null);
        RadioButton radioButton = (RadioButton) linearLayout2.findViewById(R.id.radio_button);
        radioButton.setOnClickListener(new View.OnClickListener(i) {
            /* class com.samsung.accessory.hearablemgr.module.mainmenu.$$Lambda$DetectConversationsActivity$6j9sUfp74hHmNgkKt6nnOSdiMfY */
            private final /* synthetic */ int f$1;

            {
                this.f$1 = r2;
            }

            public final void onClick(View view) {
                DetectConversationsActivity.this.lambda$addOption$0$DetectConversationsActivity(this.f$1, view);
            }
        });
        ((TextView) linearLayout2.findViewById(R.id.text_radio_option)).setText(str);
        if (i == 0) {
            linearLayout2.findViewById(R.id.divider).setVisibility(8);
        }
        linearLayout2.setOnClickListener(new View.OnClickListener(radioButton) {
            /* class com.samsung.accessory.hearablemgr.module.mainmenu.$$Lambda$DetectConversationsActivity$zu1QEB3C1kc50k1POtam98k4RWw */
            private final /* synthetic */ RadioButton f$0;

            {
                this.f$0 = r1;
            }

            public final void onClick(View view) {
                this.f$0.performClick();
            }
        });
        linearLayout2.setTag(Integer.valueOf(i));
        linearLayout.addView(linearLayout2);
        return linearLayout2;
    }

    public /* synthetic */ void lambda$addOption$0$DetectConversationsActivity(int i, View view) {
        setOption(i);
        SamsungAnalyticsUtil.sendEvent(SA.Event.CONVERSATION_MODE_TIME_SETTING, SA.Screen.CONVERSATION_AWARE, SamsungAnalyticsUtil.conversationModeEndTimeIndexToDetail(i));
    }

    private void initListener() {
        this.mSwitchBar.addOnSwitchChangeListener(new SeslSwitchBar.OnSwitchChangeListener() {
            /* class com.samsung.accessory.hearablemgr.module.mainmenu.$$Lambda$DetectConversationsActivity$gGyRquh565__ONgJDjVMN4nsuSM */

            @Override // androidx.appcompat.widget.SeslSwitchBar.OnSwitchChangeListener
            public final void onSwitchChanged(SwitchCompat switchCompat, boolean z) {
                DetectConversationsActivity.this.lambda$initListener$2$DetectConversationsActivity(switchCompat, z);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$2$DetectConversationsActivity(SwitchCompat switchCompat, boolean z) {
        setVoiceDetect(z);
        SamsungAnalyticsUtil.sendEvent(SA.Event.VOICE_DETECT_SWITCH, SA.Screen.CONVERSATION_AWARE, z ? "b" : "a");
    }

    private void initView() {
        setSwitchBackground(Application.getCoreService().getEarBudsInfo().detectConversations);
        setEnabledOption(Application.getCoreService().getEarBudsInfo().detectConversations);
        setRadioButton(Application.getCoreService().getEarBudsInfo().detectConversationsDuration);
    }

    private void setRadioButton(int i) {
        clearRadioButton(this.optionContainer);
        ((RadioButton) findViewByTag(this.optionContainer, Integer.valueOf(i)).findViewById(R.id.radio_button)).setChecked(true);
    }

    private void clearRadioButton(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            ((RadioButton) viewGroup.getChildAt(i).findViewById(R.id.radio_button)).setChecked(false);
        }
    }

    public static View findViewByTag(ViewGroup viewGroup, Object obj) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childAt = viewGroup.getChildAt(i);
            if (obj.equals(childAt.getTag())) {
                return childAt;
            }
        }
        return null;
    }

    private void setVoiceDetect(boolean z) {
        Application.getCoreService().getEarBudsInfo().detectConversations = z;
        Application.getCoreService().sendSppMessage(new MsgSimple(MsgID.SET_DETECT_CONVERSATIONS, Application.getCoreService().getEarBudsInfo().detectConversations ? (byte) 1 : 0));
        setSwitchBackground(z);
        setEnabledOption(z);
        SamsungAnalyticsUtil.setStatusInt(SA.Status.VOICE_DETECT_STATUS, z ? 1 : 0);
    }

    private void setSwitchBackground(boolean z) {
        if (this.mSwitchBar.isChecked() != z) {
            this.mSwitchBar.setChecked(z);
        }
    }

    private void setEnabledOption(boolean z) {
        UiUtil.setEnabledWithChildren(this.optionContainer, z);
    }

    private void setOption(int i) {
        setRadioButton(i);
        sendSppMessageOption(i);
    }

    private static void sendSppMessageOption(int i) {
        Application.getCoreService().getEarBudsInfo().detectConversationsDuration = i;
        Application.getCoreService().sendSppMessage(new MsgSimple(MsgID.SET_DETECT_CONVERSATIONS_DURATION, (byte) i));
        SamsungAnalyticsUtil.setStatusString(SA.Status.CONVERSATION_MODE_TIME_SETTING, SamsungAnalyticsUtil.conversationModeEndTimeIndexToDetail(i));
    }

    public static int indexToSeconds(int i) {
        if (i < 0 || i >= DETECT_SECONDS.length) {
            String str = TAG;
            Log.d(str, "out of bound index : " + i + ", set to default seconds (10 seconds)");
            i = 1;
            sendSppMessageOption(1);
        }
        return DETECT_SECONDS[i];
    }

    @Override // androidx.appcompat.app.AppCompatActivity
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
