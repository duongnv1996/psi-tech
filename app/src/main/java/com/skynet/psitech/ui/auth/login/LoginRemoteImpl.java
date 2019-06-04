package com.skynet.psitech.ui.auth.login;



import com.skynet.psitech.models.Profile;
import com.skynet.psitech.network.api.ApiResponse;
import com.skynet.psitech.network.api.ApiService;
import com.skynet.psitech.network.api.ApiUtil;
import com.skynet.psitech.network.api.CallBackBase;
import com.skynet.psitech.ui.base.Interactor;
import com.skynet.psitech.utils.AppConstant;

import retrofit2.Call;
import retrofit2.Response;

public class LoginRemoteImpl extends Interactor implements LoginContract.Interactor {
    LoginContract.PresenterI presenter;

    public LoginRemoteImpl(LoginContract.PresenterI presenter) {
        this.presenter = presenter;
    }

    @Override
    public void doLogin(String username, String password, int type) {
        getmService().login(username, password,type).enqueue(new CallBackBase<ApiResponse<Profile>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse<Profile>> call, Response<ApiResponse<Profile>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS && response.body().getData() != null) {
                        presenter.onSuccessLogin(response.body().getData());
                    } else if (response.body().getCode() == AppConstant.CODE_EXPIRED) {
                        presenter.onErrorAuthorization();

                    } else {
                        presenter.onError(response.body().getMessage());
                    }
                } else {
                    presenter.onError(response.message());
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse<Profile>> call, Throwable t) {
                presenter.onErrorApi(t.getMessage());
            }
        });
    }

    @Override
    public void doLoginGGG(String idGG, String name, String email) {

    }


    @Override
    public ApiService createService() {
        return ApiUtil.createNotTokenApi();
    }
}
