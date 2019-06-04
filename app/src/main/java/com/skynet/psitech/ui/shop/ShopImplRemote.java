package com.skynet.psitech.ui.shop;

import com.skynet.psitech.application.AppController;
import com.skynet.psitech.models.Profile;
import com.skynet.psitech.models.Shop;
import com.skynet.psitech.models.ShopResponse;
import com.skynet.psitech.network.api.ApiResponse;
import com.skynet.psitech.network.api.ApiService;
import com.skynet.psitech.network.api.ApiUtil;
import com.skynet.psitech.network.api.CallBackBase;
import com.skynet.psitech.ui.base.Interactor;
import com.skynet.psitech.utils.AppConstant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ShopImplRemote extends Interactor implements ShopContract.Interactor {
    ShopContract.Listener listener;

    public ShopImplRemote(ShopContract.Listener listener) {
        this.listener = listener;
    }

    @Override
    public ApiService createService() {
        return ApiUtil.createNotTokenApi();
    }


    @Override
    public void getListShop(int type) {
        Profile profile = AppController.getInstance().getmProfileUser();
        if (profile == null) {
            listener.onErrorAuthorization();
            return;
        }
        getmService().getListShop(profile.getId(), type).enqueue(new CallBackBase<ApiResponse<ShopResponse>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse<ShopResponse>> call, Response<ApiResponse<ShopResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS) {
                        listener.onSuccessGetListShop(response.body().getData().getHot_shop());
                    } else {
                        listener.onErrorApi(response.message());
                    }
                } else {
                    listener.onError(response.message());
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse<ShopResponse>> call, Throwable t) {

            }
        });
    }

    @Override
    public void getListFriend(int type) {
        Profile profile = AppController.getInstance().getmProfileUser();
        if (profile == null) {
            listener.onErrorAuthorization();
            return;
        }
        getmService().getListFriend(profile.getId()).enqueue(new CallBackBase<ApiResponse<List<Shop>>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse<List<Shop>>> call, Response<ApiResponse<List<Shop>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS) {
                        listener.onSuccessGetListShop(response.body().getData());
                    } else {
                        listener.onErrorApi(response.message());
                    }
                } else {
                    listener.onError(response.message());
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse<List<Shop>>> call, Throwable t) {

            }
        });
    }

    @Override
    public void getListShopNearby(double lat, double lng) {
        ApiUtil.createNotTokenApi().getListShopNearby(lat, lng).enqueue(new CallBackBase<ApiResponse<List<Shop>>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse<List<Shop>>> call, Response<ApiResponse<List<Shop>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS) {
                       listener.onSuccessGetListShop(response.body().getData());
                    }
                }
            }
            @Override
            public void onRequestFailure(Call<ApiResponse<List<Shop>>> call, Throwable t) {

            }
        });
    }

}
