package com.sec.android.diagmonagent.log.ged.servreinterface.controller;

import android.content.Context;
import com.accessorydm.tp.urlconnect.HttpNetworkInterface;
import com.sec.android.diagmonagent.common.logger.AppLog;
import com.sec.android.diagmonagent.log.ged.db.GEDDatabase;
import com.sec.android.diagmonagent.log.ged.db.dao.ResultDao;
import com.sec.android.diagmonagent.log.ged.db.dao.ServiceDao;
import com.sec.android.diagmonagent.log.ged.db.model.Event;
import com.sec.android.diagmonagent.log.ged.db.model.Result;
import com.sec.android.diagmonagent.log.ged.db.model.ServiceInfo;
import com.sec.android.diagmonagent.log.ged.db.status.ERStatus;
import com.sec.android.diagmonagent.log.ged.servreinterface.model.client.DiagmonClient;
import com.sec.android.diagmonagent.log.ged.servreinterface.model.client.FileUploadClient;
import com.sec.android.diagmonagent.log.ged.servreinterface.model.client.TokenClient;
import com.sec.android.diagmonagent.log.ged.servreinterface.model.response.EventReportResponse;
import com.sec.android.diagmonagent.log.ged.servreinterface.model.response.PolicyResponse;
import com.sec.android.diagmonagent.log.ged.servreinterface.model.response.PolicyVersionResponse;
import com.sec.android.diagmonagent.log.ged.servreinterface.model.response.Response;
import com.sec.android.diagmonagent.log.ged.servreinterface.model.response.ServiceRegistrationResponse;
import com.sec.android.diagmonagent.log.ged.servreinterface.model.response.TokenResponse;
import com.sec.android.diagmonagent.log.ged.util.DeviceUtils;
import com.sec.android.diagmonagent.log.ged.util.ParsingUtils;
import com.sec.android.diagmonagent.log.ged.util.PreferenceUtils;
import com.sec.android.diagmonagent.log.ged.util.RestUtils;
import java.io.File;

public class DiagmonApiManager {
    private static volatile DiagmonApiManager sInstance;

    public static DiagmonApiManager getInstance() {
        if (sInstance == null) {
            synchronized (DiagmonApiManager.class) {
                if (sInstance == null) {
                    sInstance = new DiagmonApiManager();
                }
            }
        }
        return sInstance;
    }

    public void refreshToken(Context context) {
        Response execute = new TokenClient(context, RestUtils.URI_TOKEN_RERESH).execute();
        if (execute != null) {
            try {
                if (execute.getCode() == 200) {
                    AppLog.i("succeed to connect to get JWT");
                    AppLog.d(execute.getBody());
                    TokenResponse parseTokenResponse = ParsingUtils.parseTokenResponse(execute.getBody());
                    if (parseTokenResponse == null) {
                        AppLog.i("token response is null");
                    } else {
                        PreferenceUtils.setJwtToken(context, parseTokenResponse.getToken());
                    }
                } else {
                    AppLog.w("failed to connect to get JWT : " + execute.getCode());
                }
            } catch (IllegalStateException | NullPointerException e) {
                AppLog.e(e.getMessage());
            }
        }
    }

    public void serviceRegister(Context context, ServiceInfo serviceInfo, int i) {
        Response execute = new DiagmonClient(context, RestUtils.URI_SERVICE_REGISTRATION, HttpNetworkInterface.XTP_HTTP_METHOD_POST, RestUtils.getDmaServiceId(), RestUtils.makeSRObject(context, serviceInfo)).execute();
        if (execute == null) {
            return;
        }
        if (execute.getCode() == 200) {
            AppLog.i("succeed to connect to register service");
            AppLog.d(execute.getBody());
            ServiceRegistrationResponse parseServiceRegistrationResponse = ParsingUtils.parseServiceRegistrationResponse(execute.getBody(), serviceInfo.getServiceId());
            ServiceDao serviceDao = GEDDatabase.get(context).getServiceDao();
            if ("Y".equalsIgnoreCase(parseServiceRegistrationResponse.getStatusCode())) {
                serviceDao.updateDocumentId(parseServiceRegistrationResponse.getDocumentId());
                serviceDao.updateStatus(1);
            } else if ("1100".equals(parseServiceRegistrationResponse.getErrorCode())) {
                serviceDao.updateStatus(2);
            } else if ("1101".equals(parseServiceRegistrationResponse.getErrorCode())) {
                serviceDao.updateStatus(3);
            } else {
                AppLog.w("ErrorCode = " + parseServiceRegistrationResponse.getErrorCode());
                AppLog.w("ErrorMessage = " + parseServiceRegistrationResponse.getErrorMessage());
            }
        } else if (RestUtils.isTokenNeedToBeUpdated(context, execute)) {
            refreshToken(context);
            AppLog.i("Retry service registration");
            if (i < 3) {
                serviceRegister(context, serviceInfo, i + 1);
            }
        } else {
            AppLog.w("failed to connect to register service : " + execute.getCode());
        }
    }

