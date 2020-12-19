package com.samsung.android.app.twatchmanager.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.k;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.util.ShowButtonBackgroundSettingObserver;
import com.samsung.android.app.watchmanager.R;

public class CommonDialog {
    private static final String TAG = ("tUHM:" + CommonDialog.class.getSimpleName());
    private View mBottomMargin_IV_TV = null;
    private View mBottomMargin_TV_CB = null;
    private int mButtonType = 0;
    protected CheckBox mCB = null;
    protected TextView mCBMessageTV = null;
    protected TextView mCancelBtn = null;
    protected LinearLayout mCancelBtnContainer = null;
    protected ImageView mCircleProgressCountIV = null;
    protected LinearLayout mCircleProgressCountLayout = null;
    protected TextView mCircleProgressCountMessageTV = null;
    protected TextView mCircleProgressCountTV = null;
    private RelativeLayout mCircleProgressLayout = null;
    private TextView mCircleProgressTV = null;
    protected Context mContext = null;
    protected k mDialog = null;
    protected DialogInterface.OnDismissListener mDismissListner;
    protected LinearLayout mLayout_CB_TV = null;
    private ImageView mMessageIV = null;
    private TextView mMessageTV = null;
    protected int mMessageType = 0;
    protected TextView mOkBtn = null;
    protected LinearLayout mOkBtnContainer = null;
    protected DialogInterface.OnDismissListener mOnDismissListener = new DialogInterface.OnDismissListener() {
        /* class com.samsung.android.app.twatchmanager.util.CommonDialog.AnonymousClass2 */

        public void onDismiss(DialogInterface dialogInterface) {
            Log.i(CommonDialog.TAG, "DialogInterface.OnDismissListener - onDismiss()");
            CommonDialog commonDialog = CommonDialog.this;
            DialogInterface.OnDismissListener onDismissListener = commonDialog.mDismissListner;
            if (onDismissListener != null) {
                onDismissListener.onDismiss(commonDialog.mDialog);
            }
            CommonDialog commonDialog2 = CommonDialog.this;
            commonDialog2.mContext = null;
            commonDialog2.mDialog = null;
            if (commonDialog2.mShowButtonBackgroundSettingObserver != null) {
                CommonDialog.this.mShowButtonBackgroundSettingObserver.setOnContentChangeListener(null);
            }
        }
    };
    private final ShowButtonBackgroundSettingObserver.OnSettingValueChangeListener mOnSettingValueChangeListener = new ShowButtonBackgroundSettingObserver.OnSettingValueChangeListener() {
        /* class com.samsung.android.app.twatchmanager.util.CommonDialog.AnonymousClass1 */

        @Override // com.samsung.android.app.twatchmanager.util.ShowButtonBackgroundSettingObserver.OnSettingValueChangeListener
        public void onChange(boolean z) {
            CommonDialog.this.initShowButtonBackground(z);
        }
    };
    private LinearLayout mPopupBtnLayout = null;
    private RelativeLayout mPopupMessageLayout = null;
    protected ProgressBar mProgressBar = null;
    protected RelativeLayout mProgressBarLayout = null;
    protected TextView mProgressBarMessageTV = null;
    protected TextView mProgressBarPercentTV = null;
    private ShowButtonBackgroundSettingObserver mShowButtonBackgroundSettingObserver;
    protected TextView mTitleTV = null;
    private int mTitleType = 0;
    protected LinearLayout midBtnDividerOuter = null;

    public interface Button_Type {
        public static final int BUTTON_NOTHING = 0;
        public static final int BUTTON_OK_CANCEL = 3;
        public static final int BUTTON_ONLY_CANCEL = 2;
        public static final int BUTTON_ONLY_OK = 1;
    }

    public interface Message_Type {
        public static final int MESSAGE_CIRCLE_PROGRESS = 4;
        public static final int MESSAGE_CIRCLE_PROGRESS_COUNT = 8;
        public static final int MESSAGE_IMAGE_TEXT = 2;
        public static final int MESSAGE_IMAGE_TEXT_CHECKBOX = 3;
        public static final int MESSAGE_NO_NETWORK = 7;
        public static final int MESSAGE_ONLY_TEXT = 0;
        public static final int MESSAGE_PROGRESS_BAR = 5;
        public static final int MESSAGE_QUICK_TUTORIAL = 6;
        public static final int MESSAGE_TEXT_CHECKBOX = 1;
    }

    public interface Title_Type {
        public static final int TITLE_EXIST = 1;
        public static final int TITLE_NOTHING = 0;
    }

