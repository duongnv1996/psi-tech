package com.skynet.psitech.ui.listfeedback;

import com.skynet.psitech.models.Booking;
import com.skynet.psitech.models.Cart;
import com.skynet.psitech.models.Feedback;
import com.skynet.psitech.ui.base.BaseView;
import com.skynet.psitech.ui.base.IBasePresenter;
import com.skynet.psitech.ui.base.OnFinishListener;

import java.util.List;

public interface ListProductContract {
    interface View extends BaseView {
        void onSucessGetListProduct(List<Feedback> list, int index);

    }

    interface Presenter extends IBasePresenter ,Listener{
        void getListProduct(int id);


    }

    interface Interactor {
        void getListProduct(int id);


    }

    interface Listener extends OnFinishListener {
        void onSucessGetListProduct(List<Feedback> response);

    }
}
