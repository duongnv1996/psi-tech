package com.skynet.psitech.ui.home;


import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;
import com.skynet.psitech.models.MyPlace;
import com.skynet.psitech.models.Profile;
import com.skynet.psitech.ui.base.BaseView;
import com.skynet.psitech.ui.base.IBasePresenter;
import com.skynet.psitech.ui.base.OnFinishListener;

public interface HomeContract {
    interface View extends BaseView {
        void onSuccessGetInfor();
        void onGetAddressFromLatlngSuccess(MyPlace place);


    }

    interface PresenterI extends IBasePresenter, Listener {
        void getInfor();
        void getAddressFromLatlng(LatLng latLng, Geocoder geocoder);
        void updateCar(int type,String name);
        void toggleOnOFF(boolean online);
    }

    interface Interactor {
        void doGetInfor(String profileInfor);
        void getAddressFromLatlng(LatLng latLng, Geocoder geocoder);
        void updateCar(int type,String name);
        void toggleOnOFF(int online);

    }

    interface Listener extends OnFinishListener {
        void onSuccessGetInfor(Profile profile);
        void onGetAddressFromLatlngSuccess(MyPlace place);

    }
}
