package com.samsung.android.app.twatchmanager.util;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

public class ActivityUtils {
    public static String FRAGMENT_TAG = "tUHMFragment";

    public static void addFragmentToActivity(FragmentManager fragmentManager, Fragment fragment, int i, String str) {
        addFragmentToActivity(fragmentManager, fragment, i, str, false);
    }

    public static void addFragmentToActivity(FragmentManager fragmentManager, Fragment fragment, int i, String str, boolean z) {
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        beginTransaction.add(i, fragment, str);
        if (z) {
            beginTransaction.addToBackStack(null);
        }
        beginTransaction.commitAllowingStateLoss();
    }

    public static void removeFragment(FragmentManager fragmentManager, Fragment fragment) {
        fragmentManager.beginTransaction().remove(fragment).commitAllowingStateLoss();
    }

    public static void replaceFragmentToActivity(FragmentManager fragmentManager, Fragment fragment, int i, String str) {
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        beginTransaction.replace(i, fragment, str);
        beginTransaction.commitAllowingStateLoss();
    }
}
