package com.skynet.psitech.ui.auth.verifyaccount;


import com.skynet.psitech.models.Profile;
import com.skynet.psitech.network.api.ApiResponse;
import com.skynet.psitech.network.api.ApiService;
import com.skynet.psitech.network.api.ApiUtil;
import com.skynet.psitech.network.api.CallBackBase;
import com.skynet.psitech.ui.base.Interactor;
import com.skynet.psitech.utils.AppConstant;

import retrofit2.Call;
import retrofit2.Response;

public class VerifyRemoteImpl extends Interactor implements VerifyAccountContract.Interactor {
    private VerifyAccountContract.VerifyListener listener;

    public VerifyRemoteImpl(VerifyAccountContract.VerifyListener listener) {
        this.listener = listener;
    }

    @Override
    public ApiService createService() {
        return ApiUtil.createNotTokenApi();
    }



    @Override
    public void signUp(String phone, String name, String email,String password) {
        getmService().signUp( phone, password,email,name).enqueue(new CallBackBase<ApiResponse<Profile>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse<Profile>> call, Response<ApiResponse<Profile>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS) {
                        listener.onSuccessSignUp(response.body().getData());
                    } else {
                        listener.onError(response.body().getMessage());
                    }
                } else {
                    listener.onErrorApi(response.message());

                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse<Profile>> call, Throwable t) {
                listener.onErrorApi(t.getMessage());
            }
        });
    }

    @Override
    public void sendCodeTo(String phone) {
        getmService().sendCode( phone,AppConstant.TYPE_USER).enqueue(new CallBackBase<ApiResponse<String>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS) {
                        listener.onSuccessSendCode(response.body().getData());
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
