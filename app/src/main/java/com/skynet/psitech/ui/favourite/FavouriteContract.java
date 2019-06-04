package com.skynet.psitech.ui.favourite;

import com.skynet.psitech.models.FavouriteItem;
import com.skynet.psitech.ui.base.BaseView;
import com.skynet.psitech.ui.base.IBasePresenter;
import com.skynet.psitech.ui.base.OnFinishListener;

public interface FavouriteContract {
    interface View extends BaseView {
        void onSucessGetList(FavouriteItem list);
    }
    interface Presenter extends IBasePresenter, Listener {
        void getList();
        void toggleFav(int idPost, boolean isFav);
    }
    interface Interactor {
        void getList();
        void toggleFav(int idPost, int isFav);
    }
    interface Listener extends OnFinishListener {
        void onSucessGetList(FavouriteItem list);
    }
}
