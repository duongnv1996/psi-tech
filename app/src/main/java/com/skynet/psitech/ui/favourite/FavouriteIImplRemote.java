package com.skynet.psitech.ui.favourite;

import com.skynet.psitech.application.AppController;
import com.skynet.psitech.models.FavouriteItem;
import com.skynet.psitech.models.Profile;
import com.skynet.psitech.network.api.ApiResponse;
import com.skynet.psitech.network.api.ApiService;
import com.skynet.psitech.network.api.ApiUtil;
import com.skynet.psitech.network.api.CallBackBase;
import com.skynet.psitech.network.api.ExceptionHandler;
import com.skynet.psitech.ui.base.Interactor;
import com.skynet.psitech.utils.AppConstant;

import retrofit2.Call;
import retrofit2.Response;

public class FavouriteIImplRemote extends Interactor implements FavouriteContract.Interactor {
    FavouriteContract.Listener listener;

    public FavouriteIImplRemote(FavouriteContract.Listener listener) {
        this.listener = listener;
    }

    @Override
    public ApiService createService() {
        return ApiUtil.createNotTokenApi();
    }

    @Override
    public void getList() {
        Profile profile = AppController.getInstance().getmProfileUser();
        if (profile == null) {
            listener.onErrorAuthorization();
            return;
        }
        getmService().getListFavourite(profile.getId()).enqueue(new CallBackBase<ApiResponse<FavouriteItem>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse<FavouriteItem>> call, Response<ApiResponse<FavouriteItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS) {
                        listener.onSucessGetList(response.body().getData());
                    } else {
                        new ExceptionHandler<FavouriteItem>(listener, response.body()).excute();
                    }
                } else {
                    listener.onError(response.message());
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse<FavouriteItem>> call, Throwable t) {

            }
        });
    }

    @Override
    public void toggleFav(int idPost, int isFav) {
        Profile profile = AppController.getInstance().getmProfileUser();
        if (profile == null) {
            listener.onErrorAuthorization();
            return;
        }
//        getmService().toggleFav(profile.getId(), idPost,isFav).enqueue(new CallBackBase<ApiResponse>() {
//            @Override
//            public void onRequestSuccess(Call<ApiResponse> call, Response<ApiResponse> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS) {
//
//                    } else {
//                        //  new ExceptionHandler<DetailPost>(listener, response.body()).excute();
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
}
