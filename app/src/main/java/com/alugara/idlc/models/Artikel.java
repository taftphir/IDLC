package com.alugara.idlc.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.alugara.idlc.BuildConfig;

public class Artikel implements Parcelable {
    private String id, idKategori, foto, judul, isi, uploadedBy, nama;

    public Artikel(String id, String idKategori, String foto, String judul, String isi, String uploadedBy, String nama) {
        this.id = id;
        this.idKategori = idKategori;
        this.foto = foto;
        this.judul = judul;
        this.isi = isi;
        this.uploadedBy = uploadedBy;
        this.nama = nama;
    }

    public String getId() {
        return id;
    }

    public String getIdKategori() {
        return idKategori;
    }

    public String getFoto() {
        return BuildConfig.BASE_IMAGE_ARTIKEL+foto;
    }

    public String getJudul() {
        return judul;
    }

    public String getIsi() {
        return isi;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public String getNama() {
        return nama;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.idKategori);
        dest.writeString(this.foto);
        dest.writeString(this.judul);
        dest.writeString(this.isi);
        dest.writeString(this.uploadedBy);
        dest.writeString(this.nama);
    }

    protected Artikel(Parcel in) {
        this.id = in.readString();
        this.idKategori = in.readString();
        this.foto = in.readString();
        this.judul = in.readString();
        this.isi = in.readString();
        this.uploadedBy = in.readString();
        this.nama = in.readString();
    }

    public static final Creator<Artikel> CREATOR = new Creator<Artikel>() {
        @Override
        public Artikel createFromParcel(Parcel source) {
            return new Artikel(source);
        }

        @Override
        public Artikel[] newArray(int size) {
            return new Artikel[size];
        }
    };
}
