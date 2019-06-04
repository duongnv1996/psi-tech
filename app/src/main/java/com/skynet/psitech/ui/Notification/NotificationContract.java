package com.skynet.psitech.ui.Notification;


import com.skynet.psitech.models.Booking;
import com.skynet.psitech.models.Promotion;
import com.skynet.psitech.ui.base.BaseView;
import com.skynet.psitech.ui.base.IBasePresenter;
import com.skynet.psitech.ui.base.OnFinishListener;

import java.util.List;

public interface NotificationContract {
    interface View extends BaseView {
        void onSuccessGetServices(List<Booking> listGroupServices);
    }

    interface Presenter extends IBasePresenter, OnHomeRequestFinish {
        void getAllService(String idShop);
    }

    interface Interactor {
        void doGetAllService(String idShop);
    }

    interface OnHomeRequestFinish extends OnFinishListener {
        void onSuccessGetServices(List<Booking> listGroupServices);
    }
}
