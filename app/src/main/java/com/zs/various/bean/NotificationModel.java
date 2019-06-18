package com.zs.various.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zs
 * @Date: 2019-06-18 14:37
 * @Description:
 */
public class NotificationModel implements Parcelable {

    public int notificationId;

    public Class<?> nextPage;

    public Map<String , String> params;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.notificationId);
        dest.writeSerializable(this.nextPage);
        dest.writeInt(this.params.size());
        for (Map.Entry<String, String> entry : this.params.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
    }

    public NotificationModel() {
    }

    protected NotificationModel(Parcel in) {
        this.notificationId = in.readInt();
        this.nextPage = (Class<?>) in.readSerializable();
        int paramsSize = in.readInt();
        this.params = new HashMap<String, String>(paramsSize);
        for (int i = 0; i < paramsSize; i++) {
            String key = in.readString();
            String value = in.readString();
            this.params.put(key, value);
        }
    }

    public static final Parcelable.Creator<NotificationModel> CREATOR = new Parcelable.Creator<NotificationModel>() {
        @Override
        public NotificationModel createFromParcel(Parcel source) {
            return new NotificationModel(source);
        }

        @Override
        public NotificationModel[] newArray(int size) {
            return new NotificationModel[size];
        }
    };
}
