package com.samsung.android.sdk.mobileservice.social.buddy.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class BuddyContract {
    public static final String AUTHORITY = "com.samsung.android.mobileservice.social.buddy";
    private static final Uri AUTHORITY_URI = Uri.parse("content://com.samsung.android.mobileservice.social.buddy");

    protected interface BuddyLookupColumns {
        public static final String ACCOUNT_NAME = "account_name";
        public static final String CONTACT_ID = "contact_id";
        public static final String CONTACT_NAME = "contact_name";
        public static final String DISPLAY_NAME = "display_name";
        public static final String DUID = "duid";
        public static final String FEATURE_ID = "feature_id";
        public static final String GUID = "guid";
        public static final String IMAGE_URI = "image_uri";
        public static final String MSISDN = "msisdn";
        public static final String STATUS_MESSAGE = "status_message";
    }

    private BuddyContract() {
    }

    public static class Info {
        public static final String ACCOUNT_NAME = "account_name";
        public static final String CONTACT_ID = "contact_id";
        public static final String CONTACT_NAME = "contact_name";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BuddyContract.AUTHORITY_URI, "buddy_info");
        public static final String DEVICE_INDEX = "device_idx";
        public static final String DUID = "DUID";
        public static final String GUID = "GUID";
        public static final String MSISDN = "MSISDN";
        public static final String PROFILE_TYPE = "profile_type";
        public static final String SIDS = "SIDS";
        public static final String STATUS_MESSAGE = "status_msg";
        public static final String TIMESTAMP = "timestamp";

        public static class ProfileType {
            public static final String BUSINESS_PROFILE = "2";
            public static final String ME_PROFILE = "1";
            public static final String PARENT_PROFILE = "3";

            private ProfileType() {
            }
        }

        private Info() {
        }
    }

    public static class Email {
        public static final String ADDRESS = "address";
        public static final String CONTACT_ID = "contact_id";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BuddyContract.AUTHORITY_URI, "buddy_email");
        public static final String LABEL = "label";
        public static final String TIMESTAMP = "timestamp";
        public static final String TYPE = "type";

        public static class Type {
            public static final String CUSTOM = "0";
            public static final String HOME = "1";
            public static final String MOBILE = "4";
            public static final String OTHER = "3";
            public static final String WORK = "2";

            private Type() {
            }
        }

        private Email() {
        }
    }

    public static class Address {
        public static final String CITY = "city";
        public static final String CONTACT_ID = "contact_id";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BuddyContract.AUTHORITY_URI, "buddy_addr");
        public static final String COUNTRY = "country";
        public static final String DISPLAY_ADDRESS = "display_addr";
        public static final String LABEL = "label";
        public static final String NEIGHBORHOOD = "neighborhood";
        public static final String POBOX = "pobox";
        public static final String POST_CODE = "post_code";
        public static final String REGION = "region";
        public static final String STREET = "street";
        public static final String TIMESTAMP = "timestamp";
        public static final String TYPE = "type";

        public static class Type {
            public static final String CUSTOM = "0";
            public static final String HOME = "1";
            public static final String OTHER = "3";
            public static final String WORK = "2";

            private Type() {
            }
        }

        private Address() {
        }
    }

    public static class Event {
        public static final String CONTACT_ID = "contact_id";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BuddyContract.AUTHORITY_URI, "buddy_event");
        public static final String DATE = "date";
        public static final String LABEL = "label";
        public static final String TIMESTAMP = "timestamp";
        public static final String TYPE = "type";

        public static class Type {
            public static final String ANNIVERSARY = "1";
            public static final String CUSTOM = "0";
            public static final String OTHER = "2";

            private Type() {
            }
        }

        private Event() {
        }
    }

    public static class Organization {
        public static final String COMPANY = "company";
        public static final String CONTACT_ID = "contact_id";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BuddyContract.AUTHORITY_URI, "buddy_org");
        public static final String DEPARTMENT = "department";
        public static final String JOB_TITLE = "job_title";
        public static final String TIMESTAMP = "timestamp";
        public static final String TYPE = "type";

        private Organization() {
        }
    }

    public static class Image {
        public static final String CONTACT_ID = "contact_id";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BuddyContract.AUTHORITY_URI, "buddy_image");
        public static final String IMAGE_NUMBER = "img_no";
        public static final String IMAGE_URL = "img_url";
        public static final String LOCAL_IMAGE_PATH = "local_image_path";
        public static final String THUMBNAIL = "thumbnail";
        public static final String TIMESTAMP = "timestamp";

        private Image() {
        }
    }

    public static class BusinessProfile {
        public static final String CONTACT_ID = "contact_id";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BuddyContract.AUTHORITY_URI, "buddy_biz_profile");
        public static final String TIMESTAMP = "timestamp";
        public static final String URL = "url";

        private BusinessProfile() {
        }
    }

    public static class ApplicationList {
        public static final String APPLICATION_ID = "app_id";
        public static final String CONTACT_ID = "contact_id";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BuddyContract.AUTHORITY_URI, "capability_info");
        public static final String SERVICE_ID = "service_id";
        public static final String TIMESTAMP = "timestamp";

        private ApplicationList() {
        }
    }

    public static final class BuddyLookup implements BaseColumns, BuddyLookupColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BuddyContract.AUTHORITY_URI, "buddy_lookup");
        public static final String KEY_TYPE_APP_ID = "appId";
        public static final String KEY_TYPE_FEATURE_ID = "featureId";

        private BuddyLookup() {
        }
    }

    public static class BuddyContactInfo {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BuddyContract.AUTHORITY_URI, "contact/info");
        public static final String DISPLAY_NAME = "display_name";
        public static final String DISPLAY_NAME_SOURCE = "display_name_source";
        public static final String GUID = "guid";
        public static final String KEY_GUID = "guid";
        public static final String MSISDN = "msisdn";

        private BuddyContactInfo() {
        }
    }
}
