package com.skynet.psitech.ui.splash;


import com.skynet.psitech.models.Profile;
import com.skynet.psitech.ui.base.BaseView;
import com.skynet.psitech.ui.base.IBasePresenter;
import com.skynet.psitech.ui.base.OnFinishListener;

public interface SlideContract  {
    interface View extends BaseView {
        void onSuccessGetInfor();

    }
    interface PresenterI extends IBasePresenter,OnFinishListener {
       void getInfor();
       void getInforSuccess(Profile profile);
       void notFoundInfor();
    }

    interface Interactor {
        void doGetInfor(String profileInfor);
    }
}
