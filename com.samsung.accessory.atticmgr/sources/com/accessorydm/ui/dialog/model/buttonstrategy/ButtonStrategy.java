package com.accessorydm.ui.dialog.model.buttonstrategy;

import com.sec.android.fotaprovider.R;

public interface ButtonStrategy {
    public static final ButtonStrategy NONE = null;

    int getId();

    String getText();

    void onClick();

    public static abstract class Neutral extends AbstractButtonStrategy {
        public static final Neutral NONE = ((Neutral) ButtonStrategy.NONE);

        @Override // com.accessorydm.ui.dialog.model.buttonstrategy.AbstractButtonStrategy, com.accessorydm.ui.dialog.model.buttonstrategy.ButtonStrategy
        public /* bridge */ /* synthetic */ void onClick() {
            super.onClick();
        }

        protected Neutral(String str) {
            super(str, -3);
        }
    }

    public static abstract class Negative extends AbstractButtonStrategy {
        public static final Negative NONE = ((Negative) ButtonStrategy.NONE);

        @Override // com.accessorydm.ui.dialog.model.buttonstrategy.AbstractButtonStrategy, com.accessorydm.ui.dialog.model.buttonstrategy.ButtonStrategy
        public /* bridge */ /* synthetic */ void onClick() {
            super.onClick();
        }

        protected Negative(String str) {
            super(str, -2);
        }
    }

    public static abstract class Positive extends AbstractButtonStrategy {
        public static final Positive NONE = ((Positive) ButtonStrategy.NONE);

        @Override // com.accessorydm.ui.dialog.model.buttonstrategy.AbstractButtonStrategy, com.accessorydm.ui.dialog.model.buttonstrategy.ButtonStrategy
        public /* bridge */ /* synthetic */ void onClick() {
            super.onClick();
        }

        protected Positive(String str) {
            super(str, -1);
        }
    }

    public static class StubOk extends Positive {
        /* access modifiers changed from: protected */
        @Override // com.accessorydm.ui.dialog.model.buttonstrategy.AbstractButtonStrategy
        public void doOnClick() {
        }

        public StubOk() {
            super(getString(R.string.STR_BTN_OK));
        }
    }

    public static class StubCancel extends Negative {
        /* access modifiers changed from: protected */
        @Override // com.accessorydm.ui.dialog.model.buttonstrategy.AbstractButtonStrategy
        public void doOnClick() {
        }

        public StubCancel() {
            super(getString(R.string.STR_BTN_CANCEL));
        }
    }
}
