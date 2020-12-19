package com.samsung.android.app.watchmanager.setupwizard.contactus;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.samsung.android.app.twatchmanager.util.HostManagerUtilsNetwork;
import com.samsung.android.app.watchmanager.R;
import com.samsung.android.app.watchmanager.setupwizard.contactus.connection.AsyncHttpRequest;
import com.samsung.android.app.watchmanager.setupwizard.contactus.connection.GlobalConst;
import com.samsung.android.app.watchmanager.setupwizard.contactus.connection.JsonParser;
import com.samsung.android.app.watchmanager.setupwizard.contactus.connection.RequestResponseClasses.AnswerList;
import com.samsung.android.app.watchmanager.setupwizard.contactus.connection.RequestResponseClasses.FeedBackList;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

public class FeedBackDetailFragment extends Fragment {
    String CSC;
    String MCC;
    String TAG = "FeedBackDetailFragment";
    private long feedBackId;
    private Activity mActivity;
    private int rating;

    public void ShowDialog() {
        final Dialog dialog = new Dialog(this.mActivity);
        dialog.setContentView(R.layout.rating_dialog);
        dialog.setTitle(this.mActivity.getString(R.string.rate_response));
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.rating_popup_background);
        final TextView textView = (TextView) dialog.findViewById(R.id.countTextView);
        final ImageView[] imageViewArr = {(ImageView) dialog.findViewById(R.id.star1), (ImageView) dialog.findViewById(R.id.star2), (ImageView) dialog.findViewById(R.id.star3), (ImageView) dialog.findViewById(R.id.star4), (ImageView) dialog.findViewById(R.id.star5)};
        final TextView textView2 = (TextView) dialog.findViewById(R.id.somehelp);
        this.rating = 0;
        AnonymousClass5 r6 = new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.contactus.FeedBackDetailFragment.AnonymousClass5 */