    public void eventReport(Context context, Event event, int i) {
        Response execute = new DiagmonClient(context, RestUtils.URI_EVENT_REPORT, HttpNetworkInterface.XTP_HTTP_METHOD_POST, event.getServiceId(), RestUtils.makeERObject(context, event)).execute();
        if (execute == null) {
            return;
        }
        if (execute.getCode() == 200) {
            AppLog.i("succeed to connect to report event");
            AppLog.d(execute.getBody());
            EventReportResponse parseEventReportResponse = ParsingUtils.parseEventReportResponse(execute.getBody());
            event.setEventId(parseEventReportResponse.getEventId());
            event.setS3Path(parseEventReportResponse.getPreSignedURL());
            event.setExpirationTime(System.currentTimeMillis() + 86400000);
            GEDDatabase.get(context).getEventDao().update(event);
            uploadFile(context, event);
        } else if (RestUtils.isTokenNeedToBeUpdated(context, execute)) {
            refreshToken(context);
            AppLog.i("Retry event report");
            if (i < 3) {
                eventReport(context, event, i + 1);
                return;
            }
            event.setRetryCount(event.getRetryCount() + 1);
            if (event.getRetryCount() >= 3) {
                event.setStatus(ERStatus.TOKEN_REFRESH_FAILED);
                GEDDatabase.get(context).getEventDao().update(event);
                ResultDao resultDao = GEDDatabase.get(context).getResultDao();
                resultDao.insert(resultDao.makeResult(event));
            }
        } else if (401 != execute.getCode() || execute.getBody() == null || !execute.getBody().contains(RestUtils.ApiErrorCode.ERROR_CODE_UNAUTHORIZED)) {
            AppLog.w("failed to connect to report event : " + execute.getCode());
            updateRetryCount(context, event, execute.getCode());
        } else {
            AppLog.w("Unauthorized error code : " + event.getErrorCode());
            event.setStatus(ERStatus.ERROR_CODE_UNAUTHORIZED);
            GEDDatabase.get(context).getEventDao().update(event);
            ResultDao resultDao2 = GEDDatabase.get(context).getResultDao();
            resultDao2.insert(resultDao2.makeResult(event));
        }
    }

    public void uploadFile(Context context, Event event) {
        String str = context.getFilesDir() + "/" + event.getLogPath();
        if (new FileUploadClient(event.getS3Path()).execute(str) == 200) {
            AppLog.d(event.getEventId());
            AppLog.i("succeed to connect to upload file");
            new File(str).delete();
            event.setStatus(200);
            GEDDatabase.get(context).getEventDao().update(event);
            resultReportAfterLogUpload(context, event, 0);
            return;
        }
        AppLog.w("Failed to connect to upload file");
        updateRetryCount(context, event);
    }

    public void resultReportAfterLogUpload(Context context, Event event, int i) {
        Response execute = new DiagmonClient(context, RestUtils.URI_RESULT_REPORT, HttpNetworkInterface.XTP_HTTP_METHOD_POST, event.getServiceId(), RestUtils.makeRRObject(event)).execute();
        if (execute == null) {
            return;
        }
        if (execute.getCode() == 200) {
            AppLog.i("succeed to connect to report result after log upload");
            AppLog.d(execute.getBody());
        } else if (RestUtils.isTokenNeedToBeUpdated(context, execute)) {
            refreshToken(context);
            AppLog.i("Retry result report after log upload");
            if (i < 3) {
                resultReportAfterLogUpload(context, event, i + 1);
            }
        } else {
            ResultDao resultDao = GEDDatabase.get(context).getResultDao();
            resultDao.insert(resultDao.makeResult(event));
            AppLog.w("failed to connect to report result after log upload: " + execute.getCode());
        }
    }

    public void resultReport(Context context, Result result, int i) {
        Response execute = new DiagmonClient(context, RestUtils.URI_RESULT_REPORT, HttpNetworkInterface.XTP_HTTP_METHOD_POST, result.getServiceId(), RestUtils.makeRRObject(result)).execute();
        if (execute == null) {
            return;
        }
        if (execute.getCode() == 200) {
            AppLog.i("succeed to connect to report result");
            AppLog.d(execute.getBody());
            GEDDatabase.get(context).getResultDao().delete(result);
        } else if (RestUtils.isTokenNeedToBeUpdated(context, execute)) {
            refreshToken(context);
            AppLog.i("Retry result report");
            if (i < 3) {
                resultReport(context, result, i + 1);
            }
        } else {
            AppLog.w("Failed to connect to report result : " + execute.getCode());
        }
    }

