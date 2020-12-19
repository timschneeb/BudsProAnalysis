package com.samsung.android.app.watchmanager.setupwizard.contactus;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.samsung.android.app.twatchmanager.util.HostManagerUtilsNetwork;
import java.util.ArrayList;

public class FeedBackDetailAdapter extends ArrayAdapter<FeedBackDetailItem> {
    private static final String TAG = "FeedBackDetailAdapter";
    String CSC = HostManagerUtilsNetwork.getCSC();
    String MCC;
    Context context;
    ArrayList<FeedBackDetailItem> feedBacksList;

    private static class ViewHolder {
        private TextView answerText;
        private TextView categotyText;
        private TextView dateText;
        private TextView questionText;

        private ViewHolder() {
        }
    }

    public FeedBackDetailAdapter(Context context2, int i, ArrayList<FeedBackDetailItem> arrayList) {
        super(context2, i, arrayList);
        this.feedBacksList = arrayList;
        this.context = context2;
        this.MCC = ContactUsActivity.getMCC(context2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r9v0 */
    /* JADX WARN: Type inference failed for: r13v1, types: [android.view.LayoutInflater] */
    /* JADX WARN: Type inference failed for: r9v1, types: [android.view.ViewGroup] */
    /* JADX WARN: Type inference failed for: r9v3 */
    /* JADX WARNING: Unknown variable types count: 2 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.View getView(int r21, android.view.View r22, android.view.ViewGroup r23) {
        /*
        // Method dump skipped, instructions count: 344
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.watchmanager.setupwizard.contactus.FeedBackDetailAdapter.getView(int, android.view.View, android.view.ViewGroup):android.view.View");
    }

    public boolean isEnabled(int i) {
        return true;
    }
}
