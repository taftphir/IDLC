package com.alugara.idlc.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Kategori implements Parcelable {
    String id, name, parent, status, img;

    public Kategori(String id, String name, String parent, String status, String img) {
        this.id = id;
        this.name = name;
        this.parent = parent;
        this.status = status;
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getParent() {
        return parent;
    }

    public String getStatus() {
        return status;
    }

    public String getImg() {
        return img;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.parent);
        dest.writeString(this.status);
        dest.writeString(this.img);
    }

    protected Kategori(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.parent = in.readString();
        this.status = in.readString();
        this.img = in.readString();
    }

    public static final Parcelable.Creator<Kategori> CREATOR = new Parcelable.Creator<Kategori>() {
        @Override
        public Kategori createFromParcel(Parcel source) {
            return new Kategori(source);
        }

        @Override
        public Kategori[] newArray(int size) {
            return new Kategori[size];
        }
    };
}
