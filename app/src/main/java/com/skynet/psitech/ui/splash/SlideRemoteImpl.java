package com.skynet.psitech.ui.splash;

import com.google.gson.Gson;
import com.skynet.psitech.models.Profile;
import com.skynet.psitech.network.api.ApiResponse;
import com.skynet.psitech.network.api.ApiService;
import com.skynet.psitech.network.api.ApiUtil;
import com.skynet.psitech.network.api.CallBackBase;
import com.skynet.psitech.ui.base.Interactor;
import com.skynet.psitech.utils.AppConstant;

import retrofit2.Call;
import retrofit2.Response;

public class SlideRemoteImpl extends Interactor implements SlideContract.Interactor {
    SlideContract.PresenterI presenter;

    public SlideRemoteImpl(SlideContract.PresenterI presenter) {
        this.presenter = presenter;
    }

    @Override
    public ApiService createService() {
        return ApiUtil.createNotTokenApi();
    }

    @Override
    public void doGetInfor(String idUser) {
        Profile profile = new Gson().fromJson(idUser, Profile.class);
        if (profile == null) {
            presenter.notFoundInfor();
            return;
        }
        getmService().getProfile(profile.getId(),AppConstant.TYPE_USER).enqueue(new CallBackBase<ApiResponse<Profile>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse<Profile>> call, final Response<ApiResponse<Profile>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS && response.body().getData() != null) {
                        new android.os.Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                presenter.getInforSuccess(response.body().getData());
//                                AppController.getInstance().setListBanner(response.body().getData().getBanners());
                            }
                        }, 2000);
                    } else {
                        presenter.notFoundInfor();
                    }
                } else {
                    presenter.notFoundInfor();
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse<Profile>> call, Throwable t) {
                presenter.notFoundInfor();

            }
        });
    }
}
