package androidx.appcompat.widget;

import android.app.PendingIntent;
import android.app.SearchableInfo;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.customview.view.AbsSavedState;
import b.a.f;
import b.a.g;
import b.a.h;
import b.a.j;
import b.e.g.t;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

public class SearchView extends LinearLayoutCompat implements b.a.d.c {
    static final a p = new a();
    private Rect A;
    private Rect B;
    private int[] C;
    private int[] D;
    private final ImageView E;
    private final Drawable F;
    private final int G;
    private final int H;
    private final Intent I;
    private final Intent J;
    private final CharSequence K;
    private c L;
    private b M;
    View.OnFocusChangeListener N;
    private d O;
    private View.OnClickListener P;
    private boolean Q;
    private boolean R;
    b.f.a.a S;
    private boolean T;
    private CharSequence U;
    private boolean V;
    private boolean W;
    private int aa;
    private boolean ba;
    private CharSequence ca;
    private CharSequence da;
    private boolean ea;
    private int fa;
    SearchableInfo ga;
    private Bundle ha;
    private final Runnable ia;
    private Runnable ja;
    private final WeakHashMap<String, Drawable.ConstantState> ka;
    private final View.OnClickListener la;
    View.OnKeyListener ma;
    private final TextView.OnEditorActionListener na;
    private final AdapterView.OnItemClickListener oa;
    private final AdapterView.OnItemSelectedListener pa;
    final SearchAutoComplete q;
    private TextWatcher qa;
    private final View r;
    private final View s;
    private final View t;
    final ImageView u;
    final ImageView v;
    final ImageView w;
    final ImageView x;
    private final View y;
    private e z;

    static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Z();

