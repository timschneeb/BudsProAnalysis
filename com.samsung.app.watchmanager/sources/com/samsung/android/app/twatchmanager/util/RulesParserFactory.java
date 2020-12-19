package com.samsung.android.app.twatchmanager.util;

public class RulesParserFactory {
    public static IRulesParser getParser(int i) {
        if (i == 1) {
            return new RulesParser(i);
        }
        if (i != 2) {
            return null;
        }
        return new RulesParser2(i);
    }
}
