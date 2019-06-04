package com.skynet.psitech.ui.contact;

import com.skynet.psitech.application.AppController;
import com.skynet.psitech.models.Profile;
import com.skynet.psitech.network.api.ApiResponse;
import com.skynet.psitech.network.api.ApiService;
import com.skynet.psitech.network.api.ApiUtil;
import com.skynet.psitech.network.api.CallBackBase;
import com.skynet.psitech.ui.base.Interactor;
import com.skynet.psitech.utils.AppConstant;

import retrofit2.Call;
import retrofit2.Response;

public class ContactUsRemote extends Interactor implements ContactUsContract.Interactor {
    ContactUsContract.Listener listener;

    public ContactUsRemote(ContactUsContract.Listener listener) {
        this.listener = listener;
    }

    @Override
    public ApiService createService() {
        return ApiUtil.createNotTokenApi();
    }



    @Override
    public void feedback(String name, String email, String phone, String content) {
        Profile profile = AppController.getInstance().getmProfileUser();
        if (profile == null) {
            listener.onErrorAuthorization();
            return;
        }
        getmService().feedback(profile.getId(),name,phone,email,content).enqueue(new CallBackBase<ApiResponse>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS) {
                        listener.onSucessgetFeedback();
                    } else {
                        listener.onError(response.message());
                    }
                } else {
                    listener.onError(response.message());
                }
            }
            @Override
            public void onRequestFailure(Call<ApiResponse> call, Throwable t) {
                listener.onErrorApi(t.getMessage());
            }
        });
    }
}
