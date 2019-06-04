package com.skynet.psitech.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Report  {
    @SerializedName("list_booking")
    List<Booking> list;
    @SerializedName("total_price")
    double price;

    public List<Booking> getList() {
        return list;
    }

    public void setList(List<Booking> list) {
        this.list = list;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
