package com.skynet.psitech.ui.detailBooking;

import com.skynet.psitech.models.Booking;
import com.skynet.psitech.ui.base.BaseView;
import com.skynet.psitech.ui.base.IBasePresenter;
import com.skynet.psitech.ui.base.OnFinishListener;

import java.util.List;

public interface DetailBookingContract {
    interface View extends BaseView {
        void onSucessGetDetailBooking(Booking booking);

    }

    interface Presenter extends IBasePresenter, Listener {
        void getDetailBooking(int id);
        void updateStatus(int idBooking,int status);
        void updateImgBooking(int bookingId,int type, List<String> list);
    }

    interface Interactor {
        void getDetailBooking(int id);
        void updateStatus(int idBooking,int status);
        void updateImgBooking(int bookingId,int type, List<String> list);
    }

    interface Listener extends OnFinishListener {
        void onSucessGetDetailBooking(Booking booking);
    }
}
