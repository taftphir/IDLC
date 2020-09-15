package com.alugara.idlc.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.alugara.idlc.BuildConfig;

public class Seminar implements Parcelable {
    String id, nama, poster, hari, tanggal, biaya, tempat, file;

    public Seminar(String id, String nama, String poster, String hari, String tanggal, String biaya, String tempat, String file) {
        this.id = id;
        this.nama = nama;
        this.poster = poster;
        this.hari = hari;
        this.tanggal = tanggal;
        this.biaya = biaya;
        this.tempat = tempat;
        this.file = file;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getPoster() {
        return BuildConfig.BASE_IMAGE_SEMINAR+poster;
    }

    public String getHari() {
        return hari;
    }

    public String getFile() {
        return file;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getBiaya() {
        return biaya;
    }

    public String getTempat() {
        return tempat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.nama);
        dest.writeString(this.poster);
        dest.writeString(this.hari);
        dest.writeString(this.tanggal);
        dest.writeString(this.biaya);
        dest.writeString(this.tempat);
        dest.writeString(this.file);
    }

    protected Seminar(Parcel in) {
        this.id = in.readString();
        this.nama = in.readString();
        this.poster = in.readString();
        this.hari = in.readString();
        this.tanggal = in.readString();
        this.biaya = in.readString();
        this.tempat = in.readString();
        this.file = in.readString();
    }

    public static final Creator<Seminar> CREATOR = new Creator<Seminar>() {
        @Override
        public Seminar createFromParcel(Parcel source) {
            return new Seminar(source);
        }

        @Override
        public Seminar[] newArray(int size) {
            return new Seminar[size];
        }
    };
}
