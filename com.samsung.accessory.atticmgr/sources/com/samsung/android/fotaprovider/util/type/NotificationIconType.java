package com.samsung.android.fotaprovider.util.type;

import com.sec.android.fotaprovider.R;

public enum NotificationIconType {
    WATCH {
        @Override // com.samsung.android.fotaprovider.util.type.NotificationIconType
        public int getIndicatorCompletion() {
            return R.drawable.ic_stat_notify_watch_completion;
        }

        @Override // com.samsung.android.fotaprovider.util.type.NotificationIconType
        public int getIndicatorDmSession() {
            return R.drawable.ic_stat_notify_watch_session;
        }

        @Override // com.samsung.android.fotaprovider.util.type.NotificationIconType
        public int getIndicatorPostpone() {
            return R.drawable.ic_stat_notify_watch_postpone;
        }
    },
    FIT2 {
        @Override // com.samsung.android.fotaprovider.util.type.NotificationIconType
        public int getIndicatorCompletion() {
            return R.drawable.ic_stat_notify_band_completion;
        }

        @Override // com.samsung.android.fotaprovider.util.type.NotificationIconType
        public int getIndicatorDmSession() {
            return R.drawable.ic_stat_notify_band_session;
        }

        @Override // com.samsung.android.fotaprovider.util.type.NotificationIconType
        public int getIndicatorPostpone() {
            return R.drawable.ic_stat_notify_band_postopone;
        }
    },
    EARBUDS_BEAN {
        @Override // com.samsung.android.fotaprovider.util.type.NotificationIconType
        public int getIndicatorCompletion() {
            return R.drawable.ic_stat_notify_bean_completion;
        }

        @Override // com.samsung.android.fotaprovider.util.type.NotificationIconType
        public int getIndicatorDmSession() {
            return R.drawable.ic_stat_notify_bean_session;
        }

        @Override // com.samsung.android.fotaprovider.util.type.NotificationIconType
        public int getIndicatorPostpone() {
            return R.drawable.ic_stat_notify_bean_postpone;
        }
    },
    EARBUDS_ATTIC {
        @Override // com.samsung.android.fotaprovider.util.type.NotificationIconType
        public int getIndicatorCompletion() {
            return R.drawable.ic_stat_notify_tws_completion;
        }

        @Override // com.samsung.android.fotaprovider.util.type.NotificationIconType
        public int getIndicatorDmSession() {
            return R.drawable.ic_stat_notify_tws_session;
        }

        @Override // com.samsung.android.fotaprovider.util.type.NotificationIconType
        public int getIndicatorPostpone() {
            return R.drawable.ic_stat_notify_tws_postpone;
        }
    },
    BAND {
        @Override // com.samsung.android.fotaprovider.util.type.NotificationIconType
        public int getIndicatorCompletion() {
            return R.drawable.ic_stat_notify_band_completion;
        }

        @Override // com.samsung.android.fotaprovider.util.type.NotificationIconType
        public int getIndicatorDmSession() {
            return R.drawable.ic_stat_notify_band_session;
        }

        @Override // com.samsung.android.fotaprovider.util.type.NotificationIconType
        public int getIndicatorPostpone() {
            return R.drawable.ic_stat_notify_band_postopone;
        }
    };

    public abstract int getIndicatorCompletion();

    public abstract int getIndicatorDmSession();

    public abstract int getIndicatorPostpone();
}
