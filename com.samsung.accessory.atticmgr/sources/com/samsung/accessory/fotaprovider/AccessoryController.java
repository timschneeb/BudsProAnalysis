package com.samsung.accessory.fotaprovider;

import com.samsung.accessory.fotaprovider.controller.AccessoryStateHandler;
import com.samsung.accessory.fotaprovider.controller.AccessoryUtil;
import com.samsung.accessory.fotaprovider.controller.ConnectionController;
import com.samsung.accessory.fotaprovider.controller.NotificationController;
import com.samsung.accessory.fotaprovider.controller.RequestController;

public abstract class AccessoryController {
    private static AccessoryController instance;
    protected AccessoryStateHandler accessoryStateHandler = new AccessoryStateHandler();
    protected AccessoryUtil accessoryUtil;
    protected ConnectionController connectionController;
    protected NotificationController notificationController = new NotificationController();
    protected RequestController requestController;

    public static void injectAccessoryController(AccessoryController accessoryController) {
        instance = accessoryController;
    }

    public static AccessoryController getInstance() {
        return instance;
    }

    public static boolean isAvailable() {
        return instance != null;
    }

    public ConnectionController getConnectionController() {
        return this.connectionController;
    }

    public RequestController getRequestController() {
        return this.requestController;
    }

    public AccessoryUtil getAccessoryUtil() {
        return this.accessoryUtil;
    }

    public NotificationController getNotificationController() {
        return this.notificationController;
    }

    public AccessoryStateHandler getAccessoryStateHandler() {
        return this.accessoryStateHandler;
    }
}
