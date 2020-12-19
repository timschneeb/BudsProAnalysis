package com.samsung.accessory.hearablemgr.core.service;

import android.text.TextUtils;
import com.samsung.accessory.hearablemgr.core.bigdata.SA;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;
import com.samsung.accessory.hearablemgr.core.service.CoreService;
import com.samsung.accessory.hearablemgr.core.service.message.Msg;
import com.samsung.accessory.hearablemgr.core.service.message.MsgUsageReport;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import seccompat.android.util.Log;

public class EarBudsUsageReporter implements CoreService.OnSppMessageListener {
    private static final String TAG = "Attic_EarBudsUsageReporter";
    private static final Map<String, String> sMapReportKeyToEventId = new HashMap();
    private CoreService mService;

    static {
        sMapReportKeyToEventId.put("PLAY", SA.Event.EARBUDS_SINGLE_TAP);
        sMapReportKeyToEventId.put("PAUS", SA.Event.EARBUDS_SINGLE_TAP);
        sMapReportKeyToEventId.put("NEXT", SA.Event.EARBUDS_DOUBLE_TAP);
        sMapReportKeyToEventId.put("PREV", SA.Event.EARBUDS_TRIPLE_TAP);
        sMapReportKeyToEventId.put("A2DD", SA.Event.EARBUDS_MUSIC_FROM_PHONE_DURATION);
        sMapReportKeyToEventId.put("A2DC", SA.Event.EARBUDS_MUSIC_FROM_PHONE_TIMES);
        sMapReportKeyToEventId.put("LOWB", SA.Event.EARBUDS_LOW_BATTERY);
        sMapReportKeyToEventId.put("LOWB0", SA.Event.EARBUDS_LOW_BATTERY);
        sMapReportKeyToEventId.put("LOWB1", SA.Event.EARBUDS_LOW_BATTERY);
        sMapReportKeyToEventId.put("LOWB2", SA.Event.EARBUDS_LOW_BATTERY);
        sMapReportKeyToEventId.put("LOWB3", SA.Event.EARBUDS_LOW_BATTERY);
        sMapReportKeyToEventId.put("DISC", SA.Event.EARBUDS_ERROR_DISCHARGING);
        sMapReportKeyToEventId.put("CHAR", SA.Event.EARBUDS_ERROR_CHARGING);
        sMapReportKeyToEventId.put("EAST", SA.Event.EARBUDS_ASSERT_CPU_EXCEPTION);
        sMapReportKeyToEventId.put("TAHL0", SA.Event.EARBUDS_TAP_AND_HOLD_LEFT);
        sMapReportKeyToEventId.put("TAHL1", SA.Event.EARBUDS_TAP_AND_HOLD_LEFT);
        sMapReportKeyToEventId.put("TAHL2", SA.Event.EARBUDS_TAP_AND_HOLD_LEFT);
        sMapReportKeyToEventId.put("TAHL3", SA.Event.EARBUDS_TAP_AND_HOLD_LEFT);
        sMapReportKeyToEventId.put("TAHL4", SA.Event.EARBUDS_TAP_AND_HOLD_LEFT);
        sMapReportKeyToEventId.put("TAHL5", SA.Event.EARBUDS_TAP_AND_HOLD_LEFT);
        sMapReportKeyToEventId.put("TAHL6", SA.Event.EARBUDS_TAP_AND_HOLD_LEFT);
        sMapReportKeyToEventId.put("TAHL7", SA.Event.EARBUDS_TAP_AND_HOLD_LEFT);
        sMapReportKeyToEventId.put("TAHL8", SA.Event.EARBUDS_TAP_AND_HOLD_LEFT);
        sMapReportKeyToEventId.put("TAHR0", SA.Event.EARBUDS_TAP_AND_HOLD_RIGHT);
        sMapReportKeyToEventId.put("TAHR1", SA.Event.EARBUDS_TAP_AND_HOLD_RIGHT);
        sMapReportKeyToEventId.put("TAHR2", SA.Event.EARBUDS_TAP_AND_HOLD_RIGHT);
        sMapReportKeyToEventId.put("TAHR3", SA.Event.EARBUDS_TAP_AND_HOLD_RIGHT);
        sMapReportKeyToEventId.put("TAHR4", SA.Event.EARBUDS_TAP_AND_HOLD_RIGHT);
        sMapReportKeyToEventId.put("TAHR5", SA.Event.EARBUDS_TAP_AND_HOLD_RIGHT);
        sMapReportKeyToEventId.put("TAHR6", SA.Event.EARBUDS_TAP_AND_HOLD_RIGHT);
        sMapReportKeyToEventId.put("TAHR7", SA.Event.EARBUDS_TAP_AND_HOLD_RIGHT);
        sMapReportKeyToEventId.put("TAHR8", SA.Event.EARBUDS_TAP_AND_HOLD_RIGHT);
        sMapReportKeyToEventId.put("LRLP", SA.Event.EARBUDS_TAP_AND_HOLD_BOTH);
        sMapReportKeyToEventId.put("ANSC", SA.Event.EARBUDS_DOUBLE_TAP);
        sMapReportKeyToEventId.put("ENDC", SA.Event.EARBUDS_DOUBLE_TAP);
        sMapReportKeyToEventId.put("WLOD", SA.Event.EARBUDS_WEARING_DURATION);
        sMapReportKeyToEventId.put("WROD", SA.Event.EARBUDS_WEARING_DURATION);
        sMapReportKeyToEventId.put("WALD", SA.Event.EARBUDS_WEARING_DURATION);
        sMapReportKeyToEventId.put("MDSW", SA.Event.EARBUDS_MASTER_SWITCHING_FOR_EACH_USE);
        sMapReportKeyToEventId.put("DCIC", SA.Event.EARBUDS_DISCONNECTION_WHILE_INCOMING_CALL);
        sMapReportKeyToEventId.put("DCDC", SA.Event.EARBUDS_DISCONNECTION_DURING_CALL);
        sMapReportKeyToEventId.put("CWFD", SA.Event.EARBUDS_DURATION_THAT_EARBUDS_CONNECTED_BUT);
        sMapReportKeyToEventId.put("ANCD", SA.Event.EARBUDS_ACTIVE_NOISE_CANCELING_DURATION);
    }

