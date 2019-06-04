package com.skynet.psitech.ui.detailBooking;

import com.skynet.psitech.application.AppController;
import com.skynet.psitech.models.Booking;
import com.skynet.psitech.models.Profile;
import com.skynet.psitech.network.api.ApiResponse;
import com.skynet.psitech.network.api.ApiService;
import com.skynet.psitech.network.api.ApiUtil;
import com.skynet.psitech.network.api.CallBackBase;
import com.skynet.psitech.ui.base.Interactor;
import com.skynet.psitech.utils.AppConstant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class DetailBookingImplRemote extends Interactor implements DetailBookingContract.Interactor {
    DetailBookingContract.Listener listener;

    public DetailBookingImplRemote(DetailBookingContract.Listener listener) {
        this.listener = listener;
    }

    @Override
    public ApiService createService() {
        return ApiUtil.createNotTokenApi();
    }


    @Override
    public void getDetailBooking(int id) {
        getmService().getDetailBooking(id).enqueue(new CallBackBase<ApiResponse<Booking>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse<Booking>> call, Response<ApiResponse<Booking>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS) {
                        listener.onSucessGetDetailBooking(response.body().getData());
                    } else {
                        listener.onError(response.body().getMessage());
                    }
                } else {
                    listener.onError(response.message());
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse<Booking>> call, Throwable t) {
                listener.onErrorApi(t.getMessage());
            }
        });
    }

    @Override
    public void updateStatus(int idBooking, int status) {
        getmService().updateStatusBooking(idBooking, status).enqueue(new CallBackBase<ApiResponse>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse> call, Response<ApiResponse> response) {
                getDetailBooking(idBooking);
            }

            @Override
            public void onRequestFailure(Call<ApiResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void updateImgBooking(int bookingId, int type, List<String> list) {
        Profile profile = AppController.getInstance().getmProfileUser();
        if (profile == null) {
            listener.onErrorAuthorization();
            return;
        }
        RequestBody TypeRequest = ApiUtil.createPartFromString(type+"");
        RequestBody bookingIdRequest = ApiUtil.createPartFromString(bookingId+"");
        List<MultipartBody.Part> parts = new ArrayList<>();
        for (String img : list) {
            File imgFile = new File(img);
            if(imgFile.exists()) {
                RequestBody requestImageFile = RequestBody.create(MediaType.parse("image/*"), new File(img));
                parts.add(MultipartBody.Part.createFormData("img[]", imgFile.getName(), requestImageFile));
            }
        }
        getmService().addPhotoBooking(bookingIdRequest,TypeRequest,parts).enqueue(new CallBackBase<ApiResponse>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS) {
                    } else {
                        listener.onError(response.body().getMessage());
                    }
                } else {
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse> call, Throwable t) {
                listener.onErrorApi(t.getMessage());
            }
        });
    }

}