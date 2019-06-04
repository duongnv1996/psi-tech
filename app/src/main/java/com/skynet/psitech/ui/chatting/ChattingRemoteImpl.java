package com.skynet.psitech.ui.chatting;


import com.skynet.psitech.application.AppController;
import com.skynet.psitech.models.ChatItem;
import com.skynet.psitech.models.Message;
import com.skynet.psitech.network.api.ApiResponse;
import com.skynet.psitech.network.api.ApiService;
import com.skynet.psitech.network.api.ApiUtil;
import com.skynet.psitech.network.api.CallBackBase;
import com.skynet.psitech.ui.base.Interactor;
import com.skynet.psitech.utils.AppConstant;

import retrofit2.Call;
import retrofit2.Response;

public class ChattingRemoteImpl extends Interactor implements ChattingContract.Interactor {
    ChattingContract.ChattingListener listener;

    public ChattingRemoteImpl(ChattingContract.ChattingListener listener) {
        this.listener = listener;
    }

    @Override
    public ApiService createService() {
        return ApiUtil.createNotTokenApi();
    }

    @Override
    public void getMessages(int idShop, int uiId, int idPost) {
        getmService().getListMessageBetween(uiId, idShop, AppConstant.TYPE_USER).enqueue(new CallBackBase<ApiResponse<ChatItem>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse<ChatItem>> call, Response<ApiResponse<ChatItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.code() == AppConstant.CODE_API_SUCCESS ) {
                        if( response.body().getData() != null)
                        listener.onSuccessGetMessages(response.body().getData().getContent(),response.body().getData().getPost());
                    } else {
                        listener.onError(response.body().getMessage());
                    }
                } else {
                    listener.onError(response.message());
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse<ChatItem>> call, Throwable t) {
                listener.onErrorApi(t.getMessage());

            }
        });
    }

    @Override
    public void sendMessage(int idPost, int idUser, int idShop, String content, String time,int attach) {
        getmService().sendMessageTo(idPost, idUser, idShop, time, content, AppController.getInstance().getmProfileUser().getType(),attach).enqueue(new CallBackBase<ApiResponse<Message>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse<Message>> call, Response<ApiResponse<Message>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.code() == AppConstant.CODE_API_SUCCESS) {
                        listener.onSuccessSendMessage(response.body().getData());
                    } else {
                        listener.onError(response.body().getMessage());
                    }
                } else {
                    listener.onError(response.message());
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse<Message>> call, Throwable t) {
                listener.onErrorApi(t.getMessage());
            }
        });
    }
}
