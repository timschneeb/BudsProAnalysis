package com.samsung.android.app.twatchmanager.uiitems;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.viewpager.widget.a;
import com.samsung.android.app.watchmanager.R;
import java.util.List;

public class ImageAdapter extends a {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private List<Drawable> IMAGES;
    private Context context;
    private LayoutInflater inflater;

    public ImageAdapter(Context context2, List<Drawable> list) {
        this.context = context2;
        this.IMAGES = list;
        this.inflater = LayoutInflater.from(context2);
    }

    @Override // androidx.viewpager.widget.a
    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        viewGroup.removeView((View) obj);
    }

    @Override // androidx.viewpager.widget.a
    public int getCount() {
        return this.IMAGES.size();
    }

    @Override // androidx.viewpager.widget.a
    public Object instantiateItem(ViewGroup viewGroup, int i) {
        View inflate = this.inflater.inflate(R.layout.background_item, viewGroup, false);
        ((ImageView) inflate.findViewById(R.id.image)).setImageDrawable(this.IMAGES.get(i));
        viewGroup.addView(inflate, 0);
        return inflate;
    }

    @Override // androidx.viewpager.widget.a
    public boolean isViewFromObject(View view, Object obj) {
        return view.equals(obj);
    }

    @Override // androidx.viewpager.widget.a
    public void restoreState(Parcelable parcelable, ClassLoader classLoader) {
    }

    @Override // androidx.viewpager.widget.a
    public Parcelable saveState() {
        return null;
    }
}
