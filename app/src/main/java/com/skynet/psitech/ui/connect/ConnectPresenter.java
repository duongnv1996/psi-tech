package com.skynet.psitech.ui.connect;

import com.google.android.gms.maps.model.LatLng;
import com.skynet.psitech.models.Booking;
import com.skynet.psitech.ui.base.Presenter;

import java.util.List;


public class ConnectPresenter extends Presenter<ConnectContract.View> implements ConnectContract.Presenter {
    ConnectContract.Interactor interactor;

    public ConnectPresenter(ConnectContract.View view) {
        super(view);
        interactor = new ConnectImplRemote(this);
    }




    @Override
    public void onDestroyView() {
        view = null;
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
    public void getDetailBooking(int id) {
        if(isAvaliableView()){
            view.showProgress();
            interactor.getDetailBooking(id);
        }
    }

    @Override
    public void getDirection(LatLng from, LatLng des) {
        if(isAvaliableView()){
            interactor.getDirection(from,des);
        }
    }


    @Override
    public void onSucessGetDetailBooking(Booking booking) {
        if (isAvaliableView()) {
            view.hiddenProgress();
            if (booking != null) {
                view.onSucessGetDetailBooking(booking);
            }
        }
    }

    @Override
    public void onSuccessGetDirection(double distance, String timeTXT, String distanceTXT, long time, List<LatLng> list) {
            if(isAvaliableView()){
                view.onSuccessGetDirection(distance,timeTXT,distanceTXT,time,list);
            }
    }
}
