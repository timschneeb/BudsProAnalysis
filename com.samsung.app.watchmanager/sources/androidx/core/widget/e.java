package androidx.core.widget;

class e implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ ContentLoadingProgressBar f654a;

    e(ContentLoadingProgressBar contentLoadingProgressBar) {
        this.f654a = contentLoadingProgressBar;
    }

    public void run() {
        ContentLoadingProgressBar contentLoadingProgressBar = this.f654a;
        contentLoadingProgressBar.f634c = false;
        if (!contentLoadingProgressBar.f635d) {
            contentLoadingProgressBar.f632a = System.currentTimeMillis();
            this.f654a.setVisibility(0);
        }
    }
}
