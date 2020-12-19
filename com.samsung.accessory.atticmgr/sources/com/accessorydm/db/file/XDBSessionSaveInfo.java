package com.accessorydm.db.file;

import java.io.Serializable;

public class XDBSessionSaveInfo implements Serializable {
    private static final long serialVersionUID = 1;
    public int nNotiRetryCount = 0;
    public int nNotiUiEvent = 0;
    public int nSessionSaveState = 0;
}