        /* renamed from: c  reason: collision with root package name */
        boolean f408c;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.f408c = ((Boolean) parcel.readValue(null)).booleanValue();
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            return "SearchView.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " isIconified=" + this.f408c + "}";
        }

        @Override // androidx.customview.view.AbsSavedState
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeValue(Boolean.valueOf(this.f408c));
        }
    }

    public static class SearchAutoComplete extends AppCompatAutoCompleteTextView {

        /* renamed from: d  reason: collision with root package name */
        private int f409d;
        private SearchView e;
        private boolean f;
        final Runnable g;

        public SearchAutoComplete(Context context) {
            this(context, null);
        }

        public SearchAutoComplete(Context context, AttributeSet attributeSet) {
            this(context, attributeSet, b.a.a.autoCompleteTextViewStyle);
        }

        public SearchAutoComplete(Context context, AttributeSet attributeSet, int i) {
            super(context, attributeSet, i);
            this.g = new aa(this);
            this.f409d = getThreshold();
        }

        private int getSearchViewTextMinWidthDp() {
            Configuration configuration = getResources().getConfiguration();
            int i = configuration.screenWidthDp;
            int i2 = configuration.screenHeightDp;
            if (i >= 960 && i2 >= 720 && configuration.orientation == 2) {
                return 256;
            }
            if (i < 600) {
                return (i < 640 || i2 < 480) ? 160 : 192;
            }
            return 192;
        }

        /* access modifiers changed from: package-private */
        public boolean a() {
            return TextUtils.getTrimmedLength(getText()) == 0;
        }

        /* access modifiers changed from: package-private */
        public void b() {
            if (this.f) {
                ((InputMethodManager) getContext().getSystemService("input_method")).showSoftInput(this, 0);
                this.f = false;
            }
        }

        public boolean enoughToFilter() {
            return this.f409d <= 0 || super.enoughToFilter();
        }

        @Override // androidx.appcompat.widget.AppCompatAutoCompleteTextView
        public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
            InputConnection onCreateInputConnection = super.onCreateInputConnection(editorInfo);
            if (this.f) {
                removeCallbacks(this.g);
                post(this.g);
            }
            return onCreateInputConnection;
        }

        /* access modifiers changed from: protected */
        public void onFinishInflate() {
            super.onFinishInflate();
            setMinWidth((int) TypedValue.applyDimension(1, (float) getSearchViewTextMinWidthDp(), getResources().getDisplayMetrics()));
        }

        /* access modifiers changed from: protected */
        public void onFocusChanged(boolean z, int i, Rect rect) {
            super.onFocusChanged(z, i, rect);
            this.e.g();
        }

        public boolean onKeyPreIme(int i, KeyEvent keyEvent) {
            if (i == 4) {
                if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                    KeyEvent.DispatcherState keyDispatcherState = getKeyDispatcherState();
                    if (keyDispatcherState != null) {
                        keyDispatcherState.startTracking(keyEvent, this);
                    }
                    return true;
                } else if (keyEvent.getAction() == 1) {
                    KeyEvent.DispatcherState keyDispatcherState2 = getKeyDispatcherState();
                    if (keyDispatcherState2 != null) {
                        keyDispatcherState2.handleUpEvent(keyEvent);
                    }
                    if (keyEvent.isTracking() && !keyEvent.isCanceled()) {
                        this.e.clearFocus();
                        setImeVisibility(false);
                        return true;
                    }
                }
            }
            return super.onKeyPreIme(i, keyEvent);
        }

        public void onWindowFocusChanged(boolean z) {
            super.onWindowFocusChanged(z);
            if (z && this.e.hasFocus() && getVisibility() == 0) {
                this.f = true;
                if (SearchView.a(getContext())) {
                    SearchView.p.a(this, true);
                }
            }
        }

        public void performCompletion() {
        }

        /* access modifiers changed from: protected */
        public void replaceText(CharSequence charSequence) {
        }

        /* access modifiers changed from: package-private */
        public void setImeVisibility(boolean z) {
            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService("input_method");
            if (!z) {
                this.f = false;
                removeCallbacks(this.g);
                inputMethodManager.hideSoftInputFromWindow(getWindowToken(), 0);
            } else if (inputMethodManager.isActive(this)) {
                this.f = false;
                removeCallbacks(this.g);
                inputMethodManager.showSoftInput(this, 0);
            } else {
                this.f = true;
            }
        }

        /* access modifiers changed from: package-private */
        public void setSearchView(SearchView searchView) {
            this.e = searchView;
        }

        public void setThreshold(int i) {
            super.setThreshold(i);
            this.f409d = i;
        }
    }

    /* access modifiers changed from: private */
    public static class a {

        /* renamed from: a  reason: collision with root package name */
        private Method f410a;

        /* renamed from: b  reason: collision with root package name */
        private Method f411b;

        /* renamed from: c  reason: collision with root package name */
        private Method f412c;

        a() {
            try {
                this.f410a = AutoCompleteTextView.class.getDeclaredMethod("doBeforeTextChanged", new Class[0]);
                this.f410a.setAccessible(true);
            } catch (NoSuchMethodException unused) {
            }
            try {
                this.f411b = AutoCompleteTextView.class.getDeclaredMethod("doAfterTextChanged", new Class[0]);
                this.f411b.setAccessible(true);
            } catch (NoSuchMethodException unused2) {
            }
            try {
                this.f412c = AutoCompleteTextView.class.getMethod("ensureImeVisible", Boolean.TYPE);
                this.f412c.setAccessible(true);
            } catch (NoSuchMethodException unused3) {
            }
        }

        /* access modifiers changed from: package-private */
        public void a(AutoCompleteTextView autoCompleteTextView) {
            Method method = this.f411b;
            if (method != null) {
                try {
                    method.invoke(autoCompleteTextView, new Object[0]);
                } catch (Exception unused) {
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void a(AutoCompleteTextView autoCompleteTextView, boolean z) {
            Method method = this.f412c;
            if (method != null) {
                try {
                    method.invoke(autoCompleteTextView, Boolean.valueOf(z));
                } catch (Exception unused) {
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void b(AutoCompleteTextView autoCompleteTextView) {
            Method method = this.f410a;
            if (method != null) {
                try {
                    method.invoke(autoCompleteTextView, new Object[0]);
                } catch (Exception unused) {
                }
            }
        }
    }

    public interface b {
        boolean onClose();
    }

    public interface c {
        boolean onQueryTextChange(String str);

        boolean onQueryTextSubmit(String str);
    }

    public interface d {
        boolean onSuggestionClick(int i);

        boolean onSuggestionSelect(int i);
    }

    private static class e extends TouchDelegate {

        /* renamed from: a  reason: collision with root package name */
        private final View f413a;

        /* renamed from: b  reason: collision with root package name */
        private final Rect f414b = new Rect();

        /* renamed from: c  reason: collision with root package name */
        private final Rect f415c = new Rect();

        /* renamed from: d  reason: collision with root package name */
        private final Rect f416d = new Rect();
        private final int e;
        private boolean f;

        public e(Rect rect, Rect rect2, View view) {
            super(rect, view);
            this.e = ViewConfiguration.get(view.getContext()).getScaledTouchSlop();
            a(rect, rect2);
            this.f413a = view;
        }

        public void a(Rect rect, Rect rect2) {
            this.f414b.set(rect);
            this.f416d.set(rect);
            Rect rect3 = this.f416d;
            int i = this.e;
            rect3.inset(-i, -i);
            this.f415c.set(rect2);
        }

        /* JADX WARNING: Removed duplicated region for block: B:17:0x003d  */
        /* JADX WARNING: Removed duplicated region for block: B:24:? A[RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTouchEvent(android.view.MotionEvent r8) {
            /*
            // Method dump skipped, instructions count: 107
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.SearchView.e.onTouchEvent(android.view.MotionEvent):boolean");
        }
    }

    public SearchView(Context context) {
        this(context, null);
    }

    public SearchView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, b.a.a.searchViewStyle);
    }

    public SearchView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.A = new Rect();
        this.B = new Rect();
        this.C = new int[2];
        this.D = new int[2];
        this.ia = new P(this);
        this.ja = new Q(this);
        this.ka = new WeakHashMap<>();
        this.la = new U(this);
        this.ma = new V(this);
        this.na = new W(this);
        this.oa = new X(this);
        this.pa = new Y(this);
        this.qa = new O(this);
        ia a2 = ia.a(context, attributeSet, j.SearchView, i, 0);
        LayoutInflater.from(context).inflate(a2.g(j.SearchView_layout, g.abc_search_view), (ViewGroup) this, true);
        this.q = (SearchAutoComplete) findViewById(f.search_src_text);
        this.q.setSearchView(this);
        this.r = findViewById(f.search_edit_frame);
        this.s = findViewById(f.search_plate);
        this.t = findViewById(f.submit_area);
        this.u = (ImageView) findViewById(f.search_button);
        this.v = (ImageView) findViewById(f.search_go_btn);
        this.w = (ImageView) findViewById(f.search_close_btn);
        this.x = (ImageView) findViewById(f.search_voice_btn);
        this.E = (ImageView) findViewById(f.search_mag_icon);
        t.a(this.s, a2.b(j.SearchView_queryBackground));
        t.a(this.t, a2.b(j.SearchView_submitBackground));
        this.u.setImageDrawable(a2.b(j.SearchView_searchIcon));
        this.v.setImageDrawable(a2.b(j.SearchView_goIcon));
        this.w.setImageDrawable(a2.b(j.SearchView_closeIcon));
        this.x.setImageDrawable(a2.b(j.SearchView_voiceIcon));
        this.E.setImageDrawable(a2.b(j.SearchView_searchIcon));
        this.F = a2.b(j.SearchView_searchHintIcon);
        qa.a(this.u, getResources().getString(h.abc_searchview_description_search));
        this.G = a2.g(j.SearchView_suggestionRowLayout, g.abc_search_dropdown_item_icons_2line);
        this.H = a2.g(j.SearchView_commitIcon, 0);
        this.u.setOnClickListener(this.la);
        this.w.setOnClickListener(this.la);
        this.v.setOnClickListener(this.la);
        this.x.setOnClickListener(this.la);
        this.q.setOnClickListener(this.la);
        this.q.addTextChangedListener(this.qa);
        this.q.setOnEditorActionListener(this.na);
        this.q.setOnItemClickListener(this.oa);
        this.q.setOnItemSelectedListener(this.pa);
        this.q.setOnKeyListener(this.ma);
        this.q.setOnFocusChangeListener(new S(this));
        setIconifiedByDefault(a2.a(j.SearchView_iconifiedByDefault, true));
        int c2 = a2.c(j.SearchView_android_maxWidth, -1);
        if (c2 != -1) {
            setMaxWidth(c2);
        }
        this.K = a2.e(j.SearchView_defaultQueryHint);
        this.U = a2.e(j.SearchView_queryHint);
        int d2 = a2.d(j.SearchView_android_imeOptions, -1);
        if (d2 != -1) {
            setImeOptions(d2);
        }
        int d3 = a2.d(j.SearchView_android_inputType, -1);
        if (d3 != -1) {
            setInputType(d3);
        }
        setFocusable(a2.a(j.SearchView_android_focusable, true));
        a2.a();
        this.I = new Intent("android.speech.action.WEB_SEARCH");
        this.I.addFlags(268435456);
        this.I.putExtra("android.speech.extra.LANGUAGE_MODEL", "web_search");
        this.J = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        this.J.addFlags(268435456);
        this.y = findViewById(this.q.getDropDownAnchor());
        View view = this.y;
        if (view != null) {
            view.addOnLayoutChangeListener(new T(this));
        }
        b(this.Q);
        o();
    }

    private Intent a(Intent intent, SearchableInfo searchableInfo) {
        ComponentName searchActivity = searchableInfo.getSearchActivity();
        Intent intent2 = new Intent("android.intent.action.SEARCH");
        intent2.setComponent(searchActivity);
        PendingIntent activity = PendingIntent.getActivity(getContext(), 0, intent2, 1073741824);
        Bundle bundle = new Bundle();
        Bundle bundle2 = this.ha;
        if (bundle2 != null) {
            bundle.putParcelable("app_data", bundle2);
        }
        Intent intent3 = new Intent(intent);
        int i = 1;
        Resources resources = getResources();
        String string = searchableInfo.getVoiceLanguageModeId() != 0 ? resources.getString(searchableInfo.getVoiceLanguageModeId()) : "free_form";
        String str = null;
        String string2 = searchableInfo.getVoicePromptTextId() != 0 ? resources.getString(searchableInfo.getVoicePromptTextId()) : null;
        String string3 = searchableInfo.getVoiceLanguageId() != 0 ? resources.getString(searchableInfo.getVoiceLanguageId()) : null;
        if (searchableInfo.getVoiceMaxResults() != 0) {
            i = searchableInfo.getVoiceMaxResults();
        }
        intent3.putExtra("android.speech.extra.LANGUAGE_MODEL", string);
        intent3.putExtra("android.speech.extra.PROMPT", string2);
        intent3.putExtra("android.speech.extra.LANGUAGE", string3);
        intent3.putExtra("android.speech.extra.MAX_RESULTS", i);
        if (searchActivity != null) {
            str = searchActivity.flattenToShortString();
        }
        intent3.putExtra("calling_package", str);
        intent3.putExtra("android.speech.extra.RESULTS_PENDINGINTENT", activity);
        intent3.putExtra("android.speech.extra.RESULTS_PENDINGINTENT_BUNDLE", bundle);
        return intent3;
    }

    private Intent a(Cursor cursor, int i, String str) {
        int i2;
        String a2;
        try {
            String a3 = ba.a(cursor, "suggest_intent_action");
            if (a3 == null) {
                a3 = this.ga.getSuggestIntentAction();
            }
            if (a3 == null) {
                a3 = "android.intent.action.SEARCH";
            }
            String a4 = ba.a(cursor, "suggest_intent_data");
            if (a4 == null) {
                a4 = this.ga.getSuggestIntentData();
            }
            if (!(a4 == null || (a2 = ba.a(cursor, "suggest_intent_data_id")) == null)) {
                a4 = a4 + "/" + Uri.encode(a2);
            }
            return a(a3, a4 == null ? null : Uri.parse(a4), ba.a(cursor, "suggest_intent_extra_data"), ba.a(cursor, "suggest_intent_query"), i, str);
        } catch (RuntimeException e2) {
            try {
                i2 = cursor.getPosition();
            } catch (RuntimeException unused) {
                i2 = -1;
            }
            Log.w("SearchView", "Search suggestions cursor at row " + i2 + " returned exception.", e2);
            return null;
        }
    }

    private Intent a(String str, Uri uri, String str2, String str3, int i, String str4) {
        Intent intent = new Intent(str);
        intent.addFlags(268435456);
        if (uri != null) {
            intent.setData(uri);
        }
        intent.putExtra("user_query", this.da);
        if (str3 != null) {
            intent.putExtra("query", str3);
        }
        if (str2 != null) {
            intent.putExtra("intent_extra_data_key", str2);
        }
        Bundle bundle = this.ha;
        if (bundle != null) {
            intent.putExtra("app_data", bundle);
        }
        if (i != 0) {
            intent.putExtra("action_key", i);
            intent.putExtra("action_msg", str4);
        }
        intent.setComponent(this.ga.getSearchActivity());
        return intent;
    }

    private void a(Intent intent) {
        if (intent != null) {
            try {
                getContext().startActivity(intent);
            } catch (RuntimeException e2) {
                Log.e("SearchView", "Failed launch activity: " + intent, e2);
            }
        }
    }

    private void a(View view, Rect rect) {
        view.getLocationInWindow(this.C);
        getLocationInWindow(this.D);
        int[] iArr = this.C;
        int i = iArr[1];
        int[] iArr2 = this.D;
        int i2 = i - iArr2[1];
        int i3 = iArr[0] - iArr2[0];
        rect.set(i3, i2, view.getWidth() + i3, view.getHeight() + i2);
    }

    private void a(boolean z2) {
        this.v.setVisibility((!this.T || !l() || !hasFocus() || (!z2 && this.ba)) ? 8 : 0);
    }

    static boolean a(Context context) {
        return context.getResources().getConfiguration().orientation == 2;
    }

    private Intent b(Intent intent, SearchableInfo searchableInfo) {
        Intent intent2 = new Intent(intent);
        ComponentName searchActivity = searchableInfo.getSearchActivity();
        intent2.putExtra("calling_package", searchActivity == null ? null : searchActivity.flattenToShortString());
        return intent2;
    }

    private void b(boolean z2) {
        this.R = z2;
        int i = 8;
        boolean z3 = false;
        int i2 = z2 ? 0 : 8;
        boolean z4 = !TextUtils.isEmpty(this.q.getText());
        this.u.setVisibility(i2);
        a(z4);
        this.r.setVisibility(z2 ? 8 : 0);
        if (this.E.getDrawable() != null && !this.Q) {
            i = 0;
        }
        this.E.setVisibility(i);
        n();
        if (!z4) {
            z3 = true;
        }
        c(z3);
        q();
    }

    private boolean b(int i, int i2, String str) {
        Cursor a2 = this.S.a();
        if (a2 == null || !a2.moveToPosition(i)) {
            return false;
        }
        a(a(a2, i2, str));
        return true;
    }

    private CharSequence c(CharSequence charSequence) {
        if (!this.Q || this.F == null) {
            return charSequence;
        }
        double textSize = (double) this.q.getTextSize();
        Double.isNaN(textSize);
        int i = (int) (textSize * 1.25d);
        this.F.setBounds(0, 0, i, i);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("   ");
        spannableStringBuilder.setSpan(new ImageSpan(this.F), 1, 2, 33);
        spannableStringBuilder.append(charSequence);
        return spannableStringBuilder;
    }

    private void c(boolean z2) {
        int i;
        if (!this.ba || c() || !z2) {
            i = 8;
        } else {
            i = 0;
            this.v.setVisibility(8);
        }
        this.x.setVisibility(i);
    }

    private void e(int i) {
        CharSequence convertToString;
        Editable text = this.q.getText();
        Cursor a2 = this.S.a();
        if (a2 != null) {
            if (!a2.moveToPosition(i) || (convertToString = this.S.convertToString(a2)) == null) {
                setQuery(text);
            } else {
                setQuery(convertToString);
            }
        }
    }

    private int getPreferredHeight() {
        return getContext().getResources().getDimensionPixelSize(b.a.d.abc_search_view_preferred_height);
    }

    private int getPreferredWidth() {
        return getContext().getResources().getDimensionPixelSize(b.a.d.abc_search_view_preferred_width);
    }

    private void j() {
        this.q.dismissDropDown();
    }

    private boolean k() {
        SearchableInfo searchableInfo = this.ga;
        if (searchableInfo == null || !searchableInfo.getVoiceSearchEnabled()) {
            return false;
        }
        Intent intent = null;
        if (this.ga.getVoiceSearchLaunchWebSearch()) {
            intent = this.I;
        } else if (this.ga.getVoiceSearchLaunchRecognizer()) {
            intent = this.J;
        }
        return (intent == null || getContext().getPackageManager().resolveActivity(intent, 65536) == null) ? false : true;
    }

    private boolean l() {
        return (this.T || this.ba) && !c();
    }

    private void m() {
        post(this.ia);
    }

    private void n() {
        boolean z2 = true;
        boolean z3 = !TextUtils.isEmpty(this.q.getText());
        int i = 0;
        if (!z3 && (!this.Q || this.ea)) {
            z2 = false;
        }
        ImageView imageView = this.w;
        if (!z2) {
            i = 8;
        }
        imageView.setVisibility(i);
        Drawable drawable = this.w.getDrawable();
        if (drawable != null) {
            drawable.setState(z3 ? ViewGroup.ENABLED_STATE_SET : ViewGroup.EMPTY_STATE_SET);
        }
    }

    private void o() {
        CharSequence queryHint = getQueryHint();
        SearchAutoComplete searchAutoComplete = this.q;
        if (queryHint == null) {
            queryHint = "";
        }
        searchAutoComplete.setHint(c(queryHint));
    }

    private void p() {
        this.q.setThreshold(this.ga.getSuggestThreshold());
        this.q.setImeOptions(this.ga.getImeOptions());
        int inputType = this.ga.getInputType();
        int i = 1;
        if ((inputType & 15) == 1) {
            inputType &= -65537;
            if (this.ga.getSuggestAuthority() != null) {
                inputType = inputType | 65536 | 524288;
            }
        }
        this.q.setInputType(inputType);
        b.f.a.a aVar = this.S;
        if (aVar != null) {
            aVar.a((Cursor) null);
        }
        if (this.ga.getSuggestAuthority() != null) {
            this.S = new ba(getContext(), this, this.ga, this.ka);
            this.q.setAdapter(this.S);
            ba baVar = (ba) this.S;
            if (this.V) {
                i = 2;
            }
            baVar.a(i);
        }
    }

    private void q() {
        this.t.setVisibility((!l() || !(this.v.getVisibility() == 0 || this.x.getVisibility() == 0)) ? 8 : 0);
    }

    private void setQuery(CharSequence charSequence) {
        this.q.setText(charSequence);
        this.q.setSelection(TextUtils.isEmpty(charSequence) ? 0 : charSequence.length());
    }

    /* access modifiers changed from: package-private */
    public void a() {
        if (this.y.getWidth() > 1) {
            Resources resources = getContext().getResources();
            int paddingLeft = this.s.getPaddingLeft();
            Rect rect = new Rect();
            boolean a2 = wa.a(this);
            int dimensionPixelSize = this.Q ? resources.getDimensionPixelSize(b.a.d.abc_dropdownitem_icon_width) + resources.getDimensionPixelSize(b.a.d.abc_dropdownitem_text_padding_left) : 0;
            this.q.getDropDownBackground().getPadding(rect);
            this.q.setDropDownHorizontalOffset(a2 ? -rect.left : paddingLeft - (rect.left + dimensionPixelSize));
            this.q.setDropDownWidth((((this.y.getWidth() + rect.left) + rect.right) + dimensionPixelSize) - paddingLeft);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(int i, String str, String str2) {
        getContext().startActivity(a("android.intent.action.SEARCH", (Uri) null, (String) null, str2, i, str));
    }

    /* access modifiers changed from: package-private */
    public void a(CharSequence charSequence) {
        setQuery(charSequence);
    }

    /* access modifiers changed from: package-private */
    public boolean a(int i, int i2, String str) {
        d dVar = this.O;
        if (dVar != null && dVar.onSuggestionClick(i)) {
            return false;
        }
        b(i, 0, null);
        this.q.setImeVisibility(false);
        j();
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean a(View view, int i, KeyEvent keyEvent) {
        if (this.ga != null && this.S != null && keyEvent.getAction() == 0 && keyEvent.hasNoModifiers()) {
            if (i == 66 || i == 84 || i == 61) {
                return a(this.q.getListSelection(), 0, (String) null);
            }
            if (i == 21 || i == 22) {
                this.q.setSelection(i == 21 ? 0 : this.q.length());
                this.q.setListSelection(0);
                this.q.clearListSelection();
                p.a(this.q, true);
                return true;
            } else if (i != 19 || this.q.getListSelection() == 0) {
                return false;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public void b() {
        p.b(this.q);
        p.a(this.q);
    }

    /* access modifiers changed from: package-private */
    public void b(CharSequence charSequence) {
        Editable text = this.q.getText();
        this.da = text;
        boolean z2 = true;
        boolean z3 = !TextUtils.isEmpty(text);
        a(z3);
        if (z3) {
            z2 = false;
        }
        c(z2);
        n();
        q();
        if (this.L != null && !TextUtils.equals(charSequence, this.ca)) {
            this.L.onQueryTextChange(charSequence.toString());
        }
        this.ca = charSequence.toString();
    }

    public boolean c() {
        return this.R;
    }

    public void clearFocus() {
        this.W = true;
        super.clearFocus();
        this.q.clearFocus();
        this.q.setImeVisibility(false);
        this.W = false;
    }

    /* access modifiers changed from: package-private */
    public void d() {
        if (!TextUtils.isEmpty(this.q.getText())) {
            this.q.setText("");
            this.q.requestFocus();
            this.q.setImeVisibility(true);
        } else if (this.Q) {
            b bVar = this.M;
            if (bVar == null || !bVar.onClose()) {
                clearFocus();
                b(true);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean d(int i) {
        d dVar = this.O;
        if (dVar != null && dVar.onSuggestionSelect(i)) {
            return false;
        }
        e(i);
        return true;
    }

    /* access modifiers changed from: package-private */
    public void e() {
        b(false);
        this.q.requestFocus();
        this.q.setImeVisibility(true);
        View.OnClickListener onClickListener = this.P;
        if (onClickListener != null) {
            onClickListener.onClick(this);
        }
    }

    /* access modifiers changed from: package-private */
    public void f() {
        Editable text = this.q.getText();
        if (text != null && TextUtils.getTrimmedLength(text) > 0) {
            c cVar = this.L;
            if (cVar == null || !cVar.onQueryTextSubmit(text.toString())) {
                if (this.ga != null) {
                    a(0, (String) null, text.toString());
                }
                this.q.setImeVisibility(false);
                j();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void g() {
        b(c());
        m();
        if (this.q.hasFocus()) {
            b();
        }
    }

    public int getImeOptions() {
        return this.q.getImeOptions();
    }

    public int getInputType() {
        return this.q.getInputType();
    }

    public int getMaxWidth() {
        return this.aa;
    }

    public CharSequence getQuery() {
        return this.q.getText();
    }

    public CharSequence getQueryHint() {
        CharSequence charSequence = this.U;
        if (charSequence != null) {
            return charSequence;
        }
        SearchableInfo searchableInfo = this.ga;
        return (searchableInfo == null || searchableInfo.getHintId() == 0) ? this.K : getContext().getText(this.ga.getHintId());
    }

    /* access modifiers changed from: package-private */
    public int getSuggestionCommitIconResId() {
        return this.H;
    }

    /* access modifiers changed from: package-private */
    public int getSuggestionRowLayout() {
        return this.G;
    }

    public b.f.a.a getSuggestionsAdapter() {
        return this.S;
    }

    /* access modifiers changed from: package-private */
    public void h() {
        Intent a2;
        SearchableInfo searchableInfo = this.ga;
        if (searchableInfo != null) {
            try {
                if (searchableInfo.getVoiceSearchLaunchWebSearch()) {
                    a2 = b(this.I, searchableInfo);
                } else if (searchableInfo.getVoiceSearchLaunchRecognizer()) {
                    a2 = a(this.J, searchableInfo);
                } else {
                    return;
                }
                getContext().startActivity(a2);
            } catch (ActivityNotFoundException unused) {
                Log.w("SearchView", "Could not find voice search activity");
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void i() {
        int[] iArr = this.q.hasFocus() ? ViewGroup.FOCUSED_STATE_SET : ViewGroup.EMPTY_STATE_SET;
        Drawable background = this.s.getBackground();
        if (background != null) {
            background.setState(iArr);
        }
        Drawable background2 = this.t.getBackground();
        if (background2 != null) {
            background2.setState(iArr);
        }
        invalidate();
    }

    @Override // b.a.d.c
    public void onActionViewCollapsed() {
        setQuery("", false);
        clearFocus();
        b(true);
        this.q.setImeOptions(this.fa);
        this.ea = false;
    }

    @Override // b.a.d.c
    public void onActionViewExpanded() {
        if (!this.ea) {
            this.ea = true;
            this.fa = this.q.getImeOptions();
            this.q.setImeOptions(this.fa | 33554432);
            this.q.setText("");
            setIconified(false);
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        removeCallbacks(this.ia);
        post(this.ja);
        super.onDetachedFromWindow();
    }

    /* access modifiers changed from: protected */
    @Override // androidx.appcompat.widget.LinearLayoutCompat
    public void onLayout(boolean z2, int i, int i2, int i3, int i4) {
        super.onLayout(z2, i, i2, i3, i4);
        if (z2) {
            a(this.q, this.A);
            Rect rect = this.B;
            Rect rect2 = this.A;
            rect.set(rect2.left, 0, rect2.right, i4 - i2);
            e eVar = this.z;
            if (eVar == null) {
                this.z = new e(this.B, this.A, this.q);
                setTouchDelegate(this.z);
                return;
            }
            eVar.a(this.B, this.A);
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x001f, code lost:
        if (r0 <= 0) goto L_0x0039;
     */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0043  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x004b  */
    @Override // androidx.appcompat.widget.LinearLayoutCompat
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onMeasure(int r4, int r5) {
        /*
            r3 = this;
            boolean r0 = r3.c()
            if (r0 == 0) goto L_0x000a
            super.onMeasure(r4, r5)
            return
        L_0x000a:
            int r0 = android.view.View.MeasureSpec.getMode(r4)
            int r4 = android.view.View.MeasureSpec.getSize(r4)
            r1 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = 1073741824(0x40000000, float:2.0)
            if (r0 == r1) goto L_0x002c
            if (r0 == 0) goto L_0x0022
            if (r0 == r2) goto L_0x001d
            goto L_0x0039
        L_0x001d:
            int r0 = r3.aa
            if (r0 <= 0) goto L_0x0039
            goto L_0x0030
        L_0x0022:
            int r4 = r3.aa
            if (r4 <= 0) goto L_0x0027
            goto L_0x0039
        L_0x0027:
            int r4 = r3.getPreferredWidth()
            goto L_0x0039
        L_0x002c:
            int r0 = r3.aa
            if (r0 <= 0) goto L_0x0031
        L_0x0030:
            goto L_0x0035
        L_0x0031:
            int r0 = r3.getPreferredWidth()
        L_0x0035:
            int r4 = java.lang.Math.min(r0, r4)
        L_0x0039:
            int r0 = android.view.View.MeasureSpec.getMode(r5)
            int r5 = android.view.View.MeasureSpec.getSize(r5)
            if (r0 == r1) goto L_0x004b
            if (r0 == 0) goto L_0x0046
            goto L_0x0053
        L_0x0046:
            int r5 = r3.getPreferredHeight()
            goto L_0x0053
        L_0x004b:
            int r0 = r3.getPreferredHeight()
            int r5 = java.lang.Math.min(r0, r5)
        L_0x0053:
            int r4 = android.view.View.MeasureSpec.makeMeasureSpec(r4, r2)
            int r5 = android.view.View.MeasureSpec.makeMeasureSpec(r5, r2)
            super.onMeasure(r4, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.SearchView.onMeasure(int, int):void");
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.a());
        b(savedState.f408c);
        requestLayout();
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.f408c = c();
        return savedState;
    }

    public void onWindowFocusChanged(boolean z2) {
        super.onWindowFocusChanged(z2);
        m();
    }

    public boolean requestFocus(int i, Rect rect) {
        if (this.W || !isFocusable()) {
            return false;
        }
        if (c()) {
            return super.requestFocus(i, rect);
        }
        boolean requestFocus = this.q.requestFocus(i, rect);
        if (requestFocus) {
            b(false);
        }
        return requestFocus;
    }

    public void setAppSearchData(Bundle bundle) {
        this.ha = bundle;
    }

    public void setIconified(boolean z2) {
        if (z2) {
            d();
        } else {
            e();
        }
    }

    public void setIconifiedByDefault(boolean z2) {
        if (this.Q != z2) {
            this.Q = z2;
            b(z2);
            o();
        }
    }

    public void setImeOptions(int i) {
        this.q.setImeOptions(i);
    }

    public void setInputType(int i) {
        this.q.setInputType(i);
    }

    public void setMaxWidth(int i) {
        this.aa = i;
        requestLayout();
    }

    public void setOnCloseListener(b bVar) {
        this.M = bVar;
    }

    public void setOnQueryTextFocusChangeListener(View.OnFocusChangeListener onFocusChangeListener) {
        this.N = onFocusChangeListener;
    }

    public void setOnQueryTextListener(c cVar) {
        this.L = cVar;
    }

    public void setOnSearchClickListener(View.OnClickListener onClickListener) {
        this.P = onClickListener;
    }

    public void setOnSuggestionListener(d dVar) {
        this.O = dVar;
    }

    public void setQuery(CharSequence charSequence, boolean z2) {
        this.q.setText(charSequence);
        if (charSequence != null) {
            SearchAutoComplete searchAutoComplete = this.q;
            searchAutoComplete.setSelection(searchAutoComplete.length());
            this.da = charSequence;
        }
        if (z2 && !TextUtils.isEmpty(charSequence)) {
            f();
        }
    }

    public void setQueryHint(CharSequence charSequence) {
        this.U = charSequence;
        o();
    }

    public void setQueryRefinementEnabled(boolean z2) {
        this.V = z2;
        b.f.a.a aVar = this.S;
        if (aVar instanceof ba) {
            ((ba) aVar).a(z2 ? 2 : 1);
        }
    }

    public void setSearchableInfo(SearchableInfo searchableInfo) {
        this.ga = searchableInfo;
        if (this.ga != null) {
            p();
            o();
        }
        this.ba = k();
        if (this.ba) {
            this.q.setPrivateImeOptions("nm");
        }
        b(c());
    }

    public void setSubmitButtonEnabled(boolean z2) {
        this.T = z2;
        b(c());
    }

    public void setSuggestionsAdapter(b.f.a.a aVar) {
        this.S = aVar;
        this.q.setAdapter(this.S);
    }
}
