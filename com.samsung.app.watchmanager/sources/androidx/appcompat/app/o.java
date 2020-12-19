package androidx.appcompat.app;

class o implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ AppCompatDelegateImpl f193a;

    o(AppCompatDelegateImpl appCompatDelegateImpl) {
        this.f193a = appCompatDelegateImpl;
    }

    public void run() {
        AppCompatDelegateImpl appCompatDelegateImpl = this.f193a;
        if ((appCompatDelegateImpl.P & 1) != 0) {
            appCompatDelegateImpl.e(0);
        }
        AppCompatDelegateImpl appCompatDelegateImpl2 = this.f193a;
        if ((appCompatDelegateImpl2.P & 4096) != 0) {
            appCompatDelegateImpl2.e(108);
        }
        AppCompatDelegateImpl appCompatDelegateImpl3 = this.f193a;
        appCompatDelegateImpl3.O = false;
        appCompatDelegateImpl3.P = 0;
    }
}