    public CommonDialog(Context context, int i, int i2, int i3) {
        this.mContext = context;
        this.mTitleType = i;
        this.mMessageType = i2;
        this.mButtonType = i3;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    @TargetApi(21)
    private void initShowButtonBackground(boolean z) {
        String str = TAG;
        Log.d(str, "initShowButtonBackground() showButtonShape:" + z);
        int i = z ? R.drawable.button_background_vision_show_shape : R.drawable.button_background;
        TextView textView = this.mOkBtn;
        if (textView != null) {
            textView.setBackgroundResource(i);
        }
        TextView textView2 = this.mCancelBtn;
        if (textView2 != null) {
            textView2.setBackgroundResource(i);
        }
    }

    public void cancel() {
        k kVar = this.mDialog;
        if (kVar != null) {
            kVar.cancel();
        }
    }

    public void createDialog() {
        Log.i(TAG, "createDialog()");
        View inflate = ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(R.layout.common_dialog_template, (ViewGroup) null);
        setAcitivyTheme();
        getResourceID(inflate);
        makeDialogForm();
        if (HostManagerUtils.isSupportButtonShapes()) {
            this.mShowButtonBackgroundSettingObserver = new ShowButtonBackgroundSettingObserver(this.mContext.getContentResolver());
            this.mShowButtonBackgroundSettingObserver.setOnContentChangeListener(this.mOnSettingValueChangeListener);
        }
        k.a aVar = new k.a(this.mContext);
        aVar.b(inflate);
        this.mDialog = aVar.a();
        this.mDialog.setCancelable(true);
        int i = this.mMessageType;
        if (i == 4 || i == 5) {
            String str = TAG;
            Log.i(str, "mMessageType : " + this.mMessageType);
            Log.i(TAG, "Ignore key input!!!");
            this.mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                /* class com.samsung.android.app.twatchmanager.util.CommonDialog.AnonymousClass3 */

                public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                    return true;
                }
            });
        }
        this.mDialog.setOnDismissListener(this.mOnDismissListener);
        this.mDialog.getWindow().setFlags(256, 256);
        this.mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.mDialog.getWindow().setGravity(80);
        this.mDialog.show();
    }

    public void dismiss() {
        k kVar = this.mDialog;
        if (kVar != null) {
            try {
                kVar.dismiss();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void getResourceID(View view) {
        Log.i(TAG, "getResourceID()");
        this.mTitleTV = (TextView) view.findViewById(R.id.popup_title_textview);
        this.mPopupMessageLayout = (RelativeLayout) view.findViewById(R.id.popup_message_layout);
        this.mMessageTV = (TextView) view.findViewById(R.id.popup_message_textview);
        this.mMessageIV = (ImageView) view.findViewById(R.id.popup_message_imageview);
        this.mLayout_CB_TV = (LinearLayout) view.findViewById(R.id.checkbox_textview_layout);
        this.mCB = (CheckBox) view.findViewById(R.id.do_not_show_again_checkbox);
        this.mCBMessageTV = (TextView) view.findViewById(R.id.do_not_show_again_textview);
        this.mBottomMargin_IV_TV = view.findViewById(R.id.between_imageview_to_textview_padding_view);
        this.mBottomMargin_TV_CB = view.findViewById(R.id.between_textview_to_checkbox_padding_view);
        this.mPopupBtnLayout = (LinearLayout) view.findViewById(R.id.popup_button_layout);
        this.mOkBtn = (TextView) view.findViewById(R.id.ok_btn);
        this.mCancelBtn = (TextView) view.findViewById(R.id.cancel_btn);
        this.mOkBtnContainer = (LinearLayout) view.findViewById(R.id.ok_btn_container);
        this.mCancelBtnContainer = (LinearLayout) view.findViewById(R.id.cancel_btn_container);
        this.midBtnDividerOuter = (LinearLayout) view.findViewById(R.id.mid_btn_divider_outer);
        this.mCircleProgressLayout = (RelativeLayout) view.findViewById(R.id.circle_progress_popup_layout);
        ProgressBar progressBar = (ProgressBar) this.mCircleProgressLayout.findViewById(R.id.circle_progress);
        if (progressBar != null && Build.VERSION.SDK_INT >= 21) {
            UIUtils.setColorFilter("#" + Integer.toHexString(this.mContext.getResources().getColor(R.color.setup_progress_tint)), progressBar.getIndeterminateDrawable());
        }
        this.mCircleProgressTV = (TextView) view.findViewById(R.id.circle_progress_message_textview);
        this.mProgressBarLayout = (RelativeLayout) view.findViewById(R.id.progress_bar_popup_layout);
        this.mProgressBarMessageTV = (TextView) view.findViewById(R.id.progress_bar_popup_message_textview);
        this.mProgressBarPercentTV = (TextView) view.findViewById(R.id.progress_bar_popup_percent_textview);
        this.mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar_popup_progressbar);
        this.mCircleProgressCountLayout = (LinearLayout) view.findViewById(R.id.circle_progress_count_popup_layout);
        this.mCircleProgressCountIV = (ImageView) view.findViewById(R.id.circle_progress_count_imageview);
        this.mCircleProgressCountMessageTV = (TextView) view.findViewById(R.id.circle_progress_count_popup_message_textview);
        this.mCircleProgressCountTV = (TextView) view.findViewById(R.id.circle_progress_count_popup_count_textview);
    }

    public boolean isCheckedCB() {
        return this.mCB.isChecked();
    }

    public boolean isShowing() {
        k kVar = this.mDialog;
        if (kVar != null) {
            return kVar.isShowing();
        }
        return false;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x012a, code lost:
        if (r0 != null) goto L_0x014a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0139, code lost:
        if (r0 != null) goto L_0x014a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0148, code lost:
        if (r0 != null) goto L_0x014a;
     */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x010b  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x014e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void makeDialogForm() {
        /*
        // Method dump skipped, instructions count: 419
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.util.CommonDialog.makeDialogForm():void");
    }

    /* access modifiers changed from: protected */
    public void setAcitivyTheme() {
        Context context = this.mContext;
        if (context != null && (context instanceof Activity)) {
            ((Activity) context).setTheme(R.style.DialogTheme);
        }
    }

    public void setCancelBtnListener(View.OnClickListener onClickListener) {
        this.mCancelBtn.setOnClickListener(onClickListener);
    }

    public void setCancelable(boolean z) {
        k kVar = this.mDialog;
        if (kVar != null) {
            kVar.setCancelable(z);
        } else {
            Log.d(TAG, "mDialog is null. please create dialog first.");
        }
    }

    public void setCanceledOnTouchOutside(boolean z) {
        k kVar = this.mDialog;
        if (kVar != null) {
            kVar.setCanceledOnTouchOutside(z);
        } else {
            Log.d(TAG, "mDialog is null. please create dialog first.");
        }
    }

    public void setCheckBoxListener(View.OnClickListener onClickListener) {
        this.mLayout_CB_TV.setOnClickListener(onClickListener);
    }

    public void setCheckCB() {
        boolean z;
        CheckBox checkBox;
        if (this.mCB.isChecked()) {
            checkBox = this.mCB;
            z = false;
        } else {
            checkBox = this.mCB;
            z = true;
        }
        checkBox.setChecked(z);
    }

    public void setImageToImageView(int i) {
        this.mMessageIV.setImageResource(i);
    }

    public void setMaxHeightToTextView(int i) {
        this.mMessageTV.setMaxHeight(i);
    }

    public void setMessage(String str) {
        TextView textView;
        int i = this.mMessageType;
        if (i == 4) {
            textView = this.mCircleProgressTV;
        } else if (i == 5) {
            textView = this.mProgressBarMessageTV;
        } else if (i == 8) {
            textView = this.mCircleProgressCountMessageTV;
        } else {
            this.mMessageTV.setText(str);
            this.mMessageTV.setMovementMethod(new ScrollingMovementMethod());
            return;
        }
        textView.setText(str);
    }

    public void setMessageFromHtml(String str) {
        this.mMessageTV.setText(Html.fromHtml(str));
        this.mMessageTV.setMovementMethod(LinkMovementMethod.getInstance());
        this.mMessageTV.setMovementMethod(new ScrollingMovementMethod());
    }

    /* access modifiers changed from: protected */
    public void setMessageType(int i) {
        this.mMessageType = i;
        if (i == 8) {
            this.mPopupMessageLayout.setVisibility(8);
            this.mCircleProgressLayout.setVisibility(8);
            this.mProgressBarLayout.setVisibility(8);
            this.mCircleProgressCountLayout.setVisibility(0);
            Animation loadAnimation = AnimationUtils.loadAnimation(this.mContext, R.anim.rotate);
            if (this.mCircleProgressCountIV != null && loadAnimation != null) {
                loadAnimation.setFillAfter(true);
                loadAnimation.setRepeatMode(1);
                loadAnimation.setRepeatCount(-1);
                this.mCircleProgressCountIV.startAnimation(loadAnimation);
            }
        }
    }

    public void setOkBtnClickable(boolean z) {
        this.mOkBtn.setClickable(z);
    }

    public void setOkBtnEnable(boolean z) {
        this.mOkBtn.setEnabled(z);
        this.mOkBtn.setAlpha(z ? 1.0f : 0.5f);
    }

    public void setOkBtnListener(View.OnClickListener onClickListener) {
        this.mOkBtn.setOnClickListener(onClickListener);
    }

    public void setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        this.mDialog.setOnCancelListener(onCancelListener);
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.mDismissListner = onDismissListener;
    }

    public void setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
        this.mDialog.setOnKeyListener(onKeyListener);
    }

    public void setTextCapsForCancelBtn(boolean z) {
        this.mCancelBtn.setAllCaps(z);
    }

    public void setTextCapsForOkBtn(boolean z) {
        this.mOkBtn.setAllCaps(z);
    }

    public void setTextToCancelBtn(String str) {
        this.mCancelBtn.setText(str);
    }

    public void setTextToCheckBox(String str) {
        this.mCBMessageTV.setText(str);
    }

    public void setTextToOkBtn(String str) {
        this.mOkBtn.setText(str);
    }

    public void setTitle(String str) {
        this.mTitleTV.setText(str);
        if (this.mTitleTV.getLineCount() >= 2) {
            this.mTitleTV.setTextSize(1, 19.0f);
        }
    }

    public void show() {
        k kVar = this.mDialog;
        if (kVar != null) {
            kVar.show();
        }
    }
}
