package com.samsung.android.sdk.mobileservice.profile.result;

import com.samsung.android.sdk.mobileservice.common.result.CommonResultStatus;
import com.samsung.android.sdk.mobileservice.common.result.Result;
import com.samsung.android.sdk.mobileservice.profile.ProfileApi;

public class ProfileBirthdayTypeResult implements Result {
    private ProfileApi.ProfileBirthdayType mProfileBirthdayType;
    private CommonResultStatus mStatus;

    public ProfileBirthdayTypeResult(CommonResultStatus commonResultStatus, String str) {
        this.mStatus = commonResultStatus;
        this.mProfileBirthdayType = convertStringToProfileBirthdayType(str);
    }

    @Override // com.samsung.android.sdk.mobileservice.common.result.Result
    public CommonResultStatus getStatus() {
        return this.mStatus;
    }

    public ProfileApi.ProfileBirthdayType getResult() {
        return this.mProfileBirthdayType;
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    private ProfileApi.ProfileBirthdayType convertStringToProfileBirthdayType(String str) {
        char c;
        switch (str.hashCode()) {
            case 48:
                if (str.equals("0")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 49:
                if (str.equals("1")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 50:
                if (str.equals("2")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        if (c == 0) {
            return ProfileApi.ProfileBirthdayType.SOLAR_BIRTHDAY;
        }
        if (c == 1) {
            return ProfileApi.ProfileBirthdayType.LUNAR_BIRTHDAY;
        }
        if (c != 2) {
            return ProfileApi.ProfileBirthdayType.INVALID;
        }
        return ProfileApi.ProfileBirthdayType.LEAP_BIRTHDAY;
    }
}
