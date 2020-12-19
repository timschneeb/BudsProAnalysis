package androidx.lifecycle;

import androidx.arch.core.util.Function;

public class Transformations {
    private Transformations() {
    }

    public static <X, Y> LiveData<Y> map(LiveData<X> liveData, final Function<X, Y> function) {
        final MediatorLiveData mediatorLiveData = new MediatorLiveData();
        mediatorLiveData.addSource(liveData, new Observer<X>() {
            /* class androidx.lifecycle.Transformations.AnonymousClass1 */

            @Override // androidx.lifecycle.Observer
            public void onChanged(X x) {
                mediatorLiveData.setValue(function.apply(x));
            }
        });
        return mediatorLiveData;
    }

    public static <X, Y> LiveData<Y> switchMap(LiveData<X> liveData, final Function<X, LiveData<Y>> function) {
        final MediatorLiveData mediatorLiveData = new MediatorLiveData();
        mediatorLiveData.addSource(liveData, new Observer<X>() {
            /* class androidx.lifecycle.Transformations.AnonymousClass2 */
            LiveData<Y> mSource;

            @Override // androidx.lifecycle.Observer
            public void onChanged(X x) {
                LiveData<Y> liveData = (LiveData) function.apply(x);
                LiveData<Y> liveData2 = this.mSource;
                if (liveData2 != liveData) {
                    if (liveData2 != null) {
                        mediatorLiveData.removeSource(liveData2);
                    }
                    this.mSource = liveData;
                    LiveData<Y> liveData3 = this.mSource;
                    if (liveData3 != null) {
                        mediatorLiveData.addSource(liveData3, new Observer<Y>() {
                            /* class androidx.lifecycle.Transformations.AnonymousClass2.AnonymousClass1 */

                            @Override // androidx.lifecycle.Observer
                            public void onChanged(Y y) {
                                mediatorLiveData.setValue(y);
                            }
                        });
                    }
                }
            }
        });
        return mediatorLiveData;
    }

    public static <X> LiveData<X> distinctUntilChanged(LiveData<X> liveData) {
        final MediatorLiveData mediatorLiveData = new MediatorLiveData();
        mediatorLiveData.addSource(liveData, new Observer<X>() {
            /* class androidx.lifecycle.Transformations.AnonymousClass3 */
            boolean mFirstTime = true;

            @Override // androidx.lifecycle.Observer
            public void onChanged(X x) {
                Object value = mediatorLiveData.getValue();
                if (this.mFirstTime || ((value == null && x != null) || (value != null && !value.equals(x)))) {
                    this.mFirstTime = false;
                    mediatorLiveData.setValue(x);
                }
            }
        });
        return mediatorLiveData;
    }
}
