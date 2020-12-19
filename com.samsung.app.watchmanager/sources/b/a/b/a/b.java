package b.a.b.a;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.StateSet;
import androidx.core.content.a.i;
import b.a.b.a.d;
import b.a.b.a.f;
import b.c.j;
import b.l.a.a.k;
import com.samsung.android.app.twatchmanager.util.ResourceRulesParser;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class b extends f {
    private C0019b p;
    private f q;
    private int r;
    private int s;
    private boolean t;

    /* access modifiers changed from: private */
    public static class a extends f {

        /* renamed from: a  reason: collision with root package name */
        private final Animatable f1210a;

        a(Animatable animatable) {
            super();
            this.f1210a = animatable;
        }

        @Override // b.a.b.a.b.f
        public void c() {
            this.f1210a.start();
        }

        @Override // b.a.b.a.b.f
        public void d() {
            this.f1210a.stop();
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b.a.b.a.b$b  reason: collision with other inner class name */
    public static class C0019b extends f.a {
        b.c.f<Long> K;
        j<Integer> L;

        C0019b(C0019b bVar, b bVar2, Resources resources) {
            super(bVar, bVar2, resources);
            j<Integer> jVar;
            if (bVar != null) {
                this.K = bVar.K;
                jVar = bVar.L;
            } else {
                this.K = new b.c.f<>();
                jVar = new j<>();
            }
            this.L = jVar;
        }

        private static long f(int i, int i2) {
            return ((long) i2) | (((long) i) << 32);
        }

        /* access modifiers changed from: package-private */
        public int a(int i, int i2, Drawable drawable, boolean z) {
            int a2 = super.a(drawable);
            long f = f(i, i2);
            long j = z ? 8589934592L : 0;
            long j2 = (long) a2;
            this.K.a(f, Long.valueOf(j2 | j));
            if (z) {
                this.K.a(f(i2, i), Long.valueOf(4294967296L | j2 | j));
            }
            return a2;
        }

        /* access modifiers changed from: package-private */
        public int a(int[] iArr, Drawable drawable, int i) {
            int a2 = super.a(iArr, drawable);
            this.L.c(a2, Integer.valueOf(i));
            return a2;
        }

        /* access modifiers changed from: package-private */
        public int b(int[] iArr) {
            int a2 = super.a(iArr);
            return a2 >= 0 ? a2 : super.a(StateSet.WILD_CARD);
        }

        /* access modifiers changed from: package-private */
        public int c(int i, int i2) {
            return (int) this.K.b(f(i, i2), -1L).longValue();
        }

        /* access modifiers changed from: package-private */
        public int d(int i) {
            if (i < 0) {
                return 0;
            }
            return this.L.b(i, 0).intValue();
        }

        /* access modifiers changed from: package-private */
        public boolean d(int i, int i2) {
            return (this.K.b(f(i, i2), -1L).longValue() & 4294967296L) != 0;
        }

        /* access modifiers changed from: package-private */
        public boolean e(int i, int i2) {
            return (this.K.b(f(i, i2), -1L).longValue() & 8589934592L) != 0;
        }

        /* access modifiers changed from: package-private */
        @Override // b.a.b.a.f.a, b.a.b.a.d.b
        public void m() {
            this.K = this.K.clone();
            this.L = this.L.clone();
        }

        @Override // b.a.b.a.f.a
        public Drawable newDrawable() {
            return new b(this, null);
        }

        @Override // b.a.b.a.f.a
        public Drawable newDrawable(Resources resources) {
            return new b(this, resources);
        }
    }

    /* access modifiers changed from: private */
    public static class c extends f {

        /* renamed from: a  reason: collision with root package name */
        private final b.l.a.a.d f1211a;

        c(b.l.a.a.d dVar) {
            super();
            this.f1211a = dVar;
        }

        @Override // b.a.b.a.b.f
        public void c() {
            this.f1211a.start();
        }

        @Override // b.a.b.a.b.f
        public void d() {
            this.f1211a.stop();
        }
    }

    /* access modifiers changed from: private */
    public static class d extends f {

        /* renamed from: a  reason: collision with root package name */
        private final ObjectAnimator f1212a;

        /* renamed from: b  reason: collision with root package name */
        private final boolean f1213b;

        d(AnimationDrawable animationDrawable, boolean z, boolean z2) {
            super();
            int numberOfFrames = animationDrawable.getNumberOfFrames();
            int i = z ? numberOfFrames - 1 : 0;
            int i2 = z ? 0 : numberOfFrames - 1;
            e eVar = new e(animationDrawable, z);
            ObjectAnimator ofInt = ObjectAnimator.ofInt(animationDrawable, "currentIndex", i, i2);
            if (Build.VERSION.SDK_INT >= 18) {
                ofInt.setAutoCancel(true);
            }
            ofInt.setDuration((long) eVar.a());
            ofInt.setInterpolator(eVar);
            this.f1213b = z2;
            this.f1212a = ofInt;
        }

        @Override // b.a.b.a.b.f
        public boolean a() {
            return this.f1213b;
        }

        @Override // b.a.b.a.b.f
        public void b() {
            this.f1212a.reverse();
        }

        @Override // b.a.b.a.b.f
        public void c() {
            this.f1212a.start();
        }

        @Override // b.a.b.a.b.f
        public void d() {
            this.f1212a.cancel();
        }
    }

    private static class e implements TimeInterpolator {

        /* renamed from: a  reason: collision with root package name */
        private int[] f1214a;

        /* renamed from: b  reason: collision with root package name */
        private int f1215b;

        /* renamed from: c  reason: collision with root package name */
        private int f1216c;

        e(AnimationDrawable animationDrawable, boolean z) {
            a(animationDrawable, z);
        }

        /* access modifiers changed from: package-private */
        public int a() {
            return this.f1216c;
        }

        /* access modifiers changed from: package-private */
        public int a(AnimationDrawable animationDrawable, boolean z) {
            int numberOfFrames = animationDrawable.getNumberOfFrames();
            this.f1215b = numberOfFrames;
            int[] iArr = this.f1214a;
            if (iArr == null || iArr.length < numberOfFrames) {
                this.f1214a = new int[numberOfFrames];
            }
            int[] iArr2 = this.f1214a;
            int i = 0;
            for (int i2 = 0; i2 < numberOfFrames; i2++) {
                int duration = animationDrawable.getDuration(z ? (numberOfFrames - i2) - 1 : i2);
                iArr2[i2] = duration;
                i += duration;
            }
            this.f1216c = i;
            return i;
        }

        public float getInterpolation(float f) {
            int i = (int) ((f * ((float) this.f1216c)) + 0.5f);
            int i2 = this.f1215b;
            int[] iArr = this.f1214a;
            int i3 = 0;
            while (i3 < i2 && i >= iArr[i3]) {
                i -= iArr[i3];
                i3++;
            }
            return (((float) i3) / ((float) i2)) + (i3 < i2 ? ((float) i) / ((float) this.f1216c) : 0.0f);
        }
    }

    /* access modifiers changed from: private */
    public static abstract class f {
        private f() {
        }

        public boolean a() {
            return false;
        }

        public void b() {
        }

        public abstract void c();

        public abstract void d();
    }

    public b() {
        this(null, null);
    }

    b(C0019b bVar, Resources resources) {
        super(null);
        this.r = -1;
        this.s = -1;
        a(new C0019b(bVar, this, resources));
        onStateChange(getState());
        jumpToCurrentState();
    }

    public static b a(Context context, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
        String name = xmlPullParser.getName();
        if (name.equals("animated-selector")) {
            b bVar = new b();
            bVar.b(context, resources, xmlPullParser, attributeSet, theme);
            return bVar;
        }
        throw new XmlPullParserException(xmlPullParser.getPositionDescription() + ": invalid animated-selector tag " + name);
    }

    private void a(TypedArray typedArray) {
        C0019b bVar = this.p;
        if (Build.VERSION.SDK_INT >= 21) {
            bVar.f1226d |= typedArray.getChangingConfigurations();
        }
        bVar.b(typedArray.getBoolean(b.a.j.AnimatedStateListDrawableCompat_android_variablePadding, bVar.i));
        bVar.a(typedArray.getBoolean(b.a.j.AnimatedStateListDrawableCompat_android_constantSize, bVar.l));
        bVar.b(typedArray.getInt(b.a.j.AnimatedStateListDrawableCompat_android_enterFadeDuration, bVar.A));
        bVar.c(typedArray.getInt(b.a.j.AnimatedStateListDrawableCompat_android_exitFadeDuration, bVar.B));
        setDither(typedArray.getBoolean(b.a.j.AnimatedStateListDrawableCompat_android_dither, bVar.x));
    }

    private boolean b(int i) {
        int i2;
        int c2;
        f fVar;
        f fVar2 = this.q;
        if (fVar2 == null) {
            i2 = b();
        } else if (i == this.r) {
            return true;
        } else {
            if (i != this.s || !fVar2.a()) {
                i2 = this.r;
                fVar2.d();
            } else {
                fVar2.b();
                this.r = this.s;
                this.s = i;
                return true;
            }
        }
        this.q = null;
        this.s = -1;
        this.r = -1;
        C0019b bVar = this.p;
        int d2 = bVar.d(i2);
        int d3 = bVar.d(i);
        if (d3 == 0 || d2 == 0 || (c2 = bVar.c(d2, d3)) < 0) {
            return false;
        }
        boolean e2 = bVar.e(d2, d3);
        a(c2);
        Drawable current = getCurrent();
        if (current instanceof AnimationDrawable) {
            fVar = new d((AnimationDrawable) current, bVar.d(d2, d3), e2);
        } else if (current instanceof b.l.a.a.d) {
            fVar = new c((b.l.a.a.d) current);
        } else {
            if (current instanceof Animatable) {
                fVar = new a((Animatable) current);
            }
            return false;
        }
        fVar.c();
        this.q = fVar;
        this.s = i2;
        this.r = i;
        return true;
    }

    private void c(Context context, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
        int depth = xmlPullParser.getDepth() + 1;
        while (true) {
            int next = xmlPullParser.next();
            if (next != 1) {
                int depth2 = xmlPullParser.getDepth();
                if (depth2 < depth && next == 3) {
                    return;
                }
                if (next == 2 && depth2 <= depth) {
                    if (xmlPullParser.getName().equals(ResourceRulesParser.XML_TAG_GROUP_IMAGES_ITEM)) {
                        d(context, resources, xmlPullParser, attributeSet, theme);
                    } else if (xmlPullParser.getName().equals("transition")) {
                        e(context, resources, xmlPullParser, attributeSet, theme);
                    }
                }
            } else {
                return;
            }
        }
    }

    private int d(Context context, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
        int next;
        TypedArray a2 = i.a(resources, theme, attributeSet, b.a.j.AnimatedStateListDrawableItem);
        int resourceId = a2.getResourceId(b.a.j.AnimatedStateListDrawableItem_android_id, 0);
        int resourceId2 = a2.getResourceId(b.a.j.AnimatedStateListDrawableItem_android_drawable, -1);
        Drawable b2 = resourceId2 > 0 ? b.a.a.a.a.b(context, resourceId2) : null;
        a2.recycle();
        int[] a3 = a(attributeSet);
        if (b2 == null) {
            do {
                next = xmlPullParser.next();
            } while (next == 4);
            if (next == 2) {
                b2 = xmlPullParser.getName().equals("vector") ? k.createFromXmlInner(resources, xmlPullParser, attributeSet, theme) : Build.VERSION.SDK_INT >= 21 ? Drawable.createFromXmlInner(resources, xmlPullParser, attributeSet, theme) : Drawable.createFromXmlInner(resources, xmlPullParser, attributeSet);
            } else {
                throw new XmlPullParserException(xmlPullParser.getPositionDescription() + ": <item> tag requires a 'drawable' attribute or child tag defining a drawable");
            }
        }
        if (b2 != null) {
            return this.p.a(a3, b2, resourceId);
        }
        throw new XmlPullParserException(xmlPullParser.getPositionDescription() + ": <item> tag requires a 'drawable' attribute or child tag defining a drawable");
    }

    private void d() {
        onStateChange(getState());
    }

    private int e(Context context, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
        int next;
        TypedArray a2 = i.a(resources, theme, attributeSet, b.a.j.AnimatedStateListDrawableTransition);
        int resourceId = a2.getResourceId(b.a.j.AnimatedStateListDrawableTransition_android_fromId, -1);
        int resourceId2 = a2.getResourceId(b.a.j.AnimatedStateListDrawableTransition_android_toId, -1);
        int resourceId3 = a2.getResourceId(b.a.j.AnimatedStateListDrawableTransition_android_drawable, -1);
        Drawable b2 = resourceId3 > 0 ? b.a.a.a.a.b(context, resourceId3) : null;
        boolean z = a2.getBoolean(b.a.j.AnimatedStateListDrawableTransition_android_reversible, false);
        a2.recycle();
        if (b2 == null) {
            do {
                next = xmlPullParser.next();
            } while (next == 4);
            if (next == 2) {
                b2 = xmlPullParser.getName().equals("animated-vector") ? b.l.a.a.d.a(context, resources, xmlPullParser, attributeSet, theme) : Build.VERSION.SDK_INT >= 21 ? Drawable.createFromXmlInner(resources, xmlPullParser, attributeSet, theme) : Drawable.createFromXmlInner(resources, xmlPullParser, attributeSet);
            } else {
                throw new XmlPullParserException(xmlPullParser.getPositionDescription() + ": <transition> tag requires a 'drawable' attribute or child tag defining a drawable");
            }
        }
        if (b2 == null) {
            throw new XmlPullParserException(xmlPullParser.getPositionDescription() + ": <transition> tag requires a 'drawable' attribute or child tag defining a drawable");
        } else if (resourceId != -1 && resourceId2 != -1) {
            return this.p.a(resourceId, resourceId2, b2, z);
        } else {
            throw new XmlPullParserException(xmlPullParser.getPositionDescription() + ": <transition> tag requires 'fromId' & 'toId' attributes");
        }
    }

    /* access modifiers changed from: package-private */
    @Override // b.a.b.a.f, b.a.b.a.f, b.a.b.a.d
    public C0019b a() {
        return new C0019b(this.p, this, null);
    }

    /* access modifiers changed from: protected */
    @Override // b.a.b.a.f, b.a.b.a.d
    public void a(d.b bVar) {
        super.a(bVar);
        if (bVar instanceof C0019b) {
            this.p = (C0019b) bVar;
        }
    }

    @Override // b.a.b.a.f, b.a.b.a.d
    public /* bridge */ /* synthetic */ void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
    }

    public void b(Context context, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
        TypedArray a2 = i.a(resources, theme, attributeSet, b.a.j.AnimatedStateListDrawableCompat);
        setVisible(a2.getBoolean(b.a.j.AnimatedStateListDrawableCompat_android_visible, true), true);
        a(a2);
        a(resources);
        a2.recycle();
        c(context, resources, xmlPullParser, attributeSet, theme);
        d();
    }

    @Override // b.a.b.a.d
    public /* bridge */ /* synthetic */ boolean canApplyTheme() {
        return super.canApplyTheme();
    }

    @Override // b.a.b.a.d
    public /* bridge */ /* synthetic */ void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override // b.a.b.a.d
    public /* bridge */ /* synthetic */ int getAlpha() {
        return super.getAlpha();
    }

    @Override // b.a.b.a.d
    public /* bridge */ /* synthetic */ int getChangingConfigurations() {
        return super.getChangingConfigurations();
    }

    @Override // b.a.b.a.d
    public /* bridge */ /* synthetic */ Drawable getCurrent() {
        return super.getCurrent();
    }

    @Override // b.a.b.a.d
    public /* bridge */ /* synthetic */ void getHotspotBounds(Rect rect) {
        super.getHotspotBounds(rect);
    }

    @Override // b.a.b.a.d
    public /* bridge */ /* synthetic */ int getIntrinsicHeight() {
        return super.getIntrinsicHeight();
    }

    @Override // b.a.b.a.d
    public /* bridge */ /* synthetic */ int getIntrinsicWidth() {
        return super.getIntrinsicWidth();
    }

    @Override // b.a.b.a.d
    public /* bridge */ /* synthetic */ int getMinimumHeight() {
        return super.getMinimumHeight();
    }

    @Override // b.a.b.a.d
    public /* bridge */ /* synthetic */ int getMinimumWidth() {
        return super.getMinimumWidth();
    }

    @Override // b.a.b.a.d
    public /* bridge */ /* synthetic */ int getOpacity() {
        return super.getOpacity();
    }

    @Override // b.a.b.a.d
    public /* bridge */ /* synthetic */ void getOutline(Outline outline) {
        super.getOutline(outline);
    }

    @Override // b.a.b.a.d
    public /* bridge */ /* synthetic */ boolean getPadding(Rect rect) {
        return super.getPadding(rect);
    }

    @Override // b.a.b.a.d
    public /* bridge */ /* synthetic */ void invalidateDrawable(Drawable drawable) {
        super.invalidateDrawable(drawable);
    }

    @Override // b.a.b.a.d
    public /* bridge */ /* synthetic */ boolean isAutoMirrored() {
        return super.isAutoMirrored();
    }

    @Override // b.a.b.a.f
    public boolean isStateful() {
        return true;
    }

    @Override // b.a.b.a.d
    public void jumpToCurrentState() {
        super.jumpToCurrentState();
        f fVar = this.q;
        if (fVar != null) {
            fVar.d();
            this.q = null;
            a(this.r);
            this.r = -1;
            this.s = -1;
        }
    }

    @Override // b.a.b.a.f, b.a.b.a.d
    public Drawable mutate() {
        if (!this.t) {
            super.mutate();
            if (this == this) {
                this.p.m();
                this.t = true;
            }
        }
        return this;
    }

    @Override // b.a.b.a.d
    public /* bridge */ /* synthetic */ boolean onLayoutDirectionChanged(int i) {
        return super.onLayoutDirectionChanged(i);
    }

    /* access modifiers changed from: protected */
    @Override // b.a.b.a.f, b.a.b.a.d
    public boolean onStateChange(int[] iArr) {
        int b2 = this.p.b(iArr);
        boolean z = b2 != b() && (b(b2) || a(b2));
        Drawable current = getCurrent();
        return current != null ? z | current.setState(iArr) : z;
    }

    @Override // b.a.b.a.d
    public /* bridge */ /* synthetic */ void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        super.scheduleDrawable(drawable, runnable, j);
    }

    @Override // b.a.b.a.d
    public /* bridge */ /* synthetic */ void setAlpha(int i) {
        super.setAlpha(i);
    }

    @Override // b.a.b.a.d
    public /* bridge */ /* synthetic */ void setAutoMirrored(boolean z) {
        super.setAutoMirrored(z);
    }

    @Override // b.a.b.a.d
    public /* bridge */ /* synthetic */ void setColorFilter(ColorFilter colorFilter) {
        super.setColorFilter(colorFilter);
    }

    @Override // b.a.b.a.d
    public /* bridge */ /* synthetic */ void setDither(boolean z) {
        super.setDither(z);
    }

    @Override // b.a.b.a.d
    public /* bridge */ /* synthetic */ void setHotspot(float f2, float f3) {
        super.setHotspot(f2, f3);
    }

    @Override // b.a.b.a.d
    public /* bridge */ /* synthetic */ void setHotspotBounds(int i, int i2, int i3, int i4) {
        super.setHotspotBounds(i, i2, i3, i4);
    }

    @Override // b.a.b.a.d
    public /* bridge */ /* synthetic */ void setTintList(ColorStateList colorStateList) {
        super.setTintList(colorStateList);
    }

    @Override // b.a.b.a.d
    public /* bridge */ /* synthetic */ void setTintMode(PorterDuff.Mode mode) {
        super.setTintMode(mode);
    }

    @Override // b.a.b.a.d
    public boolean setVisible(boolean z, boolean z2) {
        boolean visible = super.setVisible(z, z2);
        if (this.q != null && (visible || z2)) {
            if (z) {
                this.q.c();
            } else {
                jumpToCurrentState();
            }
        }
        return visible;
    }

    @Override // b.a.b.a.d
    public /* bridge */ /* synthetic */ void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        super.unscheduleDrawable(drawable, runnable);
    }
}
