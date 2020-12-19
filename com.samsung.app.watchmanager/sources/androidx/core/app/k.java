package androidx.core.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;

public final class k implements Iterable<Intent> {

    /* renamed from: a  reason: collision with root package name */
    private final ArrayList<Intent> f589a = new ArrayList<>();

    /* renamed from: b  reason: collision with root package name */
    private final Context f590b;

    public interface a {
        Intent c();
    }

    private k(Context context) {
        this.f590b = context;
    }

    public static k a(Context context) {
        return new k(context);
    }

    public k a(Activity activity) {
        Intent c2 = activity instanceof a ? ((a) activity).c() : null;
        if (c2 == null) {
            c2 = d.a(activity);
        }
        if (c2 != null) {
            ComponentName component = c2.getComponent();
            if (component == null) {
                component = c2.resolveActivity(this.f590b.getPackageManager());
            }
            a(component);
            a(c2);
        }
        return this;
    }

    public k a(ComponentName componentName) {
        int size = this.f589a.size();
        try {
            Context context = this.f590b;
            while (true) {
                Intent a2 = d.a(context, componentName);
                if (a2 == null) {
                    return this;
                }
                this.f589a.add(size, a2);
                context = this.f590b;
                componentName = a2.getComponent();
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("TaskStackBuilder", "Bad ComponentName while traversing activity parent metadata");
            throw new IllegalArgumentException(e);
        }
    }

    public k a(Intent intent) {
        this.f589a.add(intent);
        return this;
    }

    public void a() {
        a((Bundle) null);
    }

    public void a(Bundle bundle) {
        if (!this.f589a.isEmpty()) {
            ArrayList<Intent> arrayList = this.f589a;
            Intent[] intentArr = (Intent[]) arrayList.toArray(new Intent[arrayList.size()]);
            intentArr[0] = new Intent(intentArr[0]).addFlags(268484608);
            if (!androidx.core.content.a.a(this.f590b, intentArr, bundle)) {
                Intent intent = new Intent(intentArr[intentArr.length - 1]);
                intent.addFlags(268435456);
                this.f590b.startActivity(intent);
                return;
            }
            return;
        }
        throw new IllegalStateException("No intents added to TaskStackBuilder; cannot startActivities");
    }

    @Override // java.lang.Iterable
    @Deprecated
    public Iterator<Intent> iterator() {
        return this.f589a.iterator();
    }
}