    public EarBudsUsageReporter(CoreService coreService) {
        this.mService = coreService;
        this.mService.registerSppMessageListener(this);
    }

    public void destroy() {
        this.mService.unregisterSppMessageListener(this);
    }

    @Override // com.samsung.accessory.hearablemgr.core.service.CoreService.OnSppMessageListener
    public void onSppMessage(Msg msg) {
        if (msg.id == 64) {
            Log.d(TAG, "MsgID.USAGE_REPORT");
            MsgUsageReport msgUsageReport = (MsgUsageReport) msg;
            this.mService.sendSppMessage(new MsgUsageReport(msgUsageReport.responseCode));
            if (msgUsageReport.responseCode == 0) {
                logUsageReport(msgUsageReport.usageReport);
                sendEvents(msgUsageReport.usageReport);
            }
        }
    }

    private void logUsageReport(Map<String, Long> map) {
        StringBuilder sb = new StringBuilder();
        sb.append("UsageReport : {");
        for (String str : map.keySet()) {
            sb.append("(");
            sb.append(str);
            sb.append(", ");
            sb.append(map.get(str));
            sb.append("), ");
        }
        sb.append("}");
        Log.d(TAG, sb.toString());
    }

    private void sendEvents(Map<String, Long> map) {
        Iterator<String> it = map.keySet().iterator();
        while (it.hasNext()) {
            String next = it.next();
            if (TextUtils.isEmpty(next)) {
                Log.e(TAG, "key is empty !!! : " + map.get(next));
            } else {
                char c = 65535;
                int hashCode = next.hashCode();
                switch (hashCode) {
                    case 2014013:
                        if (next.equals("ANSC")) {
                            c = 3;
                            break;
                        }
                        break;
                    case 2132712:
                        if (next.equals("ENDC")) {
                            c = 4;
                            break;
                        }
                        break;
                    case 2392819:
                        if (next.equals("NEXT")) {
                            c = 2;
                            break;
                        }
                        break;
                    case 2448463:
                        if (next.equals("PAUS")) {
                            c = 1;
                            break;
                        }
                        break;
                    case 2458420:
                        if (next.equals("PLAY")) {
                            c = 0;
                            break;
                        }
                        break;
                    case 2656706:
                        if (next.equals("WALD")) {
                            c = 25;
                            break;
                        }
                        break;
                    case 2667370:
                        if (next.equals("WLOD")) {
                            c = 23;
                            break;
                        }
                        break;
                    case 2673136:
                        if (next.equals("WROD")) {
                            c = 24;
                            break;
                        }
                        break;
                    default:
                        switch (hashCode) {
                            case 79583775:
                                if (next.equals("TAHL0")) {
                                    c = 5;
                                    break;
                                }
                                break;
                            case 79583776:
                                if (next.equals("TAHL1")) {
                                    c = 6;
                                    break;
                                }
                                break;
                            case 79583777:
                                if (next.equals("TAHL2")) {
                                    c = 7;
                                    break;
                                }
                                break;
                            case 79583778:
                                if (next.equals("TAHL3")) {
                                    c = '\b';
                                    break;
                                }
                                break;
                            case 79583779:
                                if (next.equals("TAHL4")) {
                                    c = '\t';
                                    break;
                                }
                                break;
                            case 79583780:
                                if (next.equals("TAHL5")) {
                                    c = '\n';
                                    break;
                                }
                                break;
                            case 79583781:
                                if (next.equals("TAHL6")) {
                                    c = 11;
                                    break;
                                }
                                break;
                            case 79583782:
                                if (next.equals("TAHL7")) {
                                    c = '\f';
                                    break;
                                }
                                break;
                            case 79583783:
                                if (next.equals("TAHL8")) {
                                    c = '\r';
                                    break;
                                }
                                break;
                            default:
                                switch (hashCode) {
                                    case 79583961:
                                        if (next.equals("TAHR0")) {
                                            c = 14;
                                            break;
                                        }
                                        break;
                                    case 79583962:
                                        if (next.equals("TAHR1")) {
                                            c = 15;
                                            break;
                                        }
                                        break;
                                    case 79583963:
                                        if (next.equals("TAHR2")) {
                                            c = 16;
                                            break;
                                        }
                                        break;
                                    case 79583964:
                                        if (next.equals("TAHR3")) {
                                            c = 17;
                                            break;
                                        }
                                        break;
                                    case 79583965:
                                        if (next.equals("TAHR4")) {
                                            c = 18;
                                            break;
                                        }
                                        break;
                                    case 79583966:
                                        if (next.equals("TAHR5")) {
                                            c = 19;
                                            break;
                                        }
                                        break;
                                    case 79583967:
                                        if (next.equals("TAHR6")) {
                                            c = 20;
                                            break;
                                        }
                                        break;
                                    case 79583968:
                                        if (next.equals("TAHR7")) {
                                            c = 21;
                                            break;
                                        }
                                        break;
                                    case 79583969:
                                        if (next.equals("TAHR8")) {
                                            c = 22;
                                            break;
                                        }
                                        break;
                                }
                        }
                }
                switch (c) {
                    case 0:
                        SamsungAnalyticsUtil.sendEvent(SA.Event.EARBUDS_SINGLE_TAP, null, map.get(next), "a");
                        break;
                    case 1:
                        SamsungAnalyticsUtil.sendEvent(SA.Event.EARBUDS_SINGLE_TAP, null, map.get(next), "b");
                        break;
                    case 2:
                        SamsungAnalyticsUtil.sendEvent(SA.Event.EARBUDS_DOUBLE_TAP, null, map.get(next), "a");
                        break;
                    case 3:
                        SamsungAnalyticsUtil.sendEvent(SA.Event.EARBUDS_DOUBLE_TAP, null, map.get(next), "b");
                        break;
                    case 4:
                        SamsungAnalyticsUtil.sendEvent(SA.Event.EARBUDS_DOUBLE_TAP, null, map.get(next), "c");
                        break;
                    case 5:
                        SamsungAnalyticsUtil.sendEvent(SA.Event.EARBUDS_TAP_AND_HOLD_LEFT, null, map.get(next), "a");
                        break;
                    case 6:
                        SamsungAnalyticsUtil.sendEvent(SA.Event.EARBUDS_TAP_AND_HOLD_LEFT, null, map.get(next), "b");
                        break;
                    case 7:
                        SamsungAnalyticsUtil.sendEvent(SA.Event.EARBUDS_TAP_AND_HOLD_LEFT, null, map.get(next), "c");
                        break;
                    case '\b':
                        SamsungAnalyticsUtil.sendEvent(SA.Event.EARBUDS_TAP_AND_HOLD_LEFT, null, map.get(next), "d");
                        break;
                    case '\t':
                        SamsungAnalyticsUtil.sendEvent(SA.Event.EARBUDS_TAP_AND_HOLD_LEFT, null, map.get(next), "e");
                        break;
                    case '\n':
                        SamsungAnalyticsUtil.sendEvent(SA.Event.EARBUDS_TAP_AND_HOLD_LEFT, null, map.get(next), "f");
                        break;
                    case 11:
                        SamsungAnalyticsUtil.sendEvent(SA.Event.EARBUDS_TAP_AND_HOLD_LEFT, null, map.get(next), "g");
                        break;
                    case '\f':
                        SamsungAnalyticsUtil.sendEvent(SA.Event.EARBUDS_TAP_AND_HOLD_LEFT, null, map.get(next), "h");
                        break;
                    case '\r':
                        SamsungAnalyticsUtil.sendEvent(SA.Event.EARBUDS_TAP_AND_HOLD_LEFT, null, map.get(next), SA.Detail.TOUCH_AND_HOLD_DETAIL_I_FLO);
                        break;
                    case 14:
                        SamsungAnalyticsUtil.sendEvent(SA.Event.EARBUDS_TAP_AND_HOLD_RIGHT, null, map.get(next), "a");
                        break;
                    case 15:
                        SamsungAnalyticsUtil.sendEvent(SA.Event.EARBUDS_TAP_AND_HOLD_RIGHT, null, map.get(next), "b");
                        break;
                    case 16:
                        SamsungAnalyticsUtil.sendEvent(SA.Event.EARBUDS_TAP_AND_HOLD_RIGHT, null, map.get(next), "c");
                        break;
                    case 17:
                        SamsungAnalyticsUtil.sendEvent(SA.Event.EARBUDS_TAP_AND_HOLD_RIGHT, null, map.get(next), "d");
                        break;
                    case 18:
                        SamsungAnalyticsUtil.sendEvent(SA.Event.EARBUDS_TAP_AND_HOLD_RIGHT, null, map.get(next), "e");
                        break;
                    case 19:
                        SamsungAnalyticsUtil.sendEvent(SA.Event.EARBUDS_TAP_AND_HOLD_RIGHT, null, map.get(next), "f");
                        break;
                    case 20:
                        SamsungAnalyticsUtil.sendEvent(SA.Event.EARBUDS_TAP_AND_HOLD_RIGHT, null, map.get(next), "g");
                        break;
                    case 21:
                        SamsungAnalyticsUtil.sendEvent(SA.Event.EARBUDS_TAP_AND_HOLD_RIGHT, null, map.get(next), "h");
                        break;
                    case 22:
                        SamsungAnalyticsUtil.sendEvent(SA.Event.EARBUDS_TAP_AND_HOLD_RIGHT, null, map.get(next), SA.Detail.TOUCH_AND_HOLD_DETAIL_I_FLO);
                        break;
                    case 23:
                        SamsungAnalyticsUtil.sendEvent(SA.Event.EARBUDS_WEARING_DURATION, null, map.get(next), "a");
                        break;
                    case 24:
                        SamsungAnalyticsUtil.sendEvent(SA.Event.EARBUDS_WEARING_DURATION, null, map.get(next), "b");
                        break;
                    case 25:
                        SamsungAnalyticsUtil.sendEvent(SA.Event.EARBUDS_WEARING_DURATION, null, map.get(next), "c");
                        break;
                    default:
                        String str = sMapReportKeyToEventId.get(next);
                        if (str == null) {
                            Log.e(TAG, "Unknown key !!! : " + next);
                            break;
                        } else {
                            SamsungAnalyticsUtil.sendEvent(str, (String) null, map.get(next));
                            break;
                        }
                }
                it = it;
            }
        }
    }
}
