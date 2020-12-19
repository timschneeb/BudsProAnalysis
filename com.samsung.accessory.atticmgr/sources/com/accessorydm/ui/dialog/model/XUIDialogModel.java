package com.accessorydm.ui.dialog.model;

import android.content.Context;
import com.accessorydm.XDMDmUtils;
import com.accessorydm.ui.dialog.model.buttonstrategy.ButtonStrategy;

public interface XUIDialogModel {
    int[] getBlockKeyEvents();

    int getGravity();

    String getMessage();

    ButtonStrategy.Negative getNegativeButtonStrategy();

    ButtonStrategy.Neutral getNeutralButtonStrategy();

    ButtonStrategy.Positive getPositiveButtonStrategy();

    String getTitle();

    boolean isCancelable();

    void preExecute();

    public static class Base implements XUIDialogModel {
        private final String message;
        private final ButtonStrategy.Negative negativeButtonStrategy;
        private final ButtonStrategy.Neutral neutralButtonStrategy;
        private final ButtonStrategy.Positive positiveButtonStrategy;
        private final String title;

        @Override // com.accessorydm.ui.dialog.model.XUIDialogModel
        public int[] getBlockKeyEvents() {
            return null;
        }

        @Override // com.accessorydm.ui.dialog.model.XUIDialogModel
        public int getGravity() {
            return 80;
        }

        @Override // com.accessorydm.ui.dialog.model.XUIDialogModel
        public boolean isCancelable() {
            return true;
        }

        @Override // com.accessorydm.ui.dialog.model.XUIDialogModel
        public void preExecute() {
        }

        Base(String str, String str2, ButtonStrategy.Neutral neutral, ButtonStrategy.Negative negative, ButtonStrategy.Positive positive) {
            this.title = str;
            this.message = str2;
            this.neutralButtonStrategy = neutral;
            this.negativeButtonStrategy = negative;
            this.positiveButtonStrategy = positive;
        }

        @Override // com.accessorydm.ui.dialog.model.XUIDialogModel
        public String getTitle() {
            return this.title;
        }

        @Override // com.accessorydm.ui.dialog.model.XUIDialogModel
        public String getMessage() {
            return this.message;
        }

        @Override // com.accessorydm.ui.dialog.model.XUIDialogModel
        public ButtonStrategy.Neutral getNeutralButtonStrategy() {
            return this.neutralButtonStrategy;
        }

        @Override // com.accessorydm.ui.dialog.model.XUIDialogModel
        public ButtonStrategy.Negative getNegativeButtonStrategy() {
            return this.negativeButtonStrategy;
        }

        @Override // com.accessorydm.ui.dialog.model.XUIDialogModel
        public ButtonStrategy.Positive getPositiveButtonStrategy() {
            return this.positiveButtonStrategy;
        }

        static String getString(int i) {
            if (i == -1) {
                return null;
            }
            return getContext().getString(i);
        }

        private static Context getContext() {
            return XDMDmUtils.getContext();
        }
    }

    public static class StubOk extends Base {
        public StubOk(String str, String str2) {
            super(str, str2, ButtonStrategy.Neutral.NONE, ButtonStrategy.Negative.NONE, new ButtonStrategy.StubOk());
        }
    }
}
