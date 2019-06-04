package com.skynet.psitech.ui.booking;

import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;
import com.skynet.psitech.models.Booking;
import com.skynet.psitech.models.MyPlace;
import com.skynet.psitech.models.Service;
import com.skynet.psitech.ui.base.Presenter;

import java.util.List;

public class BookingPresenter extends Presenter<BookingContract.View> implements BookingContract.Presenter {
    BookingContract.Interactor interactor;

    public BookingPresenter(BookingContract.View view) {
        super(view);
        interactor = new BookingImplRemote(this);
    }

    @Override
    public void getListProduct(int id) {
        if (isAvaliableView()) {
            view.showProgress();
            interactor.getListProduct(id);
        }
    }



    @Override
    public void onDestroyView() {
        view = null;
    }


    @Override
    public void getAddressFromLatlng(LatLng latLng, Geocoder geocoder) {
        view.showProgress();
        interactor.getAddressFromLatlng(latLng, geocoder);
    }

    @Override
    public void book(Booking booking) {
        if(isAvaliableView()){
            view.showProgress();
            interactor.book(booking);
        }
    }

    @Override
    public void onGetAddressFromLatlngSuccess(MyPlace place) {
        view.showProgress();
        view.onGetAddressFromLatlngSuccess(place);
    }

    @Override
    public void onSucessBook(Booking booking) {
        if(isAvaliableView()){
            view.hiddenProgress();
            view.onSucessBook(booking);
        }
    }

    @Override
    public void onErrorApi(String message) {
        if(isAvaliableView()){
            view.hiddenProgress();
            view.onErrorApi(message);
        }
    }

    @Override
    public void onError(String message) {
        if(isAvaliableView()){
            view.hiddenProgress();
            view.onError(message);
        }
    }

    @Override
    public void onErrorAuthorization() {
        if(isAvaliableView()){
            view.hiddenProgress();
            view.onErrorAuthorization();
        }
    }

    @Override
    public void onSucessGetListProduct(List<Service> response) {
        if (isAvaliableView()) {
            view.hiddenProgress();
            if (response != null) {
                    view.onSucessGetListService(response);
            }
        }
    }
}
