package com.skynet.psitech.ui.auth.forgotPassword;


import com.skynet.psitech.network.api.ApiResponse;
import com.skynet.psitech.network.api.ApiService;
import com.skynet.psitech.network.api.ApiUtil;
import com.skynet.psitech.network.api.CallBackBase;
import com.skynet.psitech.ui.base.Interactor;
import com.skynet.psitech.utils.AppConstant;

import retrofit2.Call;
import retrofit2.Response;

public class ForgotPwImpl extends Interactor implements ForgotPwContract.Interactor {
    ForgotPwContract.Presenter presenter;

    public ForgotPwImpl(ForgotPwContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void doSignUp(String email, int type) {
        getmService().forgotPassword( email, type).enqueue(new CallBackBase<ApiResponse>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS) {
                        presenter.signUpSuccess();
                    } else {
                        presenter.onError(response.body().getMessage());
                    }
                } else {
                    presenter.onError(response.message());
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse> call, Throwable t) {
                presenter.onErrorApi(t.getMessage());
            }
        });
    }

    @Override
    public ApiService createService() {
        return ApiUtil.createNotTokenApi();
    }
}
