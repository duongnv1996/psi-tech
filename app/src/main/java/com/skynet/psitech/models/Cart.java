package com.skynet.psitech.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Cart {
    @SerializedName("list_product")
    List<Product> listProduct;
    @SerializedName("total_price")
    double totalPrice;
    @SerializedName("number_shop")
    int numberShop;
    @SerializedName("fee")
    double fee;
    @SerializedName("promotion")
    double pricePromotion;
    @SerializedName("final_price")
    double final_price;
    @SerializedName("receive")
    Receiver receiver;
    @SerializedName("time_ship")
    String time_ship;

    public String getTime_ship() {
        return time_ship;
    }

    public void setTime_ship(String time_ship) {
        this.time_ship = time_ship;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    int typePayment;

    public int getTypePayment() {
        return typePayment;
    }

    public void setTypePayment(int typePayment) {
        this.typePayment = typePayment;
    }

    public double getPricePromotion() {
        return pricePromotion;
    }

    public double getFinal_price() {
        return final_price;
    }

    public void setFinal_price(double final_price) {
        this.final_price = final_price;
    }

    public void setPricePromotion(double pricePromotion) {
        this.pricePromotion = pricePromotion;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public List<Product> getListProduct() {
        return listProduct;
    }

    public void setListProduct(List<Product> listProduct) {
        this.listProduct = listProduct;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getNumberShop() {
        return numberShop;
    }

    public void setNumberShop(int numberShop) {
        this.numberShop = numberShop;
    }
}
