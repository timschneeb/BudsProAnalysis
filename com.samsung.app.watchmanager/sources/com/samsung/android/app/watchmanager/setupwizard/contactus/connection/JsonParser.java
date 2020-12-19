package com.samsung.android.app.watchmanager.setupwizard.contactus.connection;

import c.a.a.p;
import c.a.a.q;
import com.samsung.android.app.watchmanager.setupwizard.contactus.connection.RequestResponseClasses.FeedBackList;

public class JsonParser {
    private q mBuilder = new q();
    private p mGson;

    public JsonParser() {
        q qVar = this.mBuilder;
        qVar.b();
        this.mGson = qVar.a();
    }

    public void closeParser() {
        this.mGson = null;
    }

    public String createData(Object obj) {
        return this.mGson.a(obj);
    }

    public FeedBackList[] createObject(String str) {
        return (FeedBackList[]) this.mGson.a(str, FeedBackList[].class);
    }
}
