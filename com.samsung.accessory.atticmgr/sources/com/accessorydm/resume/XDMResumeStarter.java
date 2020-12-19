package com.accessorydm.resume;

import com.accessorydm.XDMDmUtils;
import com.accessorydm.db.file.XDBFumoAdp;
import com.accessorydm.db.file.XDBPostPoneAdp;
import com.accessorydm.db.sql.XDMDbSqlQuery;
import com.accessorydm.eng.core.XDMEvent;
import com.accessorydm.interfaces.XUIEventInterface;
import com.accessorydm.network.NetworkBlockedType;
import com.accessorydm.network.NetworkChecker;
import com.accessorydm.noti.XNOTIAdapter;
import com.accessorydm.postpone.PostponeManager;
import com.accessorydm.postpone.PostponeType;
import com.samsung.android.fotaprovider.log.Log;

public enum XDMResumeStarter {
    USERINIT_RESUME {
        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.resume.XDMResumeStarter
        public void doRemainingPush() {
        }

        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.resume.XDMResumeStarter
        public boolean possibleToResume() {
            if (!XDBFumoAdp.xdbGetFUMOCheckRooting()) {
                return true;
            }
            Log.E("Watch Rooting, return");
            XDMEvent.XDMSetEvent(null, XUIEventInterface.ACCESSORY_UIEVENT.XUI_DM_ACCESSORY_SYSSCOPE_MODIFIED);
            return false;
        }

        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.resume.XDMResumeStarter
        public boolean doResume() {
            return XDMResumeOperation.getInstance().doResume();
        }
    },
    INTENT_RESUME {
        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.resume.XDMResumeStarter
        public boolean possibleToResume() {
            NetworkBlockedType networkBlockType = NetworkChecker.get().getNetworkBlockType();
            if (!networkBlockType.isBlocked()) {
                return true;
            }
            Log.I("Intent Resume Blocked Type : " + networkBlockType);
            return false;
        }

        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.resume.XDMResumeStarter
        public boolean doResume() {
            XDMDmUtils.getInstance().xdmSetResumeStatus(0);
            XDMDmUtils.getInstance().xdmSetWaitWifiConnectMode(0);
            return XDMResumeOperation.getInstance().doResume();
        }

        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.resume.XDMResumeStarter
        public void doRemainingPush() {
            Log.I(this + " doRemainingPush");
            XNOTIAdapter.xnotiPushAdpResumeNotiAction();
        }
    },
    DMINIT_RESUME {
        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.resume.XDMResumeStarter
        public boolean possibleToResume() {
            return true;
        }

        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.resume.XDMResumeStarter
        public boolean doResume() {
            if (XDBPostPoneAdp.xdbGetPostponeType() == PostponeType.NONE) {
                return XDMResumeOperation.getInstance().doResume();
            }
            PostponeManager.executePostpone();
            return true;
        }

        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.resume.XDMResumeStarter
        public void doRemainingPush() {
            Log.I(this + " doRemainingPush");
            if (XDMDbSqlQuery.xdmdbsqlGetNotiSaveState() != 0) {
                XNOTIAdapter.xnotiPushAdpResumeNotiAction();
            }
        }
    };

    /* access modifiers changed from: package-private */
    public abstract void doRemainingPush();

    /* access modifiers changed from: package-private */
    public abstract boolean doResume();

    /* access modifiers changed from: package-private */
    public abstract boolean possibleToResume();

    public boolean resumeExecute() {
        Log.I("resumeExecute: " + this);
        if (!possibleToResume()) {
            Log.I("it's not possible to resume, return");
            return true;
        } else if (doResume()) {
            return true;
        } else {
            doRemainingPush();
            return false;
        }
    }
}
