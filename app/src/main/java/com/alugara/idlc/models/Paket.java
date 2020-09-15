package com.alugara.idlc.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Paket implements Parcelable {
    String id, title, price, benefit;

    public Paket() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBenefit() {
        return benefit;
    }

    public void setBenefit(String benefit) {
        this.benefit = benefit;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.price);
        dest.writeString(this.benefit);
    }

    protected Paket(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.price = in.readString();
        this.benefit = in.readString();
    }

    public static final Parcelable.Creator<Paket> CREATOR = new Parcelable.Creator<Paket>() {
        @Override
        public Paket createFromParcel(Parcel source) {
            return new Paket(source);
        }

        @Override
        public Paket[] newArray(int size) {
            return new Paket[size];
        }
    };
}
