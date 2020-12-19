package b.l.a.a;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import androidx.core.content.a.i;
import b.e.a.b;
import com.samsung.android.app.twatchmanager.util.ResourceRulesParser;
import java.util.ArrayDeque;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class k extends i {

    /* renamed from: b  reason: collision with root package name */
    static final PorterDuff.Mode f1500b = PorterDuff.Mode.SRC_IN;

    /* renamed from: c  reason: collision with root package name */
    private g f1501c;

    /* renamed from: d  reason: collision with root package name */
    private PorterDuffColorFilter f1502d;
    private ColorFilter e;
    private boolean f;
    private boolean g;
    private Drawable.ConstantState h;
    private final float[] i;
    private final Matrix j;
    private final Rect k;

    /* access modifiers changed from: private */
    public static class a extends e {
        public a() {
        }

        public a(a aVar) {
            super(aVar);
        }

        private void a(TypedArray typedArray) {
            String string = typedArray.getString(0);
            if (string != null) {
                this.f1509b = string;
            }
            String string2 = typedArray.getString(1);
            if (string2 != null) {
                this.f1508a = b.e.a.b.a(string2);
            }
        }

        public void a(Resources resources, AttributeSet attributeSet, Resources.Theme theme, XmlPullParser xmlPullParser) {
            if (i.a(xmlPullParser, "pathData")) {
                TypedArray a2 = i.a(resources, theme, attributeSet, a.f1485d);
                a(a2);
                a2.recycle();
            }
        }

        @Override // b.l.a.a.k.e
        public boolean b() {
            return true;
        }
    }

    /* access modifiers changed from: private */
    public static class b extends e {

        /* renamed from: d  reason: collision with root package name */
        private int[] f1503d;
        androidx.core.content.a.b e;
        float f = 0.0f;
        androidx.core.content.a.b g;
        float h = 1.0f;
        int i = 0;
        float j = 1.0f;
        float k = 0.0f;
        float l = 1.0f;
        float m = 0.0f;
        Paint.Cap n = Paint.Cap.BUTT;
        Paint.Join o = Paint.Join.MITER;
        float p = 4.0f;

        public b() {
        }

        public b(b bVar) {
            super(bVar);
            this.f1503d = bVar.f1503d;
            this.e = bVar.e;
            this.f = bVar.f;
            this.h = bVar.h;
            this.g = bVar.g;
            this.i = bVar.i;
            this.j = bVar.j;
            this.k = bVar.k;
            this.l = bVar.l;
            this.m = bVar.m;
            this.n = bVar.n;
            this.o = bVar.o;
            this.p = bVar.p;
        }

        private Paint.Cap a(int i2, Paint.Cap cap) {
            return i2 != 0 ? i2 != 1 ? i2 != 2 ? cap : Paint.Cap.SQUARE : Paint.Cap.ROUND : Paint.Cap.BUTT;
        }

        private Paint.Join a(int i2, Paint.Join join) {
            return i2 != 0 ? i2 != 1 ? i2 != 2 ? join : Paint.Join.BEVEL : Paint.Join.ROUND : Paint.Join.MITER;
        }

        private void a(TypedArray typedArray, XmlPullParser xmlPullParser, Resources.Theme theme) {
            this.f1503d = null;
            if (i.a(xmlPullParser, "pathData")) {
                String string = typedArray.getString(0);
                if (string != null) {
                    this.f1509b = string;
                }
                String string2 = typedArray.getString(2);
                if (string2 != null) {
                    this.f1508a = b.e.a.b.a(string2);
                }
                this.g = i.a(typedArray, xmlPullParser, theme, "fillColor", 1, 0);
                this.j = i.a(typedArray, xmlPullParser, "fillAlpha", 12, this.j);
                this.n = a(i.b(typedArray, xmlPullParser, "strokeLineCap", 8, -1), this.n);
                this.o = a(i.b(typedArray, xmlPullParser, "strokeLineJoin", 9, -1), this.o);
                this.p = i.a(typedArray, xmlPullParser, "strokeMiterLimit", 10, this.p);
                this.e = i.a(typedArray, xmlPullParser, theme, "strokeColor", 3, 0);
                this.h = i.a(typedArray, xmlPullParser, "strokeAlpha", 11, this.h);
                this.f = i.a(typedArray, xmlPullParser, "strokeWidth", 4, this.f);
                this.l = i.a(typedArray, xmlPullParser, "trimPathEnd", 6, this.l);
                this.m = i.a(typedArray, xmlPullParser, "trimPathOffset", 7, this.m);
                this.k = i.a(typedArray, xmlPullParser, "trimPathStart", 5, this.k);
                this.i = i.b(typedArray, xmlPullParser, "fillType", 13, this.i);
            }
        }

        public void a(Resources resources, AttributeSet attributeSet, Resources.Theme theme, XmlPullParser xmlPullParser) {
            TypedArray a2 = i.a(resources, theme, attributeSet, a.f1484c);
            a(a2, xmlPullParser, theme);
            a2.recycle();
        }

        @Override // b.l.a.a.k.d
        public boolean a() {
            return this.g.d() || this.e.d();
        }

        @Override // b.l.a.a.k.d
        public boolean a(int[] iArr) {
            return this.e.a(iArr) | this.g.a(iArr);
        }

        /* access modifiers changed from: package-private */
        public float getFillAlpha() {
            return this.j;
        }

        /* access modifiers changed from: package-private */
        public int getFillColor() {
            return this.g.a();
        }

        /* access modifiers changed from: package-private */
        public float getStrokeAlpha() {
            return this.h;
        }

        /* access modifiers changed from: package-private */
        public int getStrokeColor() {
            return this.e.a();
        }

        /* access modifiers changed from: package-private */
        public float getStrokeWidth() {
            return this.f;
        }

        /* access modifiers changed from: package-private */
        public float getTrimPathEnd() {
            return this.l;
        }

        /* access modifiers changed from: package-private */
        public float getTrimPathOffset() {
            return this.m;
        }

        /* access modifiers changed from: package-private */
        public float getTrimPathStart() {
            return this.k;
        }

        /* access modifiers changed from: package-private */
        public void setFillAlpha(float f2) {
            this.j = f2;
        }

        /* access modifiers changed from: package-private */
        public void setFillColor(int i2) {
            this.g.b(i2);
        }

        /* access modifiers changed from: package-private */
        public void setStrokeAlpha(float f2) {
            this.h = f2;
        }

        /* access modifiers changed from: package-private */
        public void setStrokeColor(int i2) {
            this.e.b(i2);
        }

        /* access modifiers changed from: package-private */
        public void setStrokeWidth(float f2) {
            this.f = f2;
        }

        /* access modifiers changed from: package-private */
        public void setTrimPathEnd(float f2) {
            this.l = f2;
        }

        /* access modifiers changed from: package-private */
        public void setTrimPathOffset(float f2) {
            this.m = f2;
        }

        /* access modifiers changed from: package-private */
        public void setTrimPathStart(float f2) {
            this.k = f2;
        }
    }

    /* access modifiers changed from: private */
    public static class c extends d {

        /* renamed from: a  reason: collision with root package name */
        final Matrix f1504a = new Matrix();

        /* renamed from: b  reason: collision with root package name */
        final ArrayList<d> f1505b = new ArrayList<>();

        /* renamed from: c  reason: collision with root package name */
        float f1506c = 0.0f;

        /* renamed from: d  reason: collision with root package name */
        private float f1507d = 0.0f;
        private float e = 0.0f;
        private float f = 1.0f;
        private float g = 1.0f;
        private float h = 0.0f;
        private float i = 0.0f;
        final Matrix j = new Matrix();
        int k;
        private int[] l;
        private String m = null;

        public c() {
            super();
        }

        public c(c cVar, b.c.b<String, Object> bVar) {
            super();
            e eVar;
            this.f1506c = cVar.f1506c;
            this.f1507d = cVar.f1507d;
            this.e = cVar.e;
            this.f = cVar.f;
            this.g = cVar.g;
            this.h = cVar.h;
            this.i = cVar.i;
            this.l = cVar.l;
            this.m = cVar.m;
            this.k = cVar.k;
            String str = this.m;
            if (str != null) {
                bVar.put(str, this);
            }
            this.j.set(cVar.j);
            ArrayList<d> arrayList = cVar.f1505b;
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                d dVar = arrayList.get(i2);
                if (dVar instanceof c) {
                    this.f1505b.add(new c((c) dVar, bVar));
                } else {
                    if (dVar instanceof b) {
                        eVar = new b((b) dVar);
                    } else if (dVar instanceof a) {
                        eVar = new a((a) dVar);
                    } else {
                        throw new IllegalStateException("Unknown object in the tree!");
                    }
                    this.f1505b.add(eVar);
                    String str2 = eVar.f1509b;
                    if (str2 != null) {
                        bVar.put(str2, eVar);
                    }
                }
            }
        }

        private void a(TypedArray typedArray, XmlPullParser xmlPullParser) {
            this.l = null;
            this.f1506c = i.a(typedArray, xmlPullParser, "rotation", 5, this.f1506c);
            this.f1507d = typedArray.getFloat(1, this.f1507d);
            this.e = typedArray.getFloat(2, this.e);
            this.f = i.a(typedArray, xmlPullParser, "scaleX", 3, this.f);
            this.g = i.a(typedArray, xmlPullParser, "scaleY", 4, this.g);
            this.h = i.a(typedArray, xmlPullParser, "translateX", 6, this.h);
            this.i = i.a(typedArray, xmlPullParser, "translateY", 7, this.i);
            String string = typedArray.getString(0);
            if (string != null) {
                this.m = string;
            }
            b();
        }

        private void b() {
            this.j.reset();
            this.j.postTranslate(-this.f1507d, -this.e);
            this.j.postScale(this.f, this.g);
            this.j.postRotate(this.f1506c, 0.0f, 0.0f);
            this.j.postTranslate(this.h + this.f1507d, this.i + this.e);
        }

        public void a(Resources resources, AttributeSet attributeSet, Resources.Theme theme, XmlPullParser xmlPullParser) {
            TypedArray a2 = i.a(resources, theme, attributeSet, a.f1483b);
            a(a2, xmlPullParser);
            a2.recycle();
        }

        @Override // b.l.a.a.k.d
        public boolean a() {
            for (int i2 = 0; i2 < this.f1505b.size(); i2++) {
                if (this.f1505b.get(i2).a()) {
                    return true;
                }
            }
            return false;
        }

        @Override // b.l.a.a.k.d
        public boolean a(int[] iArr) {
            boolean z = false;
            for (int i2 = 0; i2 < this.f1505b.size(); i2++) {
                z |= this.f1505b.get(i2).a(iArr);
            }
            return z;
        }

        public String getGroupName() {
            return this.m;
        }

        public Matrix getLocalMatrix() {
            return this.j;
        }

        public float getPivotX() {
            return this.f1507d;
        }

        public float getPivotY() {
            return this.e;
        }

        public float getRotation() {
            return this.f1506c;
        }

        public float getScaleX() {
            return this.f;
        }

        public float getScaleY() {
            return this.g;
        }

        public float getTranslateX() {
            return this.h;
        }

        public float getTranslateY() {
            return this.i;
        }

        public void setPivotX(float f2) {
            if (f2 != this.f1507d) {
                this.f1507d = f2;
                b();
            }
        }

        public void setPivotY(float f2) {
            if (f2 != this.e) {
                this.e = f2;
                b();
            }
        }

        public void setRotation(float f2) {
            if (f2 != this.f1506c) {
                this.f1506c = f2;
                b();
            }
        }

        public void setScaleX(float f2) {
            if (f2 != this.f) {
                this.f = f2;
                b();
            }
        }

        public void setScaleY(float f2) {
            if (f2 != this.g) {
                this.g = f2;
                b();
            }
        }

        public void setTranslateX(float f2) {
            if (f2 != this.h) {
                this.h = f2;
                b();
            }
        }

        public void setTranslateY(float f2) {
            if (f2 != this.i) {
                this.i = f2;
                b();
            }
        }
    }

    /* access modifiers changed from: private */
    public static abstract class d {
        private d() {
        }

        public boolean a() {
            return false;
        }

        public boolean a(int[] iArr) {
            return false;
        }
    }

    /* access modifiers changed from: private */
    public static abstract class e extends d {

        /* renamed from: a  reason: collision with root package name */
        protected b.C0021b[] f1508a = null;

        /* renamed from: b  reason: collision with root package name */
        String f1509b;

        /* renamed from: c  reason: collision with root package name */
        int f1510c;

        public e() {
            super();
        }

        public e(e eVar) {
            super();
            this.f1509b = eVar.f1509b;
            this.f1510c = eVar.f1510c;
            this.f1508a = b.e.a.b.a(eVar.f1508a);
        }

        public void a(Path path) {
            path.reset();
            b.C0021b[] bVarArr = this.f1508a;
            if (bVarArr != null) {
                b.C0021b.a(bVarArr, path);
            }
        }

        public boolean b() {
            return false;
        }

        public b.C0021b[] getPathData() {
            return this.f1508a;
        }

        public String getPathName() {
            return this.f1509b;
        }

        public void setPathData(b.C0021b[] bVarArr) {
            if (!b.e.a.b.a(this.f1508a, bVarArr)) {
                this.f1508a = b.e.a.b.a(bVarArr);
            } else {
                b.e.a.b.b(this.f1508a, bVarArr);
            }
        }
    }

    /* access modifiers changed from: private */
    public static class f {

        /* renamed from: a  reason: collision with root package name */
        private static final Matrix f1511a = new Matrix();

        /* renamed from: b  reason: collision with root package name */
        private final Path f1512b;

        /* renamed from: c  reason: collision with root package name */
        private final Path f1513c;

        /* renamed from: d  reason: collision with root package name */
        private final Matrix f1514d;
        Paint e;
        Paint f;
        private PathMeasure g;
        private int h;
        final c i;
        float j;
        float k;
        float l;
        float m;
        int n;
        String o;
        Boolean p;
        final b.c.b<String, Object> q;

        public f() {
            this.f1514d = new Matrix();
            this.j = 0.0f;
            this.k = 0.0f;
            this.l = 0.0f;
            this.m = 0.0f;
            this.n = 255;
            this.o = null;
            this.p = null;
            this.q = new b.c.b<>();
            this.i = new c();
            this.f1512b = new Path();
            this.f1513c = new Path();
        }

        public f(f fVar) {
            this.f1514d = new Matrix();
            this.j = 0.0f;
            this.k = 0.0f;
            this.l = 0.0f;
            this.m = 0.0f;
            this.n = 255;
            this.o = null;
            this.p = null;
            this.q = new b.c.b<>();
            this.i = new c(fVar.i, this.q);
            this.f1512b = new Path(fVar.f1512b);
            this.f1513c = new Path(fVar.f1513c);
            this.j = fVar.j;
            this.k = fVar.k;
            this.l = fVar.l;
            this.m = fVar.m;
            this.h = fVar.h;
            this.n = fVar.n;
            this.o = fVar.o;
            String str = fVar.o;
            if (str != null) {
                this.q.put(str, this);
            }
            this.p = fVar.p;
        }

        private static float a(float f2, float f3, float f4, float f5) {
            return (f2 * f5) - (f3 * f4);
        }

        private float a(Matrix matrix) {
            float[] fArr = {0.0f, 1.0f, 1.0f, 0.0f};
            matrix.mapVectors(fArr);
            float a2 = a(fArr[0], fArr[1], fArr[2], fArr[3]);
            float max = Math.max((float) Math.hypot((double) fArr[0], (double) fArr[1]), (float) Math.hypot((double) fArr[2], (double) fArr[3]));
            if (max > 0.0f) {
                return Math.abs(a2) / max;
            }
            return 0.0f;
        }

        private void a(c cVar, Matrix matrix, Canvas canvas, int i2, int i3, ColorFilter colorFilter) {
            cVar.f1504a.set(matrix);
            cVar.f1504a.preConcat(cVar.j);
            canvas.save();
            for (int i4 = 0; i4 < cVar.f1505b.size(); i4++) {
                d dVar = cVar.f1505b.get(i4);
                if (dVar instanceof c) {
                    a((c) dVar, cVar.f1504a, canvas, i2, i3, colorFilter);
                } else if (dVar instanceof e) {
                    a(cVar, (e) dVar, canvas, i2, i3, colorFilter);
                }
            }
            canvas.restore();
        }

        private void a(c cVar, e eVar, Canvas canvas, int i2, int i3, ColorFilter colorFilter) {
            float f2 = ((float) i2) / this.l;
            float f3 = ((float) i3) / this.m;
            float min = Math.min(f2, f3);
            Matrix matrix = cVar.f1504a;
            this.f1514d.set(matrix);
            this.f1514d.postScale(f2, f3);
            float a2 = a(matrix);
            if (a2 != 0.0f) {
                eVar.a(this.f1512b);
                Path path = this.f1512b;
                this.f1513c.reset();
                if (eVar.b()) {
                    this.f1513c.addPath(path, this.f1514d);
                    canvas.clipPath(this.f1513c);
                    return;
                }
                b bVar = (b) eVar;
                if (!(bVar.k == 0.0f && bVar.l == 1.0f)) {
                    float f4 = bVar.k;
                    float f5 = bVar.m;
                    float f6 = (f4 + f5) % 1.0f;
                    float f7 = (bVar.l + f5) % 1.0f;
                    if (this.g == null) {
                        this.g = new PathMeasure();
                    }
                    this.g.setPath(this.f1512b, false);
                    float length = this.g.getLength();
                    float f8 = f6 * length;
                    float f9 = f7 * length;
                    path.reset();
                    if (f8 > f9) {
                        this.g.getSegment(f8, length, path, true);
                        this.g.getSegment(0.0f, f9, path, true);
                    } else {
                        this.g.getSegment(f8, f9, path, true);
                    }
                    path.rLineTo(0.0f, 0.0f);
                }
                this.f1513c.addPath(path, this.f1514d);
                if (bVar.g.e()) {
                    androidx.core.content.a.b bVar2 = bVar.g;
                    if (this.f == null) {
                        this.f = new Paint(1);
                        this.f.setStyle(Paint.Style.FILL);
                    }
                    Paint paint = this.f;
                    if (bVar2.c()) {
                        Shader b2 = bVar2.b();
                        b2.setLocalMatrix(this.f1514d);
                        paint.setShader(b2);
                        paint.setAlpha(Math.round(bVar.j * 255.0f));
                    } else {
                        paint.setColor(k.a(bVar2.a(), bVar.j));
                    }
                    paint.setColorFilter(colorFilter);
                    this.f1513c.setFillType(bVar.i == 0 ? Path.FillType.WINDING : Path.FillType.EVEN_ODD);
                    canvas.drawPath(this.f1513c, paint);
                }
                if (bVar.e.e()) {
                    androidx.core.content.a.b bVar3 = bVar.e;
                    if (this.e == null) {
                        this.e = new Paint(1);
                        this.e.setStyle(Paint.Style.STROKE);
                    }
                    Paint paint2 = this.e;
                    Paint.Join join = bVar.o;
                    if (join != null) {
                        paint2.setStrokeJoin(join);
                    }
                    Paint.Cap cap = bVar.n;
                    if (cap != null) {
                        paint2.setStrokeCap(cap);
                    }
                    paint2.setStrokeMiter(bVar.p);
                    if (bVar3.c()) {
                        Shader b3 = bVar3.b();
                        b3.setLocalMatrix(this.f1514d);
                        paint2.setShader(b3);
                        paint2.setAlpha(Math.round(bVar.h * 255.0f));
                    } else {
                        paint2.setColor(k.a(bVar3.a(), bVar.h));
                    }
                    paint2.setColorFilter(colorFilter);
                    paint2.setStrokeWidth(bVar.f * min * a2);
                    canvas.drawPath(this.f1513c, paint2);
                }
            }
        }

        public void a(Canvas canvas, int i2, int i3, ColorFilter colorFilter) {
            a(this.i, f1511a, canvas, i2, i3, colorFilter);
        }

        public boolean a() {
            if (this.p == null) {
                this.p = Boolean.valueOf(this.i.a());
            }
            return this.p.booleanValue();
        }

        public boolean a(int[] iArr) {
            return this.i.a(iArr);
        }

        public float getAlpha() {
            return ((float) getRootAlpha()) / 255.0f;
        }

        public int getRootAlpha() {
            return this.n;
        }

        public void setAlpha(float f2) {
            setRootAlpha((int) (f2 * 255.0f));
        }

        public void setRootAlpha(int i2) {
            this.n = i2;
        }
    }

    /* access modifiers changed from: private */
    public static class g extends Drawable.ConstantState {

        /* renamed from: a  reason: collision with root package name */
        int f1515a;

        /* renamed from: b  reason: collision with root package name */
        f f1516b;

        /* renamed from: c  reason: collision with root package name */
        ColorStateList f1517c;

        /* renamed from: d  reason: collision with root package name */
        PorterDuff.Mode f1518d;
        boolean e;
        Bitmap f;
        ColorStateList g;
        PorterDuff.Mode h;
        int i;
        boolean j;
        boolean k;
        Paint l;

        public g() {
            this.f1517c = null;
            this.f1518d = k.f1500b;
            this.f1516b = new f();
        }

        public g(g gVar) {
            this.f1517c = null;
            this.f1518d = k.f1500b;
            if (gVar != null) {
                this.f1515a = gVar.f1515a;
                this.f1516b = new f(gVar.f1516b);
                Paint paint = gVar.f1516b.f;
                if (paint != null) {
                    this.f1516b.f = new Paint(paint);
                }
                Paint paint2 = gVar.f1516b.e;
                if (paint2 != null) {
                    this.f1516b.e = new Paint(paint2);
                }
                this.f1517c = gVar.f1517c;
                this.f1518d = gVar.f1518d;
                this.e = gVar.e;
            }
        }

        public Paint a(ColorFilter colorFilter) {
            if (!b() && colorFilter == null) {
                return null;
            }
            if (this.l == null) {
                this.l = new Paint();
                this.l.setFilterBitmap(true);
            }
            this.l.setAlpha(this.f1516b.getRootAlpha());
            this.l.setColorFilter(colorFilter);
            return this.l;
        }

        public void a(Canvas canvas, ColorFilter colorFilter, Rect rect) {
            canvas.drawBitmap(this.f, (Rect) null, rect, a(colorFilter));
        }

        public boolean a() {
            return !this.k && this.g == this.f1517c && this.h == this.f1518d && this.j == this.e && this.i == this.f1516b.getRootAlpha();
        }

        public boolean a(int i2, int i3) {
            return i2 == this.f.getWidth() && i3 == this.f.getHeight();
        }

        public boolean a(int[] iArr) {
            boolean a2 = this.f1516b.a(iArr);
            this.k |= a2;
            return a2;
        }

        public void b(int i2, int i3) {
            if (this.f == null || !a(i2, i3)) {
                this.f = Bitmap.createBitmap(i2, i3, Bitmap.Config.ARGB_8888);
                this.k = true;
            }
        }

        public boolean b() {
            return this.f1516b.getRootAlpha() < 255;
        }

        public void c(int i2, int i3) {
            this.f.eraseColor(0);
            this.f1516b.a(new Canvas(this.f), i2, i3, (ColorFilter) null);
        }

        public boolean c() {
            return this.f1516b.a();
        }

        public void d() {
            this.g = this.f1517c;
            this.h = this.f1518d;
            this.i = this.f1516b.getRootAlpha();
            this.j = this.e;
            this.k = false;
        }

        public int getChangingConfigurations() {
            return this.f1515a;
        }

        public Drawable newDrawable() {
            return new k(this);
        }

        public Drawable newDrawable(Resources resources) {
            return new k(this);
        }
    }

    /* access modifiers changed from: private */
    public static class h extends Drawable.ConstantState {

        /* renamed from: a  reason: collision with root package name */
        private final Drawable.ConstantState f1519a;

        public h(Drawable.ConstantState constantState) {
            this.f1519a = constantState;
        }

        public boolean canApplyTheme() {
            return this.f1519a.canApplyTheme();
        }

        public int getChangingConfigurations() {
            return this.f1519a.getChangingConfigurations();
        }

        public Drawable newDrawable() {
            k kVar = new k();
            kVar.f1499a = (VectorDrawable) this.f1519a.newDrawable();
            return kVar;
        }

        public Drawable newDrawable(Resources resources) {
            k kVar = new k();
            kVar.f1499a = (VectorDrawable) this.f1519a.newDrawable(resources);
            return kVar;
        }

        public Drawable newDrawable(Resources resources, Resources.Theme theme) {
            k kVar = new k();
            kVar.f1499a = (VectorDrawable) this.f1519a.newDrawable(resources, theme);
            return kVar;
        }
    }

    k() {
        this.g = true;
        this.i = new float[9];
        this.j = new Matrix();
        this.k = new Rect();
        this.f1501c = new g();
    }

    k(g gVar) {
        this.g = true;
        this.i = new float[9];
        this.j = new Matrix();
        this.k = new Rect();
        this.f1501c = gVar;
        this.f1502d = a(this.f1502d, gVar.f1517c, gVar.f1518d);
    }

    static int a(int i2, float f2) {
        return (i2 & 16777215) | (((int) (((float) Color.alpha(i2)) * f2)) << 24);
    }

    private static PorterDuff.Mode a(int i2, PorterDuff.Mode mode) {
        if (i2 == 3) {
            return PorterDuff.Mode.SRC_OVER;
        }
        if (i2 == 5) {
            return PorterDuff.Mode.SRC_IN;
        }
        if (i2 == 9) {
            return PorterDuff.Mode.SRC_ATOP;
        }
        switch (i2) {
            case 14:
                return PorterDuff.Mode.MULTIPLY;
            case 15:
                return PorterDuff.Mode.SCREEN;
            case 16:
                return PorterDuff.Mode.ADD;
            default:
                return mode;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0038 A[Catch:{ IOException | XmlPullParserException -> 0x0045 }] */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x003d A[Catch:{ IOException | XmlPullParserException -> 0x0045 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static b.l.a.a.k a(android.content.res.Resources r6, int r7, android.content.res.Resources.Theme r8) {
        /*
            java.lang.String r0 = "parser error"
            java.lang.String r1 = "VectorDrawableCompat"
            int r2 = android.os.Build.VERSION.SDK_INT
            r3 = 24
            if (r2 < r3) goto L_0x0023
            b.l.a.a.k r0 = new b.l.a.a.k
            r0.<init>()
            android.graphics.drawable.Drawable r6 = androidx.core.content.a.h.a(r6, r7, r8)
            r0.f1499a = r6
            b.l.a.a.k$h r6 = new b.l.a.a.k$h
            android.graphics.drawable.Drawable r7 = r0.f1499a
            android.graphics.drawable.Drawable$ConstantState r7 = r7.getConstantState()
            r6.<init>(r7)
            r0.h = r6
            return r0
        L_0x0023:
            android.content.res.XmlResourceParser r7 = r6.getXml(r7)     // Catch:{ XmlPullParserException -> 0x0047, IOException -> 0x0045 }
            android.util.AttributeSet r2 = android.util.Xml.asAttributeSet(r7)     // Catch:{ XmlPullParserException -> 0x0047, IOException -> 0x0045 }
        L_0x002b:
            int r3 = r7.next()     // Catch:{ XmlPullParserException -> 0x0047, IOException -> 0x0045 }
            r4 = 2
            if (r3 == r4) goto L_0x0036
            r5 = 1
            if (r3 == r5) goto L_0x0036
            goto L_0x002b
        L_0x0036:
            if (r3 != r4) goto L_0x003d
            b.l.a.a.k r6 = createFromXmlInner(r6, r7, r2, r8)     // Catch:{ XmlPullParserException -> 0x0047, IOException -> 0x0045 }
            return r6
        L_0x003d:
            org.xmlpull.v1.XmlPullParserException r6 = new org.xmlpull.v1.XmlPullParserException     // Catch:{ XmlPullParserException -> 0x0047, IOException -> 0x0045 }
            java.lang.String r7 = "No start tag found"
            r6.<init>(r7)     // Catch:{ XmlPullParserException -> 0x0047, IOException -> 0x0045 }
            throw r6     // Catch:{ XmlPullParserException -> 0x0047, IOException -> 0x0045 }
        L_0x0045:
            r6 = move-exception
            goto L_0x0048
        L_0x0047:
            r6 = move-exception
        L_0x0048:
            android.util.Log.e(r1, r0, r6)
            r6 = 0
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: b.l.a.a.k.a(android.content.res.Resources, int, android.content.res.Resources$Theme):b.l.a.a.k");
    }

    /* JADX DEBUG: Multi-variable search result rejected for r3v15, resolved type: b.l.a.a.k$b */
    /* JADX WARN: Multi-variable type inference failed */
    private void a(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
        int i2;
        int i3;
        a aVar;
        g gVar = this.f1501c;
        f fVar = gVar.f1516b;
        ArrayDeque arrayDeque = new ArrayDeque();
        arrayDeque.push(fVar.i);
        int eventType = xmlPullParser.getEventType();
        int depth = xmlPullParser.getDepth() + 1;
        boolean z = true;
        while (eventType != 1 && (xmlPullParser.getDepth() >= depth || eventType != 3)) {
            if (eventType == 2) {
                String name = xmlPullParser.getName();
                c cVar = (c) arrayDeque.peek();
                if ("path".equals(name)) {
                    b bVar = new b();
                    bVar.a(resources, attributeSet, theme, xmlPullParser);
                    cVar.f1505b.add(bVar);
                    if (bVar.getPathName() != null) {
                        fVar.q.put(bVar.getPathName(), bVar);
                    }
                    z = false;
                    aVar = bVar;
                } else if ("clip-path".equals(name)) {
                    a aVar2 = new a();
                    aVar2.a(resources, attributeSet, theme, xmlPullParser);
                    cVar.f1505b.add(aVar2);
                    String pathName = aVar2.getPathName();
                    aVar = aVar2;
                    if (pathName != null) {
                        fVar.q.put(aVar2.getPathName(), aVar2);
                        aVar = aVar2;
                    }
                } else if (ResourceRulesParser.XML_TAG_GROUP.equals(name)) {
                    c cVar2 = new c();
                    cVar2.a(resources, attributeSet, theme, xmlPullParser);
                    cVar.f1505b.add(cVar2);
                    arrayDeque.push(cVar2);
                    if (cVar2.getGroupName() != null) {
                        fVar.q.put(cVar2.getGroupName(), cVar2);
                    }
                    i2 = gVar.f1515a;
                    i3 = cVar2.k;
                    gVar.f1515a = i3 | i2;
                }
                i2 = gVar.f1515a;
                i3 = aVar.f1510c;
                gVar.f1515a = i3 | i2;
            } else if (eventType == 3 && ResourceRulesParser.XML_TAG_GROUP.equals(xmlPullParser.getName())) {
                arrayDeque.pop();
            }
            eventType = xmlPullParser.next();
        }
        if (z) {
            throw new XmlPullParserException("no path defined");
        }
    }

    private void a(TypedArray typedArray, XmlPullParser xmlPullParser) {
        g gVar = this.f1501c;
        f fVar = gVar.f1516b;
        gVar.f1518d = a(i.b(typedArray, xmlPullParser, "tintMode", 6, -1), PorterDuff.Mode.SRC_IN);
        ColorStateList colorStateList = typedArray.getColorStateList(1);
        if (colorStateList != null) {
            gVar.f1517c = colorStateList;
        }
        gVar.e = i.a(typedArray, xmlPullParser, "autoMirrored", 5, gVar.e);
        fVar.l = i.a(typedArray, xmlPullParser, "viewportWidth", 7, fVar.l);
        fVar.m = i.a(typedArray, xmlPullParser, "viewportHeight", 8, fVar.m);
        if (fVar.l <= 0.0f) {
            throw new XmlPullParserException(typedArray.getPositionDescription() + "<vector> tag requires viewportWidth > 0");
        } else if (fVar.m > 0.0f) {
            fVar.j = typedArray.getDimension(3, fVar.j);
            fVar.k = typedArray.getDimension(2, fVar.k);
            if (fVar.j <= 0.0f) {
                throw new XmlPullParserException(typedArray.getPositionDescription() + "<vector> tag requires width > 0");
            } else if (fVar.k > 0.0f) {
                fVar.setAlpha(i.a(typedArray, xmlPullParser, "alpha", 4, fVar.getAlpha()));
                String string = typedArray.getString(0);
                if (string != null) {
                    fVar.o = string;
                    fVar.q.put(string, fVar);
                }
            } else {
                throw new XmlPullParserException(typedArray.getPositionDescription() + "<vector> tag requires height > 0");
            }
        } else {
            throw new XmlPullParserException(typedArray.getPositionDescription() + "<vector> tag requires viewportHeight > 0");
        }
    }

    private boolean a() {
        return Build.VERSION.SDK_INT >= 17 && isAutoMirrored() && androidx.core.graphics.drawable.a.d(this) == 1;
    }

    public static k createFromXmlInner(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
        k kVar = new k();
        kVar.inflate(resources, xmlPullParser, attributeSet, theme);
        return kVar;
    }

    /* access modifiers changed from: package-private */
    public PorterDuffColorFilter a(PorterDuffColorFilter porterDuffColorFilter, ColorStateList colorStateList, PorterDuff.Mode mode) {
        if (colorStateList == null || mode == null) {
            return null;
        }
        return new PorterDuffColorFilter(colorStateList.getColorForState(getState(), 0), mode);
    }

    /* access modifiers changed from: package-private */
    public Object a(String str) {
        return this.f1501c.f1516b.q.get(str);
    }

    /* access modifiers changed from: package-private */
    public void a(boolean z) {
        this.g = z;
    }

    @Override // b.l.a.a.i
    public /* bridge */ /* synthetic */ void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
    }

    public boolean canApplyTheme() {
        Drawable drawable = this.f1499a;
        if (drawable == null) {
            return false;
        }
        androidx.core.graphics.drawable.a.a(drawable);
        return false;
    }

    @Override // b.l.a.a.i
    public /* bridge */ /* synthetic */ void clearColorFilter() {
        super.clearColorFilter();
    }

    public void draw(Canvas canvas) {
        Drawable drawable = this.f1499a;
        if (drawable != null) {
            drawable.draw(canvas);
            return;
        }
        copyBounds(this.k);
        if (this.k.width() > 0 && this.k.height() > 0) {
            ColorFilter colorFilter = this.e;
            if (colorFilter == null) {
                colorFilter = this.f1502d;
            }
            canvas.getMatrix(this.j);
            this.j.getValues(this.i);
            float abs = Math.abs(this.i[0]);
            float abs2 = Math.abs(this.i[4]);
            float abs3 = Math.abs(this.i[1]);
            float abs4 = Math.abs(this.i[3]);
            if (!(abs3 == 0.0f && abs4 == 0.0f)) {
                abs = 1.0f;
                abs2 = 1.0f;
            }
            int min = Math.min(2048, (int) (((float) this.k.width()) * abs));
            int min2 = Math.min(2048, (int) (((float) this.k.height()) * abs2));
            if (min > 0 && min2 > 0) {
                int save = canvas.save();
                Rect rect = this.k;
                canvas.translate((float) rect.left, (float) rect.top);
                if (a()) {
                    canvas.translate((float) this.k.width(), 0.0f);
                    canvas.scale(-1.0f, 1.0f);
                }
                this.k.offsetTo(0, 0);
                this.f1501c.b(min, min2);
                if (!this.g) {
                    this.f1501c.c(min, min2);
                } else if (!this.f1501c.a()) {
                    this.f1501c.c(min, min2);
                    this.f1501c.d();
                }
                this.f1501c.a(canvas, colorFilter, this.k);
                canvas.restoreToCount(save);
            }
        }
    }

    public int getAlpha() {
        Drawable drawable = this.f1499a;
        return drawable != null ? androidx.core.graphics.drawable.a.b(drawable) : this.f1501c.f1516b.getRootAlpha();
    }

    public int getChangingConfigurations() {
        Drawable drawable = this.f1499a;
        return drawable != null ? drawable.getChangingConfigurations() : super.getChangingConfigurations() | this.f1501c.getChangingConfigurations();
    }

    @Override // b.l.a.a.i
    public /* bridge */ /* synthetic */ ColorFilter getColorFilter() {
        return super.getColorFilter();
    }

    public Drawable.ConstantState getConstantState() {
        Drawable drawable = this.f1499a;
        if (drawable != null && Build.VERSION.SDK_INT >= 24) {
            return new h(drawable.getConstantState());
        }
        this.f1501c.f1515a = getChangingConfigurations();
        return this.f1501c;
    }

    @Override // b.l.a.a.i
    public /* bridge */ /* synthetic */ Drawable getCurrent() {
        return super.getCurrent();
    }

    public int getIntrinsicHeight() {
        Drawable drawable = this.f1499a;
        return drawable != null ? drawable.getIntrinsicHeight() : (int) this.f1501c.f1516b.k;
    }

    public int getIntrinsicWidth() {
        Drawable drawable = this.f1499a;
        return drawable != null ? drawable.getIntrinsicWidth() : (int) this.f1501c.f1516b.j;
    }

    @Override // b.l.a.a.i
    public /* bridge */ /* synthetic */ int getMinimumHeight() {
        return super.getMinimumHeight();
    }

    @Override // b.l.a.a.i
    public /* bridge */ /* synthetic */ int getMinimumWidth() {
        return super.getMinimumWidth();
    }

    public int getOpacity() {
        Drawable drawable = this.f1499a;
        if (drawable != null) {
            return drawable.getOpacity();
        }
        return -3;
    }

    @Override // b.l.a.a.i
    public /* bridge */ /* synthetic */ boolean getPadding(Rect rect) {
        return super.getPadding(rect);
    }

    @Override // b.l.a.a.i
    public /* bridge */ /* synthetic */ int[] getState() {
        return super.getState();
    }

    @Override // b.l.a.a.i
    public /* bridge */ /* synthetic */ Region getTransparentRegion() {
        return super.getTransparentRegion();
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet) {
        Drawable drawable = this.f1499a;
        if (drawable != null) {
            drawable.inflate(resources, xmlPullParser, attributeSet);
        } else {
            inflate(resources, xmlPullParser, attributeSet, null);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
        Drawable drawable = this.f1499a;
        if (drawable != null) {
            androidx.core.graphics.drawable.a.a(drawable, resources, xmlPullParser, attributeSet, theme);
            return;
        }
        g gVar = this.f1501c;
        gVar.f1516b = new f();
        TypedArray a2 = i.a(resources, theme, attributeSet, a.f1482a);
        a(a2, xmlPullParser);
        a2.recycle();
        gVar.f1515a = getChangingConfigurations();
        gVar.k = true;
        a(resources, xmlPullParser, attributeSet, theme);
        this.f1502d = a(this.f1502d, gVar.f1517c, gVar.f1518d);
    }

    public void invalidateSelf() {
        Drawable drawable = this.f1499a;
        if (drawable != null) {
            drawable.invalidateSelf();
        } else {
            super.invalidateSelf();
        }
    }

    public boolean isAutoMirrored() {
        Drawable drawable = this.f1499a;
        return drawable != null ? androidx.core.graphics.drawable.a.e(drawable) : this.f1501c.e;
    }

    public boolean isStateful() {
        g gVar;
        ColorStateList colorStateList;
        Drawable drawable = this.f1499a;
        return drawable != null ? drawable.isStateful() : super.isStateful() || ((gVar = this.f1501c) != null && (gVar.c() || ((colorStateList = this.f1501c.f1517c) != null && colorStateList.isStateful())));
    }

    @Override // b.l.a.a.i
    public /* bridge */ /* synthetic */ void jumpToCurrentState() {
        super.jumpToCurrentState();
    }

    public Drawable mutate() {
        Drawable drawable = this.f1499a;
        if (drawable != null) {
            drawable.mutate();
            return this;
        }
        if (!this.f && super.mutate() == this) {
            this.f1501c = new g(this.f1501c);
            this.f = true;
        }
        return this;
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        Drawable drawable = this.f1499a;
        if (drawable != null) {
            drawable.setBounds(rect);
        }
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(int[] iArr) {
        PorterDuff.Mode mode;
        Drawable drawable = this.f1499a;
        if (drawable != null) {
            return drawable.setState(iArr);
        }
        boolean z = false;
        g gVar = this.f1501c;
        ColorStateList colorStateList = gVar.f1517c;
        if (!(colorStateList == null || (mode = gVar.f1518d) == null)) {
            this.f1502d = a(this.f1502d, colorStateList, mode);
            invalidateSelf();
            z = true;
        }
        if (!gVar.c() || !gVar.a(iArr)) {
            return z;
        }
        invalidateSelf();
        return true;
    }

    public void scheduleSelf(Runnable runnable, long j2) {
        Drawable drawable = this.f1499a;
        if (drawable != null) {
            drawable.scheduleSelf(runnable, j2);
        } else {
            super.scheduleSelf(runnable, j2);
        }
    }

    public void setAlpha(int i2) {
        Drawable drawable = this.f1499a;
        if (drawable != null) {
            drawable.setAlpha(i2);
        } else if (this.f1501c.f1516b.getRootAlpha() != i2) {
            this.f1501c.f1516b.setRootAlpha(i2);
            invalidateSelf();
        }
    }

    public void setAutoMirrored(boolean z) {
        Drawable drawable = this.f1499a;
        if (drawable != null) {
            androidx.core.graphics.drawable.a.a(drawable, z);
        } else {
            this.f1501c.e = z;
        }
    }

    @Override // b.l.a.a.i
    public /* bridge */ /* synthetic */ void setChangingConfigurations(int i2) {
        super.setChangingConfigurations(i2);
    }

    @Override // b.l.a.a.i
    public /* bridge */ /* synthetic */ void setColorFilter(int i2, PorterDuff.Mode mode) {
        super.setColorFilter(i2, mode);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        Drawable drawable = this.f1499a;
        if (drawable != null) {
            drawable.setColorFilter(colorFilter);
            return;
        }
        this.e = colorFilter;
        invalidateSelf();
    }

    @Override // b.l.a.a.i
    public /* bridge */ /* synthetic */ void setFilterBitmap(boolean z) {
        super.setFilterBitmap(z);
    }

    @Override // b.l.a.a.i
    public /* bridge */ /* synthetic */ void setHotspot(float f2, float f3) {
        super.setHotspot(f2, f3);
    }

    @Override // b.l.a.a.i
    public /* bridge */ /* synthetic */ void setHotspotBounds(int i2, int i3, int i4, int i5) {
        super.setHotspotBounds(i2, i3, i4, i5);
    }

    @Override // b.l.a.a.i
    public /* bridge */ /* synthetic */ boolean setState(int[] iArr) {
        return super.setState(iArr);
    }

    @Override // androidx.core.graphics.drawable.b
    public void setTint(int i2) {
        Drawable drawable = this.f1499a;
        if (drawable != null) {
            androidx.core.graphics.drawable.a.b(drawable, i2);
        } else {
            setTintList(ColorStateList.valueOf(i2));
        }
    }

    @Override // androidx.core.graphics.drawable.b
    public void setTintList(ColorStateList colorStateList) {
        Drawable drawable = this.f1499a;
        if (drawable != null) {
            androidx.core.graphics.drawable.a.a(drawable, colorStateList);
            return;
        }
        g gVar = this.f1501c;
        if (gVar.f1517c != colorStateList) {
            gVar.f1517c = colorStateList;
            this.f1502d = a(this.f1502d, colorStateList, gVar.f1518d);
            invalidateSelf();
        }
    }

    @Override // androidx.core.graphics.drawable.b
    public void setTintMode(PorterDuff.Mode mode) {
        Drawable drawable = this.f1499a;
        if (drawable != null) {
            androidx.core.graphics.drawable.a.a(drawable, mode);
            return;
        }
        g gVar = this.f1501c;
        if (gVar.f1518d != mode) {
            gVar.f1518d = mode;
            this.f1502d = a(this.f1502d, gVar.f1517c, mode);
            invalidateSelf();
        }
    }

    public boolean setVisible(boolean z, boolean z2) {
        Drawable drawable = this.f1499a;
        return drawable != null ? drawable.setVisible(z, z2) : super.setVisible(z, z2);
    }

    public void unscheduleSelf(Runnable runnable) {
        Drawable drawable = this.f1499a;
        if (drawable != null) {
            drawable.unscheduleSelf(runnable);
        } else {
            super.unscheduleSelf(runnable);
        }
    }
}
