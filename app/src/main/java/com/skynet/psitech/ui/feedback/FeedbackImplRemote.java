package com.skynet.psitech.ui.feedback;

import com.skynet.psitech.application.AppController;
import com.skynet.psitech.models.Feedback;
import com.skynet.psitech.models.Profile;
import com.skynet.psitech.network.api.ApiResponse;
import com.skynet.psitech.network.api.ApiService;
import com.skynet.psitech.network.api.ApiUtil;
import com.skynet.psitech.network.api.CallBackBase;
import com.skynet.psitech.ui.base.Interactor;
import com.skynet.psitech.ui.listfeedback.ListProductContract;
import com.skynet.psitech.utils.AppConstant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class FeedbackImplRemote extends Interactor implements FeedbackContract.Interactor {
    FeedbackContract.Listener listener;

    public FeedbackImplRemote(FeedbackContract.Listener listener) {
        this.listener = listener;
    }

    @Override
    public ApiService createService() {
        return ApiUtil.createNotTokenApi();
    }


    @Override
    public void addFeedback(int bookingId, String content, String address, String time, List<File> fileList) {
        Profile profile = AppController.getInstance().getmProfileUser();
        if (profile == null) {
            listener.onErrorAuthorization();
            return;
        }
        RequestBody idTechRequest = ApiUtil.createPartFromString(profile.getId()+"");
        RequestBody bookingIdRequest = ApiUtil.createPartFromString(bookingId+"");
        RequestBody contentRequest = ApiUtil.createPartFromString(content+"");
        RequestBody addressRequest = ApiUtil.createPartFromString(address+"");
        RequestBody timeRequest = ApiUtil.createPartFromString(time+"");
        List<MultipartBody.Part> parts = new ArrayList<>();
        for (File img : fileList) {
            RequestBody requestImageFile = RequestBody.create(MediaType.parse("image/*"), img);
            parts.add(MultipartBody.Part.createFormData("img[]", img.getName(), requestImageFile));
        }
        getmService().addFeeback(idTechRequest,bookingIdRequest,contentRequest,addressRequest,timeRequest,parts).enqueue(new CallBackBase<ApiResponse>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS) {
                        listener.addFeedbackSucess();
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