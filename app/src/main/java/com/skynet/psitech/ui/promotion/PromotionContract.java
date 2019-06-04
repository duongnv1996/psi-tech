package com.skynet.psitech.ui.promotion;


import com.skynet.psitech.models.Promotion;
import com.skynet.psitech.ui.base.BaseView;
import com.skynet.psitech.ui.base.IBasePresenter;
import com.skynet.psitech.ui.base.OnFinishListener;

import java.util.List;

public interface PromotionContract {
    interface View extends BaseView {
        void onSucessGetPromotion(List<Promotion> list);

    }

    interface Presenter extends IBasePresenter,PromotionListener {
        void getPromotion();
    }

    interface Interactor {
        void getPromotion();

    }

    interface PromotionListener extends OnFinishListener {
        void onSucessGetPromotion(List<Promotion> list);

    }
}
