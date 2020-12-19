package android.support.v4.media;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.BadParcelableException;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.v4.media.f;
import android.support.v4.media.g;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.b;
import android.support.v4.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class MediaBrowserCompat {

    /* renamed from: a  reason: collision with root package name */
    static final boolean f0a = Log.isLoggable("MediaBrowserCompat", 3);

    /* renamed from: b  reason: collision with root package name */
    private final e f1b;

    private static class CustomActionResultReceiver extends ResultReceiver {

        /* renamed from: d  reason: collision with root package name */
        private final String f2d;
        private final Bundle e;
        private final c f;

        /* access modifiers changed from: protected */
        @Override // android.support.v4.os.ResultReceiver
        public void a(int i, Bundle bundle) {
            if (this.f != null) {
                MediaSessionCompat.a(bundle);
                if (i == -1) {
                    this.f.a(this.f2d, this.e, bundle);
                } else if (i == 0) {
                    this.f.c(this.f2d, this.e, bundle);
                } else if (i != 1) {
                    Log.w("MediaBrowserCompat", "Unknown result code: " + i + " (extras=" + this.e + ", resultData=" + bundle + ")");
                } else {
                    this.f.b(this.f2d, this.e, bundle);
                }
            }
        }
    }

    private static class ItemReceiver extends ResultReceiver {

        /* renamed from: d  reason: collision with root package name */
        private final String f3d;
        private final d e;

        /* access modifiers changed from: protected */
        @Override // android.support.v4.os.ResultReceiver
        public void a(int i, Bundle bundle) {
            MediaSessionCompat.a(bundle);
            if (i != 0 || bundle == null || !bundle.containsKey("media_item")) {
                this.e.a(this.f3d);
                return;
            }
            Parcelable parcelable = bundle.getParcelable("media_item");
            if (parcelable == null || (parcelable instanceof MediaItem)) {
                this.e.a((MediaItem) parcelable);
            } else {
                this.e.a(this.f3d);
            }
        }
    }

    public static class MediaItem implements Parcelable {
        public static final Parcelable.Creator<MediaItem> CREATOR = new e();

        /* renamed from: a  reason: collision with root package name */
        private final int f4a;

        /* renamed from: b  reason: collision with root package name */
        private final MediaDescriptionCompat f5b;

        MediaItem(Parcel parcel) {
            this.f4a = parcel.readInt();
            this.f5b = MediaDescriptionCompat.CREATOR.createFromParcel(parcel);
        }

        public MediaItem(MediaDescriptionCompat mediaDescriptionCompat, int i) {
            if (mediaDescriptionCompat == null) {
                throw new IllegalArgumentException("description cannot be null");
            } else if (!TextUtils.isEmpty(mediaDescriptionCompat.b())) {
                this.f4a = i;
                this.f5b = mediaDescriptionCompat;
            } else {
                throw new IllegalArgumentException("description must have a non-empty media id");
            }
        }

        public static MediaItem a(Object obj) {
            if (obj == null || Build.VERSION.SDK_INT < 21) {
                return null;
            }
            return new MediaItem(MediaDescriptionCompat.a(f.c.a(obj)), f.c.b(obj));
        }

        public static List<MediaItem> a(List<?> list) {
            if (list == null || Build.VERSION.SDK_INT < 21) {
                return null;
            }
            ArrayList arrayList = new ArrayList(list.size());
            Iterator<?> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(a(it.next()));
            }
            return arrayList;
        }

        public int describeContents() {
            return 0;
        }

        public String toString() {
            return "MediaItem{" + "mFlags=" + this.f4a + ", mDescription=" + this.f5b + '}';
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.f4a);
            this.f5b.writeToParcel(parcel, i);
        }
    }

    private static class SearchResultReceiver extends ResultReceiver {

        /* renamed from: d  reason: collision with root package name */
        private final String f6d;
        private final Bundle e;
        private final k f;

        /* access modifiers changed from: protected */
        @Override // android.support.v4.os.ResultReceiver
        public void a(int i, Bundle bundle) {
            MediaSessionCompat.a(bundle);
            if (i != 0 || bundle == null || !bundle.containsKey("search_results")) {
                this.f.a(this.f6d, this.e);
                return;
            }
            Parcelable[] parcelableArray = bundle.getParcelableArray("search_results");
            ArrayList arrayList = null;
            if (parcelableArray != null) {
                arrayList = new ArrayList();
                for (Parcelable parcelable : parcelableArray) {
                    arrayList.add((MediaItem) parcelable);
                }
            }
            this.f.a(this.f6d, this.e, arrayList);
        }
    }

    /* access modifiers changed from: private */
    public static class a extends Handler {

        /* renamed from: a  reason: collision with root package name */
        private final WeakReference<j> f7a;

        /* renamed from: b  reason: collision with root package name */
        private WeakReference<Messenger> f8b;

        a(j jVar) {
            this.f7a = new WeakReference<>(jVar);
        }

        /* access modifiers changed from: package-private */
        public void a(Messenger messenger) {
            this.f8b = new WeakReference<>(messenger);
        }

        public void handleMessage(Message message) {
            WeakReference<Messenger> weakReference = this.f8b;
            if (weakReference != null && weakReference.get() != null && this.f7a.get() != null) {
                Bundle data = message.getData();
                MediaSessionCompat.a(data);
                j jVar = this.f7a.get();
                Messenger messenger = this.f8b.get();
                try {
                    int i = message.what;
                    if (i == 1) {
                        Bundle bundle = data.getBundle("data_root_hints");
                        MediaSessionCompat.a(bundle);
                        jVar.a(messenger, data.getString("data_media_item_id"), (MediaSessionCompat.Token) data.getParcelable("data_media_session_token"), bundle);
                    } else if (i == 2) {
                        jVar.a(messenger);
                    } else if (i != 3) {
                        Log.w("MediaBrowserCompat", "Unhandled message: " + message + "\n  Client version: " + 1 + "\n  Service version: " + message.arg1);
                    } else {
                        Bundle bundle2 = data.getBundle("data_options");
                        MediaSessionCompat.a(bundle2);
                        Bundle bundle3 = data.getBundle("data_notify_children_changed_options");
                        MediaSessionCompat.a(bundle3);
                        jVar.a(messenger, data.getString("data_media_item_id"), data.getParcelableArrayList("data_media_item_list"), bundle2, bundle3);
                    }
                } catch (BadParcelableException unused) {
                    Log.e("MediaBrowserCompat", "Could not unparcel the data.");
                    if (message.what == 1) {
                        jVar.a(messenger);
                    }
                }
            }
        }
    }

    public static class b {

        /* renamed from: a  reason: collision with root package name */
        final Object f9a;

        /* renamed from: b  reason: collision with root package name */
        a f10b;

        /* access modifiers changed from: package-private */
        public interface a {
            void a();

            void b();

            void onConnected();
        }

        /* renamed from: android.support.v4.media.MediaBrowserCompat$b$b  reason: collision with other inner class name */
        private class C0001b implements f.a {
            C0001b() {
            }

            @Override // android.support.v4.media.f.a
            public void a() {
                a aVar = b.this.f10b;
                if (aVar != null) {
                    aVar.a();
                }
                b.this.c();
            }

            @Override // android.support.v4.media.f.a
            public void b() {
                a aVar = b.this.f10b;
                if (aVar != null) {
                    aVar.b();
                }
                b.this.b();
            }

            @Override // android.support.v4.media.f.a
            public void onConnected() {
                a aVar = b.this.f10b;
                if (aVar != null) {
                    aVar.onConnected();
                }
                b.this.a();
            }
        }

        public b() {
            this.f9a = Build.VERSION.SDK_INT >= 21 ? f.a((f.a) new C0001b()) : null;
        }

        public void a() {
            throw null;
        }

        /* access modifiers changed from: package-private */
        public void a(a aVar) {
            this.f10b = aVar;
        }

        public void b() {
            throw null;
        }

        public void c() {
            throw null;
        }
    }

    public static abstract class c {
        public abstract void a(String str, Bundle bundle, Bundle bundle2);

        public abstract void b(String str, Bundle bundle, Bundle bundle2);

        public abstract void c(String str, Bundle bundle, Bundle bundle2);
    }

    public static abstract class d {
        public abstract void a(MediaItem mediaItem);

        public abstract void a(String str);
    }

    interface e {
        MediaSessionCompat.Token c();

        void connect();

        void d();
    }

    static class f implements e, j, b.a {

        /* renamed from: a  reason: collision with root package name */
        final Context f12a;

        /* renamed from: b  reason: collision with root package name */
        protected final Object f13b;

        /* renamed from: c  reason: collision with root package name */
        protected final Bundle f14c;

        /* renamed from: d  reason: collision with root package name */
        protected final a f15d = new a(this);
        private final b.c.b<String, m> e = new b.c.b<>();
        protected int f;
        protected l g;
        protected Messenger h;
        private MediaSessionCompat.Token i;
        private Bundle j;

        f(Context context, ComponentName componentName, b bVar, Bundle bundle) {
            this.f12a = context;
            this.f14c = bundle != null ? new Bundle(bundle) : new Bundle();
            this.f14c.putInt("extra_client_version", 1);
            bVar.a(this);
            this.f13b = f.a(context, componentName, bVar.f9a, this.f14c);
        }

        @Override // android.support.v4.media.MediaBrowserCompat.b.a
        public void a() {
            this.g = null;
            this.h = null;
            this.i = null;
            this.f15d.a(null);
        }

        @Override // android.support.v4.media.MediaBrowserCompat.j
        public void a(Messenger messenger) {
        }

        @Override // android.support.v4.media.MediaBrowserCompat.j
        public void a(Messenger messenger, String str, MediaSessionCompat.Token token, Bundle bundle) {
        }

        @Override // android.support.v4.media.MediaBrowserCompat.j
        public void a(Messenger messenger, String str, List list, Bundle bundle, Bundle bundle2) {
            if (this.h == messenger) {
                m mVar = this.e.get(str);
                if (mVar != null) {
                    n a2 = mVar.a(bundle);
                    if (a2 != null) {
                        if (bundle == null) {
                            if (list == null) {
                                a2.a(str);
                                return;
                            } else {
                                this.j = bundle2;
                                a2.a(str, list);
                            }
                        } else if (list == null) {
                            a2.a(str, bundle);
                            return;
                        } else {
                            this.j = bundle2;
                            a2.a(str, list, bundle);
                        }
                        this.j = null;
                    }
                } else if (MediaBrowserCompat.f0a) {
                    Log.d("MediaBrowserCompat", "onLoadChildren for id that isn't subscribed id=" + str);
                }
            }
        }

        @Override // android.support.v4.media.MediaBrowserCompat.b.a
        public void b() {
        }

        @Override // android.support.v4.media.MediaBrowserCompat.e
        public MediaSessionCompat.Token c() {
            if (this.i == null) {
                this.i = MediaSessionCompat.Token.a(f.d(this.f13b));
            }
            return this.i;
        }

        @Override // android.support.v4.media.MediaBrowserCompat.e
        public void connect() {
            f.a(this.f13b);
        }

        @Override // android.support.v4.media.MediaBrowserCompat.e
        public void d() {
            Messenger messenger;
            l lVar = this.g;
            if (!(lVar == null || (messenger = this.h) == null)) {
                try {
                    lVar.b(messenger);
                } catch (RemoteException unused) {
                    Log.i("MediaBrowserCompat", "Remote error unregistering client messenger.");
                }
            }
            f.b(this.f13b);
        }

        @Override // android.support.v4.media.MediaBrowserCompat.b.a
        public void onConnected() {
            Bundle c2 = f.c(this.f13b);
            if (c2 != null) {
                this.f = c2.getInt("extra_service_version", 0);
                IBinder a2 = androidx.core.app.c.a(c2, "extra_messenger");
                if (a2 != null) {
                    this.g = new l(a2, this.f14c);
                    this.h = new Messenger(this.f15d);
                    this.f15d.a(this.h);
                    try {
                        this.g.b(this.f12a, this.h);
                    } catch (RemoteException unused) {
                        Log.i("MediaBrowserCompat", "Remote error registering client messenger.");
                    }
                }
                android.support.v4.media.session.b a3 = b.a.a(androidx.core.app.c.a(c2, "extra_session_binder"));
                if (a3 != null) {
                    this.i = MediaSessionCompat.Token.a(f.d(this.f13b), a3);
                }
            }
        }
    }

    static class g extends f {
        g(Context context, ComponentName componentName, b bVar, Bundle bundle) {
            super(context, componentName, bVar, bundle);
        }
    }

    static class h extends g {
        h(Context context, ComponentName componentName, b bVar, Bundle bundle) {
            super(context, componentName, bVar, bundle);
        }
    }

    /* access modifiers changed from: package-private */
    public static class i implements e, j {

        /* renamed from: a  reason: collision with root package name */
        final Context f16a;

        /* renamed from: b  reason: collision with root package name */
        final ComponentName f17b;

        /* renamed from: c  reason: collision with root package name */
        final b f18c;

        /* renamed from: d  reason: collision with root package name */
        final Bundle f19d;
        final a e = new a(this);
        private final b.c.b<String, m> f = new b.c.b<>();
        int g = 1;
        a h;
        l i;
        Messenger j;
        private String k;
        private MediaSessionCompat.Token l;
        private Bundle m;
        private Bundle n;

        /* access modifiers changed from: private */
        public class a implements ServiceConnection {
            a() {
            }

            private void a(Runnable runnable) {
                if (Thread.currentThread() == i.this.e.getLooper().getThread()) {
                    runnable.run();
                } else {
                    i.this.e.post(runnable);
                }
            }

            /* access modifiers changed from: package-private */
            public boolean a(String str) {
                int i;
                i iVar = i.this;
                if (iVar.h == this && (i = iVar.g) != 0 && i != 1) {
                    return true;
                }
                int i2 = i.this.g;
                if (i2 == 0 || i2 == 1) {
                    return false;
                }
                Log.i("MediaBrowserCompat", str + " for " + i.this.f17b + " with mServiceConnection=" + i.this.h + " this=" + this);
                return false;
            }

            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                a(new c(this, componentName, iBinder));
            }

            public void onServiceDisconnected(ComponentName componentName) {
                a(new d(this, componentName));
            }
        }

        public i(Context context, ComponentName componentName, b bVar, Bundle bundle) {
            if (context == null) {
                throw new IllegalArgumentException("context must not be null");
            } else if (componentName == null) {
                throw new IllegalArgumentException("service component must not be null");
            } else if (bVar != null) {
                this.f16a = context;
                this.f17b = componentName;
                this.f18c = bVar;
                this.f19d = bundle == null ? null : new Bundle(bundle);
            } else {
                throw new IllegalArgumentException("connection callback must not be null");
            }
        }

        private static String a(int i2) {
            if (i2 == 0) {
                return "CONNECT_STATE_DISCONNECTING";
            }
            if (i2 == 1) {
                return "CONNECT_STATE_DISCONNECTED";
            }
            if (i2 == 2) {
                return "CONNECT_STATE_CONNECTING";
            }
            if (i2 == 3) {
                return "CONNECT_STATE_CONNECTED";
            }
            if (i2 == 4) {
                return "CONNECT_STATE_SUSPENDED";
            }
            return "UNKNOWN/" + i2;
        }

        private boolean a(Messenger messenger, String str) {
            int i2;
            if (this.j == messenger && (i2 = this.g) != 0 && i2 != 1) {
                return true;
            }
            int i3 = this.g;
            if (i3 == 0 || i3 == 1) {
                return false;
            }
            Log.i("MediaBrowserCompat", str + " for " + this.f17b + " with mCallbacksMessenger=" + this.j + " this=" + this);
            return false;
        }

        /* access modifiers changed from: package-private */
        public void a() {
            Log.d("MediaBrowserCompat", "MediaBrowserCompat...");
            Log.d("MediaBrowserCompat", "  mServiceComponent=" + this.f17b);
            Log.d("MediaBrowserCompat", "  mCallback=" + this.f18c);
            Log.d("MediaBrowserCompat", "  mRootHints=" + this.f19d);
            Log.d("MediaBrowserCompat", "  mState=" + a(this.g));
            Log.d("MediaBrowserCompat", "  mServiceConnection=" + this.h);
            Log.d("MediaBrowserCompat", "  mServiceBinderWrapper=" + this.i);
            Log.d("MediaBrowserCompat", "  mCallbacksMessenger=" + this.j);
            Log.d("MediaBrowserCompat", "  mRootId=" + this.k);
            Log.d("MediaBrowserCompat", "  mMediaSessionToken=" + this.l);
        }

        @Override // android.support.v4.media.MediaBrowserCompat.j
        public void a(Messenger messenger) {
            Log.e("MediaBrowserCompat", "onConnectFailed for " + this.f17b);
            if (a(messenger, "onConnectFailed")) {
                if (this.g != 2) {
                    Log.w("MediaBrowserCompat", "onConnect from service while mState=" + a(this.g) + "... ignoring");
                    return;
                }
                b();
                this.f18c.b();
            }
        }

        @Override // android.support.v4.media.MediaBrowserCompat.j
        public void a(Messenger messenger, String str, MediaSessionCompat.Token token, Bundle bundle) {
            if (a(messenger, "onConnect")) {
                if (this.g != 2) {
                    Log.w("MediaBrowserCompat", "onConnect from service while mState=" + a(this.g) + "... ignoring");
                    return;
                }
                this.k = str;
                this.l = token;
                this.m = bundle;
                this.g = 3;
                if (MediaBrowserCompat.f0a) {
                    Log.d("MediaBrowserCompat", "ServiceCallbacks.onConnect...");
                    a();
                }
                this.f18c.a();
                try {
                    for (Map.Entry<String, m> entry : this.f.entrySet()) {
                        String key = entry.getKey();
                        m value = entry.getValue();
                        List<n> a2 = value.a();
                        List<Bundle> b2 = value.b();
                        for (int i2 = 0; i2 < a2.size(); i2++) {
                            this.i.a(key, a2.get(i2).f26b, b2.get(i2), this.j);
                        }
                    }
                } catch (RemoteException unused) {
                    Log.d("MediaBrowserCompat", "addSubscription failed with RemoteException.");
                }
            }
        }

        @Override // android.support.v4.media.MediaBrowserCompat.j
        public void a(Messenger messenger, String str, List list, Bundle bundle, Bundle bundle2) {
            if (a(messenger, "onLoadChildren")) {
                if (MediaBrowserCompat.f0a) {
                    Log.d("MediaBrowserCompat", "onLoadChildren for " + this.f17b + " id=" + str);
                }
                m mVar = this.f.get(str);
                if (mVar != null) {
                    n a2 = mVar.a(bundle);
                    if (a2 != null) {
                        if (bundle == null) {
                            if (list == null) {
                                a2.a(str);
                                return;
                            } else {
                                this.n = bundle2;
                                a2.a(str, list);
                            }
                        } else if (list == null) {
                            a2.a(str, bundle);
                            return;
                        } else {
                            this.n = bundle2;
                            a2.a(str, list, bundle);
                        }
                        this.n = null;
                    }
                } else if (MediaBrowserCompat.f0a) {
                    Log.d("MediaBrowserCompat", "onLoadChildren for id that isn't subscribed id=" + str);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void b() {
            a aVar = this.h;
            if (aVar != null) {
                this.f16a.unbindService(aVar);
            }
            this.g = 1;
            this.h = null;
            this.i = null;
            this.j = null;
            this.e.a(null);
            this.k = null;
            this.l = null;
        }

        @Override // android.support.v4.media.MediaBrowserCompat.e
        public MediaSessionCompat.Token c() {
            if (e()) {
                return this.l;
            }
            throw new IllegalStateException("getSessionToken() called while not connected(state=" + this.g + ")");
        }

        @Override // android.support.v4.media.MediaBrowserCompat.e
        public void connect() {
            int i2 = this.g;
            if (i2 == 0 || i2 == 1) {
                this.g = 2;
                this.e.post(new a(this));
                return;
            }
            throw new IllegalStateException("connect() called while neigther disconnecting nor disconnected (state=" + a(this.g) + ")");
        }

        @Override // android.support.v4.media.MediaBrowserCompat.e
        public void d() {
            this.g = 0;
            this.e.post(new b(this));
        }

        public boolean e() {
            return this.g == 3;
        }
    }

    interface j {
        void a(Messenger messenger);

        void a(Messenger messenger, String str, MediaSessionCompat.Token token, Bundle bundle);

        void a(Messenger messenger, String str, List list, Bundle bundle, Bundle bundle2);
    }

    public static abstract class k {
        public abstract void a(String str, Bundle bundle);

        public abstract void a(String str, Bundle bundle, List<MediaItem> list);
    }

    /* access modifiers changed from: private */
    public static class l {

        /* renamed from: a  reason: collision with root package name */
        private Messenger f21a;

        /* renamed from: b  reason: collision with root package name */
        private Bundle f22b;

        public l(IBinder iBinder, Bundle bundle) {
            this.f21a = new Messenger(iBinder);
            this.f22b = bundle;
        }

        private void a(int i, Bundle bundle, Messenger messenger) {
            Message obtain = Message.obtain();
            obtain.what = i;
            obtain.arg1 = 1;
            obtain.setData(bundle);
            obtain.replyTo = messenger;
            this.f21a.send(obtain);
        }

        /* access modifiers changed from: package-private */
        public void a(Context context, Messenger messenger) {
            Bundle bundle = new Bundle();
            bundle.putString("data_package_name", context.getPackageName());
            bundle.putBundle("data_root_hints", this.f22b);
            a(1, bundle, messenger);
        }

        /* access modifiers changed from: package-private */
        public void a(Messenger messenger) {
            a(2, null, messenger);
        }

        /* access modifiers changed from: package-private */
        public void a(String str, IBinder iBinder, Bundle bundle, Messenger messenger) {
            Bundle bundle2 = new Bundle();
            bundle2.putString("data_media_item_id", str);
            androidx.core.app.c.a(bundle2, "data_callback_token", iBinder);
            bundle2.putBundle("data_options", bundle);
            a(3, bundle2, messenger);
        }

        /* access modifiers changed from: package-private */
        public void b(Context context, Messenger messenger) {
            Bundle bundle = new Bundle();
            bundle.putString("data_package_name", context.getPackageName());
            bundle.putBundle("data_root_hints", this.f22b);
            a(6, bundle, messenger);
        }

        /* access modifiers changed from: package-private */
        public void b(Messenger messenger) {
            a(7, null, messenger);
        }
    }

    private static class m {

        /* renamed from: a  reason: collision with root package name */
        private final List<n> f23a = new ArrayList();

        /* renamed from: b  reason: collision with root package name */
        private final List<Bundle> f24b = new ArrayList();

        public n a(Bundle bundle) {
            for (int i = 0; i < this.f24b.size(); i++) {
                if (androidx.media.d.a(this.f24b.get(i), bundle)) {
                    return this.f23a.get(i);
                }
            }
            return null;
        }

        public List<n> a() {
            return this.f23a;
        }

        public List<Bundle> b() {
            return this.f24b;
        }
    }

    public static abstract class n {

        /* renamed from: a  reason: collision with root package name */
        final Object f25a;

        /* renamed from: b  reason: collision with root package name */
        final IBinder f26b = new Binder();

        /* renamed from: c  reason: collision with root package name */
        WeakReference<m> f27c;

        private class a implements f.d {
            a() {
            }

            /* access modifiers changed from: package-private */
            public List<MediaItem> a(List<MediaItem> list, Bundle bundle) {
                if (list == null) {
                    return null;
                }
                int i = bundle.getInt("android.media.browse.extra.PAGE", -1);
                int i2 = bundle.getInt("android.media.browse.extra.PAGE_SIZE", -1);
                if (i == -1 && i2 == -1) {
                    return list;
                }
                int i3 = i2 * i;
                int i4 = i3 + i2;
                if (i < 0 || i2 < 1 || i3 >= list.size()) {
                    return Collections.emptyList();
                }
                if (i4 > list.size()) {
                    i4 = list.size();
                }
                return list.subList(i3, i4);
            }

            @Override // android.support.v4.media.f.d
            public void a(String str) {
                n.this.a(str);
            }

            @Override // android.support.v4.media.f.d
            public void a(String str, List<?> list) {
                WeakReference<m> weakReference = n.this.f27c;
                m mVar = weakReference == null ? null : weakReference.get();
                if (mVar == null) {
                    n.this.a(str, MediaItem.a(list));
                    return;
                }
                List<MediaItem> a2 = MediaItem.a(list);
                List<n> a3 = mVar.a();
                List<Bundle> b2 = mVar.b();
                for (int i = 0; i < a3.size(); i++) {
                    Bundle bundle = b2.get(i);
                    if (bundle == null) {
                        n.this.a(str, a2);
                    } else {
                        n.this.a(str, a(a2, bundle), bundle);
                    }
                }
            }
        }

        private class b extends a implements g.a {
            b() {
                super();
            }

            @Override // android.support.v4.media.g.a
            public void a(String str, Bundle bundle) {
                n.this.a(str, bundle);
            }

            @Override // android.support.v4.media.g.a
            public void a(String str, List<?> list, Bundle bundle) {
                n.this.a(str, MediaItem.a(list), bundle);
            }
        }

        public n() {
            int i = Build.VERSION.SDK_INT;
            this.f25a = i >= 26 ? g.a(new b()) : i >= 21 ? f.a((f.d) new a()) : null;
        }

        public void a(String str) {
        }

        public void a(String str, Bundle bundle) {
        }

        public void a(String str, List<MediaItem> list) {
        }

        public void a(String str, List<MediaItem> list, Bundle bundle) {
        }
    }

    public MediaBrowserCompat(Context context, ComponentName componentName, b bVar, Bundle bundle) {
        int i2 = Build.VERSION.SDK_INT;
        this.f1b = i2 >= 26 ? new h(context, componentName, bVar, bundle) : i2 >= 23 ? new g(context, componentName, bVar, bundle) : i2 >= 21 ? new f(context, componentName, bVar, bundle) : new i(context, componentName, bVar, bundle);
    }

    public void a() {
        this.f1b.connect();
    }

    public void b() {
        this.f1b.d();
    }

    public MediaSessionCompat.Token c() {
        return this.f1b.c();
    }
}
