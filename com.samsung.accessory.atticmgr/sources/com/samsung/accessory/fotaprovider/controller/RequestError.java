package com.samsung.accessory.fotaprovider.controller;

import com.accessorydm.interfaces.XFOTAInterface;

public enum RequestError {
    ERROR_UNKNOWN(0),
    ERROR_CONNECT(110),
    ERROR_COMMAND(120),
    ERROR_FILE_TRANSFER(130),
    ERROR_FILE_TRANSFER_ACCESSORY_UNCOUPLED(133),
    ERROR_TIME_OUT(140),
    ERROR_CONNECTION_LOST(150),
    ERROR_LOW_MEMORY(160),
    ERROR_DIFFERENT_DEVICE(210),
    ERROR_WRONG_MSGID(XFOTAInterface.XDL_STATE_POSTPONE_TO_UPDATE),
    ERROR_JSON_EXCEPTION(XFOTAInterface.XDL_STATE_DOWNLOAD_IN_CANCEL),
    ERROR_CONSUMER_RESULT(XFOTAInterface.XDL_STATE_USER_CANCEL_REPORTING),
    ERROR_CONSUMER_SCANNING(250),
    ERROR_CONSUMER_MODIFIED(260),
    ERROR_CONSUMER_MDM_BLOCKED(270);
    
    private final int errorCode;

    private RequestError(int i) {
        this.errorCode = i;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public static RequestError getRequestErrorByErrorCode(int i) {
        RequestError[] values = values();
        for (RequestError requestError : values) {
            if (i == requestError.errorCode) {
                return requestError;
            }
        }
        return ERROR_UNKNOWN;
    }
}
