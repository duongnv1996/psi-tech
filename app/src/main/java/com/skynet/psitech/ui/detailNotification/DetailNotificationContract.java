package com.skynet.psitech.ui.detailNotification;


import com.skynet.psitech.models.Notification;
import com.skynet.psitech.ui.base.BaseView;
import com.skynet.psitech.ui.base.IBasePresenter;
import com.skynet.psitech.ui.base.OnFinishListener;

public interface DetailNotificationContract  {
    interface View extends BaseView {
        void onSuccessGetDetail(Notification notification);

    }

    interface Presenter extends IBasePresenter,OnFinishDetailNotificationListener{
        void getDetail(String id);
    }

    interface Interactor {
        void doGetDetail(String id);
    }

    interface OnFinishDetailNotificationListener extends OnFinishListener {
        void onSuccessGetDetail(Notification notification);
    }
}
