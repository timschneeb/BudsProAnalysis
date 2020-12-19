package com.samsung.accessory.fotaprovider;

public enum AccessoryState {
    NONE(0),
    INIT(10),
    COPY_FAILED(20),
    COPY_IN_PROGRESS(30),
    COPY_COMPLETE(40),
    READY_TO_UPDATE(50),
    UPDATE_IN_PROGRESS(60),
    UPDATE_TO_REPORTING(65),
    UPDATE_FAILED(80),
    UPDATE_SUCCESSFUL(100);
    
    private int accessoryState;

    private AccessoryState(int i) {
        this.accessoryState = i;
    }

    public int getValue() {
        return this.accessoryState;
    }

    public static AccessoryState getStateByValue(int i) {
        AccessoryState[] values = values();
        for (AccessoryState accessoryState2 : values) {
            if (accessoryState2.getValue() == i) {
                return accessoryState2;
            }
        }
        return NONE;
    }
}
