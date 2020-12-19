package com.accessorydm.eng.core;

public class XDMUicResult {
    public int MenuNumbers;
    public int[] MultiSelected = new int[this.UIC_MAX_CHOICE_MENU];
    public int SingleSelected;
    public int UICType;
    int UIC_MAX_CHOICE_MENU = 32;
    public int appId;
    public int result;
    public XDMText text;

    XDMUicResult() {
    }
}
