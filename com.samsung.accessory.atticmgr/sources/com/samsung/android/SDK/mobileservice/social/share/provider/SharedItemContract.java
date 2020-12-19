package com.samsung.android.sdk.mobileservice.social.share.provider;

import android.net.Uri;

public class SharedItemContract {
    public static final String AUTHORITY = "com.samsung.android.mobileservice.social.share.item";
    private static final String AUTHORITY_URI = "content://com.samsung.android.mobileservice.social.share.item";

    private SharedItemContract() {
    }

    public static class Item {
        public static final String CONTENT_LOCAL_PATH = "original_content_path";
        public static final Uri CONTENT_URI = Uri.parse("content://com.samsung.android.mobileservice.social.share.item/");
        public static final String CREATED_TIME = "createTime";
        public static final String DOWNLOAD_URL = "download_url";
        public static final String IS_OWNED_BY_ME = "is_owned_by_me";
        public static final String ITEM_ID = "itemId";
        public static final String LAST_SYNCED_TIME = "last_synced_time";
        public static final String MEMO = "memo";
        public static final String META_DATA = "meta_data";
        public static final String MIME_TYPE = "mime_type";
        public static final String MODIFIED_TIME = "modifiedTime";
        public static final String ORIGINAL_URL = "original_url";
        public static final String OWNER = "owner";
        public static final String SIZE = "size";
        public static final String SOURCE_CID = "source_cid";
        public static final String SPACE_ID = "spaceId";
        public static final String STREAMING_URL = "streaming_url";
        public static final String THUMBNAIL_LOCAL_PATH = "thumbnail_local_path";
        public static final String THUMBNAIL_URL = "thumbnail_url";
        public static final String TITLE = "title";

        private Item() {
        }
    }
}
