package com.skynet.psitech.ui.location;

import com.google.android.gms.maps.model.LatLng;
import com.skynet.psitech.models.MyPlace;
import com.skynet.psitech.ui.base.BaseView;
import com.skynet.psitech.ui.base.IBasePresenter;
import com.skynet.psitech.ui.base.OnFinishListener;

public interface LocationContract {
    interface View extends BaseView {
        void onSuccessGetMyAddress(MyPlace response);
    }

    interface Presenter extends IBasePresenter, Listener {
        void getMyAddress(LatLng latLng);
    }

    interface Interactor {
        void getMyAddress(LatLng latLng);

    }

    interface Listener extends OnFinishListener {
        void onSuccessGetMyAddress(MyPlace response);
    }
}
