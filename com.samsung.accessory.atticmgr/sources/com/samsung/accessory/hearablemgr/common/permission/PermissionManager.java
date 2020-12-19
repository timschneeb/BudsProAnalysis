package com.samsung.accessory.hearablemgr.common.permission;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import java.util.ArrayList;
import java.util.HashSet;
import seccompat.android.util.Log;

public class PermissionManager {
    public static final int ALL_DENIED = -2;
    public static final int ALL_GRANTED = 1;
    public static final String[] ALL_PERMISSION_LIST = {"android.permission.READ_PHONE_STATE", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.READ_CONTACTS", "android.permission.READ_CALENDAR", "android.permission.READ_SMS", "android.permission.READ_CALL_LOG"};
    public static final int DENIED = -1;
    public static final int GRANTED = 0;
    public static final int PEMISSION_REQUEST_CODE = 100;
    public static final int PERMISSION_READ_CALENDAR = 1;
    public static final int PERMISSION_READ_CONTACTS = 0;
    public static final int PERMISSION_READ_EXTERNAL_STORAGE = 4;
    public static final int PERMISSION_READ_PHONE_STATE = 2;
    public static final int PERMISSION_READ_SMS = 3;
    private static final String TAG = "Attic_PermissionManager";
    public static PermissionManager instance = null;

    public interface DialogListener {
        void onRequestDismissAction();

        void onRequestNegativeAction();

        void onRequestPositiveAction();
    }

    private PermissionManager() {
    }

    public static boolean isPermissionGranted(Context context, String str) {
        return ContextCompat.checkSelfPermission(context, str) == 0;
    }

    public static boolean isBasicPermissionGranted(Context context, String[] strArr) {
        for (String str : strArr) {
            if (ContextCompat.checkSelfPermission(context, str) == -1) {
                Log.d(TAG, "No permission!");
                return false;
            }
        }
        for (String str2 : strArr) {
            setPermissionNeverAskAgain(str2, false);
        }
        return true;
    }

    public static PermissionManager getInstance() {
        if (instance == null) {
            instance = new PermissionManager();
        }
        return instance;
    }

    public void onDestroy() {
        instance = null;
    }

    public static boolean isSystemDialogEnable(Context context, String[] strArr) {
        Log.v(TAG, "isSystemDialogEnable()");
        for (int i = 0; i < strArr.length; i++) {
            if (!(isPermissionGranted(context, strArr[i]) || getPermissionNeverAskAgain(strArr[i]))) {
                return true;
            }
        }
        return false;
    }

    public static void setPermissionNeverAskAgain(String str, boolean z) {
        Log.d(TAG, "setPermissionNaverAskAgain val = " + z);
        Preferences.putBoolean(str, Boolean.valueOf(z));
    }

    public static boolean getPermissionNeverAskAgain(String str) {
        return Preferences.getBoolean(str, false);
    }

    public static AlertDialog showPermissionSettingsDialog(Activity activity, String str, String[] strArr, final DialogListener dialogListener) {
        Log.i(TAG, "mStringArray.length" + strArr.length);
        HashSet hashSet = new HashSet(strArr.length);
        PackageManager packageManager = activity.getPackageManager();
        for (String str2 : strArr) {
            Log.d(TAG, "permission : " + str2);
            if (!isPermissionGranted(activity, str2)) {
                try {
                    String str3 = packageManager.getPermissionInfo(str2, 128).group;
                    if (str3.equalsIgnoreCase("android.permission-group.UNDEFINED")) {
                        str3 = getHardcodeGroupName(str2);
                        Log.i(TAG, "Match: " + str2 + " with group: " + str3);
                    }
                    if (str3 != null) {
                        if (!str3.isEmpty()) {
                            PermissionGroupInfo permissionGroupInfo = packageManager.getPermissionGroupInfo(str3, 128);
                            hashSet.add(new PermissionItem(permissionGroupInfo.loadIcon(packageManager), permissionGroupInfo.loadLabel(packageManager).toString()));
                        }
                    }
                    PermissionItem permissionGroup = getPermissionGroup(str2, activity);
                    if (permissionGroup != null) {
                        hashSet.add(permissionGroup);
                        Log.i(TAG, "Read hardcode permission group: " + str2 + " match: " + permissionGroup.text);
                    }
                } catch (Exception e) {
                    Log.w(TAG, "Permission label fetch", e);
                }
            }
        }
        if (hashSet.size() > 0) {
            PermissionListAdapter permissionListAdapter = new PermissionListAdapter(activity, new ArrayList(hashSet));
            View inflate = ((LayoutInflater) activity.getSystemService("layout_inflater")).inflate(R.layout.dialog_permission_list, (ViewGroup) null);
            ((ListView) inflate.findViewById(R.id.permission_list)).setAdapter((ListAdapter) permissionListAdapter);
            ((TextView) inflate.findViewById(R.id.popup_message_textview)).setText(getSpannableMessage(activity.getString(R.string.warning_message_for_Runtime_permission, new Object[]{str}), str));
            final AlertDialog create = new AlertDialog.Builder(activity).setView(inflate).setCancelable(false).create();
            ((TextView) inflate.findViewById(R.id.cancel_btn)).setOnClickListener(new View.OnClickListener() {
                /* class com.samsung.accessory.hearablemgr.common.permission.PermissionManager.AnonymousClass1 */

                public void onClick(View view) {
                    Log.d(PermissionManager.TAG, "Clicked cancel on permission dialog");
                    dialogListener.onRequestNegativeAction();
                }
            });
            ((TextView) inflate.findViewById(R.id.settings)).setOnClickListener(new View.OnClickListener() {
                /* class com.samsung.accessory.hearablemgr.common.permission.PermissionManager.AnonymousClass2 */

                public void onClick(View view) {
                    create.dismiss();
                    dialogListener.onRequestPositiveAction();
                }
            });
            if (create != null) {
                create.show();
                return create;
            }
        }
        return null;
    }

    private static SpannableString getSpannableMessage(String str, String str2) {
        SpannableString spannableString = new SpannableString(str);
        int indexOf = spannableString.toString().indexOf(str2.toString(), 0);
        spannableString.setSpan(new StyleSpan(1), indexOf, str2.length() + indexOf, 0);
        return spannableString;
    }

    private static String getHardcodeGroupName(String str) {
        if (str.contains("RECEIVE_SMS") || str.contains("SEND_SMS") || str.contains("READ_SMS") || str.contains("RECEIVE_WAP_PUSH") || str.contains("RECEIVE_MMS")) {
            return "android.permission-group.SMS";
        }
        if (str.contains("READ_PHONE_STATE") || str.contains("CALL_PHONE") || str.contains("ADD_VOICEMAIL") || str.contains("USE_SIP") || str.contains("ANSWER_PHONE_CALLS")) {
            return "android.permission-group.PHONE";
        }
        if (str.contains("WRITE_EXTERNAL_STORAGE") || str.contains("READ_EXTERNAL_STORAGE")) {
            return "android.permission-group.STORAGE";
        }
        if (str.contains("WRITE_CALENDAR") || str.contains("READ_CALENDAR")) {
            return "android.permission-group.CALENDAR";
        }
        if (str.contains("READ_CONTACTS") || str.contains("WRITE_CONTACTS")) {
            return "android.permission-group.CONTACTS";
        }
        if (str.contains("READ_CALL_LOG") || str.contains("WRITE_CALL_LOG") || str.contains("PROCESS_OUTGOING_CALLS")) {
            return "android.permission-group.CALL_LOG";
        }
        return null;
    }

    private static PermissionItem getPermissionGroup(String str, Context context) {
        if (str.contains("RECEIVE_SMS") || str.contains("SEND_SMS") || str.contains("READ_SMS") || str.contains("RECEIVE_WAP_PUSH") || str.contains("RECEIVE_MMS")) {
            return new PermissionItem(context.getResources().getString(R.string.oobe_permission_sms_title));
        }
        if (str.contains("READ_PHONE_STATE") || str.contains("CALL_PHONE") || str.contains("ADD_VOICEMAIL") || str.contains("USE_SIP") || str.contains("ANSWER_PHONE_CALLS")) {
            return new PermissionItem(context.getResources().getString(R.string.oobe_permission_phone_title));
        }
        if (str.contains("WRITE_EXTERNAL_STORAGE") || str.contains("READ_EXTERNAL_STORAGE")) {
            return new PermissionItem(context.getResources().getString(R.string.oobe_permission_storage_title));
        }
        if (str.contains("WRITE_CALENDAR") || str.contains("READ_CALENDAR")) {
            return new PermissionItem(context.getResources().getString(R.string.oobe_permission_calendar_title));
        }
        if (str.contains("READ_CONTACTS") || str.contains("WRITE_CONTACTS")) {
            return new PermissionItem(context.getResources().getString(R.string.oobe_permission_contacts_title));
        }
        if (str.contains("READ_CALL_LOG") || str.contains("WRITE_CALL_LOG") || str.contains("PROCESS_OUTGOING_CALLS")) {
            return new PermissionItem(context.getResources().getString(R.string.oobe_permission_calllog_title));
        }
        return null;
    }
}
