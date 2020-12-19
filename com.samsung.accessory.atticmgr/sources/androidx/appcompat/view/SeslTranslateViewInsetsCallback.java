package androidx.appcompat.view;

import android.graphics.Insets;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import java.util.List;

public class SeslTranslateViewInsetsCallback extends WindowInsetsAnimation.Callback {
    private int mDeferInsetTypes;
    private int mPersistentInsetTypes;
    private View mView;

    public SeslTranslateViewInsetsCallback(View view, int i, int i2) {
        this(view, i, i2, 0);
    }

    public SeslTranslateViewInsetsCallback(View view, int i, int i2, int i3) {
        super(i3);
        this.mView = view;
        this.mPersistentInsetTypes = i;
        this.mDeferInsetTypes = i2;
    }

    public WindowInsets onProgress(WindowInsets windowInsets, List<WindowInsetsAnimation> list) {
        Insets max = Insets.max(Insets.subtract(windowInsets.getInsets(this.mDeferInsetTypes), windowInsets.getInsets(this.mPersistentInsetTypes)), Insets.NONE);
        this.mView.setTranslationY((float) (max.top - max.bottom));
        return windowInsets;
    }

    public void onEnd(WindowInsetsAnimation windowInsetsAnimation) {
        this.mView.setTranslationY(0.0f);
    }
}
