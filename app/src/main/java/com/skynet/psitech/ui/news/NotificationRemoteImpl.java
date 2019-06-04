package com.skynet.psitech.ui.news;


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

public class NotificationRemoteImpl extends Interactor implements NotificationContract.Interactor {
    NotificationContract.Presenter presenter;

    public NotificationRemoteImpl(NotificationContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public ApiService createService() {
        return ApiUtil.createNotTokenApi();
    }



    @Override
    public void doGetAllService(String idShop) {
//        getmService().getListNotification(idShop, 2).enqueue(new CallBackBase<ApiResponse<List<Promotion>>>() {
//            @Override
//            public void onRequestSuccess(Call<ApiResponse<List<Promotion>>> call, Response<ApiResponse<List<Promotion>>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS && response.body().getData() != null) {
//
//                        presenter.onSuccessGetServices(response.body().getData());
//                    } else {
//                        presenter.onError(response.body().getMessage());
//                    }
//                } else {
//                    presenter.onError(response.message());
//                }
//            }
//
//            @Override
//            public void onRequestFailure(Call<ApiResponse<List<Promotion>>> call, Throwable t) {
//                presenter.onErrorApi(t.getMessage());
//            }
//        });
    }
}
