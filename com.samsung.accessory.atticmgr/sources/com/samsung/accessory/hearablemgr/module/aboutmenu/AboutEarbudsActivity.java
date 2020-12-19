package com.samsung.accessory.hearablemgr.module.aboutmenu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.preference.PreferenceKey;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import com.samsung.accessory.hearablemgr.common.uhm.UhmFwUtil;
import com.samsung.accessory.hearablemgr.common.ui.OnSingleClickListener;
import com.samsung.accessory.hearablemgr.common.util.BluetoothUtil;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.core.EarBudsInfo;
import com.samsung.accessory.hearablemgr.core.bigdata.SA;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;
import com.samsung.accessory.hearablemgr.core.service.CoreService;
import com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity;
import com.samsung.accessory.hearablemgr.module.mainmenu.GeneralActivity;
import com.samsung.android.sdk.mobileservice.social.group.provider.GroupInvitationContract;
import seccompat.android.util.Log;

public class AboutEarbudsActivity extends PermissionCheckActivity {
    private static final int LEFT = 0;
    private static final int RENAME_MAX_INPUT_LENGTH = 62;
    private static final int RIGHT = 1;
    private static final String TAG = "Attic_AboutEarbudsActivity";
    private Button button;
    private TextView deviceName;
    private AppCompatButton editButton;
    public InputFilter inputFilter = new InputFilter() {
        /* class com.samsung.accessory.hearablemgr.module.aboutmenu.AboutEarbudsActivity.AnonymousClass11 */

        public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
            if ("\n".equals(charSequence)) {
                return "";
            }
            if (!Util.hasEmoji(charSequence)) {
                return charSequence;
            }
            Toast.makeText(Application.getContext(), AboutEarbudsActivity.this.getString(R.string.about_earbuds_edit_invalid_character), 0).show();
            return spanned.subSequence(i3, i4);
        }
    };
    private boolean invalidInputFlag = false;
    private boolean isEnteredName = false;
    private TextView leftSerialNumber;
    public InputFilter.LengthFilter lengthFilter = new InputFilter.LengthFilter(62) {
        /* class com.samsung.accessory.hearablemgr.module.aboutmenu.AboutEarbudsActivity.AnonymousClass10 */

        public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
            CharSequence filter = super.filter(charSequence, i, i2, spanned, i3, i4);
            if (filter != null) {
                AboutEarbudsActivity.this.invalidInputFlag = true;
                Log.d(AboutEarbudsActivity.TAG, "lengthFilter : invalidInputFlag" + AboutEarbudsActivity.this.invalidInputFlag);
            }
            return filter;
        }
    };
    private View mLabelL;
    private View mLabelR;
    private View mLayoutLeftSerialNumber;
    private View mLayoutRightSerialNumber;
    private String mMessage = GroupInvitationContract.Invitation.MESSAGE;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        /* class com.samsung.accessory.hearablemgr.module.aboutmenu.AboutEarbudsActivity.AnonymousClass12 */

        /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
        public void onReceive(Context context, Intent intent) {
            char c;
            Log.d(AboutEarbudsActivity.TAG, "onReceive() : " + intent.getAction());
            String action = intent.getAction();
            switch (action.hashCode()) {
                case -1854841232:
                    if (action.equals(CoreService.ACTION_MSG_ID_STATUS_UPDATED)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -1658427642:
                    if (action.equals(CoreService.ACTION_MSG_ID_DEBUG_SERIAL_NUMBER)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 1215575359:
                    if (action.equals(CoreService.ACTION_MSG_ID_DEBUG_GET_ALL_DATA)) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 1335721824:
                    if (action.equals(CoreService.ACTION_DEVICE_DISCONNECTED)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1403073508:
                    if (action.equals(CoreService.ACTION_DEVICE_CONNECTED)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            if (c == 0 || c == 1 || c == 2 || c == 3 || c == 4) {
                AboutEarbudsActivity.this.updateUI();
            }
        }
    };
    private TextView rightSerialNumber;
    private TextView sw_version;

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, com.samsung.accessory.hearablemgr.module.base.OrientationPolicyActivity, androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onCreate(Bundle bundle) {
        Log.d(TAG, "onCreate()");
        super.onCreate(bundle);
        setContentView(R.layout.activity_about_earbuds);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        initView();
        registerReceiver();
        findViewById(R.id.menu_legal_information).setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.aboutmenu.AboutEarbudsActivity.AnonymousClass1 */

            public void onClick(View view) {
                SamsungAnalyticsUtil.sendEvent(SA.Event.LEGAL_INFORMATION, SA.Screen.ABOUT_EARBUDS);
                AboutEarbudsActivity aboutEarbudsActivity = AboutEarbudsActivity.this;
                aboutEarbudsActivity.startActivity(new Intent(aboutEarbudsActivity, LegalnformationActivity.class));
            }
        });
        findViewById(R.id.menu_battery_information).setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.aboutmenu.AboutEarbudsActivity.AnonymousClass2 */

            public void onClick(View view) {
                SamsungAnalyticsUtil.sendEvent(SA.Event.BATTERY_INFORMATION, SA.Screen.ABOUT_EARBUDS);
                AboutEarbudsActivity aboutEarbudsActivity = AboutEarbudsActivity.this;
                aboutEarbudsActivity.startActivity(new Intent(aboutEarbudsActivity, BatteryInformationActivity.class));
            }
        });
        findViewById(R.id.menu_reset_earbuds).setOnClickListener(new OnSingleClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.aboutmenu.AboutEarbudsActivity.AnonymousClass3 */

            @Override // com.samsung.accessory.hearablemgr.common.ui.OnSingleClickListener
            public void onSingleClick(View view) {
                SamsungAnalyticsUtil.sendEvent(SA.Event.ELSE_RESET_EARBUDS, SA.Screen.ABOUT_EARBUDS);
                AboutEarbudsActivity aboutEarbudsActivity = AboutEarbudsActivity.this;
                aboutEarbudsActivity.startActivity(new Intent(aboutEarbudsActivity, GeneralActivity.class));
            }
        });
        updateResetMenu();
        updateEditDeviceName();
    }

    /* access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onResume() {
        super.onResume();
        SamsungAnalyticsUtil.sendPage(SA.Screen.ABOUT_EARBUDS);
        updateUI();
    }

    /* access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onPause() {
        super.onPause();
    }

    /* access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onDestroy() {
        unregisterReceiver(this.mReceiver);
        super.onDestroy();
    }

    private void initView() {
        this.deviceName = (TextView) findViewById(R.id.device_name);
        this.editButton = (AppCompatButton) findViewById(R.id.edit_button);
        this.leftSerialNumber = (TextView) findViewById(R.id.left_serial_number);
        this.rightSerialNumber = (TextView) findViewById(R.id.right_serial_number);
        this.mLabelL = findViewById(R.id.label_left);
        this.mLabelR = findViewById(R.id.label_right);
        this.mLayoutLeftSerialNumber = findViewById(R.id.layout_left_serial_number);
        this.mLayoutRightSerialNumber = findViewById(R.id.layout_right_serial_number);
        this.sw_version = (TextView) findViewById(R.id.menu_sw_version);
        ((TextView) findViewById(R.id.text_model_name)).setText("SM-R190");
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateUI() {
        Log.d(TAG, "updateUI()");
        updateEditDeviceName();
        updateResetMenu();
        updateDeviceName();
        updateSerialNumber();
        updateSWVersion();
    }

    private void updateEditDeviceName() {
        this.editButton = (AppCompatButton) findViewById(R.id.edit_button);
        if (!Util.isSamsungDevice() || !Application.getCoreService().isConnected()) {
            this.editButton.setVisibility(4);
        } else {
            this.editButton.setVisibility(0);
        }
        this.editButton.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.aboutmenu.AboutEarbudsActivity.AnonymousClass4 */

            public void onClick(View view) {
                SamsungAnalyticsUtil.sendEvent(SA.Event.DEVICE_NAME_EDIT, SA.Screen.ABOUT_EARBUDS);
                AboutEarbudsActivity.this.editDialog();
            }
        });
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void editDialog() {
        Log.d(TAG, "editDialog");
        this.mMessage = BluetoothUtil.getAliasName(UhmFwUtil.getLastLaunchDeviceId());
        View inflate = View.inflate(this, R.layout.edit_text_rename, null);
        final TextView textView = (TextView) inflate.findViewById(R.id.text_invalid_reason);
        textView.setPaintFlags(textView.getPaintFlags() | 8);
        Application.getContext();
        final InputMethodManager inputMethodManager = (InputMethodManager) getSystemService("input_method");
        final EditText editText = (EditText) inflate.findViewById(R.id.editText_device_name);
        editText.setText(this.mMessage);
        editText.requestFocus();
        editText.selectAll();
        boolean z = false;
        editText.setFilters(new InputFilter[]{this.inputFilter, this.lengthFilter});
        editText.setPrivateImeOptions("inputType=PredictionOff;disableEmoticonInput=true;disableImage=true;disableSticker=true;disableGifKeyboard=true");
        editText.addTextChangedListener(new TextWatcher() {
            /* class com.samsung.accessory.hearablemgr.module.aboutmenu.AboutEarbudsActivity.AnonymousClass5 */

            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                int length = editText.getText().toString().length();
                AboutEarbudsActivity.this.isEnteredName = length > 0;
                AboutEarbudsActivity.this.button.setEnabled(AboutEarbudsActivity.this.isEnteredName);
                if (AboutEarbudsActivity.this.invalidInputFlag || length >= 62) {
                    AboutEarbudsActivity.this.invalidInputFlag = false;
                    textView.setText(AboutEarbudsActivity.this.getResources().getQuantityString(R.plurals.about_earbuds_edit_invalid_length, 62, 62));
                    textView.setVisibility(0);
                    return;
                }
                editText.getBackground().clearColorFilter();
                textView.setVisibility(4);
            }
        });
        if (editText.getText().toString().length() > 0) {
            z = true;
        }
        this.isEnteredName = z;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.about_earbuds_edit_title));
        builder.setView(inflate);
        builder.setNegativeButton(getApplicationContext().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.aboutmenu.AboutEarbudsActivity.AnonymousClass6 */

            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setPositiveButton(R.string.about_earbuds_edit_rename, new DialogInterface.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.aboutmenu.AboutEarbudsActivity.AnonymousClass7 */

            public void onClick(DialogInterface dialogInterface, int i) {
                AboutEarbudsActivity.this.mMessage = editText.getText().toString();
                BluetoothUtil.setAliasName(UhmFwUtil.getLastLaunchDeviceId(), AboutEarbudsActivity.this.mMessage);
                AboutEarbudsActivity.this.updateDeviceName();
                dialogInterface.cancel();
            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            /* class com.samsung.accessory.hearablemgr.module.aboutmenu.AboutEarbudsActivity.AnonymousClass8 */

            public void onDismiss(DialogInterface dialogInterface) {
                AboutEarbudsActivity.this.hideKeyboard();
            }
        });
        builder.setCancelable(true);
        this.button = builder.show().getButton(-1);
        this.button.setEnabled(this.isEnteredName);
        editText.postDelayed(new Runnable() {
            /* class com.samsung.accessory.hearablemgr.module.aboutmenu.AboutEarbudsActivity.AnonymousClass9 */

            public void run() {
                inputMethodManager.showSoftInput(editText, 1);
            }
        }, 100);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void hideKeyboard() {
        Application.getContext();
        ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(this.editButton.getWindowToken(), 1);
    }

    private void updateResetMenu() {
        if (Application.getCoreService().isConnected()) {
            findViewById(R.id.menu_reset_earbuds_title).setEnabled(true);
            findViewById(R.id.menu_reset_earbuds).setEnabled(true);
            return;
        }
        findViewById(R.id.menu_reset_earbuds_title).setEnabled(false);
        findViewById(R.id.menu_reset_earbuds).setEnabled(false);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateDeviceName() {
        String str;
        String lastLaunchDeviceId = UhmFwUtil.getLastLaunchDeviceId();
        if (lastLaunchDeviceId != null) {
            str = BluetoothUtil.getAliasName(lastLaunchDeviceId);
            if (str == null) {
                str = Application.getUhmDatabase().getDeviceName(lastLaunchDeviceId);
            }
        } else {
            str = null;
        }
        Log.d(TAG, "updateDeviceName() : name = " + str);
        if (str == null) {
            str = getString(R.string.app_name);
        }
        AboutEarbudsActivityUtil.setDeviceName(this.deviceName, str);
    }

    private void updateSerialNumber() {
        setVisibilitySerialNumber(0);
        setVisibilitySerialNumber(1);
    }

    private void setVisibilitySerialNumber(int i) {
        int i2;
        TextView textView = i == 0 ? this.leftSerialNumber : this.rightSerialNumber;
        View view = i == 0 ? this.mLabelL : this.mLabelR;
        View view2 = i == 0 ? this.mLayoutLeftSerialNumber : this.mLayoutRightSerialNumber;
        String serialNumber = getSerialNumber(i);
        if (serialNumber != null) {
            textView.setText(serialNumber);
            i2 = 0;
        } else {
            i2 = 8;
        }
        view2.setVisibility(i2);
        view.setVisibility(i2);
    }

    private String getSerialNumber(int i) {
        EarBudsInfo earBudsInfo = Application.getCoreService().getEarBudsInfo();
        return i == 0 ? earBudsInfo.serialNumber_left != null ? earBudsInfo.serialNumber_left : Preferences.getString(PreferenceKey.LEFT_INFO_SN, null) : earBudsInfo.serialNumber_right != null ? earBudsInfo.serialNumber_right : Preferences.getString(PreferenceKey.RIGHT_INFO_SN, null);
    }

    private void updateSWVersion() {
        AboutEarbudsActivityUtil.updateSWVersion(this.sw_version);
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CoreService.ACTION_DEVICE_CONNECTED);
        intentFilter.addAction(CoreService.ACTION_DEVICE_DISCONNECTED);
        intentFilter.addAction(CoreService.ACTION_MSG_ID_STATUS_UPDATED);
        intentFilter.addAction(CoreService.ACTION_MSG_ID_DEBUG_SERIAL_NUMBER);
        intentFilter.addAction(CoreService.ACTION_MSG_ID_DEBUG_GET_ALL_DATA);
        registerReceiver(this.mReceiver, intentFilter);
    }

    @Override // androidx.appcompat.app.AppCompatActivity
    public boolean onSupportNavigateUp() {
        SamsungAnalyticsUtil.sendEvent(SA.Event.UP_BUTTON, SA.Screen.ABOUT_EARBUDS);
        finish();
        return true;
    }
}
