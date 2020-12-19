package com.samsung.android.app.watchmanager.setupwizard.contactus;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.samsung.android.app.twatchmanager.contentprovider.DeviceRegistryData;
import com.samsung.android.app.twatchmanager.contentprovider.RegistryDbManagerWithProvider;
import com.samsung.android.app.twatchmanager.smartswitch.FileUtils;
import com.samsung.android.app.twatchmanager.update.UpdateCheckTask;
import com.samsung.android.app.twatchmanager.util.HostManagerUtilsDBOperations;
import com.samsung.android.app.twatchmanager.util.HostManagerUtilsNetwork;
import com.samsung.android.app.watchmanager.R;
import com.samsung.android.app.watchmanager.setupwizard.contactus.connection.AsyncHttpRequest;
import com.samsung.android.app.watchmanager.setupwizard.contactus.connection.GlobalConst;
import com.samsung.android.app.watchmanager.setupwizard.contactus.connection.JsonParser;
import com.samsung.android.app.watchmanager.setupwizard.contactus.connection.RequestResponseClasses.ApplicationInfo;
import com.samsung.android.app.watchmanager.setupwizard.contactus.connection.RequestResponseClasses.DeviceInfo;
import com.samsung.android.app.watchmanager.setupwizard.contactus.connection.RequestResponseClasses.FeedBackRequestBody;
import com.samsung.android.app.watchmanager.setupwizard.contactus.connection.RequestResponseClasses.FeedBackType;
import com.samsung.android.app.watchmanager.setupwizard.contactus.connection.RequestResponseClasses.Question;
import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class AskandErrorReportFragment extends Fragment {
    public static final String ASK_QUESTIONS = "AskQuestionsFrag";
    public static final String FEEDBACKID = "feedbackID";
    public static final String FEEDBACKLIST = "feedbacklist";
    private static final int MAX_FILE_SIZE = 26214400;
    protected static final int REQUEST_PICK_IMAGE = 1;
    private static final int REQUEST_SCREEN_OVERLAY = 1234;
    protected static final int REQUEST_SCREEN_RECORD = 2345;
    private static String TAG = "AskandError";
    int _numberOfFiles = 0;
    private AskandErrorReportFragment askandErrorReportFragment;
    private View attachmentDivider;
    private TextView attachmentStatus;
    private LinearLayout attachments;
    private LinearLayout autoAttachLayout;
    private LinearLayout autoAttachedInfo;
    private TextView buildnumber;
    CountDownTimer countDownTimer = null;
    Map<String, File> fileMap;
    View floating;
    private LinearLayout frequencySelector;
    private TextView frequencyTextView;
    private boolean fromAskFrag;
    private CheckBox includeLogCheck;
    private boolean isDragging;
    private Activity mActivity;
    private EditText mQuestionEditText;
    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        /* class com.samsung.android.app.watchmanager.setupwizard.contactus.AskandErrorReportFragment.AnonymousClass1 */

        public void afterTextChanged(Editable editable) {
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            TextView textView = AskandErrorReportFragment.this.mTextView;
            textView.setText(String.valueOf(charSequence.length()) + "/1000");
        }
    };
    private TextView mTextView;
    private TextView modelNumber;
    private String parentId;
    private String pluginName;
    private BroadcastReceiver receiver;
    ProgressDialog ringProgressDialog;
    View rootView;
    ScreenRecording screenRecording;
    WindowManager.LayoutParams screenoverlaylayoutParams;
    private LinearLayout screenshotLayout;
    private RelativeLayout systemLog;
    private float totalAttachSize;
    AsyncTask<Object, Integer, String> uploadingTask;
    private TextView version;
    WindowManager windowManager;

    interface ScreenshotCallback {
        void screenshotCaptureFinished(boolean z);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0096  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x009e  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00a9 A[SYNTHETIC, Splitter:B:33:0x00a9] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00b1 A[Catch:{ IOException -> 0x00ad }] */
    /* JADX WARNING: Removed duplicated region for block: B:42:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void collectLogs(boolean r6) {
        /*
        // Method dump skipped, instructions count: 185
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.watchmanager.setupwizard.contactus.AskandErrorReportFragment.collectLogs(boolean):void");
    }

    private Drawable getGalleryIcon() {
        PackageManager packageManager = this.mActivity.getPackageManager();
        try {
            Intent intent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            Drawable drawable = null;
            for (ResolveInfo resolveInfo : packageManager.queryIntentActivities(intent, 64)) {
                Log.d(TAG, "name:" + resolveInfo.activityInfo.processName);
                drawable = packageManager.getApplicationIcon(resolveInfo.activityInfo.processName);
            }
            return drawable;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private View.OnTouchListener getOntouchListerner(final int i, final int i2) {
        return new View.OnTouchListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.contactus.AskandErrorReportFragment.AnonymousClass9 */
            private float initialTouchX;
            private float initialTouchY;
            private int initialX;
            private int initialY;
            private int maxX;
            private int maxY;
            private int measuredheight;
            private int measuredwidth;
            private int minX;
            private int minY;
            private long startTime;

            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == 0) {
                    this.startTime = System.currentTimeMillis();
                    WindowManager.LayoutParams layoutParams = AskandErrorReportFragment.this.screenoverlaylayoutParams;
                    this.initialX = layoutParams.x;
                    this.initialY = layoutParams.y;
                    this.initialTouchX = motionEvent.getRawX();
                    this.initialTouchY = motionEvent.getRawY();
                    this.measuredheight = AskandErrorReportFragment.this.floating.getMeasuredHeight();
                    this.measuredwidth = AskandErrorReportFragment.this.floating.getMeasuredWidth();
                    this.minX = (this.measuredwidth - i2) / 2;
                    this.minY = (this.measuredheight - i) / 2;
                    this.maxX = -this.minX;
                    this.maxY = -this.minY;
                } else if (action == 1) {
                    System.out.println("in ACTION_UP");
                    if (System.currentTimeMillis() - this.startTime > 200) {
                        AskandErrorReportFragment.this.isDragging = true;
                    } else {
                        AskandErrorReportFragment.this.isDragging = false;
                    }
                } else if (action == 2) {
                    AskandErrorReportFragment.this.screenoverlaylayoutParams.x = this.initialX + ((int) (motionEvent.getRawX() - this.initialTouchX));
                    AskandErrorReportFragment.this.screenoverlaylayoutParams.y = this.initialY + ((int) (motionEvent.getRawY() - this.initialTouchY));
                    WindowManager.LayoutParams layoutParams2 = AskandErrorReportFragment.this.screenoverlaylayoutParams;
                    int i = layoutParams2.x;
                    int i2 = this.minX;
                    if (i < i2) {
                        layoutParams2.x = i2;
                    }
                    WindowManager.LayoutParams layoutParams3 = AskandErrorReportFragment.this.screenoverlaylayoutParams;
                    int i3 = layoutParams3.y;
                    int i4 = this.minY;
                    if (i3 < i4) {
                        layoutParams3.y = i4;
                    }
                    WindowManager.LayoutParams layoutParams4 = AskandErrorReportFragment.this.screenoverlaylayoutParams;
                    int i5 = layoutParams4.x;
                    int i6 = this.maxX;
                    if (i5 > i6) {
                        layoutParams4.x = i6;
                    }
                    WindowManager.LayoutParams layoutParams5 = AskandErrorReportFragment.this.screenoverlaylayoutParams;
                    int i7 = layoutParams5.y;
                    int i8 = this.maxY;
                    if (i7 > i8) {
                        layoutParams5.y = i8;
                    }
                    AskandErrorReportFragment askandErrorReportFragment = AskandErrorReportFragment.this;
                    askandErrorReportFragment.windowManager.updateViewLayout(askandErrorReportFragment.floating, askandErrorReportFragment.screenoverlaylayoutParams);
                }
                return false;
            }
        };
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void loadAllScreenshots() {
        File file;
        String[] strArr;
        final String str;
        int i;
        File file2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/video");
        String[] list = file2.list();
        int length = list.length;
        int i2 = 0;
        int i3 = 0;
        while (i3 < length) {
            final File file3 = new File(file2.getPath(), list[i3]);
            final String path = file3.getPath();
            if (file3.exists()) {
                final long length2 = file3.length();
                String str2 = String.valueOf(length2 / 1024) + "KB";
                if (!this.fileMap.containsKey(path)) {
                    float f = this.totalAttachSize;
                    float f2 = (float) length2;
                    if (f + f2 > 2.62144E7f) {
                        Activity activity = this.mActivity;
                        String string = activity.getResources().getString(R.string.size_limit_exceeds);
                        Object[] objArr = new Object[1];
                        objArr[i2] = "25MB";
                        Toast.makeText(activity, String.format(string, objArr), i2).show();
                        return;
                    }
                    this.totalAttachSize = f + f2;
                    this.fileMap.put(path, file3);
                    TextView textView = this.attachmentStatus;
                    StringBuilder sb = new StringBuilder();
                    sb.append(this.fileMap.size());
                    sb.append("/10(");
                    Object[] objArr2 = new Object[1];
                    objArr2[i2] = Float.valueOf(this.totalAttachSize / 1048576.0f);
                    sb.append(String.format("%.1g%n", objArr2));
                    sb.append("MB/25MB)");
                    textView.setText(sb.toString());
                    final View inflate = ((LayoutInflater) this.mActivity.getApplicationContext().getSystemService("layout_inflater")).inflate(R.layout.screenshot_thumbnail, (ViewGroup) null);
                    ImageView imageView = (ImageView) inflate.findViewById(R.id.thumbnailImageView);
                    ImageView imageView2 = (ImageView) inflate.findViewById(R.id.removeButton);
                    ImageView imageView3 = (ImageView) inflate.findViewById(R.id.videoPlayImage);
                    ((TextView) inflate.findViewById(R.id.fileName)).setText(file3.getName());
                    ((TextView) inflate.findViewById(R.id.fileSize)).setText(str2);
                    file = file2;
                    strArr = list;
                    imageView2.setOnClickListener(new View.OnClickListener() {
                        /* class com.samsung.android.app.watchmanager.setupwizard.contactus.AskandErrorReportFragment.AnonymousClass10 */

                        public void onClick(View view) {
                            String str;
                            AskandErrorReportFragment.this.screenshotLayout.removeView(inflate);
                            AskandErrorReportFragment.this.fileMap.remove(path);
                            AskandErrorReportFragment.this.totalAttachSize -= (float) length2;
                            if (AskandErrorReportFragment.this.totalAttachSize > 0.0f) {
                                str = String.format("%.1g%n", Float.valueOf(AskandErrorReportFragment.this.totalAttachSize / 1048576.0f));
                            } else {
                                str = UpdateCheckTask.RESULT_CANT_FIND_APP;
                            }
                            AskandErrorReportFragment.this.attachmentStatus.setText(AskandErrorReportFragment.this.fileMap.size() + "/10(" + str + "MB/25MB)");
                            if (AskandErrorReportFragment.this.screenshotLayout.getChildCount() == 0) {
                                AskandErrorReportFragment.this.attachments.setVisibility(8);
                                AskandErrorReportFragment.this.attachmentDivider.setVisibility(8);
                                AskandErrorReportFragment.this.screenshotLayout.setVisibility(8);
                            }
                        }
                    });
                    if (Build.VERSION.SDK_INT >= 21) {
                        imageView.setClipToOutline(true);
                    }
                    imageView.setImageBitmap(createVideoThumbNail(path));
                    if (path.endsWith("png")) {
                        str = "image/*";
                        i = 0;
                    } else {
                        imageView3.setImageDrawable(getResources().getDrawable(R.drawable.community_ic_play));
                        i = 0;
                        imageView3.setVisibility(0);
                        str = "video/*";
                    }
                    imageView.setOnClickListener(new View.OnClickListener() {
                        /* class com.samsung.android.app.watchmanager.setupwizard.contactus.AskandErrorReportFragment.AnonymousClass11 */

                        public void onClick(View view) {
                            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(path));
                            intent.setDataAndType(Uri.fromFile(file3), str);
                            AskandErrorReportFragment.this.startActivity(intent);
                        }
                    });
                    this.attachments.setVisibility(i);
                    this.attachmentDivider.setVisibility(i);
                    this.screenshotLayout.setVisibility(i);
                    this.screenshotLayout.addView(inflate);
                    i3++;
                    list = strArr;
                    file2 = file;
                    i2 = 0;
                }
            }
            file = file2;
            strArr = list;
            i3++;
            list = strArr;
            file2 = file;
            i2 = 0;
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void pickAudio() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("audio/*");
        intent.putExtra("android.intent.extra.LOCAL_ONLY", true);
        startActivityForResult(intent, 1);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void pickImage() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.putExtra("android.intent.extra.ALLOW_MULTIPLE", true);
        intent.setType("*/*");
        intent.putExtra("android.intent.extra.LOCAL_ONLY", true);
        intent.putExtra("android.intent.extra.MIME_TYPES", new String[]{"image/*", "video/*"});
        startActivityForResult(intent, 1);
    }

    public static StringBuilder readLogs() {
        StringBuilder sb = new StringBuilder();
        Log.d(TAG, "ReadingLogs");
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("logcat -d").getInputStream()));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                sb.append(readLine + "\n");
            }
            Log.d(TAG, "Done reading logs");
        } catch (IOException unused) {
        }
        return sb;
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0047 A[SYNTHETIC, Splitter:B:23:0x0047] */
    /* JADX WARNING: Removed duplicated region for block: B:29:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void saveFile(java.lang.String r4, java.lang.String r5, android.graphics.Bitmap r6) {
        /*
            r3 = this;
            java.io.File r0 = new java.io.File
            r0.<init>(r4)
            boolean r1 = r0.exists()
            if (r1 != 0) goto L_0x000e
            r0.mkdir()
        L_0x000e:
            r0 = 0
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0036 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0036 }
            r2.<init>()     // Catch:{ Exception -> 0x0036 }
            r2.append(r4)     // Catch:{ Exception -> 0x0036 }
            r2.append(r5)     // Catch:{ Exception -> 0x0036 }
            java.lang.String r4 = r2.toString()     // Catch:{ Exception -> 0x0036 }
            r1.<init>(r4)     // Catch:{ Exception -> 0x0036 }
            android.graphics.Bitmap$CompressFormat r4 = android.graphics.Bitmap.CompressFormat.PNG     // Catch:{ Exception -> 0x0031, all -> 0x002e }
            r5 = 100
            r6.compress(r4, r5, r1)     // Catch:{ Exception -> 0x0031, all -> 0x002e }
            r1.close()     // Catch:{ IOException -> 0x0040 }
            goto L_0x0044
        L_0x002e:
            r4 = move-exception
            r0 = r1
            goto L_0x0045
        L_0x0031:
            r4 = move-exception
            r0 = r1
            goto L_0x0037
        L_0x0034:
            r4 = move-exception
            goto L_0x0045
        L_0x0036:
            r4 = move-exception
        L_0x0037:
            r4.printStackTrace()     // Catch:{ all -> 0x0034 }
            if (r0 == 0) goto L_0x0044
            r0.close()
            goto L_0x0044
        L_0x0040:
            r4 = move-exception
            r4.printStackTrace()
        L_0x0044:
            return
        L_0x0045:
            if (r0 == 0) goto L_0x004f
            r0.close()     // Catch:{ IOException -> 0x004b }
            goto L_0x004f
        L_0x004b:
            r5 = move-exception
            r5.printStackTrace()
        L_0x004f:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.watchmanager.setupwizard.contactus.AskandErrorReportFragment.saveFile(java.lang.String, java.lang.String, android.graphics.Bitmap):void");
    }

    private void sendBroadcastforGearLogs() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.android.gearlog_sm_response");
        this.receiver = new BroadcastReceiver() {
            /* class com.samsung.android.app.watchmanager.setupwizard.contactus.AskandErrorReportFragment.AnonymousClass5 */

            public void onReceive(Context context, Intent intent) {
                String str;
                String str2;
                if ("1".equals(intent.getStringExtra("success"))) {
                    str2 = AskandErrorReportFragment.TAG;
                    str = "com.samsung.android.gearlog_sm_response:sucess";
                } else {
                    str2 = AskandErrorReportFragment.TAG;
                    str = "com.samsung.android.gearlog_sm_response:fail";
                }
                Log.d(str2, str);
                String str3 = AskandErrorReportFragment.TAG;
                Log.d(str3, "success:" + intent.getStringExtra("success"));
                ProgressDialog progressDialog = AskandErrorReportFragment.this.ringProgressDialog;
                if (progressDialog != null) {
                    progressDialog.cancel();
                }
            }
        };
        this.mActivity.registerReceiver(this.receiver, intentFilter);
        this.mActivity.sendBroadcast(new Intent("com.samsung.android.gearlog_sm_request"));
        Log.d(TAG, "broadcast sent to get gear logs");
    }

    private void sendFeedBack() {
        showProgressDialog("Sending feedback...");
        FeedBackType feedBackType = this.fromAskFrag ? new FeedBackType("QNA", "APPFEEDBACK", 1) : new FeedBackType("ERROR", "APPFEEDBACK", 2);
        String charSequence = this.frequencyTextView.getText().toString();
        if (charSequence.equalsIgnoreCase(this.mActivity.getResources().getString(R.string.frequency)) || this.fromAskFrag) {
            charSequence = this.mActivity.getResources().getString(R.string.none);
        }
        if (charSequence.equals(this.mActivity.getString(R.string.once))) {
            charSequence = "ONCE";
        } else if (charSequence.equals(this.mActivity.getString(R.string.sometimes))) {
            charSequence = "SOMETIMES";
        } else if (charSequence.equals(this.mActivity.getString(R.string.always))) {
            charSequence = "ALWAYS";
        }
        Question question = new Question(this.mQuestionEditText.getText().toString(), null, charSequence);
        DeviceInfo deviceInfo = new DeviceInfo(this.modelNumber.getText().toString(), this.version.getText().toString(), this.buildnumber.getText().toString());
        ApplicationInfo applicationInfo = new ApplicationInfo(GlobalConst.SCS_CLIENT_ID_OF_SM, "2.2.17051961", "com.samsung.android.app.watchmanager");
        final String csc = HostManagerUtilsNetwork.getCSC();
        final String mcc = ContactUsActivity.getMCC(this.mActivity);
        new AsyncHttpRequest(new AsyncHttpRequest.AsyncResponse() {
            /* class com.samsung.android.app.watchmanager.setupwizard.contactus.AskandErrorReportFragment.AnonymousClass15 */

            @Override // com.samsung.android.app.watchmanager.setupwizard.contactus.connection.AsyncHttpRequest.AsyncResponse
            public void processFinish(String str) {
                if (str == null || !str.contains("feedbackId")) {
                    Toast.makeText(AskandErrorReportFragment.this.mActivity, AskandErrorReportFragment.this.mActivity.getResources().getString(R.string.feedback_not_sent), 0).show();
                    AskandErrorReportFragment.this.ringProgressDialog.cancel();
                    return;
                }
                Map<String, File> map = AskandErrorReportFragment.this.fileMap;
                if ((map == null || map.size() == 0) && !AskandErrorReportFragment.this.includeLogCheck.isChecked()) {
                    Toast.makeText(AskandErrorReportFragment.this.mActivity, AskandErrorReportFragment.this.mActivity.getResources().getString(R.string.feedback_sent_success), 0).show();
                    AskandErrorReportFragment.this.ringProgressDialog.onBackPressed();
                    return;
                }
                try {
                    AskandErrorReportFragment.this.uploadAttachments(new JSONObject(str).getLong("feedbackId"), mcc, csc);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).execute(2, new JsonParser().createData(new FeedBackRequestBody(feedBackType, question, deviceInfo, null, applicationInfo, this.parentId, null, null)), csc, mcc);
    }

    private void showChooserDialog() {
        final Dialog dialog = new Dialog(this.mActivity, R.style.CustomDialog);
        dialog.setContentView(R.layout.attach_chooser);
        dialog.setTitle("Attach");
        Window window = dialog.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        window.setLayout(-1, -2);
        attributes.gravity = 80;
        window.setAttributes(attributes);
        ImageView imageView = (ImageView) dialog.findViewById(R.id.gallery);
        imageView.setImageDrawable(getGalleryIcon());
        imageView.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.contactus.AskandErrorReportFragment.AnonymousClass6 */

            public void onClick(View view) {
                Activity activity;
                String format;
                String str = AskandErrorReportFragment.TAG;
                Log.d(str, "totalAttachSize:" + AskandErrorReportFragment.this.totalAttachSize);
                if (AskandErrorReportFragment.this.fileMap.size() >= 10 || AskandErrorReportFragment.this.totalAttachSize >= 2.62144E7f) {
                    if (AskandErrorReportFragment.this.fileMap.size() >= 10) {
                        activity = AskandErrorReportFragment.this.mActivity;
                        format = String.format(AskandErrorReportFragment.this.mActivity.getResources().getString(R.string.cant_attach_more_files), 10);
                    } else if (AskandErrorReportFragment.this.totalAttachSize >= 2.62144E7f) {
                        activity = AskandErrorReportFragment.this.mActivity;
                        format = String.format(AskandErrorReportFragment.this.mActivity.getResources().getString(R.string.size_limit_exceeds), "25MB");
                    }
                    Toast.makeText(activity, format, 0).show();
                } else {
                    AskandErrorReportFragment.this.pickImage();
                }
                dialog.cancel();
            }
        });
        ((ImageView) dialog.findViewById(R.id.audio)).setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.contactus.AskandErrorReportFragment.AnonymousClass7 */

            public void onClick(View view) {
                AskandErrorReportFragment.this.pickAudio();
                dialog.cancel();
            }
        });
        ImageView imageView2 = (ImageView) dialog.findViewById(R.id.screenshot);
        if (Build.VERSION.SDK_INT > 19) {
            imageView2.setOnClickListener(new View.OnClickListener() {
                /* class com.samsung.android.app.watchmanager.setupwizard.contactus.AskandErrorReportFragment.AnonymousClass8 */

                public void onClick(View view) {
                    AskandErrorReportFragment askandErrorReportFragment = AskandErrorReportFragment.this;
                    askandErrorReportFragment.floating = View.inflate(askandErrorReportFragment.mActivity, R.layout.screen_capture_layout, null);
                    AskandErrorReportFragment askandErrorReportFragment2 = AskandErrorReportFragment.this;
                    askandErrorReportFragment2.windowManager = (WindowManager) askandErrorReportFragment2.mActivity.getSystemService("window");
                    AskandErrorReportFragment.this.screenoverlaylayoutParams = new WindowManager.LayoutParams(-2, -2, 2038, 262664, -3);
                    AskandErrorReportFragment askandErrorReportFragment3 = AskandErrorReportFragment.this;
                    WindowManager.LayoutParams layoutParams = askandErrorReportFragment3.screenoverlaylayoutParams;
                    layoutParams.gravity = 17;
                    if (Build.VERSION.SDK_INT < 23) {
                        askandErrorReportFragment3.windowManager.addView(askandErrorReportFragment3.floating, layoutParams);
                    } else if (!Settings.canDrawOverlays(askandErrorReportFragment3.mActivity)) {
                        AskandErrorReportFragment.this.startActivityForResult(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + AskandErrorReportFragment.this.mActivity.getPackageName())), AskandErrorReportFragment.REQUEST_SCREEN_OVERLAY);
                    } else {
                        AskandErrorReportFragment askandErrorReportFragment4 = AskandErrorReportFragment.this;
                        askandErrorReportFragment4.windowManager.addView(askandErrorReportFragment4.floating, askandErrorReportFragment4.screenoverlaylayoutParams);
                    }
                    View findViewById = AskandErrorReportFragment.this.floating.findViewById(R.id.videoButton);
                    final View findViewById2 = AskandErrorReportFragment.this.floating.findViewById(R.id.normalLinLayout);
                    final View findViewById3 = AskandErrorReportFragment.this.floating.findViewById(R.id.onRecordingFrameLayout);
                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    AskandErrorReportFragment.this.mActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                    View.OnTouchListener ontouchListerner = AskandErrorReportFragment.this.getOntouchListerner(displayMetrics.heightPixels, displayMetrics.widthPixels);
                    AskandErrorReportFragment.this.deleteOldfiles();
                    View findViewById4 = AskandErrorReportFragment.this.floating.findViewById(R.id.screenshotButton);
                    findViewById4.setOnClickListener(new View.OnClickListener() {
                        /* class com.samsung.android.app.watchmanager.setupwizard.contactus.AskandErrorReportFragment.AnonymousClass8.AnonymousClass1 */

                        public void onClick(View view) {
                            if (!AskandErrorReportFragment.this.isDragging) {
                                AskandErrorReportFragment askandErrorReportFragment = AskandErrorReportFragment.this;
                                if (askandErrorReportFragment.screenRecording == null) {
                                    askandErrorReportFragment.screenRecording = new ScreenRecording(askandErrorReportFragment.mActivity, AskandErrorReportFragment.this.askandErrorReportFragment);
                                }
                                if (AskandErrorReportFragment.this.screenRecording.getmImageReader() == null) {
                                    AskandErrorReportFragment.this.screenRecording.initImageReader();
                                    AskandErrorReportFragment.this.screenRecording.setImage(true);
                                    AskandErrorReportFragment.this.screenRecording.shareScreen();
                                }
                            }
                        }
                    });
                    findViewById.setOnClickListener(new View.OnClickListener() {
                        /* class com.samsung.android.app.watchmanager.setupwizard.contactus.AskandErrorReportFragment.AnonymousClass8.AnonymousClass2 */

                        public void onClick(View view) {
                            if (!AskandErrorReportFragment.this.isDragging) {
                                AskandErrorReportFragment askandErrorReportFragment = AskandErrorReportFragment.this;
                                if (askandErrorReportFragment.screenRecording == null) {
                                    askandErrorReportFragment.screenRecording = new ScreenRecording(askandErrorReportFragment.mActivity, AskandErrorReportFragment.this.askandErrorReportFragment);
                                }
                                AskandErrorReportFragment.this.screenRecording.initRecorder();
                                AskandErrorReportFragment.this.screenRecording.setImage(false);
                                AskandErrorReportFragment.this.screenRecording.shareScreen();
                                findViewById2.setVisibility(8);
                                findViewById3.setVisibility(0);
                                AskandErrorReportFragment askandErrorReportFragment2 = AskandErrorReportFragment.this;
                                askandErrorReportFragment2.startTimer((TextView) askandErrorReportFragment2.floating.findViewById(R.id.recordingTimeTextView), findViewById2, findViewById3);
                            }
                        }
                    });
                    findViewById3.setOnClickListener(new View.OnClickListener() {
                        /* class com.samsung.android.app.watchmanager.setupwizard.contactus.AskandErrorReportFragment.AnonymousClass8.AnonymousClass3 */

                        public void onClick(View view) {
                            if (!AskandErrorReportFragment.this.isDragging) {
                                AskandErrorReportFragment.this.screenRecording.closeRecording();
                                findViewById2.setVisibility(0);
                                findViewById3.setVisibility(8);
                                AskandErrorReportFragment.this.countDownTimer.cancel();
                            }
                        }
                    });
                    View findViewById5 = AskandErrorReportFragment.this.floating.findViewById(R.id.doneButton);
                    findViewById5.setOnClickListener(new View.OnClickListener() {
                        /* class com.samsung.android.app.watchmanager.setupwizard.contactus.AskandErrorReportFragment.AnonymousClass8.AnonymousClass4 */

                        public void onClick(View view) {
                            if (!AskandErrorReportFragment.this.isDragging) {
                                AskandErrorReportFragment askandErrorReportFragment = AskandErrorReportFragment.this;
                                askandErrorReportFragment.windowManager.removeView(askandErrorReportFragment.floating);
                                AskandErrorReportFragment askandErrorReportFragment2 = AskandErrorReportFragment.this;
                                askandErrorReportFragment2.floating = null;
                                CountDownTimer countDownTimer = askandErrorReportFragment2.countDownTimer;
                                if (countDownTimer != null) {
                                    countDownTimer.cancel();
                                }
                                AskandErrorReportFragment.this.loadAllScreenshots();
                                AskandErrorReportFragment.this.startActivity(new Intent(AskandErrorReportFragment.this.mActivity, ContactUsActivity.class));
                            }
                        }
                    });
                    findViewById.setOnTouchListener(ontouchListerner);
                    findViewById3.setOnTouchListener(ontouchListerner);
                    findViewById4.setOnTouchListener(ontouchListerner);
                    findViewById5.setOnTouchListener(ontouchListerner);
                    dialog.cancel();
                }
            });
        } else {
            imageView2.setVisibility(8);
        }
        dialog.show();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void showLogProgressDialog(String str) {
        this.ringProgressDialog = new ProgressDialog(this.mActivity) {
            /* class com.samsung.android.app.watchmanager.setupwizard.contactus.AskandErrorReportFragment.AnonymousClass14 */

            public void onBackPressed() {
                super.onBackPressed();
                AskandErrorReportFragment.this.mActivity.onBackPressed();
            }
        };
        this.ringProgressDialog.setProgressStyle(0);
        this.ringProgressDialog.setCancelable(true);
        this.ringProgressDialog.setMessage(str);
        this.ringProgressDialog.show();
    }

    private void showProgressDialog(String str) {
        this.ringProgressDialog = new ProgressDialog(this.mActivity) {
            /* class com.samsung.android.app.watchmanager.setupwizard.contactus.AskandErrorReportFragment.AnonymousClass12 */

            public void onBackPressed() {
                super.onBackPressed();
                AskandErrorReportFragment.this.mActivity.onBackPressed();
            }
        };
        this.ringProgressDialog.setProgressStyle(1);
        this.ringProgressDialog.setCancelable(true);
        this.ringProgressDialog.setMessage(str);
        this.ringProgressDialog.setProgress(0);
        this.ringProgressDialog.setProgressNumberFormat(null);
        this.ringProgressDialog.setButton(-2, this.mActivity.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.contactus.AskandErrorReportFragment.AnonymousClass13 */

            public void onClick(DialogInterface dialogInterface, int i) {
                AsyncTask<Object, Integer, String> asyncTask = AskandErrorReportFragment.this.uploadingTask;
                if (asyncTask != null) {
                    asyncTask.cancel(true);
                }
                dialogInterface.dismiss();
            }
        });
        this.ringProgressDialog.show();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void uploadAttachments(long j, String str, String str2) {
        if (this.includeLogCheck.isChecked()) {
            zipLogFiles();
        }
        if (this.includeLogCheck.isChecked() || this.fileMap.size() > 0) {
            this.uploadingTask = new AsyncHttpRequest(new AsyncHttpRequest.AsyncResponse() {
                /* class com.samsung.android.app.watchmanager.setupwizard.contactus.AskandErrorReportFragment.AnonymousClass16 */

                @Override // com.samsung.android.app.watchmanager.setupwizard.contactus.connection.AsyncHttpRequest.AsyncResponse
                public void processFinish(String str) {
                    if ("OK".equals(str)) {
                        Toast.makeText(AskandErrorReportFragment.this.mActivity, AskandErrorReportFragment.this.mActivity.getResources().getString(R.string.feedback_sent_success), 0).show();
                        AskandErrorReportFragment.this.ringProgressDialog.onBackPressed();
                        return;
                    }
                    AskandErrorReportFragment.this.ringProgressDialog.cancel();
                    Toast.makeText(AskandErrorReportFragment.this.mActivity, AskandErrorReportFragment.this.mActivity.getResources().getString(R.string.feedback_not_sent), 0).show();
                }
            }, this.ringProgressDialog).execute(7, this.fileMap, Long.valueOf(j), str, str2, Boolean.valueOf(this.includeLogCheck.isChecked()));
        }
    }

    private void zipLogFiles() {
        File file = new File(Environment.getExternalStorageDirectory() + "/log/GearLog/");
        File file2 = new File(Environment.getExternalStorageDirectory() + "/log/GearLog/GWLog.zip");
        if (!file.exists()) {
            Log.d(TAG, "LongLifeDump folder is not exist");
        } else {
            FileUtils.zipFiles(file.listFiles(new FilenameFilter() {
                /* class com.samsung.android.app.watchmanager.setupwizard.contactus.AskandErrorReportFragment.AnonymousClass17 */

                public boolean accept(File file, String str) {
                    return str.toLowerCase().endsWith(".log");
                }
            }), file2);
        }
    }

    public Bitmap createVideoThumbNail(String str) {
        return ThumbnailUtils.createVideoThumbnail(str, 3);
    }

    public void deleteOldfiles() {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/video");
        if (file.exists()) {
            for (String str : file.list()) {
                File file2 = new File(file.getPath(), str);
                if (!this.fileMap.containsKey(file2.getPath())) {
                    file2.delete();
                }
            }
            return;
        }
        file.mkdir();
    }

    /* JADX WARNING: Removed duplicated region for block: B:61:0x0157  */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x0167  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0170  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String[] getRealPathFromURI(android.net.Uri r20) {
        /*
        // Method dump skipped, instructions count: 374
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.watchmanager.setupwizard.contactus.AskandErrorReportFragment.getRealPathFromURI(android.net.Uri):java.lang.String[]");
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0214  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void getScreenShot() {
        /*
        // Method dump skipped, instructions count: 641
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.watchmanager.setupwizard.contactus.AskandErrorReportFragment.getScreenShot():void");
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        Iterator it;
        char c2;
        final String str;
        int i3;
        ViewGroup viewGroup = null;
        int i4 = 1;
        if (i != 1) {
            if (i == REQUEST_SCREEN_OVERLAY) {
                Log.d(TAG, "REQUEST_SCREEN_OVERLAY:" + i2 + " ");
                if (Build.VERSION.SDK_INT < 23 || !Settings.canDrawOverlays(this.mActivity)) {
                    this.floating = null;
                } else {
                    this.windowManager.addView(this.floating, this.screenoverlaylayoutParams);
                }
            } else if (i == REQUEST_SCREEN_RECORD) {
                Log.d(TAG, "REQUEST_SCREEN_RECORD:" + i2 + " ");
                this.screenRecording.onActivityResult(i2, intent);
            }
        } else if (-1 == i2) {
            ArrayList arrayList = new ArrayList();
            int i5 = 0;
            if (intent.getClipData() != null) {
                ClipData clipData = intent.getClipData();
                for (int i6 = 0; i6 < clipData.getItemCount(); i6++) {
                    arrayList.add(clipData.getItemAt(i6).getUri());
                }
                Log.v(TAG, "Selected Images" + arrayList.size());
            } else if (intent.getData() != null) {
                arrayList.add(intent.getData());
            }
            Log.d(TAG, "result_ok");
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                Uri uri = (Uri) it2.next();
                Log.d(TAG, "hello:::" + uri.toString());
                String[] realPathFromURI = getRealPathFromURI(uri);
                if (realPathFromURI != null) {
                    final String str2 = realPathFromURI[i5];
                    Log.d(TAG, "path::" + str2);
                    if (str2 != null) {
                        final File file = new File(str2);
                        if (file.exists()) {
                            final long length = file.length();
                            String str3 = String.valueOf(length / 1024) + "KB";
                            if (this.fileMap.containsKey(str2)) {
                                Activity activity = this.mActivity;
                                Toast.makeText(activity, activity.getResources().getString(R.string.file_already_attached), i5).show();
                            } else {
                                float f = this.totalAttachSize;
                                float f2 = (float) length;
                                if (f + f2 > 2.62144E7f) {
                                    Activity activity2 = this.mActivity;
                                    String string = activity2.getResources().getString(R.string.size_limit_exceeds);
                                    Object[] objArr = new Object[i4];
                                    objArr[i5] = "25MB";
                                    Toast.makeText(activity2, String.format(string, objArr), i5).show();
                                    return;
                                }
                                this.totalAttachSize = f + f2;
                                this.fileMap.put(str2, file);
                                TextView textView = this.attachmentStatus;
                                StringBuilder sb = new StringBuilder();
                                sb.append(this.fileMap.size());
                                sb.append("/10(");
                                Object[] objArr2 = new Object[i4];
                                objArr2[i5] = Float.valueOf(this.totalAttachSize / 1048576.0f);
                                sb.append(String.format("%.1g%n", objArr2));
                                sb.append("MB/25MB)");
                                textView.setText(sb.toString());
                                final View inflate = ((LayoutInflater) this.mActivity.getApplicationContext().getSystemService("layout_inflater")).inflate(R.layout.screenshot_thumbnail, viewGroup);
                                ImageView imageView = (ImageView) inflate.findViewById(R.id.thumbnailImageView);
                                ImageView imageView2 = (ImageView) inflate.findViewById(R.id.removeButton);
                                ImageView imageView3 = (ImageView) inflate.findViewById(R.id.videoPlayImage);
                                ((TextView) inflate.findViewById(R.id.fileName)).setText(file.getName());
                                ((TextView) inflate.findViewById(R.id.fileSize)).setText(str3);
                                it = it2;
                                imageView2.setOnClickListener(new View.OnClickListener() {
                                    /* class com.samsung.android.app.watchmanager.setupwizard.contactus.AskandErrorReportFragment.AnonymousClass18 */

                                    public void onClick(View view) {
                                        String str;
                                        AskandErrorReportFragment.this.screenshotLayout.removeView(inflate);
                                        AskandErrorReportFragment.this.fileMap.remove(str2);
                                        AskandErrorReportFragment.this.totalAttachSize -= (float) length;
                                        if (AskandErrorReportFragment.this.totalAttachSize > 0.0f) {
                                            str = String.format("%.1g%n", Float.valueOf(AskandErrorReportFragment.this.totalAttachSize / 1048576.0f));
                                        } else {
                                            str = UpdateCheckTask.RESULT_CANT_FIND_APP;
                                        }
                                        AskandErrorReportFragment.this.attachmentStatus.setText(AskandErrorReportFragment.this.fileMap.size() + "/10(" + str + "MB/25MB)");
                                        if (AskandErrorReportFragment.this.screenshotLayout.getChildCount() == 0) {
                                            AskandErrorReportFragment.this.attachments.setVisibility(8);
                                            AskandErrorReportFragment.this.attachmentDivider.setVisibility(8);
                                            AskandErrorReportFragment.this.screenshotLayout.setVisibility(8);
                                        }
                                    }
                                });
                                if (Build.VERSION.SDK_INT >= 21) {
                                    c2 = 1;
                                    imageView.setClipToOutline(true);
                                } else {
                                    c2 = 1;
                                }
                                if (realPathFromURI[c2].equals("3")) {
                                    imageView.setImageResource(R.drawable.audio);
                                    imageView.setBackgroundColor(this.mActivity.getResources().getColor(R.color.background));
                                    str = "audio/*";
                                } else if (realPathFromURI[1].equals("1")) {
                                    imageView.setImageURI(uri);
                                    str = "image/*";
                                } else {
                                    imageView3.setImageDrawable(getResources().getDrawable(R.drawable.community_ic_play));
                                    i3 = 0;
                                    imageView3.setVisibility(0);
                                    imageView.setImageBitmap(createVideoThumbNail(str2));
                                    str = "video/*";
                                    imageView.setOnClickListener(new View.OnClickListener() {
                                        /* class com.samsung.android.app.watchmanager.setupwizard.contactus.AskandErrorReportFragment.AnonymousClass19 */

                                        public void onClick(View view) {
                                            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str2));
                                            intent.setDataAndType(Uri.fromFile(file), str);
                                            AskandErrorReportFragment.this.startActivity(intent);
                                        }
                                    });
                                    this.attachments.setVisibility(i3);
                                    this.attachmentDivider.setVisibility(i3);
                                    this.screenshotLayout.setVisibility(i3);
                                    this.screenshotLayout.addView(inflate);
                                    it2 = it;
                                    viewGroup = null;
                                    i4 = 1;
                                    i5 = 0;
                                }
                                i3 = 0;
                                imageView.setOnClickListener(new View.OnClickListener() {
                                    /* class com.samsung.android.app.watchmanager.setupwizard.contactus.AskandErrorReportFragment.AnonymousClass19 */

                                    public void onClick(View view) {
                                        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str2));
                                        intent.setDataAndType(Uri.fromFile(file), str);
                                        AskandErrorReportFragment.this.startActivity(intent);
                                    }
                                });
                                this.attachments.setVisibility(i3);
                                this.attachmentDivider.setVisibility(i3);
                                this.screenshotLayout.setVisibility(i3);
                                this.screenshotLayout.addView(inflate);
                                it2 = it;
                                viewGroup = null;
                                i4 = 1;
                                i5 = 0;
                            }
                        }
                    }
                }
                it = it2;
                it2 = it;
                viewGroup = null;
                i4 = 1;
                i5 = 0;
            }
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.contact_us_menu, menu);
        menu.findItem(R.id.delete).setVisible(false);
        menu.findItem(R.id.compose).setVisible(false);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.rootView = layoutInflater.inflate(R.layout.fragment_ask_error_report, (ViewGroup) null);
        setHasOptionsMenu(true);
        return this.rootView;
    }

    public void onDestroy() {
        View view;
        BroadcastReceiver broadcastReceiver = this.receiver;
        if (broadcastReceiver != null) {
            this.mActivity.unregisterReceiver(broadcastReceiver);
        }
        WindowManager windowManager2 = this.windowManager;
        if (!(windowManager2 == null || (view = this.floating) == null)) {
            windowManager2.removeView(view);
            this.floating = null;
        }
        ScreenRecording screenRecording2 = this.screenRecording;
        if (screenRecording2 != null) {
            screenRecording2.destroyMediaProjection();
            this.screenRecording = null;
        }
        CountDownTimer countDownTimer2 = this.countDownTimer;
        if (countDownTimer2 != null) {
            countDownTimer2.cancel();
        }
        super.onDestroy();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        Activity activity;
        String str;
        Resources resources;
        int i;
        int itemId = menuItem.getItemId();
        if (itemId != R.id.attach) {
            if (itemId != R.id.send) {
                return super.onOptionsItemSelected(menuItem);
            }
            if (!HostManagerUtilsNetwork.isNetworkAvailable(this.mActivity)) {
                activity = this.mActivity;
                resources = activity.getResources();
                i = R.string.no_internet_access;
            } else {
                String obj = this.mQuestionEditText.getText().toString();
                if (obj == null || obj.isEmpty()) {
                    activity = this.mActivity;
                    resources = activity.getResources();
                    i = R.string.no_text_entered;
                } else if (this.frequencySelector.getVisibility() != 0 || !this.frequencyTextView.getText().equals("Frequency")) {
                    sendFeedBack();
                    return true;
                } else {
                    activity = this.mActivity;
                    resources = activity.getResources();
                    i = R.string.frequency_not_selected;
                }
            }
            str = resources.getString(i);
        } else if (this.floating != null) {
            activity = this.mActivity;
            str = activity.getString(R.string.unable_to_enable);
        } else {
            showChooserDialog();
            return true;
        }
        Toast.makeText(activity, str, 0).show();
        return true;
    }

    public void onStart() {
        super.onStart();
    }

    public void onViewCreated(View view, Bundle bundle) {
        ActionBar actionBar;
        int i;
        Resources resources;
        super.onViewCreated(view, bundle);
        this.mActivity = getActivity();
        Bundle arguments = getArguments();
        this.frequencySelector = (LinearLayout) this.rootView.findViewById(R.id.frequencyDropdownSelector);
        if (arguments != null) {
            if (arguments.getBoolean(ASK_QUESTIONS)) {
                Log.d(TAG, "true");
                this.fromAskFrag = true;
                this.systemLog = (RelativeLayout) this.rootView.findViewById(R.id.errorReportBottomCheckBoxLinearLayout);
                this.systemLog.setVisibility(8);
                this.frequencySelector.setVisibility(8);
                actionBar = this.mActivity.getActionBar();
                resources = this.mActivity.getResources();
                i = R.string.contact_us_ask;
            } else {
                actionBar = this.mActivity.getActionBar();
                resources = this.mActivity.getResources();
                i = R.string.contact_us_error;
            }
            actionBar.setTitle(resources.getString(i).toUpperCase());
            long j = arguments.getLong(FEEDBACKID, -1);
            if (j != -1) {
                this.parentId = String.valueOf(j);
            }
            this.pluginName = arguments.getString("pluginName");
        }
        this.frequencyTextView = (TextView) this.rootView.findViewById(R.id.frequencyDropdownSelectorTextView);
        this.frequencySelector.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.contactus.AskandErrorReportFragment.AnonymousClass2 */

            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(AskandErrorReportFragment.this.mActivity, AskandErrorReportFragment.this.frequencySelector);
                popupMenu.getMenuInflater().inflate(R.menu.frequency_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    /* class com.samsung.android.app.watchmanager.setupwizard.contactus.AskandErrorReportFragment.AnonymousClass2.AnonymousClass1 */

                    public boolean onMenuItemClick(MenuItem menuItem) {
                        AskandErrorReportFragment.this.frequencyTextView.setText(menuItem.getTitle());
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        this.screenshotLayout = (LinearLayout) this.rootView.findViewById(R.id.linearLayoutScreenShotList);
        this.attachments = (LinearLayout) this.rootView.findViewById(R.id.buttonAddScreenShot);
        this.attachmentDivider = this.rootView.findViewById(R.id.attachmentDivider);
        this.attachmentStatus = (TextView) this.rootView.findViewById(R.id.screenShotAttachmentStatus);
        this.mTextView = (TextView) this.rootView.findViewById(R.id.countTextView);
        this.mQuestionEditText = (EditText) this.rootView.findViewById(R.id.questionEditText);
        this.mQuestionEditText.addTextChangedListener(this.mTextEditorWatcher);
        this.fileMap = new HashMap();
        this.autoAttachLayout = (LinearLayout) this.rootView.findViewById(R.id.autoAttachedLayout);
        this.autoAttachedInfo = (LinearLayout) this.rootView.findViewById(R.id.autoAttachedContentsLayout);
        this.autoAttachLayout.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.contactus.AskandErrorReportFragment.AnonymousClass3 */

            public void onClick(View view) {
                LinearLayout linearLayout;
                int i;
                if (AskandErrorReportFragment.this.autoAttachedInfo.getVisibility() == 0) {
                    linearLayout = AskandErrorReportFragment.this.autoAttachedInfo;
                    i = 8;
                } else {
                    linearLayout = AskandErrorReportFragment.this.autoAttachedInfo;
                    i = 0;
                }
                linearLayout.setVisibility(i);
            }
        });
        this.modelNumber = (TextView) this.rootView.findViewById(R.id.modelNumber);
        this.version = (TextView) this.rootView.findViewById(R.id.release);
        this.buildnumber = (TextView) this.rootView.findViewById(R.id.buildNumber);
        this.modelNumber.setText(Build.MODEL);
        this.version.setText(Build.VERSION.RELEASE);
        this.buildnumber.setText(Build.DISPLAY);
        this.includeLogCheck = (CheckBox) this.rootView.findViewById(R.id.logIncludeCheckBox);
        this.includeLogCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.contactus.AskandErrorReportFragment.AnonymousClass4 */

            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                DeviceRegistryData deviceRegistryData;
                if (z) {
                    List<DeviceRegistryData> queryLastLaunchDeviceRegistryData = new RegistryDbManagerWithProvider().queryLastLaunchDeviceRegistryData(AskandErrorReportFragment.this.mActivity);
                    boolean z2 = false;
                    if (!queryLastLaunchDeviceRegistryData.isEmpty() && (deviceRegistryData = queryLastLaunchDeviceRegistryData.get(0)) != null && (z2 = HostManagerUtilsDBOperations.isConnected(AskandErrorReportFragment.this.mActivity, deviceRegistryData.deviceBtID))) {
                        AskandErrorReportFragment.this.showLogProgressDialog("Collecting Gear logs...");
                    }
                    AskandErrorReportFragment.this.collectLogs(z2);
                }
            }
        });
        this.includeLogCheck.setChecked(arguments.getBoolean("checkbokChecked"));
        ((TextView) this.rootView.findViewById(R.id.titleEditText)).setText(this.pluginName);
        this.askandErrorReportFragment = this;
    }

    public void startTimer(final TextView textView, final View view, final View view2) {
        this.countDownTimer = new CountDownTimer(60000, 1000) {
            /* class com.samsung.android.app.watchmanager.setupwizard.contactus.AskandErrorReportFragment.AnonymousClass20 */
            int i = 0;

            public void onFinish() {
                textView.setText("01:00");
                AskandErrorReportFragment.this.screenRecording.closeRecording();
                view.setVisibility(0);
                view2.setVisibility(8);
                Toast.makeText(AskandErrorReportFragment.this.mActivity, String.format(AskandErrorReportFragment.this.mActivity.getString(R.string.max_record_time), 60), 0).show();
            }

            public void onTick(long j) {
                this.i++;
                textView.setText("00:" + String.format("%02d", Integer.valueOf(this.i)));
            }
        };
        this.countDownTimer.start();
    }
}
