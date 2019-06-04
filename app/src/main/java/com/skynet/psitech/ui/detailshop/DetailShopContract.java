package com.skynet.psitech.ui.detailshop;

import com.skynet.psitech.models.ShopDetail;
import com.skynet.psitech.ui.base.BaseView;
import com.skynet.psitech.ui.base.IBasePresenter;
import com.skynet.psitech.ui.base.OnFinishListener;

public interface DetailShopContract {
    interface View extends BaseView {
        void onSucessGetShop(ShopDetail shopDetail);
    }

    interface Presenter extends IBasePresenter ,Listener{
        void getShop(int id);
        void toggleFav(int id,boolean toggle);
    }

    interface Interactor {
        void getShop(int id);
        void toggleFav(int id,boolean toggle);
    }

    interface Listener extends OnFinishListener {
        void onSucessGetShop(ShopDetail shopDetail);
    }
}
