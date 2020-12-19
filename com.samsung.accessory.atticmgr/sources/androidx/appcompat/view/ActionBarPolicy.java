package androidx.appcompat.view;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Build;
import android.view.KeyCharacterMap;
import android.view.ViewConfiguration;
import androidx.appcompat.R;

public class ActionBarPolicy {
    private static final float MENU_WIDTH_LIMIT_FACTOR = 0.7f;
    private Context mContext;

    public boolean enableHomeButtonByDefault() {
        return false;
    }

    public int getStackedTabMaxWidth() {
        return 0;
    }

    public boolean hasEmbeddedTabs() {
        return false;
    }

    public static ActionBarPolicy get(Context context) {
        return new ActionBarPolicy(context);
    }

    private ActionBarPolicy(Context context) {
        this.mContext = context;
    }

    public int getMaxActionButtons() {
        Configuration configuration = this.mContext.getResources().getConfiguration();
        int i = configuration.screenWidthDp;
        int i2 = configuration.screenHeightDp;
        if (configuration.smallestScreenWidthDp > 600 || i > 600) {
            return 5;
        }
        if (i > 960 && i2 > 720) {
            return 5;
        }
        if (i > 720 && i2 > 960) {
            return 5;
        }
        if (i >= 500) {
            return 4;
        }
        if (i > 640 && i2 > 480) {
            return 4;
        }
        if (i <= 480 || i2 <= 640) {
            return i >= 360 ? 3 : 2;
        }
        return 4;
    }

    public boolean showsOverflowMenuButton() {
        if (Build.VERSION.SDK_INT >= 19) {
            return true;
        }
        return !ViewConfiguration.get(this.mContext).hasPermanentMenuKey();
    }

    public int getEmbeddedMenuWidthLimit() {
        return (int) (((float) this.mContext.getResources().getDisplayMetrics().widthPixels) * MENU_WIDTH_LIMIT_FACTOR);
    }

    public int getTabContainerHeight() {
        TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(null, R.styleable.ActionBar, R.attr.actionBarStyle, 0);
        int layoutDimension = obtainStyledAttributes.getLayoutDimension(R.styleable.ActionBar_height, 0);
        obtainStyledAttributes.recycle();
        return layoutDimension;
    }

    public boolean hasNavigationBar() {
        return !ViewConfiguration.get(this.mContext).hasPermanentMenuKey() && !KeyCharacterMap.deviceHasKey(4);
    }
}
