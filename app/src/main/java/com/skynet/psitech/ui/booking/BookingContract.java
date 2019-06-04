package com.skynet.psitech.ui.booking;

import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;
import com.skynet.psitech.models.Booking;
import com.skynet.psitech.models.MyPlace;
import com.skynet.psitech.models.Service;
import com.skynet.psitech.ui.base.BaseView;
import com.skynet.psitech.ui.base.IBasePresenter;
import com.skynet.psitech.ui.base.OnFinishListener;

import java.util.List;

public interface BookingContract {
    interface View extends BaseView {
        void onSucessGetListService(List<Service> list);
        void onGetAddressFromLatlngSuccess(MyPlace place);
        void onSucessBook(Booking booking);
    }

    interface Presenter extends IBasePresenter ,Listener{
        void getListProduct(int id);
        void getAddressFromLatlng(LatLng latLng, Geocoder geocoder);
        void book(Booking booking);
        }

    interface Interactor {
        void getListProduct(int id);
        void getAddressFromLatlng(LatLng latLng, Geocoder geocoder);
        void book(Booking booking);

    }

    interface Listener extends OnFinishListener {
        void onSucessGetListProduct(List<Service> response);
        void onGetAddressFromLatlngSuccess(MyPlace place);
        void onSucessBook(Booking booking);
    }
}
