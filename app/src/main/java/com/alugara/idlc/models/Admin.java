package com.alugara.idlc.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Admin implements Parcelable {
    String id, name, online;

    public Admin(String id, String name, String online) {
        this.id = id;
        this.name = name;
        this.online = online;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOnline() {
        return online;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.online);
    }

    protected Admin(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.online = in.readString();
    }

    public static final Parcelable.Creator<Admin> CREATOR = new Parcelable.Creator<Admin>() {
        @Override
        public Admin createFromParcel(Parcel source) {
            return new Admin(source);
        }

        @Override
        public Admin[] newArray(int size) {
            return new Admin[size];
        }
    };
}
