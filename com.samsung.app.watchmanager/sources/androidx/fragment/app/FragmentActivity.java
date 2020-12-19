package androidx.fragment.app;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import androidx.core.app.ComponentActivity;
import androidx.core.app.a;
import androidx.lifecycle.f;
import androidx.lifecycle.t;
import androidx.lifecycle.u;
import b.c.j;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public class FragmentActivity extends ComponentActivity implements u, a.AbstractC0010a, a.c {

    /* renamed from: c  reason: collision with root package name */
    final Handler f719c = new HandlerC0087g(this);

    /* renamed from: d  reason: collision with root package name */
    final C0089i f720d = C0089i.a(new a());
    private t e;
    boolean f;
    boolean g;
    boolean h = true;
    boolean i;
    boolean j;
    int k;
    j<String> l;

    class a extends AbstractC0090j<FragmentActivity> {
        public a() {
            super(FragmentActivity.this);
        }

        @Override // androidx.fragment.app.AbstractC0088h
        public View a(int i) {
            return FragmentActivity.this.findViewById(i);
        }

        @Override // androidx.fragment.app.AbstractC0090j
        public void a(Fragment fragment) {
            FragmentActivity.this.a(fragment);
        }

        @Override // androidx.fragment.app.AbstractC0090j
        public void a(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            FragmentActivity.this.dump(str, fileDescriptor, printWriter, strArr);
        }

        @Override // androidx.fragment.app.AbstractC0088h
        public boolean a() {
            Window window = FragmentActivity.this.getWindow();
            return (window == null || window.peekDecorView() == null) ? false : true;
        }

        @Override // androidx.fragment.app.AbstractC0090j
        public boolean b(Fragment fragment) {
            return !FragmentActivity.this.isFinishing();
        }

        @Override // androidx.fragment.app.AbstractC0090j
        public LayoutInflater f() {
            return FragmentActivity.this.getLayoutInflater().cloneInContext(FragmentActivity.this);
        }

        @Override // androidx.fragment.app.AbstractC0090j
        public int g() {
            Window window = FragmentActivity.this.getWindow();
            if (window == null) {
                return 0;
            }
            return window.getAttributes().windowAnimations;
        }

        @Override // androidx.fragment.app.AbstractC0090j
        public boolean h() {
            return FragmentActivity.this.getWindow() != null;
        }

        @Override // androidx.fragment.app.AbstractC0090j
        public void i() {
            FragmentActivity.this.g();
        }
    }

    static final class b {

        /* renamed from: a  reason: collision with root package name */
        Object f721a;

        /* renamed from: b  reason: collision with root package name */
        t f722b;

        /* renamed from: c  reason: collision with root package name */
        s f723c;

        b() {
        }
    }

    static void a(int i2) {
        if ((i2 & -65536) != 0) {
            throw new IllegalArgumentException("Can only use lower 16 bits for requestCode");
        }
    }

    private static boolean a(AbstractC0091k kVar, f.b bVar) {
        boolean z = false;
        for (Fragment fragment : kVar.c()) {
            if (fragment != null) {
                if (fragment.a().a().a(f.b.STARTED)) {
                    fragment.U.a(bVar);
                    z = true;
                }
                AbstractC0091k P = fragment.P();
                if (P != null) {
                    z |= a(P, bVar);
                }
            }
        }
        return z;
    }

    private void h() {
        do {
        } while (a(d(), f.b.CREATED));
    }

    /* access modifiers changed from: package-private */
    public final View a(View view, String str, Context context, AttributeSet attributeSet) {
        return this.f720d.a(view, str, context, attributeSet);
    }

    @Override // androidx.lifecycle.h, androidx.core.app.ComponentActivity
    public f a() {
        return super.a();
    }

    public void a(Fragment fragment) {
    }

    /* access modifiers changed from: protected */
    public boolean a(View view, Menu menu) {
        return super.onPreparePanel(0, view, menu);
    }

    @Override // androidx.lifecycle.u
    public t b() {
        if (getApplication() != null) {
            if (this.e == null) {
                b bVar = (b) getLastNonConfigurationInstance();
                if (bVar != null) {
                    this.e = bVar.f722b;
                }
                if (this.e == null) {
                    this.e = new t();
                }
            }
            return this.e;
        }
        throw new IllegalStateException("Your activity is not yet attached to the Application instance. You can't request ViewModel before onCreate call.");
    }

    public AbstractC0091k d() {
        return this.f720d.j();
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        super.dump(str, fileDescriptor, printWriter, strArr);
        printWriter.print(str);
        printWriter.print("Local FragmentActivity ");
        printWriter.print(Integer.toHexString(System.identityHashCode(this)));
        printWriter.println(" State:");
        String str2 = str + "  ";
        printWriter.print(str2);
        printWriter.print("mCreated=");
        printWriter.print(this.f);
        printWriter.print(" mResumed=");
        printWriter.print(this.g);
        printWriter.print(" mStopped=");
        printWriter.print(this.h);
        if (getApplication() != null) {
            b.i.a.a.a(this).a(str2, fileDescriptor, printWriter, strArr);
        }
        this.f720d.j().a(str, fileDescriptor, printWriter, strArr);
    }

    /* access modifiers changed from: protected */
    public void e() {
        this.f720d.f();
    }

    public Object f() {
        return null;
    }

    @Deprecated
    public void g() {
        invalidateOptionsMenu();
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i2, int i3, Intent intent) {
        this.f720d.k();
        int i4 = i2 >> 16;
        if (i4 != 0) {
            int i5 = i4 - 1;
            String b2 = this.l.b(i5);
            this.l.d(i5);
            if (b2 == null) {
                Log.w("FragmentActivity", "Activity result delivered for unknown Fragment.");
                return;
            }
            Fragment a2 = this.f720d.a(b2);
            if (a2 == null) {
                Log.w("FragmentActivity", "Activity result no fragment exists for who: " + b2);
                return;
            }
            a2.a(i2 & 65535, i3, intent);
            return;
        }
        a.b a3 = androidx.core.app.a.a();
        if (a3 == null || !a3.a(this, i2, i3, intent)) {
            super.onActivityResult(i2, i3, intent);
        }
    }

    public void onBackPressed() {
        AbstractC0091k j2 = this.f720d.j();
        boolean d2 = j2.d();
        if (d2 && Build.VERSION.SDK_INT <= 25) {
            return;
        }
        if (d2 || !j2.e()) {
            super.onBackPressed();
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.f720d.k();
        this.f720d.a(configuration);
    }

    /* access modifiers changed from: protected */
    @Override // androidx.core.app.ComponentActivity
    public void onCreate(Bundle bundle) {
        t tVar;
        s sVar = null;
        this.f720d.a((Fragment) null);
        super.onCreate(bundle);
        b bVar = (b) getLastNonConfigurationInstance();
        if (!(bVar == null || (tVar = bVar.f722b) == null || this.e != null)) {
            this.e = tVar;
        }
        if (bundle != null) {
            Parcelable parcelable = bundle.getParcelable("android:support:fragments");
            C0089i iVar = this.f720d;
            if (bVar != null) {
                sVar = bVar.f723c;
            }
            iVar.a(parcelable, sVar);
            if (bundle.containsKey("android:support:next_request_index")) {
                this.k = bundle.getInt("android:support:next_request_index");
                int[] intArray = bundle.getIntArray("android:support:request_indicies");
                String[] stringArray = bundle.getStringArray("android:support:request_fragment_who");
                if (intArray == null || stringArray == null || intArray.length != stringArray.length) {
                    Log.w("FragmentActivity", "Invalid requestCode mapping in savedInstanceState.");
                } else {
                    this.l = new j<>(intArray.length);
                    for (int i2 = 0; i2 < intArray.length; i2++) {
                        this.l.c(intArray[i2], stringArray[i2]);
                    }
                }
            }
        }
        if (this.l == null) {
            this.l = new j<>();
            this.k = 0;
        }
        this.f720d.b();
    }

    public boolean onCreatePanelMenu(int i2, Menu menu) {
        return i2 == 0 ? super.onCreatePanelMenu(i2, menu) | this.f720d.a(menu, getMenuInflater()) : super.onCreatePanelMenu(i2, menu);
    }

    public View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        View a2 = a(view, str, context, attributeSet);
        return a2 == null ? super.onCreateView(view, str, context, attributeSet) : a2;
    }

    public View onCreateView(String str, Context context, AttributeSet attributeSet) {
        View a2 = a(null, str, context, attributeSet);
        return a2 == null ? super.onCreateView(str, context, attributeSet) : a2;
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        if (this.e != null && !isChangingConfigurations()) {
            this.e.a();
        }
        this.f720d.c();
    }

    public void onLowMemory() {
        super.onLowMemory();
        this.f720d.d();
    }

    public boolean onMenuItemSelected(int i2, MenuItem menuItem) {
        if (super.onMenuItemSelected(i2, menuItem)) {
            return true;
        }
        if (i2 == 0) {
            return this.f720d.b(menuItem);
        }
        if (i2 != 6) {
            return false;
        }
        return this.f720d.a(menuItem);
    }

    public void onMultiWindowModeChanged(boolean z) {
        this.f720d.a(z);
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.f720d.k();
    }

    public void onPanelClosed(int i2, Menu menu) {
        if (i2 == 0) {
            this.f720d.a(menu);
        }
        super.onPanelClosed(i2, menu);
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        this.g = false;
        if (this.f719c.hasMessages(2)) {
            this.f719c.removeMessages(2);
            e();
        }
        this.f720d.e();
    }

    public void onPictureInPictureModeChanged(boolean z) {
        this.f720d.b(z);
    }

    /* access modifiers changed from: protected */
    public void onPostResume() {
        super.onPostResume();
        this.f719c.removeMessages(2);
        e();
        this.f720d.i();
    }

    public boolean onPreparePanel(int i2, View view, Menu menu) {
        return (i2 != 0 || menu == null) ? super.onPreparePanel(i2, view, menu) : a(view, menu) | this.f720d.b(menu);
    }

    public void onRequestPermissionsResult(int i2, String[] strArr, int[] iArr) {
        this.f720d.k();
        int i3 = (i2 >> 16) & 65535;
        if (i3 != 0) {
            int i4 = i3 - 1;
            String b2 = this.l.b(i4);
            this.l.d(i4);
            if (b2 == null) {
                Log.w("FragmentActivity", "Activity result delivered for unknown Fragment.");
                return;
            }
            Fragment a2 = this.f720d.a(b2);
            if (a2 == null) {
                Log.w("FragmentActivity", "Activity result no fragment exists for who: " + b2);
                return;
            }
            a2.a(i2 & 65535, strArr, iArr);
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        this.f719c.sendEmptyMessage(2);
        this.g = true;
        this.f720d.i();
    }

    public final Object onRetainNonConfigurationInstance() {
        Object f2 = f();
        s l2 = this.f720d.l();
        if (l2 == null && this.e == null && f2 == null) {
            return null;
        }
        b bVar = new b();
        bVar.f721a = f2;
        bVar.f722b = this.e;
        bVar.f723c = l2;
        return bVar;
    }

    /* access modifiers changed from: protected */
    @Override // androidx.core.app.ComponentActivity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        h();
        Parcelable m = this.f720d.m();
        if (m != null) {
            bundle.putParcelable("android:support:fragments", m);
        }
        if (this.l.b() > 0) {
            bundle.putInt("android:support:next_request_index", this.k);
            int[] iArr = new int[this.l.b()];
            String[] strArr = new String[this.l.b()];
            for (int i2 = 0; i2 < this.l.b(); i2++) {
                iArr[i2] = this.l.c(i2);
                strArr[i2] = this.l.e(i2);
            }
            bundle.putIntArray("android:support:request_indicies", iArr);
            bundle.putStringArray("android:support:request_fragment_who", strArr);
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        this.h = false;
        if (!this.f) {
            this.f = true;
            this.f720d.a();
        }
        this.f720d.k();
        this.f720d.i();
        this.f720d.g();
    }

    public void onStateNotSaved() {
        this.f720d.k();
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        this.h = true;
        h();
        this.f720d.h();
    }

    public void startActivityForResult(Intent intent, int i2) {
        if (!this.j && i2 != -1) {
            a(i2);
        }
        super.startActivityForResult(intent, i2);
    }

    public void startActivityForResult(Intent intent, int i2, Bundle bundle) {
        if (!this.j && i2 != -1) {
            a(i2);
        }
        super.startActivityForResult(intent, i2, bundle);
    }

    @Override // android.app.Activity
    public void startIntentSenderForResult(IntentSender intentSender, int i2, Intent intent, int i3, int i4, int i5) {
        if (!this.i && i2 != -1) {
            a(i2);
        }
        super.startIntentSenderForResult(intentSender, i2, intent, i3, i4, i5);
    }

    @Override // android.app.Activity
    public void startIntentSenderForResult(IntentSender intentSender, int i2, Intent intent, int i3, int i4, int i5, Bundle bundle) {
        if (!this.i && i2 != -1) {
            a(i2);
        }
        super.startIntentSenderForResult(intentSender, i2, intent, i3, i4, i5, bundle);
    }
}
