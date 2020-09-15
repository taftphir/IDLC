package com.alugara.idlc.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.alugara.idlc.BuildConfig;

public class Files implements Parcelable {
    String id, idKategori, name, url, viewed, downloaded, parent, status, thumbnail;

    public String getThumbnail() {
        return BuildConfig.BASE_IMAGE_FILES+thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Files(String id, String idKategori, String name, String url, String viewed, String downloaded, String parent, String status, String thumbnail) {
        this.id = id;
        this.idKategori = idKategori;
        this.name = name;
        this.url = url;
        this.viewed = viewed;
        this.downloaded = downloaded;
        this.parent = parent;
        this.status = status;
        this.thumbnail = thumbnail;
    }

    public String getId() {
        return id;
    }

    public String getIdKategori() {
        return idKategori;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return BuildConfig.BASE_IMAGE_URL+url;
    }

    public String getViewed() {
        return viewed;
    }

    public String getDownloaded() {
        return downloaded;
    }

    public String getParent() {
        return parent;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.idKategori);
        dest.writeString(this.name);
        dest.writeString(this.url);
        dest.writeString(this.viewed);
        dest.writeString(this.downloaded);
        dest.writeString(this.parent);
        dest.writeString(this.status);
        dest.writeString(this.thumbnail);
    }

    protected Files(Parcel in) {
        this.id = in.readString();
        this.idKategori = in.readString();
        this.name = in.readString();
        this.url = in.readString();
        this.viewed = in.readString();
        this.downloaded = in.readString();
        this.parent = in.readString();
        this.status = in.readString();
        this.thumbnail = in.readString();
    }

    public static final Creator<Files> CREATOR = new Creator<Files>() {
        @Override
        public Files createFromParcel(Parcel source) {
            return new Files(source);
        }

        @Override
        public Files[] newArray(int size) {
            return new Files[size];
        }
    };
}
