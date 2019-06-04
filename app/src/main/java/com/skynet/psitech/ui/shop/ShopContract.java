package com.skynet.psitech.ui.shop;

import com.skynet.psitech.models.Shop;
import com.skynet.psitech.ui.base.BaseView;
import com.skynet.psitech.ui.base.IBasePresenter;
import com.skynet.psitech.ui.base.OnFinishListener;

import java.util.List;

public interface ShopContract {
    interface View extends BaseView {
        void onSuccessGetListShop(List<Shop> list);
        void onSuccessGetListFriendShop(List<Shop> list);
        void onSuccessGetListNearbyShop(List<Shop> list);
    }

    interface Presenter extends IBasePresenter ,Listener{
        void getListShop(int type);
        void getListFriend();
        void getListShopNearby(double lat,double lng);
    }

    interface Interactor {
        void getListShop(int type);
        void getListFriend(int type);
        void getListShopNearby(double lat,double lng);

    }

    interface Listener extends OnFinishListener {
        void onSuccessGetListShop(List<Shop> response);
    }
}
