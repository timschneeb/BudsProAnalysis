package com.samsung.android.sdk.mobileservice.social.share;

import android.net.Uri;
import android.os.Bundle;
import com.samsung.android.sdk.mobileservice.social.share.provider.SharedItemContract;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SharedItemWithUriList {
    private List<Content> mContentList = new ArrayList();
    private long mCreatedTime;
    private boolean mIsOwnedByMe;
    private String mItemId;
    private String mLastModifierId;
    private String mLeaderId;
    private String mMemo;
    private Map mMetaData;
    private long mModifiedTime;
    private String mSpaceId;
    private String mTitle;

    public List<Content> getContentList() {
        return this.mContentList;
    }

    public void addContent(Content content) {
        this.mContentList.add(content);
    }

    public SharedItemWithUriList(String str, String str2, String str3) {
        setItemId(str);
        setSpaceId(str2);
        setLeaderId(str3);
    }

    public SharedItemWithUriList(Bundle bundle) {
        this.mItemId = bundle.getString("item_id");
        this.mSpaceId = bundle.getString("space_id");
        this.mLeaderId = bundle.getString("owner_id");
        this.mLastModifierId = bundle.getString("last_modifier_id");
        this.mTitle = bundle.getString("title", "");
        this.mMemo = bundle.getString("memo", "");
        this.mCreatedTime = bundle.getLong("created_time", 0);
        this.mModifiedTime = bundle.getLong("modified_time", 0);
        this.mIsOwnedByMe = bundle.getBoolean("is_owned_by_me", false);
        this.mMetaData = (HashMap) bundle.getSerializable("meta_data");
        Iterator it = bundle.getParcelableArrayList("share_file_list").iterator();
        while (it.hasNext()) {
            Bundle bundle2 = (Bundle) it.next();
            this.mContentList.add(new Content(bundle2.getString("content_hash", ""), bundle2.getString("mime_type", ""), Uri.parse(bundle2.getString("thumbnail_uri", "")), bundle2.getString("file_name"), bundle2.getLong("file_size", 0), bundle2.getString(SharedItemContract.Item.CONTENT_LOCAL_PATH, "")));
        }
    }

    public String getItemId() {
        return this.mItemId;
    }

    public void setItemId(String str) {
        this.mItemId = str;
    }

    public String getSpaceId() {
        return this.mSpaceId;
    }

    public void setSpaceId(String str) {
        this.mSpaceId = str;
    }

    public String getLeaderId() {
        return this.mLeaderId;
    }

    public void setLeaderId(String str) {
        this.mLeaderId = str;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public void setTitle(String str) {
        this.mTitle = str;
    }

    public String getMemo() {
        return this.mMemo;
    }

    public void setMemo(String str) {
        this.mMemo = str;
    }

    public long getCreatedTime() {
        return this.mCreatedTime;
    }

    public void setCreatedTime(long j) {
        this.mCreatedTime = j;
    }

    public long getModifiedTime() {
        return this.mModifiedTime;
    }

    public void setModifiedTime(long j) {
        this.mModifiedTime = j;
    }

    public boolean isOwnedByMe() {
        return this.mIsOwnedByMe;
    }

    public void setOwnedByMe(boolean z) {
        this.mIsOwnedByMe = z;
    }

    public Map getMetaData() {
        return this.mMetaData;
    }

    public void setMetaData(Map map) {
        this.mMetaData = map;
    }

    public String getLastModifierId() {
        return this.mLastModifierId;
    }

    public void setLastModifierId(String str) {
        this.mLastModifierId = str;
    }

    public static class Content {
        private String mFileName;
        private String mHash;
        private String mMimeType;
        private String mOriginalContentPath;
        private long mSize;
        private Uri mThumbnailFileUri;

        public Content(String str, String str2, Uri uri, String str3, long j, String str4) {
            this.mHash = str;
            this.mMimeType = str2;
            this.mThumbnailFileUri = uri;
            this.mFileName = str3;
            this.mSize = j;
            this.mOriginalContentPath = str4;
        }

        public String getHash() {
            return this.mHash;
        }

        public String getMimeType() {
            return this.mMimeType;
        }

        public String getOriginalContentPath() {
            return this.mOriginalContentPath;
        }

        public Uri getThumbnailFileUri() {
            return this.mThumbnailFileUri;
        }

        public long getSize() {
            return this.mSize;
        }

        public String getFileName() {
            return this.mFileName;
        }
    }
}
