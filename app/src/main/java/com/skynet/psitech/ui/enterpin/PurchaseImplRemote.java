package com.skynet.psitech.ui.enterpin;

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

public class PurchaseImplRemote extends Interactor implements PurchaseContract.Interactor {
    PurchaseContract.Listener listener;

    public PurchaseImplRemote(PurchaseContract.Listener listener) {
        this.listener = listener;
    }

    @Override
    public ApiService createService() {
        return ApiUtil.createNotTokenApi();
    }

    @Override
    public void book() {
        Profile profile = AppController.getInstance().getmProfileUser();
        if(profile== null){
            listener.onErrorAuthorization();
            return;
        }
//        getmService().booking( profile.getId(),AppController.getInstance().getCart().getTypePayment()).enqueue(new CallBackBase<ApiResponse>() {
//            @Override
//            public void onRequestSuccess(Call<ApiResponse> call, Response<ApiResponse> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS) {
//                        listener.onSucessBooking();
//                    } else {
//                        listener.onError(response.body().getMessage());
//                    }
//                } else {
//                    listener.onError(response.message());
//                }
//            }
//
//            @Override
//            public void onRequestFailure(Call<ApiResponse> call, Throwable t) {
//                listener.onErrorApi(t.getMessage());
//            }
//        });
    }

    @Override
    public void sendPin() {
        Profile profile = AppController.getInstance().getmProfileUser();
        if(profile== null){
            listener.onErrorAuthorization();
            return;
        }
        getmService().sendCodeBooking( profile.getPhone()).enqueue(new CallBackBase<ApiResponse<String>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS) {
                        listener.onSucessSendPin(response.body().getData());
                    } else {
                        listener.onError(response.body().getMessage());
                    }
                } else {
                    listener.onError(response.message());
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse<String>> call, Throwable t) {
                listener.onErrorApi(t.getMessage());
            }
        });
    }
}