    public boolean getPolicyVersionInfo(Context context) {
        Response execute = new DiagmonClient(RestUtils.getVersionCheckDomain(context) + RestUtils.getVersionCheckUri(context), HttpNetworkInterface.XTP_HTTP_METHOD_GET).execute();
        if (execute == null) {
            AppLog.w("Policy version response is null");
            return false;
        } else if (execute.getCode() == 200) {
            AppLog.i("succeed to connect to get policy version");
            AppLog.d(execute.getBody());
            PolicyVersionResponse parsePolicyVersionResponse = ParsingUtils.parsePolicyVersionResponse(execute.getBody());
            PreferenceUtils.setPolicyVersionInfoUrl(context, parsePolicyVersionResponse.getUrl());
            PreferenceUtils.setNeededPolicyVersion(context, parsePolicyVersionResponse.getLatestDefault());
            return true;
        } else {
            AppLog.w("Failed to connect to get policy version : " + execute.getCode());
            return false;
        }
    }

    public void refreshPolicy(Context context, String str, int i) {
        if ("0".equals(PreferenceUtils.getNeededPolicyVersion(context))) {
            AppLog.i("Needed policy version is invalid");
            return;
        }
        Response execute = new DiagmonClient(context, RestUtils.URI_POLICY_DOWNLOAD, getUriParam(context), HttpNetworkInterface.XTP_HTTP_METHOD_GET, RestUtils.getDmaServiceId()).execute();
        if (execute == null) {
            return;
        }
        if (execute.getCode() == 200) {
            AppLog.i("succeed to connect to refresh policy");
            AppLog.d(execute.getBody());
            PolicyResponse parsePolicyResponse = ParsingUtils.parsePolicyResponse(execute.getBody(), str);
            PreferenceUtils.initPolicyPreference(context);
            PreferenceUtils.setCurrentPolicyVersion(context, parsePolicyResponse.getVersion());
            PreferenceUtils.setPollingInterval(context, parsePolicyResponse.getPollingInterval());
            PreferenceUtils.setDefaultUploadFile(context, parsePolicyResponse.getDefaultUploadFile());
            PreferenceUtils.setDefaultMaxFileSize(context, parsePolicyResponse.getDefaultMaxFileSize());
            PreferenceUtils.setDefaultMaxFileCount(context, parsePolicyResponse.getDefaultMaxFileCount());
            PreferenceUtils.setUploadFileValue(context, parsePolicyResponse.getUploadFileValue());
            PreferenceUtils.setUploadFileServiceVersion(context, parsePolicyResponse.getUploadFileServiceVersion());
            PreferenceUtils.setUploadFileErrorCode(context, parsePolicyResponse.getUploadFileErrorCode());
            PreferenceUtils.setMaxFileSizeValue(context, parsePolicyResponse.getMaxFileSizeValue());
            PreferenceUtils.setMaxFileSizeServiceVersion(context, parsePolicyResponse.getMaxFileSizeServiceVersion());
            PreferenceUtils.setMaxFileSizeErrorCode(context, parsePolicyResponse.getMaxFileSizeErrorCode());
            PreferenceUtils.setMaxFileCountValue(context, parsePolicyResponse.getMaxFileCountValue());
        } else if (RestUtils.isTokenNeedToBeUpdated(context, execute)) {
            refreshToken(context);
            AppLog.i("Retry refresh policy");
            if (i < 3) {
                refreshPolicy(context, str, i + 1);
            }
        } else {
            AppLog.w("Failed to connect to refresh policy : " + execute.getCode());
        }
    }

    private void updateRetryCount(Context context, Event event) {
        event.setRetryCount(event.getRetryCount() + 1);
        if (event.getRetryCount() < 3) {
            GEDDatabase.get(context).getEventDao().update(event);
            return;
        }
        AppLog.i("upload retry count over - delete LogFile");
        DeviceUtils.deleteFiles(context.getApplicationContext().getFilesDir() + "/" + event.getLogPath());
        event.setStatus(302);
        GEDDatabase.get(context).getEventDao().update(event);
        ResultDao resultDao = GEDDatabase.get(context).getResultDao();
        resultDao.insert(resultDao.makeResult(event));
    }

    private void updateRetryCount(Context context, Event event, int i) {
        event.setRetryCount(event.getRetryCount() + 1);
        if (event.getRetryCount() < 3) {
            GEDDatabase.get(context).getEventDao().update(event);
            return;
        }
        if (i == 400) {
            event.setStatus(400);
        } else if (i == 401) {
            event.setStatus(ERStatus.PERMISSION_DENIED);
        } else if (i != 500) {
            event.setStatus(ERStatus.UNKNOWN);
        } else {
            event.setStatus(500);
        }
        AppLog.i("upload retry count over - delete LogFile");
        DeviceUtils.deleteFiles(context.getApplicationContext().getFilesDir() + "/" + event.getLogPath());
        GEDDatabase.get(context).getEventDao().update(event);
        ResultDao resultDao = GEDDatabase.get(context).getResultDao();
        resultDao.insert(resultDao.makeResult(event));
    }

    private String getUriParam(Context context) {
        return "?policyVersion=" + PreferenceUtils.getNeededPolicyVersion(context) + "&currentPolicyVersion=" + PreferenceUtils.getCurrentPolicyVersion(context) + "&dmaVersion=ged&tmcc=" + DeviceUtils.getTmcc(context) + "&smcc=" + DeviceUtils.getSmcc(context);
    }
}
