package com.accessorydm.interfaces;

public interface XUICInterface {
    public static final int UIC_ECHOTYPE_PASSWORD = 2;
    public static final int UIC_ECHOTYPE_TEXT = 1;
    public static final int UIC_INPUTTYPE_ALPHANUMERIC = 1;
    public static final int UIC_INPUTTYPE_DATE = 3;
    public static final int UIC_INPUTTYPE_IPADDRESS = 6;
    public static final int UIC_INPUTTYPE_NUMERIC = 2;
    public static final int UIC_INPUTTYPE_PHONENUBMER = 5;
    public static final int UIC_INPUTTYPE_TIME = 4;
    public static final int UIC_MAX_CHOICE_MENU = 32;
    public static final int UIC_MAX_TITLE_SIZE = 128;
    public static final int UIC_MAX_USERINPUT_SIZE = 128;
    public static final int UIC_PROGRTYPE_END = 2;
    public static final int UIC_PROGRTYPE_START = 0;
    public static final int UIC_PROGRTYPE_UPDATA = 1;
    public static final int UIC_RESULT_MULTI_CHOICE = 5;
    public static final int UIC_RESULT_NOT_EXCUTED = 17;
    public static final int UIC_RESULT_OK = 0;
    public static final int UIC_RESULT_REJECT = 2;
    public static final int UIC_RESULT_SINGLE_CHOICE = 4;
    public static final int UIC_RESULT_TIMEOUT = 16;
    public static final int UIC_RESULT_YES = 1;
    public static final int UIC_SAVE_NONE = 0;
    public static final int UIC_SAVE_OK = 1;
    public static final int UIC_TYPE_CONFIRM = 2;
    public static final int UIC_TYPE_DISP = 1;
    public static final int UIC_TYPE_INPUT = 3;
    public static final int UIC_TYPE_MULTI_CHOICE = 5;
    public static final int UIC_TYPE_NONE = 0;
    public static final int UIC_TYPE_PROGR = 6;
    public static final int UIC_TYPE_SINGLE_CHOICE = 4;

    public enum XUICFlag {
        UIC_NONE,
        UIC_TRUE,
        UIC_FALSE,
        UIC_CANCELED,
        UIC_TIMEOUT
    }
}
