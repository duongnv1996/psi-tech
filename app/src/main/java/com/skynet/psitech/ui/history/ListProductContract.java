package com.skynet.psitech.ui.history;

import com.skynet.psitech.models.Booking;
import com.skynet.psitech.models.Cart;
import com.skynet.psitech.ui.base.BaseView;
import com.skynet.psitech.ui.base.IBasePresenter;
import com.skynet.psitech.ui.base.OnFinishListener;

import java.util.List;

public interface ListProductContract {
    interface View extends BaseView {
        void onSucessGetListProduct(List<Booking> list, int index);        void onSucessGetCart(Cart cart);

    }

    interface Presenter extends IBasePresenter ,Listener{
        void getListProduct(int id);
        void toggleFav(int id, boolean toggle);        void getCart();

    }

    interface Interactor {
        void getListProduct(int id);
        void toggleFav(int id, boolean toggle);        void getCart();

    }

    interface Listener extends OnFinishListener {
        void onSucessGetListProduct(List<Booking> response);        void onSucessGetCart(Cart cart);

    }
}
