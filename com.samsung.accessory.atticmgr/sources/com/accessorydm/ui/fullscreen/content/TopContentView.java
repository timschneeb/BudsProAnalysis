package com.accessorydm.ui.fullscreen.content;

import android.app.Activity;
import android.view.ViewStub;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.sec.android.fotaprovider.R;

public interface TopContentView {

    public static abstract class BaseTopContentView implements TopContentView {
        Activity parentActivity;
        ViewStub viewStub;

        BaseTopContentView(Activity activity, ViewStub viewStub2, int i) {
            this.parentActivity = activity;
            this.viewStub = viewStub2;
            viewStub2.setLayoutResource(i);
            viewStub2.inflate();
        }
    }

    public static class Guide extends BaseTopContentView {
        private TextView tvText;
        private TextView tvTitle;

        public Guide(Activity activity, ViewStub viewStub) {
            super(activity, viewStub, R.layout.top_content_guide);
            this.tvTitle = (TextView) activity.findViewById(R.id.textview_top_content_guide_title);
            this.tvText = (TextView) activity.findViewById(R.id.textview_top_content_guide_text);
        }

        public void setTitle(String str) {
            this.tvTitle.setText(str);
        }

        public void setText(String str) {
            this.tvText.setText(str);
        }

        public void hideText() {
            this.tvText.setVisibility(8);
        }
    }

    public static class Progress extends BaseTopContentView {
        private ProgressBar progressBar;
        private TextView tvSizeText;
        private TextView tvTitle;

        public Progress(Activity activity, ViewStub viewStub) {
            super(activity, viewStub, R.layout.top_content_progress);
            this.tvTitle = (TextView) activity.findViewById(R.id.textview_top_content_progress_title);
            this.progressBar = (ProgressBar) activity.findViewById(R.id.progressbar_top_content_horizontal_progress);
            this.tvSizeText = (TextView) activity.findViewById(R.id.textview_top_content_progress_size);
            this.progressBar.setMax(100);
        }

        public void setTitle(String str) {
            this.tvTitle.setText(str);
        }

        public void setIndeterminateProgressbar(boolean z) {
            this.progressBar.setIndeterminate(z);
        }

        public void setProgressbarProgress(int i) {
            this.progressBar.setProgress(i);
        }

        public void setSizeText(String str) {
            this.tvSizeText.setText(str);
        }
    }
}
