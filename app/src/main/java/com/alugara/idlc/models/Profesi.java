package com.alugara.idlc.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class Profesi implements Parcelable {
    String id, name;

    public Profesi(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
    }

    protected Profesi(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<Profesi> CREATOR = new Parcelable.Creator<Profesi>() {
        @Override
        public Profesi createFromParcel(Parcel source) {
            return new Profesi(source);
        }

        @Override
        public Profesi[] newArray(int size) {
            return new Profesi[size];
        }
    };

    @Override
    public String toString() {
        return name;
    }
}
