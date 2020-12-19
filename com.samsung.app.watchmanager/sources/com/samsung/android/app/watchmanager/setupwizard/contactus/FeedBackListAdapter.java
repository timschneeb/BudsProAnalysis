package com.samsung.android.app.watchmanager.setupwizard.contactus;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.samsung.android.app.watchmanager.R;
import java.util.ArrayList;

public class FeedBackListAdapter extends ArrayAdapter<FeedBackListItem> {
    private String TAG = "FeedBackListAdapter";
    Context context;
    ArrayList<FeedBackListItem> feedBacksList;

    private static class ViewHolder {
        private TextView category;
        private TextView date;
        private TextView feedbacktype;
        private TextView questionTitle;

        private ViewHolder() {
        }
    }

    public FeedBackListAdapter(Context context2, int i, ArrayList<FeedBackListItem> arrayList) {
        super(context2, i, arrayList);
        this.feedBacksList = arrayList;
        this.context = context2;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = ((Activity) this.context).getLayoutInflater().inflate(R.layout.feedbacklist_listitem, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.questionTitle = (TextView) view.findViewById(R.id.question_text);
            viewHolder.category = (TextView) view.findViewById(R.id.category_text);
            viewHolder.feedbacktype = (TextView) view.findViewById(R.id.type_text);
            viewHolder.date = (TextView) view.findViewById(R.id.date_text);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        FeedBackListItem feedBackListItem = this.feedBacksList.get(i);
        viewHolder.questionTitle.setText(feedBackListItem.title);
        viewHolder.category.setText(feedBackListItem.category);
        viewHolder.feedbacktype.setText(feedBackListItem.feedbacktype);
        viewHolder.date.setText(feedBackListItem.date);
        String str = this.TAG;
        Log.d(str, "postition:" + i + "  answered:" + feedBackListItem.answered);
        View findViewById = view.findViewById(R.id.answered_text);
        if (feedBackListItem.answered) {
            findViewById.setVisibility(0);
        } else {
            findViewById.setVisibility(8);
        }
        View findViewById2 = view.findViewById(R.id.badgenew);
        if (!feedBackListItem.answered || !feedBackListItem.validation) {
            findViewById2.setVisibility(4);
        } else {
            findViewById2.setVisibility(0);
        }
        return view;
    }

    public boolean isEnabled(int i) {
        return true;
    }
}
