package com.skynet.psitech.ui.auth.login;


import com.skynet.psitech.models.Profile;
import com.skynet.psitech.ui.base.BaseView;
import com.skynet.psitech.ui.base.IBasePresenter;
import com.skynet.psitech.ui.base.OnFinishListener;

public interface LoginContract  {
    interface View extends BaseView {
        void onSuccessLogin(Profile profile);
        void onSuccesLoginFacebook(Profile profile);

    }

    interface PresenterI extends IBasePresenter,OnFinishListener {
        void login(String username, String password, int type);
        void onSuccessLogin(Profile profile);
    }

    interface Interactor {
        void doLogin(String username, String password, int type);
        void doLoginGGG(String idGG, String email, String name);

    }
}
