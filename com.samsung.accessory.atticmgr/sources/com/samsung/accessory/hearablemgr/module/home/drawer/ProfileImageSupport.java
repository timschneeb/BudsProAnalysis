package com.samsung.accessory.hearablemgr.module.home.drawer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.common.ui.OnSingleClickListener;
import com.samsung.accessory.hearablemgr.common.util.ResponseCallback;
import com.samsung.accessory.hearablemgr.common.util.SamsungAccountUtil;
import com.samsung.accessory.hearablemgr.common.util.SeMobileServiceUtil;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.android.sdk.mobileservice.profile.ProfileApi;
import com.samsung.android.sdk.mobileservice.profile.result.ProfileResult;
import seccompat.android.util.Log;

public class ProfileImageSupport {
    private static final String TAG = "Attic_ProfileImageSupport";
    private final Activity mActivity;
    private final OnSingleClickListener mClickListener = new OnSingleClickListener() {
        /* class com.samsung.accessory.hearablemgr.module.home.drawer.ProfileImageSupport.AnonymousClass2 */

        @Override // com.samsung.accessory.hearablemgr.common.ui.OnSingleClickListener
        public void onSingleClick(View view) {
            Log.d(ProfileImageSupport.TAG, "onClick()");
            SamsungAccountUtil.startSettingActivity(ProfileImageSupport.this.mActivity);
        }
    };
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final ImageView mImageView;

    ProfileImageSupport(Activity activity) {
        this.mActivity = activity;
        this.mImageView = (ImageView) this.mActivity.findViewById(R.id.image_account);
        this.mImageView.setOnClickListener(this.mClickListener);
        clipImageViewToCircle();
        updateUI();
    }

    public void updateUI() {
        Log.d(TAG, "updateUI()");
        boolean isSupport = isSupport();
        this.mImageView.setVisibility(isSupport ? 0 : 4);
        this.mImageView.setEnabled(isSupport);
        if (!isSupport) {
            return;
        }
        if (SamsungAccountUtil.isSignedIn()) {
            SeMobileServiceUtil.getProfileApi(new ResponseCallback() {
                /* class com.samsung.accessory.hearablemgr.module.home.drawer.ProfileImageSupport.AnonymousClass1 */

                @Override // com.samsung.accessory.hearablemgr.common.util.ResponseCallback
                public void onResponse(String str) {
                    ProfileResult profile;
                    byte[] photo;
                    if (str == null) {
                        Log.d(ProfileImageSupport.TAG, "onResponse() : " + getExtraObject());
                        ProfileApi profileApi = (ProfileApi) getExtraObject();
                        if (profileApi != null && (profile = profileApi.getProfile()) != null && profile.getStatus() != null && profile.getStatus().getCode() == 1 && (photo = profile.getResult().getPhotoInstance().getPhoto()) != null) {
                            final Bitmap decodeByteArray = BitmapFactory.decodeByteArray(photo, 0, photo.length);
                            ProfileImageSupport.this.mHandler.post(new Runnable() {
                                /* class com.samsung.accessory.hearablemgr.module.home.drawer.ProfileImageSupport.AnonymousClass1.AnonymousClass1 */

                                public void run() {
                                    ProfileImageSupport.this.mImageView.setImageBitmap(decodeByteArray);
                                    ProfileImageSupport.this.clipImageViewToCircle();
                                }
                            });
                            return;
                        }
                        return;
                    }
                    Log.e(ProfileImageSupport.TAG, "failureReason : " + str);
                    ProfileImageSupport.this.mHandler.post(new Runnable() {
                        /* class com.samsung.accessory.hearablemgr.module.home.drawer.ProfileImageSupport.AnonymousClass1.AnonymousClass2 */

                        public void run() {
                            ProfileImageSupport.this.setImageViewToDefault();
                        }
                    });
                }
            });
        } else {
            setImageViewToDefault();
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setImageViewToDefault() {
        this.mImageView.setImageResource(R.drawable.drawer_ic_account_default);
        clipImageViewToCircle();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void clipImageViewToCircle() {
        ImageView imageView = this.mImageView;
        if (imageView != null) {
            imageView.setBackground(new ShapeDrawable(new OvalShape()));
            this.mImageView.setClipToOutline(true);
        }
    }

    static boolean isSupport() {
        boolean z = Build.VERSION.SDK_INT >= 23 && Util.isSamsungDevice() && SamsungAccountUtil.isSettingSupport();
        Log.d(TAG, "isSupport() : " + z);
        return z;
    }
}
