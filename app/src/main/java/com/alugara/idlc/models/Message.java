package com.alugara.idlc.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Message implements Parcelable {
    private String id, from, to, message, sent_at, is_read;

    public Message(String id, String from, String to, String message, String sent_at, String is_read) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.message = message;
        this.sent_at = sent_at;
        this.is_read = is_read;
    }

    public String getId() {
        return id;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getMessage() {
        return message;
    }

    public String getSent_at() {
        return sent_at;
    }

    public String getIs_read() {
        return is_read;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.from);
        dest.writeString(this.to);
        dest.writeString(this.message);
        dest.writeString(this.sent_at);
        dest.writeString(this.is_read);
    }

    protected Message(Parcel in) {
        this.id = in.readString();
        this.from = in.readString();
        this.to = in.readString();
        this.message = in.readString();
        this.sent_at = in.readString();
        this.is_read = in.readString();
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel source) {
            return new Message(source);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}
