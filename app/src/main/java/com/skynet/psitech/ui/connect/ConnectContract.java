package com.skynet.psitech.ui.connect;

import com.google.android.gms.maps.model.LatLng;
import com.skynet.psitech.models.Booking;
import com.skynet.psitech.ui.base.BaseView;
import com.skynet.psitech.ui.base.IBasePresenter;
import com.skynet.psitech.ui.base.OnFinishListener;

import java.util.List;

public interface ConnectContract {
    interface View extends BaseView {
        void onSucessGetDetailBooking(Booking booking);
        void onSuccessGetDirection(double distance, String timeTXT, String distanceTXT, long time, List<LatLng> list);

    }

    interface Presenter extends IBasePresenter, Listener {
        void getDetailBooking(int id);
        void getDirection(LatLng from,LatLng des);
    }

    interface Interactor {
        void getDetailBooking(int id);
        void getDirection(LatLng from,LatLng des);


    }

    interface Listener extends OnFinishListener {
        void onSucessGetDetailBooking(Booking booking);
             void onSuccessGetDirection(double distance, String timeTXT, String distanceTXT, long time, List<LatLng> list);

    }
}
