package androidx.media;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;
import androidx.media.v;
import androidx.media.w;
import androidx.media.x;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public abstract class MediaBrowserServiceCompat extends Service {

    /* renamed from: a  reason: collision with root package name */
    static final boolean f866a = Log.isLoggable("MBServiceCompat", 3);

    /* renamed from: b  reason: collision with root package name */
    private c f867b;

    /* renamed from: c  reason: collision with root package name */
    final b.c.b<IBinder, b> f868c = new b.c.b<>();

    /* renamed from: d  reason: collision with root package name */
    b f869d;
    final m e = new m();
    MediaSessionCompat.Token f;

    public static final class a {
        public Bundle a() {
            throw null;
        }

        public String b() {
            throw null;
        }
    }

    /* access modifiers changed from: private */
    public class b implements IBinder.DeathRecipient {

        /* renamed from: a  reason: collision with root package name */
        public final String f870a;

        /* renamed from: b  reason: collision with root package name */
        public final int f871b;

        /* renamed from: c  reason: collision with root package name */
        public final int f872c;

        /* renamed from: d  reason: collision with root package name */
        public final y f873d;
        public final Bundle e;
        public final k f;
        public final HashMap<String, List<b.e.f.d<IBinder, Bundle>>> g = new HashMap<>();
        public a h;

        b(String str, int i2, int i3, Bundle bundle, k kVar) {
            this.f870a = str;
            this.f871b = i2;
            this.f872c = i3;
            this.f873d = new y(str, i2, i3);
            this.e = bundle;
            this.f = kVar;
        }

        public void binderDied() {
            MediaBrowserServiceCompat.this.e.post(new i(this));
        }
    }

    interface c {
        IBinder a(Intent intent);

        void onCreate();
    }

    /* access modifiers changed from: package-private */
    public class d implements c, v.d {

        /* renamed from: a  reason: collision with root package name */
        final List<Bundle> f874a = new ArrayList();

        /* renamed from: b  reason: collision with root package name */
        Object f875b;

        /* renamed from: c  reason: collision with root package name */
        Messenger f876c;

        d() {
        }

        @Override // androidx.media.MediaBrowserServiceCompat.c
        public IBinder a(Intent intent) {
            return v.a(this.f875b, intent);
        }

        @Override // androidx.media.v.d
        public v.a a(String str, int i, Bundle bundle) {
            Bundle bundle2;
            if (bundle == null || bundle.getInt("extra_client_version", 0) == 0) {
                bundle2 = null;
            } else {
                bundle.remove("extra_client_version");
                this.f876c = new Messenger(MediaBrowserServiceCompat.this.e);
                bundle2 = new Bundle();
                bundle2.putInt("extra_service_version", 2);
                androidx.core.app.c.a(bundle2, "extra_messenger", this.f876c.getBinder());
                MediaSessionCompat.Token token = MediaBrowserServiceCompat.this.f;
                if (token != null) {
                    android.support.v4.media.session.b a2 = token.a();
                    androidx.core.app.c.a(bundle2, "extra_session_binder", a2 == null ? null : a2.asBinder());
                } else {
                    this.f874a.add(bundle2);
                }
            }
            MediaBrowserServiceCompat mediaBrowserServiceCompat = MediaBrowserServiceCompat.this;
            mediaBrowserServiceCompat.f869d = new b(str, -1, i, bundle, null);
            a a3 = MediaBrowserServiceCompat.this.a(str, i, bundle);
            MediaBrowserServiceCompat.this.f869d = null;
            if (a3 == null) {
                return null;
            }
            if (bundle2 == null) {
                a3.a();
                throw null;
            }
            a3.a();
            throw null;
        }

        @Override // androidx.media.v.d
        public void b(String str, v.c<List<Parcel>> cVar) {
            MediaBrowserServiceCompat.this.a(str, new j(this, str, cVar));
        }

        @Override // androidx.media.MediaBrowserServiceCompat.c
        public void onCreate() {
            this.f875b = v.a(MediaBrowserServiceCompat.this, this);
            v.a(this.f875b);
        }
    }

    /* access modifiers changed from: package-private */
    public class e extends d implements w.b {
        e() {
            super();
        }

        @Override // androidx.media.w.b
        public void a(String str, v.c<Parcel> cVar) {
            MediaBrowserServiceCompat.this.b(str, new k(this, str, cVar));
        }

        @Override // androidx.media.MediaBrowserServiceCompat.c, androidx.media.MediaBrowserServiceCompat.d
        public void onCreate() {
            this.f875b = w.a(MediaBrowserServiceCompat.this, this);
            v.a(this.f875b);
        }
    }

    /* access modifiers changed from: package-private */
    public class f extends e implements x.c {
        f() {
            super();
        }

        @Override // androidx.media.x.c
        public void a(String str, x.b bVar, Bundle bundle) {
            MediaBrowserServiceCompat.this.a(str, new l(this, str, bVar), bundle);
        }

        @Override // androidx.media.MediaBrowserServiceCompat.c, androidx.media.MediaBrowserServiceCompat.e, androidx.media.MediaBrowserServiceCompat.d
        public void onCreate() {
            this.f875b = x.a(MediaBrowserServiceCompat.this, this);
            v.a(this.f875b);
        }
    }

    class g extends f {
        g() {
            super();
        }
    }

    class h implements c {

        /* renamed from: a  reason: collision with root package name */
        private Messenger f878a;

        h() {
        }

        @Override // androidx.media.MediaBrowserServiceCompat.c
        public IBinder a(Intent intent) {
            if ("android.media.browse.MediaBrowserService".equals(intent.getAction())) {
                return this.f878a.getBinder();
            }
            return null;
        }

        @Override // androidx.media.MediaBrowserServiceCompat.c
        public void onCreate() {
            this.f878a = new Messenger(MediaBrowserServiceCompat.this.e);
        }
    }

    public static class i<T> {

        /* renamed from: a  reason: collision with root package name */
        private final Object f880a;

        /* renamed from: b  reason: collision with root package name */
        private boolean f881b;

        /* renamed from: c  reason: collision with root package name */
        private boolean f882c;

        /* renamed from: d  reason: collision with root package name */
        private boolean f883d;
        private int e;

        i(Object obj) {
            this.f880a = obj;
        }

        /* access modifiers changed from: package-private */
        public int a() {
            return this.e;
        }

        /* access modifiers changed from: package-private */
        public void a(int i) {
            this.e = i;
        }

        /* access modifiers changed from: package-private */
        public void a(Bundle bundle) {
            throw new UnsupportedOperationException("It is not supported to send an error for " + this.f880a);
        }

        /* access modifiers changed from: package-private */
        public void a(T t) {
            throw null;
        }

        public void b(Bundle bundle) {
            if (this.f882c || this.f883d) {
                throw new IllegalStateException("sendError() called when either sendResult() or sendError() had already been called for: " + this.f880a);
            }
            this.f883d = true;
            a(bundle);
        }

        public void b(T t) {
            if (this.f882c || this.f883d) {
                throw new IllegalStateException("sendResult() called when either sendResult() or sendError() had already been called for: " + this.f880a);
            }
            this.f882c = true;
            a((Object) t);
        }

        /* access modifiers changed from: package-private */
        public boolean b() {
            return this.f881b || this.f882c || this.f883d;
        }
    }

    /* access modifiers changed from: private */
    public class j {
        j() {
        }

        public void a(k kVar) {
            MediaBrowserServiceCompat.this.e.a(new n(this, kVar));
        }

        public void a(k kVar, String str, int i, int i2, Bundle bundle) {
            MediaBrowserServiceCompat.this.e.a(new r(this, kVar, str, i, i2, bundle));
        }

        public void a(String str, int i, int i2, Bundle bundle, k kVar) {
            if (MediaBrowserServiceCompat.this.a(str, i2)) {
                MediaBrowserServiceCompat.this.e.a(new m(this, kVar, str, i, i2, bundle));
                return;
            }
            throw new IllegalArgumentException("Package/uid mismatch: uid=" + i2 + " package=" + str);
        }

        public void a(String str, Bundle bundle, ResultReceiver resultReceiver, k kVar) {
            if (!TextUtils.isEmpty(str) && resultReceiver != null) {
                MediaBrowserServiceCompat.this.e.a(new t(this, kVar, str, bundle, resultReceiver));
            }
        }

        public void a(String str, IBinder iBinder, Bundle bundle, k kVar) {
            MediaBrowserServiceCompat.this.e.a(new o(this, kVar, str, iBinder, bundle));
        }

        public void a(String str, IBinder iBinder, k kVar) {
            MediaBrowserServiceCompat.this.e.a(new p(this, kVar, str, iBinder));
        }

        public void a(String str, ResultReceiver resultReceiver, k kVar) {
            if (!TextUtils.isEmpty(str) && resultReceiver != null) {
                MediaBrowserServiceCompat.this.e.a(new q(this, kVar, str, resultReceiver));
            }
        }

        public void b(k kVar) {
            MediaBrowserServiceCompat.this.e.a(new s(this, kVar));
        }

        public void b(String str, Bundle bundle, ResultReceiver resultReceiver, k kVar) {
            if (!TextUtils.isEmpty(str) && resultReceiver != null) {
                MediaBrowserServiceCompat.this.e.a(new u(this, kVar, str, bundle, resultReceiver));
            }
        }
    }

    /* access modifiers changed from: private */
    public interface k {
        void a();

        void a(String str, List<MediaBrowserCompat.MediaItem> list, Bundle bundle, Bundle bundle2);

        IBinder asBinder();
    }

    private static class l implements k {

        /* renamed from: a  reason: collision with root package name */
        final Messenger f885a;

        l(Messenger messenger) {
            this.f885a = messenger;
        }

        private void a(int i, Bundle bundle) {
            Message obtain = Message.obtain();
            obtain.what = i;
            obtain.arg1 = 2;
            obtain.setData(bundle);
            this.f885a.send(obtain);
        }

        @Override // androidx.media.MediaBrowserServiceCompat.k
        public void a() {
            a(2, null);
        }

        @Override // androidx.media.MediaBrowserServiceCompat.k
        public void a(String str, List<MediaBrowserCompat.MediaItem> list, Bundle bundle, Bundle bundle2) {
            Bundle bundle3 = new Bundle();
            bundle3.putString("data_media_item_id", str);
            bundle3.putBundle("data_options", bundle);
            bundle3.putBundle("data_notify_children_changed_options", bundle2);
            if (list != null) {
                bundle3.putParcelableArrayList("data_media_item_list", list instanceof ArrayList ? (ArrayList) list : new ArrayList<>(list));
            }
            a(3, bundle3);
        }

        @Override // androidx.media.MediaBrowserServiceCompat.k
        public IBinder asBinder() {
            return this.f885a.getBinder();
        }
    }

    /* access modifiers changed from: private */
    public final class m extends Handler {

        /* renamed from: a  reason: collision with root package name */
        private final j f886a = new j();

        m() {
        }

        public void a(Runnable runnable) {
            if (Thread.currentThread() == getLooper().getThread()) {
                runnable.run();
            } else {
                post(runnable);
            }
        }

        public void handleMessage(Message message) {
            Bundle data = message.getData();
            switch (message.what) {
                case 1:
                    Bundle bundle = data.getBundle("data_root_hints");
                    MediaSessionCompat.a(bundle);
                    this.f886a.a(data.getString("data_package_name"), data.getInt("data_calling_pid"), data.getInt("data_calling_uid"), bundle, new l(message.replyTo));
                    return;
                case 2:
                    this.f886a.a(new l(message.replyTo));
                    return;
                case 3:
                    Bundle bundle2 = data.getBundle("data_options");
                    MediaSessionCompat.a(bundle2);
                    this.f886a.a(data.getString("data_media_item_id"), androidx.core.app.c.a(data, "data_callback_token"), bundle2, new l(message.replyTo));
                    return;
                case 4:
                    this.f886a.a(data.getString("data_media_item_id"), androidx.core.app.c.a(data, "data_callback_token"), new l(message.replyTo));
                    return;
                case 5:
                    this.f886a.a(data.getString("data_media_item_id"), (ResultReceiver) data.getParcelable("data_result_receiver"), new l(message.replyTo));
                    return;
                case 6:
                    Bundle bundle3 = data.getBundle("data_root_hints");
                    MediaSessionCompat.a(bundle3);
                    this.f886a.a(new l(message.replyTo), data.getString("data_package_name"), data.getInt("data_calling_pid"), data.getInt("data_calling_uid"), bundle3);
                    return;
                case 7:
                    this.f886a.b(new l(message.replyTo));
                    return;
                case 8:
                    Bundle bundle4 = data.getBundle("data_search_extras");
                    MediaSessionCompat.a(bundle4);
                    this.f886a.a(data.getString("data_search_query"), bundle4, (ResultReceiver) data.getParcelable("data_result_receiver"), new l(message.replyTo));
                    return;
                case 9:
                    Bundle bundle5 = data.getBundle("data_custom_action_extras");
                    MediaSessionCompat.a(bundle5);
                    this.f886a.b(data.getString("data_custom_action"), bundle5, (ResultReceiver) data.getParcelable("data_result_receiver"), new l(message.replyTo));
                    return;
                default:
                    Log.w("MBServiceCompat", "Unhandled message: " + message + "\n  Service version: " + 2 + "\n  Client version: " + message.arg1);
                    return;
            }
        }

        public boolean sendMessageAtTime(Message message, long j) {
            Bundle data = message.getData();
            data.setClassLoader(MediaBrowserCompat.class.getClassLoader());
            data.putInt("data_calling_uid", Binder.getCallingUid());
            data.putInt("data_calling_pid", Binder.getCallingPid());
            return super.sendMessageAtTime(message, j);
        }
    }

    public abstract a a(String str, int i2, Bundle bundle);

    /* access modifiers changed from: package-private */
    public List<MediaBrowserCompat.MediaItem> a(List<MediaBrowserCompat.MediaItem> list, Bundle bundle) {
        if (list == null) {
            return null;
        }
        int i2 = bundle.getInt("android.media.browse.extra.PAGE", -1);
        int i3 = bundle.getInt("android.media.browse.extra.PAGE_SIZE", -1);
        if (i2 == -1 && i3 == -1) {
            return list;
        }
        int i4 = i3 * i2;
        int i5 = i4 + i3;
        if (i2 < 0 || i3 < 1 || i4 >= list.size()) {
            return Collections.emptyList();
        }
        if (i5 > list.size()) {
            i5 = list.size();
        }
        return list.subList(i4, i5);
    }

    public void a(String str) {
    }

    public void a(String str, Bundle bundle) {
    }

    /* access modifiers changed from: package-private */
    public void a(String str, Bundle bundle, b bVar, ResultReceiver resultReceiver) {
        h hVar = new h(this, str, resultReceiver);
        this.f869d = bVar;
        a(str, bundle, hVar);
        this.f869d = null;
        if (!hVar.b()) {
            throw new IllegalStateException("onCustomAction must call detach() or sendResult() or sendError() before returning for action=" + str + " extras=" + bundle);
        }
    }

    public void a(String str, Bundle bundle, i<Bundle> iVar) {
        iVar.b((Bundle) null);
    }

    /* access modifiers changed from: package-private */
    public void a(String str, b bVar, Bundle bundle, Bundle bundle2) {
        e eVar = new e(this, str, bVar, str, bundle, bundle2);
        this.f869d = bVar;
        if (bundle == null) {
            a(str, eVar);
        } else {
            a(str, eVar, bundle);
        }
        this.f869d = null;
        if (!eVar.b()) {
            throw new IllegalStateException("onLoadChildren must call detach() or sendResult() before returning for package=" + bVar.f870a + " id=" + str);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(String str, b bVar, IBinder iBinder, Bundle bundle) {
        List<b.e.f.d<IBinder, Bundle>> list = bVar.g.get(str);
        if (list == null) {
            list = new ArrayList<>();
        }
        for (b.e.f.d<IBinder, Bundle> dVar : list) {
            if (iBinder == dVar.f1393a && d.a(bundle, dVar.f1394b)) {
                return;
            }
        }
        list.add(new b.e.f.d<>(iBinder, bundle));
        bVar.g.put(str, list);
        a(str, bVar, bundle, (Bundle) null);
        this.f869d = bVar;
        a(str, bundle);
        this.f869d = null;
    }

    /* access modifiers changed from: package-private */
    public void a(String str, b bVar, ResultReceiver resultReceiver) {
        f fVar = new f(this, str, resultReceiver);
        this.f869d = bVar;
        b(str, fVar);
        this.f869d = null;
        if (!fVar.b()) {
            throw new IllegalStateException("onLoadItem must call detach() or sendResult() before returning for id=" + str);
        }
    }

    public abstract void a(String str, i<List<MediaBrowserCompat.MediaItem>> iVar);

    public void a(String str, i<List<MediaBrowserCompat.MediaItem>> iVar, Bundle bundle) {
        iVar.a(1);
        a(str, iVar);
    }

    /* access modifiers changed from: package-private */
    public boolean a(String str, int i2) {
        if (str == null) {
            return false;
        }
        for (String str2 : getPackageManager().getPackagesForUid(i2)) {
            if (str2.equals(str)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean a(String str, b bVar, IBinder iBinder) {
        boolean z = true;
        boolean z2 = false;
        if (iBinder == null) {
            try {
                if (bVar.g.remove(str) == null) {
                    z = false;
                }
                return z;
            } finally {
                this.f869d = bVar;
                a(str);
                this.f869d = null;
            }
        } else {
            List<b.e.f.d<IBinder, Bundle>> list = bVar.g.get(str);
            if (list != null) {
                Iterator<b.e.f.d<IBinder, Bundle>> it = list.iterator();
                while (it.hasNext()) {
                    if (iBinder == it.next().f1393a) {
                        it.remove();
                        z2 = true;
                    }
                }
                if (list.size() == 0) {
                    bVar.g.remove(str);
                }
            }
            this.f869d = bVar;
            a(str);
            this.f869d = null;
            return z2;
        }
    }

    /* access modifiers changed from: package-private */
    public void b(String str, Bundle bundle, b bVar, ResultReceiver resultReceiver) {
        g gVar = new g(this, str, resultReceiver);
        this.f869d = bVar;
        b(str, bundle, gVar);
        this.f869d = null;
        if (!gVar.b()) {
            throw new IllegalStateException("onSearch must call detach() or sendResult() before returning for query=" + str);
        }
    }

    public void b(String str, Bundle bundle, i<List<MediaBrowserCompat.MediaItem>> iVar) {
        iVar.a(4);
        iVar.b((List<MediaBrowserCompat.MediaItem>) null);
    }

    public void b(String str, i<MediaBrowserCompat.MediaItem> iVar) {
        iVar.a(2);
        iVar.b((MediaBrowserCompat.MediaItem) null);
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    }

    public IBinder onBind(Intent intent) {
        return this.f867b.a(intent);
    }

    public void onCreate() {
        super.onCreate();
        int i2 = Build.VERSION.SDK_INT;
        this.f867b = i2 >= 28 ? new g() : i2 >= 26 ? new f() : i2 >= 23 ? new e() : i2 >= 21 ? new d() : new h();
        this.f867b.onCreate();
    }
}
