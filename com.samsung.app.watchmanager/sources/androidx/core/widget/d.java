package androidx.core.widget;

class d implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ ContentLoadingProgressBar f653a;

    d(ContentLoadingProgressBar contentLoadingProgressBar) {
        this.f653a = contentLoadingProgressBar;
    }

    public void run() {
        ContentLoadingProgressBar contentLoadingProgressBar = this.f653a;
        contentLoadingProgressBar.f633b = false;
        contentLoadingProgressBar.f632a = -1;
        contentLoadingProgressBar.setVisibility(8);
    }
}
