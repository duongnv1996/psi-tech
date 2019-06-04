package com.skynet.psitech.ui.detailhistory;

import com.skynet.psitech.models.History;
import com.skynet.psitech.ui.base.BaseView;
import com.skynet.psitech.ui.base.IBasePresenter;
import com.skynet.psitech.ui.base.OnFinishListener;

public interface HistoryContract {
    interface View extends BaseView {
        void onSucessGetCart(History history);
        void onSucessCancel();

    }
    interface Presenter extends IBasePresenter,Listener{
        void getHistory(int id);
        void cancle(int id);
    }
    interface Interactor{
        void getHistory(int id);
        void cancle(int id);

    }
    interface Listener extends OnFinishListener{
        void onSucessGetCart(History history);
        void onSucessCancel();
    }
}
