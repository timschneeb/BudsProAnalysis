package com.accessorydm.ui.fullscreen.basefullscreen;

public interface XUIBaseFullscreenContract {

    public interface Presenter {
        void initializeListenersAfterCreatedUI();

        void onCreate();

        void onDestroy();

        boolean onKeyDown(int i);

        boolean onOptionsItemSelected(int i);
    }

    public interface View {
        void xuiOnBackPressed();

        void xuiSetAppBarWithTitleText(String str);
    }
}
