package com.skynet.psitech.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class Step implements Parcelable {

    @Expose
    @SerializedName("active")
    private String active;
    @Expose
    @SerializedName("date")
    private String date;
    @Expose
    @SerializedName("position")
    private String position;
    @Expose
    @SerializedName("time_work")
    private int time_work;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("service_id")
    private int service_id;
    @Expose
    @SerializedName("id")
    private int id;

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getTime_work() {
        return time_work;
    }

    public void setTime_work(int time_work) {
        this.time_work = time_work;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.active);
        dest.writeString(this.date);
        dest.writeString(this.position);
        dest.writeInt(this.time_work);
        dest.writeString(this.title);
        dest.writeInt(this.service_id);
        dest.writeInt(this.id);
    }

    public Step() {
    }

    protected Step(Parcel in) {
        this.active = in.readString();
        this.date = in.readString();
        this.position = in.readString();
        this.time_work = in.readInt();
        this.title = in.readString();
        this.service_id = in.readInt();
        this.id = in.readInt();
    }

    public static final Parcelable.Creator<Step> CREATOR = new Parcelable.Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel source) {
            return new Step(source);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };
}
