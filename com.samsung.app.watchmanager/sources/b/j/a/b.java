package b.j.a;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import com.samsung.android.app.twatchmanager.model.GroupInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public final class b {

    /* renamed from: a  reason: collision with root package name */
    private static final Object f1472a = new Object();

    /* renamed from: b  reason: collision with root package name */
    private static b f1473b;

    /* renamed from: c  reason: collision with root package name */
    private final Context f1474c;

    /* renamed from: d  reason: collision with root package name */
    private final HashMap<BroadcastReceiver, ArrayList<C0030b>> f1475d = new HashMap<>();
    private final HashMap<String, ArrayList<C0030b>> e = new HashMap<>();
    private final ArrayList<a> f = new ArrayList<>();
    private final Handler g;

    private static final class a {

        /* renamed from: a  reason: collision with root package name */
        final Intent f1476a;

        /* renamed from: b  reason: collision with root package name */
        final ArrayList<C0030b> f1477b;

        a(Intent intent, ArrayList<C0030b> arrayList) {
            this.f1476a = intent;
            this.f1477b = arrayList;
        }
    }

    /* renamed from: b.j.a.b$b  reason: collision with other inner class name */
    private static final class C0030b {

        /* renamed from: a  reason: collision with root package name */
        final IntentFilter f1478a;

        /* renamed from: b  reason: collision with root package name */
        final BroadcastReceiver f1479b;

        /* renamed from: c  reason: collision with root package name */
        boolean f1480c;

        /* renamed from: d  reason: collision with root package name */
        boolean f1481d;

        C0030b(IntentFilter intentFilter, BroadcastReceiver broadcastReceiver) {
            this.f1478a = intentFilter;
            this.f1479b = broadcastReceiver;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(128);
            sb.append("Receiver{");
            sb.append(this.f1479b);
            sb.append(" filter=");
            sb.append(this.f1478a);
            if (this.f1481d) {
                sb.append(" DEAD");
            }
            sb.append("}");
            return sb.toString();
        }
    }

    private b(Context context) {
        this.f1474c = context;
        this.g = new a(this, context.getMainLooper());
    }

    public static b a(Context context) {
        b bVar;
        synchronized (f1472a) {
            if (f1473b == null) {
                f1473b = new b(context.getApplicationContext());
            }
            bVar = f1473b;
        }
        return bVar;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001d, code lost:
        if (r2 >= r1.length) goto L_0x0000;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001f, code lost:
        r3 = r1[r2];
        r4 = r3.f1477b.size();
        r5 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0028, code lost:
        if (r5 >= r4) goto L_0x0042;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002a, code lost:
        r6 = r3.f1477b.get(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0034, code lost:
        if (r6.f1481d != false) goto L_0x003f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0036, code lost:
        r6.f1479b.onReceive(r9.f1474c, r3.f1476a);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x003f, code lost:
        r5 = r5 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0042, code lost:
        r2 = r2 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x001a, code lost:
        r2 = 0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a() {
        /*
            r9 = this;
        L_0x0000:
            java.util.HashMap<android.content.BroadcastReceiver, java.util.ArrayList<b.j.a.b$b>> r0 = r9.f1475d
            monitor-enter(r0)
            java.util.ArrayList<b.j.a.b$a> r1 = r9.f     // Catch:{ all -> 0x0045 }
            int r1 = r1.size()     // Catch:{ all -> 0x0045 }
            if (r1 > 0) goto L_0x000d
            monitor-exit(r0)     // Catch:{ all -> 0x0045 }
            return
        L_0x000d:
            b.j.a.b$a[] r1 = new b.j.a.b.a[r1]     // Catch:{ all -> 0x0045 }
            java.util.ArrayList<b.j.a.b$a> r2 = r9.f     // Catch:{ all -> 0x0045 }
            r2.toArray(r1)     // Catch:{ all -> 0x0045 }
            java.util.ArrayList<b.j.a.b$a> r2 = r9.f     // Catch:{ all -> 0x0045 }
            r2.clear()     // Catch:{ all -> 0x0045 }
            monitor-exit(r0)     // Catch:{ all -> 0x0045 }
            r0 = 0
            r2 = 0
        L_0x001c:
            int r3 = r1.length
            if (r2 >= r3) goto L_0x0000
            r3 = r1[r2]
            java.util.ArrayList<b.j.a.b$b> r4 = r3.f1477b
            int r4 = r4.size()
            r5 = 0
        L_0x0028:
            if (r5 >= r4) goto L_0x0042
            java.util.ArrayList<b.j.a.b$b> r6 = r3.f1477b
            java.lang.Object r6 = r6.get(r5)
            b.j.a.b$b r6 = (b.j.a.b.C0030b) r6
            boolean r7 = r6.f1481d
            if (r7 != 0) goto L_0x003f
            android.content.BroadcastReceiver r6 = r6.f1479b
            android.content.Context r7 = r9.f1474c
            android.content.Intent r8 = r3.f1476a
            r6.onReceive(r7, r8)
        L_0x003f:
            int r5 = r5 + 1
            goto L_0x0028
        L_0x0042:
            int r2 = r2 + 1
            goto L_0x001c
        L_0x0045:
            r1 = move-exception
            monitor-exit(r0)
            goto L_0x0049
        L_0x0048:
            throw r1
        L_0x0049:
            goto L_0x0048
        */
        throw new UnsupportedOperationException("Method not decompiled: b.j.a.b.a():void");
    }

    public void a(BroadcastReceiver broadcastReceiver) {
        synchronized (this.f1475d) {
            ArrayList<C0030b> remove = this.f1475d.remove(broadcastReceiver);
            if (remove != null) {
                for (int size = remove.size() - 1; size >= 0; size--) {
                    C0030b bVar = remove.get(size);
                    bVar.f1481d = true;
                    for (int i = 0; i < bVar.f1478a.countActions(); i++) {
                        String action = bVar.f1478a.getAction(i);
                        ArrayList<C0030b> arrayList = this.e.get(action);
                        if (arrayList != null) {
                            for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
                                C0030b bVar2 = arrayList.get(size2);
                                if (bVar2.f1479b == broadcastReceiver) {
                                    bVar2.f1481d = true;
                                    arrayList.remove(size2);
                                }
                            }
                            if (arrayList.size() <= 0) {
                                this.e.remove(action);
                            }
                        }
                    }
                }
            }
        }
    }

    public void a(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        synchronized (this.f1475d) {
            C0030b bVar = new C0030b(intentFilter, broadcastReceiver);
            ArrayList<C0030b> arrayList = this.f1475d.get(broadcastReceiver);
            if (arrayList == null) {
                arrayList = new ArrayList<>(1);
                this.f1475d.put(broadcastReceiver, arrayList);
            }
            arrayList.add(bVar);
            for (int i = 0; i < intentFilter.countActions(); i++) {
                String action = intentFilter.getAction(i);
                ArrayList<C0030b> arrayList2 = this.e.get(action);
                if (arrayList2 == null) {
                    arrayList2 = new ArrayList<>(1);
                    this.e.put(action, arrayList2);
                }
                arrayList2.add(bVar);
            }
        }
    }

    public boolean a(Intent intent) {
        String str;
        ArrayList<C0030b> arrayList;
        int i;
        String str2;
        ArrayList arrayList2;
        synchronized (this.f1475d) {
            String action = intent.getAction();
            String resolveTypeIfNeeded = intent.resolveTypeIfNeeded(this.f1474c.getContentResolver());
            Uri data = intent.getData();
            String scheme = intent.getScheme();
            Set<String> categories = intent.getCategories();
            boolean z = (intent.getFlags() & 8) != 0;
            if (z) {
                Log.v("LocalBroadcastManager", "Resolving type " + resolveTypeIfNeeded + " scheme " + scheme + " of intent " + intent);
            }
            ArrayList<C0030b> arrayList3 = this.e.get(intent.getAction());
            if (arrayList3 != null) {
                if (z) {
                    Log.v("LocalBroadcastManager", "Action list: " + arrayList3);
                }
                ArrayList arrayList4 = null;
                int i2 = 0;
                while (i2 < arrayList3.size()) {
                    C0030b bVar = arrayList3.get(i2);
                    if (z) {
                        Log.v("LocalBroadcastManager", "Matching against filter " + bVar.f1478a);
                    }
                    if (bVar.f1480c) {
                        if (z) {
                            Log.v("LocalBroadcastManager", "  Filter's target already added");
                        }
                        i = i2;
                        arrayList = arrayList3;
                        str2 = action;
                        str = resolveTypeIfNeeded;
                        arrayList2 = arrayList4;
                    } else {
                        str2 = action;
                        arrayList2 = arrayList4;
                        i = i2;
                        arrayList = arrayList3;
                        str = resolveTypeIfNeeded;
                        int match = bVar.f1478a.match(action, resolveTypeIfNeeded, scheme, data, categories, "LocalBroadcastManager");
                        if (match >= 0) {
                            if (z) {
                                Log.v("LocalBroadcastManager", "  Filter matched!  match=0x" + Integer.toHexString(match));
                            }
                            arrayList4 = arrayList2 == null ? new ArrayList() : arrayList2;
                            arrayList4.add(bVar);
                            bVar.f1480c = true;
                            i2 = i + 1;
                            action = str2;
                            arrayList3 = arrayList;
                            resolveTypeIfNeeded = str;
                        } else if (z) {
                            Log.v("LocalBroadcastManager", "  Filter did not match: " + (match != -4 ? match != -3 ? match != -2 ? match != -1 ? "unknown reason" : GroupInfo.ImageInfo.ATTR_TYPE : "data" : "action" : "category"));
                        }
                    }
                    arrayList4 = arrayList2;
                    i2 = i + 1;
                    action = str2;
                    arrayList3 = arrayList;
                    resolveTypeIfNeeded = str;
                }
                if (arrayList4 != null) {
                    for (int i3 = 0; i3 < arrayList4.size(); i3++) {
                        ((C0030b) arrayList4.get(i3)).f1480c = false;
                    }
                    this.f.add(new a(intent, arrayList4));
                    if (!this.g.hasMessages(1)) {
                        this.g.sendEmptyMessage(1);
                    }
                    return true;
                }
            }
            return false;
        }
    }
}