            public void onClick(View view) {
                TextView textView;
                int i;
                Activity activity;
                FeedBackDetailFragment.this.rating = 5;
                textView2.setText(FeedBackDetailFragment.this.mActivity.getString(R.string.rating5));
                switch (view.getId()) {
                    case R.id.star1:
                        FeedBackDetailFragment.this.rating = 1;
                        textView = textView2;
                        activity = FeedBackDetailFragment.this.mActivity;
                        i = R.string.rating1;
                        textView.setText(activity.getString(i));
                        break;
                    case R.id.star2:
                        FeedBackDetailFragment.this.rating = 2;
                        textView = textView2;
                        activity = FeedBackDetailFragment.this.mActivity;
                        i = R.string.rating2;
                        textView.setText(activity.getString(i));
                        break;
                    case R.id.star3:
                        FeedBackDetailFragment.this.rating = 3;
                        textView = textView2;
                        activity = FeedBackDetailFragment.this.mActivity;
                        i = R.string.rating3;
                        textView.setText(activity.getString(i));
                        break;
                    case R.id.star4:
                        FeedBackDetailFragment.this.rating = 4;
                        textView = textView2;
                        activity = FeedBackDetailFragment.this.mActivity;
                        i = R.string.rating4;
                        textView.setText(activity.getString(i));
                        break;
                }
                for (int i2 = 0; i2 < FeedBackDetailFragment.this.rating; i2++) {
                    imageViewArr[i2].setImageResource(R.drawable.assessments_favorite_on);
                }
                for (int i3 = FeedBackDetailFragment.this.rating; i3 < 5; i3++) {
                    imageViewArr[i3].setImageResource(R.drawable.assessments_favorite_off);
                }
            }
        };
        for (int i = 0; i < 5; i++) {
            imageViewArr[i].setOnClickListener(r6);
        }
        ((TextView) dialog.findViewById(R.id.cancel_btn)).setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.contactus.FeedBackDetailFragment.AnonymousClass6 */

            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        final EditText editText = (EditText) dialog.findViewById(R.id.FeedbackEditText);
        editText.addTextChangedListener(new TextWatcher() {
            /* class com.samsung.android.app.watchmanager.setupwizard.contactus.FeedBackDetailFragment.AnonymousClass7 */

            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                TextView textView = textView;
                textView.setText("(" + String.valueOf(charSequence.length()) + "/250)");
            }
        });
        ((TextView) dialog.findViewById(R.id.ok_btn)).setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.contactus.FeedBackDetailFragment.AnonymousClass8 */

            public void onClick(View view) {
                String str;
                String obj = editText.getText().toString();
                if (!obj.isEmpty()) {
                    str = "{\"rating\":" + FeedBackDetailFragment.this.rating + ",\"ratingReason\":\"" + obj + "\"}";
                } else {
                    str = "{\"rating\":" + FeedBackDetailFragment.this.rating + "}";
                }
                Log.d(FeedBackDetailFragment.this.TAG, "json:::" + str);
                try {
                    AsyncHttpRequest asyncHttpRequest = new AsyncHttpRequest(null);
                    Object[] objArr = {8, Integer.valueOf(FeedBackDetailFragment.this.rating), Long.valueOf(FeedBackDetailFragment.this.feedBackId), FeedBackDetailFragment.this.CSC, FeedBackDetailFragment.this.MCC, str};
                    Log.d(FeedBackDetailFragment.this.TAG, "REsp:" + ((String) asyncHttpRequest.execute(objArr).get()));
                    if (str.equals("OK")) {
                        dialog.dismiss();
                    } else {
                        Toast.makeText(FeedBackDetailFragment.this.mActivity, FeedBackDetailFragment.this.mActivity.getString(R.string.error_while_sending), 0).show();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e2) {
                    e2.printStackTrace();
                }
            }
        });
        dialog.show();
    }

    public String getFormattedDate(long j) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(j);
        Calendar instance2 = Calendar.getInstance();
        return DateFormat.format(instance2.get(5) == instance.get(5) ? "h:mm aa" : instance2.get(1) == instance.get(1) ? "dd/MM" : "yyyy", instance).toString();
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.contact_us_menu, menu);
        menu.getItem(0).setVisible(false);
        menu.getItem(1).setVisible(false);
        menu.getItem(3).setVisible(false);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_feedback_detail, (ViewGroup) null);
        setHasOptionsMenu(true);
        return inflate;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != R.id.delete) {
            return super.onOptionsItemSelected(menuItem);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mActivity);
        builder.setMessage("This Feedback will be deleted.You will not receive a response from Samsung about it.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.contactus.FeedBackDetailFragment.AnonymousClass1 */

            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    String str = (String) new AsyncHttpRequest(null).execute(4, FeedBackDetailFragment.this.CSC, FeedBackDetailFragment.this.MCC, Long.valueOf(FeedBackDetailFragment.this.feedBackId)).get();
                    String str2 = FeedBackDetailFragment.this.TAG;
                    Log.d(str2, "REsp:" + str);
                    if (str.equals("OK")) {
                        FeedBackDetailFragment.this.mActivity.onBackPressed();
                    } else {
                        Log.d(FeedBackDetailFragment.this.TAG, "Error occured while deleting");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e2) {
                    e2.printStackTrace();
                }
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.contactus.FeedBackDetailFragment.AnonymousClass2 */

            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.create().show();
        return true;
    }

    public void onStart() {
        ActionBar actionBar;
        String upperCase;
        AnswerList answerList;
        super.onStart();
        this.feedBackId = getArguments().getLong(AskandErrorReportFragment.FEEDBACKID);
        String csc = HostManagerUtilsNetwork.getCSC();
        String mcc = ContactUsActivity.getMCC(this.mActivity);
        try {
            AnswerList answerList2 = null;
            String str = (String) new AsyncHttpRequest(null).execute(5, csc, mcc, Long.valueOf(this.feedBackId)).get();
            String str2 = this.TAG;
            Log.d(str2, "josm:" + str);
            final FeedBackList[] createObject = new JsonParser().createObject(str);
            String str3 = this.TAG;
            Log.d(str3, "sizeeee:" + createObject.length);
            if (createObject[0].getType().getMainType().equals("ERROR")) {
                actionBar = this.mActivity.getActionBar();
                upperCase = this.mActivity.getString(R.string.contact_us_error).toUpperCase();
            } else {
                actionBar = this.mActivity.getActionBar();
                upperCase = this.mActivity.getString(R.string.contact_us_ask).toUpperCase();
            }
            actionBar.setTitle(upperCase);
            new SimpleDateFormat("dd/MM/yyyy");
            ArrayList arrayList = new ArrayList();
            int i = 0;
            boolean z = false;
            while (i < createObject.length) {
                String title = createObject[i].getQuestion().getTitle();
                long feedbackId = createObject[i].getFeedbackId();
                if (title == null) {
                    title = createObject[i].getQuestion().getBody();
                }
                AnswerList[] answerList3 = createObject[i].getAnswerList();
                if (answerList3 == null || answerList3.length <= 0) {
                    answerList = answerList2;
                } else {
                    if (i == createObject.length - 1 && createObject[i].getStatus().equals("CLOSED")) {
                        z = true;
                    }
                    answerList = answerList3[0];
                }
                arrayList.add(new FeedBackDetailItem("[" + createObject[i].getType().getSubType() + "/" + createObject[i].getType().getCategoryType() + "]", getFormattedDate(createObject[i].getWriteDateTime()), title, answerList, feedbackId));
                if (answerList3 != null && answerList3.length > 1) {
                    for (int i2 = 1; i2 < answerList3.length; i2++) {
                        arrayList.add(new FeedBackDetailItem(null, null, null, answerList3[i2], feedbackId));
                    }
                }
                i++;
                z = z;
                answerList2 = null;
            }
            FeedBackDetailAdapter feedBackDetailAdapter = new FeedBackDetailAdapter(this.mActivity, R.layout.listitem_feedback_detail, arrayList);
            ListView listView = (ListView) this.mActivity.findViewById(R.id.feedbackdetail_listview);
            listView.setClickable(false);
            ViewGroup viewGroup = (ViewGroup) this.mActivity.getLayoutInflater().inflate(R.layout.feedback_detail_footer, (ViewGroup) listView, false);
            if (!z || createObject.length >= 10) {
                ((LinearLayout) viewGroup.findViewById(R.id.bottom_button_view)).setVisibility(8);
            } else {
                ((TextView) viewGroup.findViewById(R.id.feedback_footer_textview)).setText(this.mActivity.getString(R.string.ask_again_for_additional_questions));
            }
            ((Button) viewGroup.findViewById(R.id.ask_again_button)).setOnClickListener(new View.OnClickListener() {
                /* class com.samsung.android.app.watchmanager.setupwizard.contactus.FeedBackDetailFragment.AnonymousClass3 */

                public void onClick(View view) {
                    if (!createObject[0].getType().getMainType().equals("ERROR")) {
                        FeedBackList[] feedBackListArr = createObject;
                        ((ContactUsActivity) FeedBackDetailFragment.this.mActivity).loadAskQuestions(feedBackListArr[feedBackListArr.length - 1].getFeedbackId());
                    } else if (!FeedBackDetailFragment.this.mActivity.getSharedPreferences(GlobalConst.PREF_SM, 0).getBoolean("collect_system_logs_always", false)) {
                        FeedBackList[] feedBackListArr2 = createObject;
                        ((ContactUsActivity) FeedBackDetailFragment.this.mActivity).loadErrorReport(feedBackListArr2[feedBackListArr2.length - 1].getFeedbackId(), false);
                    } else {
                        FeedBackList[] feedBackListArr3 = createObject;
                        ((ContactUsActivity) FeedBackDetailFragment.this.mActivity).loadErrorReport(feedBackListArr3[feedBackListArr3.length - 1].getFeedbackId(), true);
                    }
                }
            });
            ((Button) viewGroup.findViewById(R.id.rate_response_button)).setOnClickListener(new View.OnClickListener() {
                /* class com.samsung.android.app.watchmanager.setupwizard.contactus.FeedBackDetailFragment.AnonymousClass4 */

                public void onClick(View view) {
                    FeedBackDetailFragment.this.ShowDialog();
                }
            });
            listView.addFooterView(viewGroup);
            listView.setAdapter((ListAdapter) feedBackDetailAdapter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e2) {
            e2.printStackTrace();
        }
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.mActivity = getActivity();
        this.CSC = HostManagerUtilsNetwork.getCSC();
        this.MCC = ContactUsActivity.getMCC(this.mActivity);
        this.mActivity.getActionBar().setTitle(this.mActivity.getString(R.string.feedback).toUpperCase());
    }
}
