package com.skynet.psitech.ui.promotion;


import com.skynet.psitech.application.AppController;
import com.skynet.psitech.models.Profile;
import com.skynet.psitech.models.Promotion;
import com.skynet.psitech.network.api.ApiResponse;
import com.skynet.psitech.network.api.ApiService;
import com.skynet.psitech.network.api.ApiUtil;
import com.skynet.psitech.network.api.CallBackBase;
import com.skynet.psitech.ui.base.Interactor;
import com.skynet.psitech.utils.AppConstant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class PromotionRemoteImpl extends Interactor implements PromotionContract.Interactor {
    PromotionContract.PromotionListener listener;

    public PromotionRemoteImpl(PromotionContract.PromotionListener listener) {
        this.listener = listener;
    }

    @Override
    public ApiService createService() {
        return ApiUtil.createNotTokenApi();
    }

    @Override
    public void getPromotion() {
        Profile profile = AppController.getInstance().getmProfileUser();
        if (profile == null) {
            listener.onErrorAuthorization();
            return;
        }
        ApiUtil.createNotTokenApi().getListPromotion(profile.getId()).enqueue(new CallBackBase<ApiResponse<List<Promotion>>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse<List<Promotion>>> call, Response<ApiResponse<List<Promotion>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS) {
                        listener.onSucessGetPromotion(response.body().getData());
                    } else {
                        listener.onError(response.body().getMessage());
                    }
                } else {
                    listener.onError(response.message());
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse<List<Promotion>>> call, Throwable t) {
                listener.onErrorApi(t.getMessage());

            }
        });
    }
}
