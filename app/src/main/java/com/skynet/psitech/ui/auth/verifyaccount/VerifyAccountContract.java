package com.skynet.psitech.ui.auth.verifyaccount;


import com.skynet.psitech.models.Profile;
import com.skynet.psitech.ui.base.BaseView;
import com.skynet.psitech.ui.base.IBasePresenter;
import com.skynet.psitech.ui.base.OnFinishListener;

public interface VerifyAccountContract {

    interface View extends BaseView {
        void onSuccessSignUp();

        void onSuccessSendCode(String code);

    }

    interface Presenter extends IBasePresenter, VerifyListener {
        void signUp(String phone, String name,String email,String password);

        void sendCode(String phone);

    }

    interface Interactor {
        void signUp(String phone, String name,String email,String password);

        void sendCodeTo(String phone);

    }

    interface VerifyListener extends OnFinishListener {
        void onSuccessSignUp(Profile profile);

        void onSuccessSendCode(String code);
    }

}
