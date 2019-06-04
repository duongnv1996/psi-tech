package com.skynet.psitech.ui.main;

import com.skynet.psitech.ui.base.BaseView;
import com.skynet.psitech.ui.base.IBasePresenter;
import com.skynet.psitech.ui.base.OnFinishListener;

public interface ContactContract {
    interface View extends BaseView {

    }

    interface Presenter extends IBasePresenter ,Listener{
        void updateToken();

    }

    interface Interactor {
        void updateToken();
    }

    interface Listener extends OnFinishListener {

    }
}
