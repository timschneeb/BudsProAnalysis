package androidx.appcompat.util;

import android.content.ContentResolver;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import androidx.core.view.ViewCompat;

public class SeslShowButtonShapesHelper {
    private Drawable mBackgroundOff;
    private Drawable mBackgroundOn;
    private ContentResolver mContentResolver;
    private View mView;

    public SeslShowButtonShapesHelper(View view, Drawable drawable, Drawable drawable2) {
        this.mView = view;
        this.mContentResolver = view.getContext().getContentResolver();
        this.mBackgroundOn = drawable;
        this.mBackgroundOff = drawable2;
    }

    public void setBackgroundOff(Drawable drawable) {
        Drawable drawable2 = this.mBackgroundOn;
        if (drawable2 == null || drawable2 != drawable) {
            this.mBackgroundOff = drawable;
            return;
        }
        Log.w("SeslSBBHelper", drawable + "is same drawable with mBackgroundOn");
    }

    public void setBackgroundOn(Drawable drawable) {
        this.mBackgroundOn = drawable;
    }

    public void updateButtonBackground() {
        boolean z = false;
        if (Settings.System.getInt(this.mContentResolver, "show_button_background", 0) == 1) {
            z = true;
        }
        ViewCompat.setBackground(this.mView, z ? this.mBackgroundOn : this.mBackgroundOff);
    }

    public void updateOverflowButtonBackground(Drawable drawable) {
        this.mBackgroundOn = drawable;
        updateButtonBackground();
    }
}
