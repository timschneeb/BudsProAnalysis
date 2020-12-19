package android.support.v4.media.session;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.a;
import android.support.v4.media.session.b;
import android.support.v4.media.session.c;
import android.util.Log;
import android.view.KeyEvent;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public final class MediaControllerCompat {

    /* renamed from: a  reason: collision with root package name */
    private final b f53a;

    /* renamed from: b  reason: collision with root package name */
    private final MediaSessionCompat.Token f54b;

    /* renamed from: c  reason: collision with root package name */
    private final HashSet<a> f55c = new HashSet<>();

    static class MediaControllerImplApi21 implements b {

        /* renamed from: a  reason: collision with root package name */
        protected final Object f56a;

        /* renamed from: b  reason: collision with root package name */
        final Object f57b = new Object();

        /* renamed from: c  reason: collision with root package name */
        private final List<a> f58c = new ArrayList();

        /* renamed from: d  reason: collision with root package name */
        private HashMap<a, a> f59d = new HashMap<>();
        final MediaSessionCompat.Token e;

        /* access modifiers changed from: private */
        public static class ExtraBinderRequestResultReceiver extends ResultReceiver {

            /* renamed from: a  reason: collision with root package name */
            private WeakReference<MediaControllerImplApi21> f60a;

            ExtraBinderRequestResultReceiver(MediaControllerImplApi21 mediaControllerImplApi21) {
                super(null);
                this.f60a = new WeakReference<>(mediaControllerImplApi21);
            }

            /* access modifiers changed from: protected */
            public void onReceiveResult(int i, Bundle bundle) {
                MediaControllerImplApi21 mediaControllerImplApi21 = this.f60a.get();
                if (mediaControllerImplApi21 != null && bundle != null) {
                    synchronized (mediaControllerImplApi21.f57b) {
                        mediaControllerImplApi21.e.a(b.a.a(androidx.core.app.c.a(bundle, "android.support.v4.media.session.EXTRA_BINDER")));
                        mediaControllerImplApi21.e.a(bundle.getBundle("android.support.v4.media.session.SESSION_TOKEN2_BUNDLE"));
                        mediaControllerImplApi21.a();
                    }
                }
            }
        }

        /* access modifiers changed from: private */
        public static class a extends a.c {
            a(a aVar) {
                super(aVar);
            }

            @Override // android.support.v4.media.session.a, android.support.v4.media.session.MediaControllerCompat.a.c
            public void a() {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.a, android.support.v4.media.session.MediaControllerCompat.a.c
            public void a(Bundle bundle) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.a, android.support.v4.media.session.MediaControllerCompat.a.c
            public void a(MediaMetadataCompat mediaMetadataCompat) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.a, android.support.v4.media.session.MediaControllerCompat.a.c
            public void a(ParcelableVolumeInfo parcelableVolumeInfo) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.a, android.support.v4.media.session.MediaControllerCompat.a.c
            public void a(CharSequence charSequence) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.a, android.support.v4.media.session.MediaControllerCompat.a.c
            public void a(List<MediaSessionCompat.QueueItem> list) {
                throw new AssertionError();
            }
        }

        public MediaControllerImplApi21(Context context, MediaSessionCompat.Token token) {
            this.e = token;
            this.f56a = c.a(context, this.e.b());
            if (this.f56a == null) {
                throw new RemoteException();
            } else if (this.e.a() == null) {
                b();
            }
        }

        private void b() {
            a("android.support.v4.media.session.command.GET_EXTRA_BINDER", null, new ExtraBinderRequestResultReceiver(this));
        }

        /* access modifiers changed from: package-private */
        public void a() {
            if (this.e.a() != null) {
                for (a aVar : this.f58c) {
                    a aVar2 = new a(aVar);
                    this.f59d.put(aVar, aVar2);
                    aVar.f63c = aVar2;
                    try {
                        this.e.a().a(aVar2);
                        aVar.a(13, null, null);
                    } catch (RemoteException e2) {
                        Log.e("MediaControllerCompat", "Dead object in registerCallback.", e2);
                    }
                }
                this.f58c.clear();
            }
        }

        public void a(String str, Bundle bundle, ResultReceiver resultReceiver) {
            c.a(this.f56a, str, bundle, resultReceiver);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.b
        public boolean a(KeyEvent keyEvent) {
            return c.a(this.f56a, keyEvent);
        }
    }

    public static abstract class a implements IBinder.DeathRecipient {

        /* renamed from: a  reason: collision with root package name */
        final Object f61a;

        /* renamed from: b  reason: collision with root package name */
        HandlerC0002a f62b;

        /* renamed from: c  reason: collision with root package name */
        a f63c;

        /* access modifiers changed from: private */
        /* renamed from: android.support.v4.media.session.MediaControllerCompat$a$a  reason: collision with other inner class name */
        public class HandlerC0002a extends Handler {
        }

        private static class b implements c.a {

            /* renamed from: a  reason: collision with root package name */
            private final WeakReference<a> f64a;

            b(a aVar) {
                this.f64a = new WeakReference<>(aVar);
            }

            @Override // android.support.v4.media.session.c.a
            public void a() {
                a aVar = this.f64a.get();
                if (aVar != null) {
                    aVar.a();
                }
            }

            @Override // android.support.v4.media.session.c.a
            public void a(int i, int i2, int i3, int i4, int i5) {
                a aVar = this.f64a.get();
                if (aVar != null) {
                    aVar.a(new f(i, i2, i3, i4, i5));
                }
            }

            @Override // android.support.v4.media.session.c.a
            public void a(Bundle bundle) {
                a aVar = this.f64a.get();
                if (aVar != null) {
                    aVar.a(bundle);
                }
            }

            @Override // android.support.v4.media.session.c.a
            public void a(CharSequence charSequence) {
                a aVar = this.f64a.get();
                if (aVar != null) {
                    aVar.a(charSequence);
                }
            }

            @Override // android.support.v4.media.session.c.a
            public void a(Object obj) {
                a aVar = this.f64a.get();
                if (aVar != null) {
                    aVar.a(MediaMetadataCompat.a(obj));
                }
            }

            @Override // android.support.v4.media.session.c.a
            public void a(String str, Bundle bundle) {
                a aVar = this.f64a.get();
                if (aVar == null) {
                    return;
                }
                if (aVar.f63c == null || Build.VERSION.SDK_INT >= 23) {
                    aVar.a(str, bundle);
                }
            }

            @Override // android.support.v4.media.session.c.a
            public void a(List<?> list) {
                a aVar = this.f64a.get();
                if (aVar != null) {
                    aVar.a(MediaSessionCompat.QueueItem.a(list));
                }
            }

            @Override // android.support.v4.media.session.c.a
            public void b(Object obj) {
                a aVar = this.f64a.get();
                if (aVar != null && aVar.f63c == null) {
                    aVar.a(PlaybackStateCompat.a(obj));
                }
            }
        }

        private static class c extends a.AbstractBinderC0003a {

            /* renamed from: a  reason: collision with root package name */
            private final WeakReference<a> f65a;

            c(a aVar) {
                this.f65a = new WeakReference<>(aVar);
            }

            @Override // android.support.v4.media.session.a
            public void a() {
                a aVar = this.f65a.get();
                if (aVar != null) {
                    aVar.a(8, null, null);
                }
            }

            @Override // android.support.v4.media.session.a
            public void a(int i) {
                a aVar = this.f65a.get();
                if (aVar != null) {
                    aVar.a(9, Integer.valueOf(i), null);
                }
            }

            @Override // android.support.v4.media.session.a
            public void a(Bundle bundle) {
                a aVar = this.f65a.get();
                if (aVar != null) {
                    aVar.a(7, bundle, null);
                }
            }

            @Override // android.support.v4.media.session.a
            public void a(MediaMetadataCompat mediaMetadataCompat) {
                a aVar = this.f65a.get();
                if (aVar != null) {
                    aVar.a(3, mediaMetadataCompat, null);
                }
            }

            @Override // android.support.v4.media.session.a
            public void a(ParcelableVolumeInfo parcelableVolumeInfo) {
                a aVar = this.f65a.get();
                if (aVar != null) {
                    aVar.a(4, parcelableVolumeInfo != null ? new f(parcelableVolumeInfo.f78a, parcelableVolumeInfo.f79b, parcelableVolumeInfo.f80c, parcelableVolumeInfo.f81d, parcelableVolumeInfo.e) : null, null);
                }
            }

            @Override // android.support.v4.media.session.a
            public void a(PlaybackStateCompat playbackStateCompat) {
                a aVar = this.f65a.get();
                if (aVar != null) {
                    aVar.a(2, playbackStateCompat, null);
                }
            }

            @Override // android.support.v4.media.session.a
            public void a(CharSequence charSequence) {
                a aVar = this.f65a.get();
                if (aVar != null) {
                    aVar.a(6, charSequence, null);
                }
            }

            @Override // android.support.v4.media.session.a
            public void a(String str, Bundle bundle) {
                a aVar = this.f65a.get();
                if (aVar != null) {
                    aVar.a(1, str, bundle);
                }
            }

            @Override // android.support.v4.media.session.a
            public void a(List<MediaSessionCompat.QueueItem> list) {
                a aVar = this.f65a.get();
                if (aVar != null) {
                    aVar.a(5, list, null);
                }
            }

            @Override // android.support.v4.media.session.a
            public void a(boolean z) {
                a aVar = this.f65a.get();
                if (aVar != null) {
                    aVar.a(11, Boolean.valueOf(z), null);
                }
            }

            @Override // android.support.v4.media.session.a
            public void b(int i) {
                a aVar = this.f65a.get();
                if (aVar != null) {
                    aVar.a(12, Integer.valueOf(i), null);
                }
            }

            @Override // android.support.v4.media.session.a
            public void b(boolean z) {
            }

            @Override // android.support.v4.media.session.a
            public void c() {
                a aVar = this.f65a.get();
                if (aVar != null) {
                    aVar.a(13, null, null);
                }
            }
        }

        public a() {
            c cVar;
            if (Build.VERSION.SDK_INT >= 21) {
                cVar = c.a(new b(this));
            } else {
                c cVar2 = new c(this);
                this.f63c = cVar2;
                cVar = cVar2;
            }
            this.f61a = cVar;
        }

        public void a() {
        }

        /* access modifiers changed from: package-private */
        public void a(int i, Object obj, Bundle bundle) {
            HandlerC0002a aVar = this.f62b;
            if (aVar != null) {
                Message obtainMessage = aVar.obtainMessage(i, obj);
                obtainMessage.setData(bundle);
                obtainMessage.sendToTarget();
            }
        }

        public void a(Bundle bundle) {
        }

        public void a(MediaMetadataCompat mediaMetadataCompat) {
        }

        public void a(f fVar) {
        }

        public void a(PlaybackStateCompat playbackStateCompat) {
        }

        public void a(CharSequence charSequence) {
        }

        public void a(String str, Bundle bundle) {
        }

        public void a(List<MediaSessionCompat.QueueItem> list) {
        }

        public void binderDied() {
            a(8, null, null);
        }
    }

    interface b {
        boolean a(KeyEvent keyEvent);
    }

    static class c extends MediaControllerImplApi21 {
        public c(Context context, MediaSessionCompat.Token token) {
            super(context, token);
        }
    }

    static class d extends c {
        public d(Context context, MediaSessionCompat.Token token) {
            super(context, token);
        }
    }

    static class e implements b {

        /* renamed from: a  reason: collision with root package name */
        private b f66a;

        public e(MediaSessionCompat.Token token) {
            this.f66a = b.a.a((IBinder) token.b());
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.b
        public boolean a(KeyEvent keyEvent) {
            if (keyEvent != null) {
                try {
                    this.f66a.a(keyEvent);
                    return false;
                } catch (RemoteException e) {
                    Log.e("MediaControllerCompat", "Dead object in dispatchMediaButtonEvent.", e);
                    return false;
                }
            } else {
                throw new IllegalArgumentException("event may not be null.");
            }
        }
    }

    public static final class f {

        /* renamed from: a  reason: collision with root package name */
        private final int f67a;

        /* renamed from: b  reason: collision with root package name */
        private final int f68b;

        /* renamed from: c  reason: collision with root package name */
        private final int f69c;

        /* renamed from: d  reason: collision with root package name */
        private final int f70d;
        private final int e;

        f(int i, int i2, int i3, int i4, int i5) {
            this.f67a = i;
            this.f68b = i2;
            this.f69c = i3;
            this.f70d = i4;
            this.e = i5;
        }
    }

    public MediaControllerCompat(Context context, MediaSessionCompat.Token token) {
        b mediaControllerImplApi21;
        if (token != null) {
            this.f54b = token;
            int i = Build.VERSION.SDK_INT;
            if (i >= 24) {
                mediaControllerImplApi21 = new d(context, token);
            } else if (i >= 23) {
                mediaControllerImplApi21 = new c(context, token);
            } else if (i >= 21) {
                mediaControllerImplApi21 = new MediaControllerImplApi21(context, token);
            } else {
                this.f53a = new e(token);
                return;
            }
            this.f53a = mediaControllerImplApi21;
            return;
        }
        throw new IllegalArgumentException("sessionToken must not be null");
    }

    public boolean a(KeyEvent keyEvent) {
        if (keyEvent != null) {
            return this.f53a.a(keyEvent);
        }
        throw new IllegalArgumentException("KeyEvent may not be null");
    }
}
