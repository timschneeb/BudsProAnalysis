package androidx.core.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.widget.RemoteViews;
import java.util.ArrayList;

public class f {

    public static class a {

        /* renamed from: a  reason: collision with root package name */
        final Bundle f573a;

        /* renamed from: b  reason: collision with root package name */
        private final i[] f574b;

        /* renamed from: c  reason: collision with root package name */
        private final i[] f575c;

        /* renamed from: d  reason: collision with root package name */
        private boolean f576d;
        boolean e;
        private final int f;
        public int g;
        public CharSequence h;
        public PendingIntent i;

        public PendingIntent a() {
            return this.i;
        }

        public boolean b() {
            return this.f576d;
        }

        public i[] c() {
            return this.f575c;
        }

        public Bundle d() {
            return this.f573a;
        }

        public int e() {
            return this.g;
        }

        public i[] f() {
            return this.f574b;
        }

        public int g() {
            return this.f;
        }

        public boolean h() {
            return this.e;
        }

        public CharSequence i() {
            return this.h;
        }
    }

    public static class b {
        String A;
        Bundle B;
        int C = 0;
        int D = 0;
        Notification E;
        RemoteViews F;
        RemoteViews G;
        RemoteViews H;
        String I;
        int J = 0;
        String K;
        long L;
        int M = 0;
        Notification N = new Notification();
        @Deprecated
        public ArrayList<String> O;

        /* renamed from: a  reason: collision with root package name */
        public Context f577a;

        /* renamed from: b  reason: collision with root package name */
        public ArrayList<a> f578b = new ArrayList<>();

        /* renamed from: c  reason: collision with root package name */
        ArrayList<a> f579c = new ArrayList<>();

        /* renamed from: d  reason: collision with root package name */
        CharSequence f580d;
        CharSequence e;
        PendingIntent f;
        PendingIntent g;
        RemoteViews h;
        Bitmap i;
        CharSequence j;
        int k;
        int l;
        boolean m = true;
        boolean n;
        c o;
        CharSequence p;
        CharSequence[] q;
        int r;
        int s;
        boolean t;
        String u;
        boolean v;
        String w;
        boolean x = false;
        boolean y;
        boolean z;

        public b(Context context, String str) {
            this.f577a = context;
            this.I = str;
            this.N.when = System.currentTimeMillis();
            this.N.audioStreamType = -1;
            this.l = 0;
            this.O = new ArrayList<>();
        }

        protected static CharSequence a(CharSequence charSequence) {
            return (charSequence != null && charSequence.length() > 5120) ? charSequence.subSequence(0, 5120) : charSequence;
        }

        public Notification a() {
            return new g(this).a();
        }

        public Bundle b() {
            if (this.B == null) {
                this.B = new Bundle();
            }
            return this.B;
        }

        public b b(CharSequence charSequence) {
            this.e = a(charSequence);
            return this;
        }

        public b c(CharSequence charSequence) {
            this.f580d = a(charSequence);
            return this;
        }
    }

    public static abstract class c {
        public abstract void a(Bundle bundle);

        public abstract void a(e eVar);

        public abstract RemoteViews b(e eVar);

        public abstract RemoteViews c(e eVar);

        public abstract RemoteViews d(e eVar);
    }

    public static Bundle a(Notification notification) {
        int i = Build.VERSION.SDK_INT;
        if (i >= 19) {
            return notification.extras;
        }
        if (i >= 16) {
            return h.a(notification);
        }
        return null;
    }
}
