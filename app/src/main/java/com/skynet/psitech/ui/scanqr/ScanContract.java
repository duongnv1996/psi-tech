package com.skynet.psitech.ui.scanqr;

import com.skynet.psitech.ui.base.BaseView;
import com.skynet.psitech.ui.base.IBasePresenter;
import com.skynet.psitech.ui.base.OnFinishListener;

public interface ScanContract {
    interface View extends BaseView {
    }

    interface Presenter extends IBasePresenter ,Listener{
        void getShop(int id);
    }

    interface Interactor {
        void getShop(int id);
    }

    interface Listener extends OnFinishListener {
    }
}
