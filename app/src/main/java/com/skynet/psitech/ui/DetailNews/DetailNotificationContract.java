package com.skynet.psitech.ui.DetailNews;


import com.skynet.psitech.models.Promotion;
import com.skynet.psitech.ui.base.BaseView;
import com.skynet.psitech.ui.base.IBasePresenter;
import com.skynet.psitech.ui.base.OnFinishListener;

public interface DetailNotificationContract  {
    interface View extends BaseView {
        void onSuccessGetDetail(Promotion notification);

    }

    interface Presenter extends IBasePresenter,OnFinishDetailNotificationListener{
        void getDetail(String id);
    }

    interface Interactor {
        void doGetDetail(String id);
    }

    interface OnFinishDetailNotificationListener extends OnFinishListener {
        void onSuccessGetDetail(Promotion notification);
    }
}
