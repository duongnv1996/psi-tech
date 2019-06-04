package com.skynet.psitech.ui.home;


import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;
import com.skynet.psitech.application.AppController;
import com.skynet.psitech.models.MyPlace;
import com.skynet.psitech.models.Profile;
import com.skynet.psitech.utils.AppConstant;

public class HomePresenterI implements HomeContract.PresenterI {
    HomeContract.View view;
    HomeContract.Interactor interactor;

    public HomePresenterI(HomeContract.View view) {
        this.view = view;
        interactor = new HomeRemoteImpl(this);
    }

    @Override
    public void getInfor() {

        String profileStr = AppController.getInstance().getmSetting().getString(AppConstant.KEY_PROFILE, "");
        if (profileStr.isEmpty()) {
            view.onError("not found");
        } else {
            view.showProgress();
            interactor.doGetInfor(profileStr);
        }

    }

    @Override
    public void onSuccessGetInfor(Profile profile) {
        if (view == null) return;
        view.hiddenProgress();
        AppController.getInstance().setmProfileUser(profile);
        view.onSuccessGetInfor();
    }


    @Override
    public void getAddressFromLatlng(LatLng latLng, Geocoder geocoder) {
        view.showProgress();
        interactor.getAddressFromLatlng(latLng, geocoder);
    }

    @Override
    public void updateCar(int type, String name) {
        interactor.updateCar(type, name);
    }

    @Override
    public void toggleOnOFF(boolean online) {
        interactor.toggleOnOFF(online ?  1 : 0);
    }

    @Override
    public void onGetAddressFromLatlngSuccess(MyPlace place) {
        view.showProgress();
        view.onGetAddressFromLatlngSuccess(place);
    }

    @Override
    public void onDestroyView() {
        view = null;
    }

    @Override
    public void onErrorApi(String message) {
        if (view == null) return;
        view.hiddenProgress();
        view.onError(message);

    }

    @Override
    public void onError(String message) {
        if (view == null) return;
        view.hiddenProgress();
        view.onError(message);

    }

    @Override
    public void onErrorAuthorization() {
        if (view == null) return;
        view.hiddenProgress();
        view.onErrorAuthorization();

    }


}
