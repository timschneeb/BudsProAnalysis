package com.accessorydm.ui.fullscreen.content;

import android.app.Activity;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import com.accessorydm.ui.fullscreen.content.BottomContentView;
import com.sec.android.fotaprovider.R;

public interface BottomContentView {

    public static abstract class BaseBottomContentView implements BottomContentView {
        Activity parentActivity;
        ViewStub viewStub;

        BaseBottomContentView(Activity activity, ViewStub viewStub2, int i) {
            this.parentActivity = activity;
            this.viewStub = viewStub2;
            viewStub2.setLayoutResource(i);
            viewStub2.inflate();
        }
    }

    public static class TwoButtons extends BaseBottomContentView {
        Button buttonFirst;
        Button buttonSecond;

        public interface BottomButtonAction {
            void firstButtonAction();

            void secondButtonAction();
        }

        public TwoButtons(Activity activity, ViewStub viewStub) {
            super(activity, viewStub, R.layout.bottom_content_buttons);
            this.buttonFirst = (Button) activity.findViewById(R.id.button_bottom_content_first);
            this.buttonSecond = (Button) activity.findViewById(R.id.button_bottom_content_second);
        }

        public void setBottomButtonClickListeners(BottomButtonAction bottomButtonAction) {
            this.buttonFirst.setOnClickListener(new View.OnClickListener() {
                /* class com.accessorydm.ui.fullscreen.content.$$Lambda$BottomContentView$TwoButtons$ro2OcmsFzNMg8glLremU5HCae2c */

                public final void onClick(View view) {
                    BottomContentView.TwoButtons.BottomButtonAction.this.firstButtonAction();
                }
            });
            this.buttonSecond.setOnClickListener(new View.OnClickListener() {
                /* class com.accessorydm.ui.fullscreen.content.$$Lambda$BottomContentView$TwoButtons$5_lJtgI_aA5Y8_i8n9V3iRF1Ef8 */

                public final void onClick(View view) {
                    BottomContentView.TwoButtons.BottomButtonAction.this.secondButtonAction();
                }
            });
        }

        public void hideFirstButton() {
            this.buttonFirst.setVisibility(8);
        }

        public void setFirstButtonText(String str) {
            this.buttonFirst.setText(str);
        }

        public void setSecondButtonText(String str) {
            this.buttonSecond.setText(str);
        }

        public void disableButtons() {
            this.buttonFirst.setEnabled(false);
            this.buttonSecond.setEnabled(false);
        }
    }
}
