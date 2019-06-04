package com.skynet.psitech.ui.detailBooking;

import com.skynet.psitech.models.Booking;
import com.skynet.psitech.ui.base.Presenter;

import java.util.List;


public class DetailBookingPresenter extends Presenter<DetailBookingContract.View> implements DetailBookingContract.Presenter {
    DetailBookingContract.Interactor interactor;

    public DetailBookingPresenter(DetailBookingContract.View view) {
        super(view);
        interactor = new DetailBookingImplRemote(this);
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
    public void updateStatus(int idBooking, int status) {
        if(isAvaliableView()){
            view.showProgress();
            interactor.updateStatus(idBooking,status);
        }
    }

    @Override
    public void updateImgBooking(int bookingId, int type, List<String> list) {
        if(isAvaliableView()){
            interactor.updateImgBooking(bookingId,type,list);
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
}
