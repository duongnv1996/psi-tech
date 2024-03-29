package com.skynet.psitech.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class Promotion implements Parcelable {
    @Expose
    @SerializedName("active")
    private String active;
    @Expose
    @SerializedName("user_read")
    private int user_read;
    @Expose
    @SerializedName("img")
    private String img;
    @Expose
    @SerializedName("date")
    private String date;
    @Expose
    @SerializedName("code")
    private String code;
    @Expose
    @SerializedName("date_end")
    private String date_end;
    @Expose
    @SerializedName("date_start")
    private String date_start;
    @Expose
    @SerializedName("value")
    private int value;
    @Expose
    @SerializedName("content")
    private String content;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("id")
    private int id;



    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public int getUser_read() {
        return user_read;
    }

    public void setUser_read(int user_read) {
        this.user_read = user_read;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public String getDate_end() {
        return date_end;
    }

    public void setDate_end(String date_end) {
        this.date_end = date_end;
    }

    public String getDate_start() {
        return date_start;
    }

    public void setDate_start(String date_start) {
        this.date_start = date_start;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.active);
        dest.writeInt(this.user_read);
        dest.writeString(this.img);
        dest.writeString(this.date);
        dest.writeString(this.code);
        dest.writeString(this.date_end);
        dest.writeString(this.date_start);
        dest.writeInt(this.value);
        dest.writeString(this.content);
        dest.writeString(this.title);
        dest.writeInt(this.id);
    }

    public Promotion() {
    }

    protected Promotion(Parcel in) {
        this.active = in.readString();
        this.user_read = in.readInt();
        this.img = in.readString();
        this.date = in.readString();
        this.code = in.readString();
        this.date_end = in.readString();
        this.date_start = in.readString();
        this.value = in.readInt();
        this.content = in.readString();
        this.title = in.readString();
        this.id = in.readInt();
    }

    public static final Parcelable.Creator<Promotion> CREATOR = new Parcelable.Creator<Promotion>() {
        @Override
        public Promotion createFromParcel(Parcel source) {
            return new Promotion(source);
        }

        @Override
        public Promotion[] newArray(int size) {
            return new Promotion[size];
        }
    };
}
