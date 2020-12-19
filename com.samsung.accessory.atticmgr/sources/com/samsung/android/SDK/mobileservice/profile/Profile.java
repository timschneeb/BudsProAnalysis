package com.samsung.android.sdk.mobileservice.profile;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

public class Profile implements Parcelable {
    public static final int ACTION_TYPE_DELETE = 2;
    public static final int ACTION_TYPE_MODIFY = 1;
    public static final int ACTION_TYPE_NONE = 0;
    public static final int CALENDAR_TYPE_ADDED_VERSION = 4;
    public static final String CALENDAR_TYPE_LEAP = "2";
    public static final String CALENDAR_TYPE_LUNAR = "1";
    public static final String CALENDAR_TYPE_SOLAR = "0";
    public static final Parcelable.Creator<Profile> CREATOR = new Parcelable.Creator<Profile>() {
        /* class com.samsung.android.sdk.mobileservice.profile.Profile.AnonymousClass1 */

        @Override // android.os.Parcelable.Creator
        public Profile createFromParcel(Parcel parcel) {
            return new Profile(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public Profile[] newArray(int i) {
            return new Profile[i];
        }
    };
    private static final int PLACE_DELETED_VERSION = 3;
    public static final int PROFILE_VERSION = 4;
    private static int sConnectedProfileVersion = 4;
    private AccountBirthday mAccountBirthday;
    private AccountName mAccountName;
    private Birthday mBirthday;
    private EmailAddress mEmailAddress;
    private Event mEvent;
    private Gender mGender;
    private Health mHealth;
    private MessengerAccount mMessengerAccount;
    private Name mName;
    private Nickname mNickname;
    private Note mNote;
    private Organization mOrganization;
    private PhoneNumber mPhoneNumber;
    private Photo mPhoto;
    private StatusMessage mStatusMessage;
    private WebAddress mWebAddress;

    public int describeContents() {
        return 0;
    }

    public Profile(Parcel parcel) {
        readFromParcel(parcel);
    }

    public void readFromParcel(Parcel parcel) {
        sConnectedProfileVersion = parcel.readInt();
        this.mName = new Name(parcel);
        this.mAccountName = new AccountName(parcel);
        this.mBirthday = new Birthday(parcel);
        this.mAccountBirthday = new AccountBirthday(parcel);
        this.mNickname = new Nickname(parcel);
        this.mPhoto = new Photo(parcel);
        this.mOrganization = new Organization(parcel);
        this.mGender = new Gender(parcel);
        this.mStatusMessage = new StatusMessage(parcel);
        this.mNote = new Note(parcel);
        this.mMessengerAccount = new MessengerAccount(parcel);
        this.mPhoneNumber = new PhoneNumber(parcel);
        this.mEmailAddress = new EmailAddress(parcel);
        this.mWebAddress = new WebAddress(parcel);
        this.mEvent = new Event(parcel);
        this.mHealth = new Health(parcel);
        if (sConnectedProfileVersion < 3) {
            readDeprecatedPlaceData(parcel);
        }
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(4);
        this.mName.putParcelData(parcel);
        this.mAccountName.putParcelData(parcel);
        this.mBirthday.putParcelData(parcel);
        this.mAccountBirthday.putParcelData(parcel);
        this.mNickname.putParcelData(parcel);
        this.mPhoto.putParcelData(parcel);
        this.mOrganization.putParcelData(parcel);
        this.mGender.putParcelData(parcel);
        this.mStatusMessage.putParcelData(parcel);
        this.mNote.putParcelData(parcel);
        this.mMessengerAccount.putParcelData(parcel);
        this.mPhoneNumber.putParcelData(parcel);
        this.mEmailAddress.putParcelData(parcel);
        this.mWebAddress.putParcelData(parcel);
        this.mEvent.putParcelData(parcel);
        this.mHealth.putParcelData(parcel);
        if (sConnectedProfileVersion < 3) {
            writeDeprecatedPlaceData(parcel);
        }
    }

    private void readDeprecatedPlaceData(Parcel parcel) {
        parcel.readInt();
        parcel.readInt();
        parcel.readInt();
        parcel.readTypedList(new ArrayList(), PlaceData.CREATOR);
    }

    private void writeDeprecatedPlaceData(Parcel parcel) {
        parcel.writeInt(0);
        parcel.writeInt(0);
        parcel.writeInt(0);
        parcel.writeTypedList(new ArrayList());
    }

    public static class Name {
        private int mAction;
        private String mFamilyName;
        private String mGivenName;
        private boolean mLocked;
        private String mMiddleName;
        private String mPhoneticFamilyName;
        private String mPhoneticGivenName;
        private String mPhoneticMiddleName;
        private String mPrefixName;
        private String mSuffixName;

        public Name(Parcel parcel) {
            this.mLocked = parcel.readByte() != 1 ? false : true;
            this.mAction = parcel.readInt();
            this.mPrefixName = parcel.readString();
            this.mGivenName = parcel.readString();
            this.mMiddleName = parcel.readString();
            this.mFamilyName = parcel.readString();
            this.mSuffixName = parcel.readString();
            this.mPhoneticGivenName = parcel.readString();
            this.mPhoneticMiddleName = parcel.readString();
            this.mPhoneticFamilyName = parcel.readString();
        }

        public void putParcelData(Parcel parcel) {
            int i = 1;
            if (!this.mLocked) {
                i = 0;
            }
            parcel.writeByte((byte) i);
            parcel.writeInt(this.mAction);
            parcel.writeString(this.mPrefixName);
            parcel.writeString(this.mGivenName);
            parcel.writeString(this.mMiddleName);
            parcel.writeString(this.mFamilyName);
            parcel.writeString(this.mSuffixName);
            parcel.writeString(this.mPhoneticGivenName);
            parcel.writeString(this.mPhoneticMiddleName);
            parcel.writeString(this.mPhoneticFamilyName);
        }

        public Name() {
        }

        public int getAction() {
            return this.mAction;
        }

        public void setAction(int i) {
            this.mAction = i;
        }

        public boolean isLocked() {
            return this.mLocked;
        }

        public void setLock(boolean z) {
            this.mLocked = z;
        }

        public String getPrefixName() {
            return this.mPrefixName;
        }

        public String getGivenName() {
            return this.mGivenName;
        }

        public String getMiddleName() {
            return this.mMiddleName;
        }

        public String getFamilyName() {
            return this.mFamilyName;
        }

        public String getSuffixName() {
            return this.mSuffixName;
        }

        public String getPhoneticGivenName() {
            return this.mPhoneticGivenName;
        }

        public String getPhoneticMiddleName() {
            return this.mPhoneticMiddleName;
        }

        public String getPhoneticFamilyName() {
            return this.mPhoneticFamilyName;
        }

        public void setPrefixName(String str) {
            this.mPrefixName = str;
        }

        public void setGivenName(String str) {
            this.mGivenName = str;
        }

        public void setMiddleName(String str) {
            this.mMiddleName = str;
        }

        public void setFamilyName(String str) {
            this.mFamilyName = str;
        }

        public void setSuffixName(String str) {
            this.mSuffixName = str;
        }

        public void setPhoneticGivenName(String str) {
            this.mPhoneticGivenName = str;
        }

        public void setPhoneticMiddleName(String str) {
            this.mPhoneticMiddleName = str;
        }

        public void setPhoneticFamilyName(String str) {
            this.mPhoneticFamilyName = str;
        }

        public void setName(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
            this.mPrefixName = str;
            this.mGivenName = str2;
            this.mMiddleName = str3;
            this.mFamilyName = str4;
            this.mSuffixName = str5;
            this.mPhoneticGivenName = str6;
            this.mPhoneticMiddleName = str7;
            this.mPhoneticFamilyName = str8;
        }
    }

    public static class AccountName {
        private int mAction;
        private String mFamilyName;
        private String mGivenName;
        private boolean mLocked;

        public AccountName(Parcel parcel) {
            this.mLocked = parcel.readByte() != 1 ? false : true;
            this.mAction = parcel.readInt();
            this.mGivenName = parcel.readString();
            this.mFamilyName = parcel.readString();
        }

        public void putParcelData(Parcel parcel) {
            int i = 1;
            if (!this.mLocked) {
                i = 0;
            }
            parcel.writeByte((byte) i);
            parcel.writeInt(this.mAction);
            parcel.writeString(this.mGivenName);
            parcel.writeString(this.mFamilyName);
        }

        public AccountName() {
        }

        public int getAction() {
            return this.mAction;
        }

        public void setAction(int i) {
            this.mAction = i;
        }

        public boolean isLocked() {
            return this.mLocked;
        }

        public void setLock(boolean z) {
            this.mLocked = z;
        }

        public String getGivenName() {
            return this.mGivenName;
        }

        public String getFamilyName() {
            return this.mFamilyName;
        }

        public void setName(String str, String str2) {
            this.mGivenName = str;
            this.mFamilyName = str2;
        }
    }

    public static class Birthday {
        private int mAction;
        private String mCalendarType;
        private String mDay;
        private boolean mLocked;
        private String mMonth;
        private String mYear;

        public Birthday(Parcel parcel) {
            readFromParcel(parcel);
        }

        public int getAction() {
            return this.mAction;
        }

        public void setAction(int i) {
            this.mAction = i;
        }

        public Birthday() {
        }

        public boolean isLocked() {
            return this.mLocked;
        }

        public void setLock(boolean z) {
            this.mLocked = z;
        }

        public String getYear() {
            return this.mYear;
        }

        public String getMonth() {
            return this.mMonth;
        }

        public String getDay() {
            return this.mDay;
        }

        public String getCalendarType() {
            return this.mCalendarType;
        }

        public void setYear(String str) {
            this.mYear = str;
        }

        public void setMonth(String str) {
            this.mMonth = str;
        }

        public void setDay(String str) {
            this.mDay = str;
        }

        public void setCalendarType(String str) {
            this.mCalendarType = str;
        }

        public void setBirthday(String str, String str2, String str3) {
            this.mYear = str;
            this.mMonth = str2;
            this.mDay = str3;
        }

        public void setBirthday(String str, String str2, String str3, String str4) {
            this.mYear = str;
            this.mMonth = str2;
            this.mDay = str3;
            this.mCalendarType = str4;
        }

        public void readFromParcel(Parcel parcel) {
            boolean z = true;
            if (parcel.readByte() != 1) {
                z = false;
            }
            this.mLocked = z;
            this.mAction = parcel.readInt();
            this.mYear = parcel.readString();
            this.mMonth = parcel.readString();
            this.mDay = parcel.readString();
            if (Profile.sConnectedProfileVersion >= 4) {
                this.mCalendarType = parcel.readString();
            }
        }

        public void putParcelData(Parcel parcel) {
            parcel.writeByte(this.mLocked ? (byte) 1 : 0);
            parcel.writeInt(this.mAction);
            parcel.writeString(this.mYear);
            parcel.writeString(this.mMonth);
            parcel.writeString(this.mDay);
            if (Profile.sConnectedProfileVersion >= 4) {
                parcel.writeString(this.mCalendarType);
            }
        }
    }

    public static class AccountBirthday {
        private int mAction;
        private String mDay;
        private boolean mLocked;
        private String mMonth;
        private String mYear;

        public AccountBirthday(Parcel parcel) {
            readFromParcel(parcel);
        }

        public int getAction() {
            return this.mAction;
        }

        public void setAction(int i) {
            this.mAction = i;
        }

        public AccountBirthday() {
        }

        public boolean isLocked() {
            return this.mLocked;
        }

        public void setLock(boolean z) {
            this.mLocked = z;
        }

        public String getYear() {
            return this.mYear;
        }

        public String getMonth() {
            return this.mMonth;
        }

        public String getDay() {
            return this.mDay;
        }

        public void setBirthday(String str, String str2, String str3) {
            this.mYear = str;
            this.mMonth = str2;
            this.mDay = str3;
        }

        public void readFromParcel(Parcel parcel) {
            boolean z = true;
            if (parcel.readByte() != 1) {
                z = false;
            }
            this.mLocked = z;
            this.mAction = parcel.readInt();
            this.mYear = parcel.readString();
            this.mMonth = parcel.readString();
            this.mDay = parcel.readString();
        }

        public void putParcelData(Parcel parcel) {
            parcel.writeByte(this.mLocked ? (byte) 1 : 0);
            parcel.writeInt(this.mAction);
            parcel.writeString(this.mYear);
            parcel.writeString(this.mMonth);
            parcel.writeString(this.mDay);
        }
    }

    public static class Nickname {
        private int mAction;
        private boolean mLocked;
        private String mNickName;

        public Nickname(Parcel parcel) {
            this.mLocked = parcel.readByte() != 1 ? false : true;
            this.mAction = parcel.readInt();
            this.mNickName = parcel.readString();
        }

        public void putParcelData(Parcel parcel) {
            parcel.writeByte(this.mLocked ? (byte) 1 : 0);
            parcel.writeInt(this.mAction);
            parcel.writeString(this.mNickName);
        }

        public Nickname() {
        }

        public int getAction() {
            return this.mAction;
        }

        public void setAction(int i) {
            this.mAction = i;
        }

        public boolean isLocked() {
            return this.mLocked;
        }

        public void setLock(boolean z) {
            this.mLocked = z;
        }

        public String getNickname() {
            return this.mNickName;
        }

        public void setNickname(String str) {
            this.mNickName = str;
        }
    }

    public static class Photo {
        private int mAction;
        private boolean mLocked;
        private byte[] mPhoto;
        private int mPhotoSize;
        private String mPhotoType;

        public Photo(Parcel parcel) {
            this.mLocked = parcel.readByte() != 1 ? false : true;
            this.mAction = parcel.readInt();
            this.mPhotoSize = parcel.readInt();
            int i = this.mPhotoSize;
            if (i > 0) {
                this.mPhoto = new byte[i];
                parcel.readByteArray(this.mPhoto);
            }
            this.mPhotoType = parcel.readString();
        }

        public void putParcelData(Parcel parcel) {
            parcel.writeByte(this.mLocked ? (byte) 1 : 0);
            parcel.writeInt(this.mAction);
            parcel.writeInt(this.mPhotoSize);
            if (this.mPhotoSize > 0) {
                parcel.writeByteArray(this.mPhoto);
            }
            parcel.writeString(this.mPhotoType);
        }

        public Photo() {
        }

        public int getAction() {
            return this.mAction;
        }

        public void setAction(int i) {
            this.mAction = i;
        }

        public boolean isLocked() {
            return this.mLocked;
        }

        public void setLock(boolean z) {
            this.mLocked = z;
        }

        public byte[] getPhoto() {
            return this.mPhoto;
        }

        public void setPhoto(byte[] bArr) {
            this.mPhoto = bArr;
            if (bArr == null) {
                this.mPhotoSize = 0;
            } else {
                this.mPhotoSize = bArr.length;
            }
        }

        public String getPhotoType() {
            return this.mPhotoType;
        }

        public void setPhotoType(String str) {
            this.mPhotoType = str;
        }
    }

    public static class Organization {
        private int mAction;
        private String mCompany;
        private String mDepartment;
        private boolean mLocked;
        private String mTitle;

        public Organization(Parcel parcel) {
            this.mLocked = parcel.readByte() != 1 ? false : true;
            this.mAction = parcel.readInt();
            this.mCompany = parcel.readString();
            this.mDepartment = parcel.readString();
            this.mTitle = parcel.readString();
        }

        public void putParcelData(Parcel parcel) {
            parcel.writeByte(this.mLocked ? (byte) 1 : 0);
            parcel.writeInt(this.mAction);
            parcel.writeString(this.mCompany);
            parcel.writeString(this.mDepartment);
            parcel.writeString(this.mTitle);
        }

        public Organization() {
        }

        public int getAction() {
            return this.mAction;
        }

        public void setAction(int i) {
            this.mAction = i;
        }

        public boolean isLocked() {
            return this.mLocked;
        }

        public void setLock(boolean z) {
            this.mLocked = z;
        }

        public String getCompany() {
            return this.mCompany;
        }

        public String getDepartment() {
            return this.mDepartment;
        }

        public String getTitle() {
            return this.mTitle;
        }

        public void setCompany(String str) {
            this.mCompany = str;
        }

        public void setDepartment(String str) {
            this.mDepartment = str;
        }

        public void setTitle(String str) {
            this.mTitle = str;
        }

        public void setOrganization(String str, String str2, String str3) {
            this.mCompany = str;
            this.mDepartment = str2;
            this.mTitle = str3;
        }
    }

    public static class Gender {
        public static final String TYPE_FEMALE = "female";
        public static final String TYPE_MALE = "male";
        private int mAction;
        private boolean mLocked;
        private String mValue;

        public Gender(Parcel parcel) {
            this.mLocked = parcel.readByte() != 1 ? false : true;
            this.mAction = parcel.readInt();
            this.mValue = parcel.readString();
        }

        public void putParcelData(Parcel parcel) {
            int i = 1;
            if (!this.mLocked) {
                i = 0;
            }
            parcel.writeByte((byte) i);
            parcel.writeInt(this.mAction);
            parcel.writeString(this.mValue);
        }

        public Gender() {
        }

        public int getAction() {
            return this.mAction;
        }

        public void setAction(int i) {
            this.mAction = i;
        }

        public boolean isLocked() {
            return this.mLocked;
        }

        public void setLock(boolean z) {
            this.mLocked = z;
        }

        public String getGender() {
            return this.mValue;
        }

        public boolean setGender(String str) {
            this.mValue = str;
            return true;
        }
    }

    public static class StatusMessage {
        private int mAction;
        private boolean mLocked;
        private String mValue;

        public StatusMessage(Parcel parcel) {
            this.mLocked = parcel.readByte() != 1 ? false : true;
            this.mAction = parcel.readInt();
            this.mValue = parcel.readString();
        }

        public void putParcelData(Parcel parcel) {
            parcel.writeByte(this.mLocked ? (byte) 1 : 0);
            parcel.writeInt(this.mAction);
            parcel.writeString(this.mValue);
        }

        public StatusMessage() {
        }

        public int getAction() {
            return this.mAction;
        }

        public void setAction(int i) {
            this.mAction = i;
        }

        public boolean isLocked() {
            return this.mLocked;
        }

        public void setLock(boolean z) {
            this.mLocked = z;
        }

        public String getStatusMessage() {
            return this.mValue;
        }

        public void setStatusMessage(String str) {
            this.mValue = str;
        }
    }

    public static class Note {
        private int mAction;
        private boolean mLocked;
        private String mValue;

        public Note(Parcel parcel) {
            this.mLocked = parcel.readByte() != 1 ? false : true;
            this.mAction = parcel.readInt();
            this.mValue = parcel.readString();
        }

        public void putParcelData(Parcel parcel) {
            parcel.writeByte(this.mLocked ? (byte) 1 : 0);
            parcel.writeInt(this.mAction);
            parcel.writeString(this.mValue);
        }

        public Note() {
        }

        public int getAction() {
            return this.mAction;
        }

        public void setAction(int i) {
            this.mAction = i;
        }

        public boolean isLocked() {
            return this.mLocked;
        }

        public void setLock(boolean z) {
            this.mLocked = z;
        }

        public String getNote() {
            return this.mValue;
        }

        public void setNote(String str) {
            this.mValue = str;
        }
    }

    public static class MessengerAccount {
        private int mAction;
        private ArrayList<MessengerAccountData> mMessengerAccountData;

        public int getAction() {
            return this.mAction;
        }

        public void setAction(int i) {
            this.mAction = i;
        }

        public MessengerAccount() {
            this.mMessengerAccountData = new ArrayList<>();
        }

        public MessengerAccount(Parcel parcel) {
            readFromParcel(parcel);
        }

        public ArrayList<MessengerAccountData> getMessengerAccountData() {
            return this.mMessengerAccountData;
        }

        public void readFromParcel(Parcel parcel) {
            this.mAction = parcel.readInt();
            this.mMessengerAccountData = new ArrayList<>();
            parcel.readTypedList(this.mMessengerAccountData, MessengerAccountData.CREATOR);
        }

        public void putParcelData(Parcel parcel) {
            parcel.writeInt(this.mAction);
            parcel.writeTypedList(this.mMessengerAccountData);
        }
    }

    public static class MessengerAccountData implements Parcelable {
        public static final Parcelable.Creator<MessengerAccountData> CREATOR = new Parcelable.Creator<MessengerAccountData>() {
            /* class com.samsung.android.sdk.mobileservice.profile.Profile.MessengerAccountData.AnonymousClass1 */

            @Override // android.os.Parcelable.Creator
            public MessengerAccountData createFromParcel(Parcel parcel) {
                return new MessengerAccountData(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public MessengerAccountData[] newArray(int i) {
                return new MessengerAccountData[i];
            }
        };
        public static final String TYPE_AIM = "AIM";
        public static final String TYPE_CUSTOM = "custom";
        public static final String TYPE_FACEBOOK = "facebook";
        public static final String TYPE_HANGOUTS = "hangouts";
        public static final String TYPE_ICQ = "ICQ";
        public static final String TYPE_JABBER = "jabber";
        public static final String TYPE_QQ = "QQ";
        public static final String TYPE_SKYPE = "skype";
        public static final String TYPE_WHATSAPP = "whatsApp";
        public static final String TYPE_WINDOWS_LIVE = "windowsLive";
        public static final String TYPE_YAHOO = "yahoo";
        private String mLabel;
        private boolean mLocked;
        private String mType;
        private String mValue;

        public int describeContents() {
            return 0;
        }

        public MessengerAccountData(Parcel parcel) {
            readFromParcel(parcel);
        }

        public void readFromParcel(Parcel parcel) {
            boolean z = true;
            if (parcel.readByte() != 1) {
                z = false;
            }
            this.mLocked = z;
            this.mValue = parcel.readString();
            this.mType = parcel.readString();
            this.mLabel = parcel.readString();
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeByte(this.mLocked ? (byte) 1 : 0);
            parcel.writeString(this.mValue);
            parcel.writeString(this.mType);
            parcel.writeString(this.mLabel);
        }

        public MessengerAccountData() {
        }

        public boolean isLocked() {
            return this.mLocked;
        }

        public void setLock(boolean z) {
            this.mLocked = z;
        }

        public String getMessengerAccount() {
            return this.mValue;
        }

        public String getType() {
            return this.mType;
        }

        public String getLabel() {
            return this.mLabel;
        }

        public boolean setMessengerAccount(String str, String str2, String str3) {
            this.mType = str2;
            this.mValue = str;
            this.mLabel = str3;
            return true;
        }
    }

    public static class PhoneNumber {
        private int mAction;
        private ArrayList<PhoneNumberData> mPhoneNumberData;

        public int getAction() {
            return this.mAction;
        }

        public void setAction(int i) {
            this.mAction = i;
        }

        public PhoneNumber() {
            this.mPhoneNumberData = new ArrayList<>();
        }

        public PhoneNumber(Parcel parcel) {
            readFromParcel(parcel);
        }

        public ArrayList<PhoneNumberData> getPhoneNumberData() {
            return this.mPhoneNumberData;
        }

        public void readFromParcel(Parcel parcel) {
            this.mAction = parcel.readInt();
            this.mPhoneNumberData = new ArrayList<>();
            parcel.readTypedList(this.mPhoneNumberData, PhoneNumberData.CREATOR);
        }

        public void putParcelData(Parcel parcel) {
            parcel.writeInt(this.mAction);
            parcel.writeTypedList(this.mPhoneNumberData);
        }
    }

    public static class PhoneNumberData implements Parcelable {
        public static final Parcelable.Creator<PhoneNumberData> CREATOR = new Parcelable.Creator<PhoneNumberData>() {
            /* class com.samsung.android.sdk.mobileservice.profile.Profile.PhoneNumberData.AnonymousClass1 */

            @Override // android.os.Parcelable.Creator
            public PhoneNumberData createFromParcel(Parcel parcel) {
                return new PhoneNumberData(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public PhoneNumberData[] newArray(int i) {
                return new PhoneNumberData[i];
            }
        };
        public static final String TYPE_CALLBACK = "callback";
        public static final String TYPE_CUSTOM = "custom";
        public static final String TYPE_HOME = "home";
        public static final String TYPE_HOME_FAX = "homeFax";
        public static final String TYPE_MOBILE = "mobile";
        public static final String TYPE_OTHER = "other";
        public static final String TYPE_PAGER = "pager";
        public static final String TYPE_REPRESENTATIVE = "representative";
        public static final String TYPE_WORK = "work";
        public static final String TYPE_WORK_FAX = "workFax";
        private String mLabel;
        private boolean mLocked;
        private String mType;
        private String mValue;

        public int describeContents() {
            return 0;
        }

        public PhoneNumberData(Parcel parcel) {
            readFromParcel(parcel);
        }

        public void readFromParcel(Parcel parcel) {
            boolean z = true;
            if (parcel.readByte() != 1) {
                z = false;
            }
            this.mLocked = z;
            this.mValue = parcel.readString();
            this.mType = parcel.readString();
            this.mLabel = parcel.readString();
        }

        public PhoneNumberData() {
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeByte(this.mLocked ? (byte) 1 : 0);
            parcel.writeString(this.mValue);
            parcel.writeString(this.mType);
            parcel.writeString(this.mLabel);
        }

        public boolean isLocked() {
            return this.mLocked;
        }

        public void setLock(boolean z) {
            this.mLocked = z;
        }

        public String getPhoneNumber() {
            return this.mValue;
        }

        public String getType() {
            return this.mType;
        }

        public String getLabel() {
            return this.mLabel;
        }

        public boolean setPhoneNumber(boolean z, String str, String str2, String str3) {
            this.mLocked = z;
            this.mType = str2;
            this.mValue = str;
            this.mLabel = str3;
            return true;
        }
    }

    public static class EmailAddress {
        private int mAction;
        private ArrayList<EmailAddressData> mEmailAddressData;

        public int getAction() {
            return this.mAction;
        }

        public void setAction(int i) {
            this.mAction = i;
        }

        public EmailAddress() {
            this.mEmailAddressData = new ArrayList<>();
        }

        public EmailAddress(Parcel parcel) {
            readFromParcel(parcel);
        }

        public ArrayList<EmailAddressData> getEmailAddressData() {
            return this.mEmailAddressData;
        }

        public void readFromParcel(Parcel parcel) {
            this.mAction = parcel.readInt();
            this.mEmailAddressData = new ArrayList<>();
            parcel.readTypedList(this.mEmailAddressData, EmailAddressData.CREATOR);
        }

        public void putParcelData(Parcel parcel) {
            parcel.writeInt(this.mAction);
            parcel.writeTypedList(this.mEmailAddressData);
        }
    }

    public static class EmailAddressData implements Parcelable {
        public static final Parcelable.Creator<EmailAddressData> CREATOR = new Parcelable.Creator<EmailAddressData>() {
            /* class com.samsung.android.sdk.mobileservice.profile.Profile.EmailAddressData.AnonymousClass1 */

            @Override // android.os.Parcelable.Creator
            public EmailAddressData createFromParcel(Parcel parcel) {
                return new EmailAddressData(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public EmailAddressData[] newArray(int i) {
                return new EmailAddressData[i];
            }
        };
        public static final String TYPE_CUSTOM = "custom";
        public static final String TYPE_HOME = "home";
        public static final String TYPE_OTHER = "other";
        public static final String TYPE_REPRESENTATIVE = "representative";
        public static final String TYPE_WORK = "work";
        private String mLabel;
        private boolean mLocked;
        private String mType;
        private String mValue;

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeByte(this.mLocked ? (byte) 1 : 0);
            parcel.writeString(this.mValue);
            parcel.writeString(this.mType);
            parcel.writeString(this.mLabel);
        }

        public EmailAddressData(Parcel parcel) {
            readFromParcel(parcel);
        }

        public void readFromParcel(Parcel parcel) {
            boolean z = true;
            if (parcel.readByte() != 1) {
                z = false;
            }
            this.mLocked = z;
            this.mValue = parcel.readString();
            this.mType = parcel.readString();
            this.mLabel = parcel.readString();
        }

        public EmailAddressData() {
        }

        public boolean isLocked() {
            return this.mLocked;
        }

        public void setLock(boolean z) {
            this.mLocked = z;
        }

        public String getEmailAddress() {
            return this.mValue;
        }

        public String getType() {
            return this.mType;
        }

        public String getLabel() {
            return this.mLabel;
        }

        public boolean setEmailAddress(boolean z, String str, String str2, String str3) {
            this.mLocked = z;
            this.mType = str2;
            this.mValue = str;
            this.mLabel = str3;
            return true;
        }
    }

    public static class WebAddress {
        private int mAction;
        private ArrayList<WebAddressData> mWebAddressData;

        public int getAction() {
            return this.mAction;
        }

        public void setAction(int i) {
            this.mAction = i;
        }

        public WebAddress() {
            this.mWebAddressData = new ArrayList<>();
        }

        public WebAddress(Parcel parcel) {
            readFromParcel(parcel);
        }

        public ArrayList<WebAddressData> getWebAddressData() {
            return this.mWebAddressData;
        }

        public void readFromParcel(Parcel parcel) {
            this.mAction = parcel.readInt();
            this.mWebAddressData = new ArrayList<>();
            parcel.readTypedList(this.mWebAddressData, WebAddressData.CREATOR);
        }

        public void putParcelData(Parcel parcel) {
            parcel.writeInt(this.mAction);
            parcel.writeTypedList(this.mWebAddressData);
        }
    }

    public static class WebAddressData implements Parcelable {
        public static final Parcelable.Creator<WebAddressData> CREATOR = new Parcelable.Creator<WebAddressData>() {
            /* class com.samsung.android.sdk.mobileservice.profile.Profile.WebAddressData.AnonymousClass1 */

            @Override // android.os.Parcelable.Creator
            public WebAddressData createFromParcel(Parcel parcel) {
                return new WebAddressData(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public WebAddressData[] newArray(int i) {
                return new WebAddressData[i];
            }
        };
        private boolean mLocked;
        private String mValue;

        public int describeContents() {
            return 0;
        }

        public WebAddressData(Parcel parcel) {
            readFromParcel(parcel);
        }

        public void readFromParcel(Parcel parcel) {
            boolean z = true;
            if (parcel.readByte() != 1) {
                z = false;
            }
            this.mLocked = z;
            this.mValue = parcel.readString();
        }

        public WebAddressData() {
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeByte(this.mLocked ? (byte) 1 : 0);
            parcel.writeString(this.mValue);
        }

        public boolean isLocked() {
            return this.mLocked;
        }

        public void setLock(boolean z) {
            this.mLocked = z;
        }

        public String getWebAddress() {
            return this.mValue;
        }

        public void setWebAddress(String str) {
            this.mValue = str;
        }
    }

    public static class PlaceData implements Parcelable {
        public static final Parcelable.Creator<PlaceData> CREATOR = new Parcelable.Creator<PlaceData>() {
            /* class com.samsung.android.sdk.mobileservice.profile.Profile.PlaceData.AnonymousClass1 */

            @Override // android.os.Parcelable.Creator
            public PlaceData createFromParcel(Parcel parcel) {
                return new PlaceData(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public PlaceData[] newArray(int i) {
                return new PlaceData[i];
            }
        };
        private String mAddress;
        private String mBluetoothMacAddress;
        private String mBluetoothName;
        private int mCategory;
        private Double mLatitude;
        private String mLocationType;
        private boolean mLocked;
        private Double mLongitude;
        private String mName;
        private String mPlaceKey;
        private Long mTimeStamp;
        private int mType;
        private String mWifiBssId;
        private String mWifiName;

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeByte(this.mLocked ? (byte) 1 : 0);
            parcel.writeString(this.mPlaceKey);
            parcel.writeString(this.mName);
            parcel.writeInt(this.mCategory);
            parcel.writeInt(this.mType);
            parcel.writeString(this.mLocationType);
            parcel.writeString(this.mAddress);
            parcel.writeDouble(this.mLatitude.doubleValue());
            parcel.writeDouble(this.mLongitude.doubleValue());
            parcel.writeString(this.mWifiName);
            parcel.writeString(this.mWifiBssId);
            parcel.writeString(this.mBluetoothName);
            parcel.writeString(this.mBluetoothMacAddress);
            parcel.writeLong(this.mTimeStamp.longValue());
        }

        public PlaceData(Parcel parcel) {
            readFromParcel(parcel);
        }

        public void readFromParcel(Parcel parcel) {
            boolean z = true;
            if (parcel.readByte() != 1) {
                z = false;
            }
            this.mLocked = z;
            this.mPlaceKey = parcel.readString();
            this.mName = parcel.readString();
            this.mCategory = parcel.readInt();
            this.mType = parcel.readInt();
            this.mLocationType = parcel.readString();
            this.mAddress = parcel.readString();
            this.mLatitude = Double.valueOf(parcel.readDouble());
            this.mLongitude = Double.valueOf(parcel.readDouble());
            this.mWifiName = parcel.readString();
            this.mWifiBssId = parcel.readString();
            this.mBluetoothName = parcel.readString();
            this.mBluetoothMacAddress = parcel.readString();
            this.mTimeStamp = Long.valueOf(parcel.readLong());
        }
    }

    public static class Event {
        private int mAction;
        private ArrayList<EventData> mEventData = new ArrayList<>();

        public Event(Parcel parcel) {
            this.mAction = parcel.readInt();
            parcel.readTypedList(this.mEventData, EventData.CREATOR);
        }

        public void putParcelData(Parcel parcel) {
            parcel.writeInt(this.mAction);
            parcel.writeTypedList(this.mEventData);
        }

        public Event() {
        }

        public int getAction() {
            return this.mAction;
        }

        public void setAction(int i) {
            this.mAction = i;
        }

        public ArrayList<EventData> getEventData() {
            return this.mEventData;
        }
    }

    public static class EventData implements Parcelable {
        public static final Parcelable.Creator<EventData> CREATOR = new Parcelable.Creator<EventData>() {
            /* class com.samsung.android.sdk.mobileservice.profile.Profile.EventData.AnonymousClass1 */

            @Override // android.os.Parcelable.Creator
            public EventData createFromParcel(Parcel parcel) {
                return new EventData(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public EventData[] newArray(int i) {
                return new EventData[i];
            }
        };
        public static final String TYPE_ANNIVERSARY = "anniversary";
        public static final String TYPE_CUSTOM = "custom";
        public static final String TYPE_OTHER = "other";
        private String mCalendarType;
        private String mLabel;
        private boolean mLocked;
        private String mType;
        private String mValue;

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeByte(this.mLocked ? (byte) 1 : 0);
            parcel.writeString(this.mValue);
            parcel.writeString(this.mType);
            parcel.writeString(this.mLabel);
            if (Profile.sConnectedProfileVersion >= 4) {
                parcel.writeString(this.mCalendarType);
            }
        }

        public EventData(Parcel parcel) {
            readFromParcel(parcel);
        }

        public void readFromParcel(Parcel parcel) {
            boolean z = true;
            if (parcel.readByte() != 1) {
                z = false;
            }
            this.mLocked = z;
            this.mValue = parcel.readString();
            this.mType = parcel.readString();
            this.mLabel = parcel.readString();
            if (Profile.sConnectedProfileVersion >= 4) {
                this.mCalendarType = parcel.readString();
            }
        }

        public EventData() {
        }

        public boolean isLocked() {
            return this.mLocked;
        }

        public void setLock(boolean z) {
            this.mLocked = z;
        }

        public String getEvent() {
            return this.mValue;
        }

        public String getType() {
            return this.mType;
        }

        public String getLabel() {
            return this.mLabel;
        }

        public String getCalendarType() {
            return this.mCalendarType;
        }

        public boolean setEvent(String str, String str2, String str3) {
            this.mType = str2;
            this.mValue = str;
            this.mLabel = str3;
            return true;
        }

        public boolean setEvent(String str, String str2, String str3, String str4) {
            this.mType = str2;
            this.mValue = str;
            this.mLabel = str3;
            this.mCalendarType = str4;
            return true;
        }
    }

    public static class Health {
        private int mAction;
        private String mActivityLevel;
        private String mHeight;
        private boolean mLocked;
        private String mWeight;

        public Health(Parcel parcel) {
            this.mLocked = parcel.readByte() != 1 ? false : true;
            this.mAction = parcel.readInt();
            this.mHeight = parcel.readString();
            this.mWeight = parcel.readString();
            this.mActivityLevel = parcel.readString();
        }

        public void putParcelData(Parcel parcel) {
            parcel.writeByte(this.mLocked ? (byte) 1 : 0);
            parcel.writeInt(this.mAction);
            parcel.writeString(this.mHeight);
            parcel.writeString(this.mWeight);
            parcel.writeString(this.mActivityLevel);
        }

        public Health() {
        }

        public int getAction() {
            return this.mAction;
        }

        public void setAction(int i) {
            this.mAction = i;
        }

        public boolean isLocked() {
            return this.mLocked;
        }

        public void setLock(boolean z) {
            this.mLocked = z;
        }

        public String getHeight() {
            return this.mHeight;
        }

        public String getWeight() {
            return this.mWeight;
        }

        public String getActivityLevel() {
            return this.mActivityLevel;
        }

        public void setHeight(String str) {
            this.mHeight = str;
        }

        public void setWeight(String str) {
            this.mWeight = str;
        }

        public void setActivityLevel(String str) {
            this.mActivityLevel = str;
        }

        public void setHealth(String str, String str2, String str3) {
            this.mHeight = str;
            this.mWeight = str2;
            this.mActivityLevel = str3;
        }
    }

    public Profile() {
        this.mName = new Name();
        this.mAccountName = new AccountName();
        this.mBirthday = new Birthday();
        this.mAccountBirthday = new AccountBirthday();
        this.mNickname = new Nickname();
        this.mPhoto = new Photo();
        this.mOrganization = new Organization();
        this.mGender = new Gender();
        this.mStatusMessage = new StatusMessage();
        this.mNote = new Note();
        this.mMessengerAccount = new MessengerAccount();
        this.mPhoneNumber = new PhoneNumber();
        this.mEmailAddress = new EmailAddress();
        this.mWebAddress = new WebAddress();
        this.mEvent = new Event();
        this.mHealth = new Health();
    }

    public int getConnectedProfileVersion() {
        return sConnectedProfileVersion;
    }

    public void setConnectedProfileVersion(int i) {
        sConnectedProfileVersion = i;
    }

    public Name getNameInstance() {
        return this.mName;
    }

    public AccountName getAccountNameInstance() {
        return this.mAccountName;
    }

    public Birthday getBirthdayInstance() {
        return this.mBirthday;
    }

    public AccountBirthday getAccountBirthdayInstance() {
        return this.mAccountBirthday;
    }

    public Nickname getNicknameInstance() {
        return this.mNickname;
    }

    public Photo getPhotoInstance() {
        return this.mPhoto;
    }

    public Organization getOrganizationInstance() {
        return this.mOrganization;
    }

    public Gender getGenderInstance() {
        return this.mGender;
    }

    public StatusMessage getStatusMessageInstance() {
        return this.mStatusMessage;
    }

    public Note getNoteInstance() {
        return this.mNote;
    }

    public MessengerAccount getMessengerAccountInstance() {
        return this.mMessengerAccount;
    }

    public PhoneNumber getPhoneNumberInstance() {
        return this.mPhoneNumber;
    }

    public EmailAddress getEmailAddressInstance() {
        return this.mEmailAddress;
    }

    public WebAddress getWebAddressInstance() {
        return this.mWebAddress;
    }

    public Event getEventInstance() {
        return this.mEvent;
    }

    public Health getHealthInstance() {
        return this.mHealth;
    }
}
