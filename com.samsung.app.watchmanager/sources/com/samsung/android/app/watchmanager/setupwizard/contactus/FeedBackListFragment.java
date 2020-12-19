package com.samsung.android.app.watchmanager.setupwizard.contactus;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.samsung.android.app.twatchmanager.util.HostManagerUtilsNetwork;
import com.samsung.android.app.watchmanager.R;
import com.samsung.android.app.watchmanager.setupwizard.contactus.connection.AsyncHttpRequest;
import com.samsung.android.app.watchmanager.setupwizard.contactus.connection.JsonParser;
import com.samsung.android.app.watchmanager.setupwizard.contactus.connection.RequestResponseClasses.FeedBackList;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FeedBackListFragment extends Fragment {
    String TAG = "FeedBackListFragment";
    Map<Integer, Long> feedbackIds = new HashMap();
    private View feedbackListView;
    private Activity mActivity;
    private View noFeedbacksView;
    View rootView;

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.contact_us_menu, menu);
        menu.getItem(0).setVisible(false);
        menu.getItem(1).setVisible(false);
        menu.getItem(2).setVisible(false);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.rootView = layoutInflater.inflate(R.layout.feedback_list_fragment, (ViewGroup) null);
        setHasOptionsMenu(true);
        return this.rootView;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != R.id.compose) {
            return super.onOptionsItemSelected(menuItem);
        }
        this.mActivity.onBackPressed();
        return true;
    }

    public void onStart() {
        super.onStart();
        String csc = HostManagerUtilsNetwork.getCSC();
        String mcc = ContactUsActivity.getMCC(this.mActivity);
        this.noFeedbacksView = this.rootView.findViewById(R.id.no_feedbacks);
        this.feedbackListView = this.rootView.findViewById(R.id.feedback_listview);
        final AnonymousClass1 r2 = new ProgressDialog(this.mActivity) {
            /* class com.samsung.android.app.watchmanager.setupwizard.contactus.FeedBackListFragment.AnonymousClass1 */

            public void onBackPressed() {
                super.onBackPressed();
                FeedBackListFragment.this.mActivity.onBackPressed();
            }
        };
        r2.setProgressStyle(0);
        r2.setCancelable(true);
        r2.setMessage("Loading...");
        r2.show();
        new AsyncHttpRequest(new AsyncHttpRequest.AsyncResponse() {
            /* class com.samsung.android.app.watchmanager.setupwizard.contactus.FeedBackListFragment.AnonymousClass2 */

            @Override // com.samsung.android.app.watchmanager.setupwizard.contactus.connection.AsyncHttpRequest.AsyncResponse
            public void processFinish(String str) {
                r2.dismiss();
                if (!str.contains("errorCode") || !str.contains("errorMessage")) {
                    FeedBackList[] createObject = new JsonParser().createObject(str);
                    if (createObject == null || createObject.length <= 0) {
                        FeedBackListFragment.this.noFeedbacksView.setVisibility(0);
                        FeedBackListFragment.this.feedbackListView.setVisibility(8);
                        return;
                    }
                    FeedBackListFragment.this.noFeedbacksView.setVisibility(8);
                    FeedBackListFragment.this.feedbackListView.setVisibility(0);
                    String str2 = FeedBackListFragment.this.TAG;
                    Log.d(str2, "sizeeee:" + createObject.length);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yyyy");
                    ArrayList arrayList = new ArrayList();
                    int i = 0;
                    while (i < createObject.length) {
                        String title = createObject[i].getQuestion().getTitle();
                        if (title == null) {
                            title = createObject[i].getQuestion().getBody();
                        }
                        String str3 = createObject[i].getType().getMainType().equals("ERROR") ? "Error Reports" : "Ask Questions";
                        boolean z = createObject[i].getAnswerList() != null && createObject[i].getAnswerList().length > 0;
                        String str4 = FeedBackListFragment.this.TAG;
                        Log.d(str4, "answered:" + z + "  :" + Arrays.toString(createObject[i].getAnswerList()));
                        boolean isValidation = createObject[i].isValidation();
                        arrayList.add(new FeedBackListItem(str3, title, createObject[i].getType().getSubType() + "/" + createObject[i].getType().getCategoryType() + ".", simpleDateFormat.format(new Date(createObject[i].getWriteDateTime())), z, isValidation));
                        FeedBackListFragment.this.feedbackIds.put(Integer.valueOf(i), Long.valueOf(createObject[i].getFeedbackId()));
                        i++;
                        arrayList = arrayList;
                    }
                    FeedBackListAdapter feedBackListAdapter = new FeedBackListAdapter(FeedBackListFragment.this.mActivity, R.layout.feedbacklist_listitem, arrayList);
                    ListView listView = (ListView) FeedBackListFragment.this.rootView.findViewById(R.id.feedback_listview);
                    if (listView != null) {
                        listView.setAdapter((ListAdapter) feedBackListAdapter);
                        listView.setItemsCanFocus(true);
                        listView.setClickable(true);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            /* class com.samsung.android.app.watchmanager.setupwizard.contactus.FeedBackListFragment.AnonymousClass2.AnonymousClass1 */

                            @Override // android.widget.AdapterView.OnItemClickListener
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                                String str = FeedBackListFragment.this.TAG;
                                Log.d(str, "position:" + i + " map size:" + FeedBackListFragment.this.feedbackIds.size());
                                ((ContactUsActivity) FeedBackListFragment.this.mActivity).loadFeedBackDetailFragment(FeedBackListFragment.this.feedbackIds.get(Integer.valueOf(i)).longValue());
                            }
                        });
                        return;
                    }
                    return;
                }
                Toast.makeText(FeedBackListFragment.this.mActivity, FeedBackListFragment.this.mActivity.getString(R.string.something_went_wrong), 0).show();
            }
        }).execute(3, csc, mcc);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.mActivity = getActivity();
        this.mActivity.getActionBar().setTitle(this.mActivity.getString(R.string.feedback).toUpperCase());
    }
}
