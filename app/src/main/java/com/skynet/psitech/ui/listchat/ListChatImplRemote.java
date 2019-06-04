package com.skynet.psitech.ui.listchat;

import com.skynet.psitech.application.AppController;
import com.skynet.psitech.models.ChatItem;
import com.skynet.psitech.models.Profile;
import com.skynet.psitech.network.api.ApiResponse;
import com.skynet.psitech.network.api.ApiService;
import com.skynet.psitech.network.api.ApiUtil;
import com.skynet.psitech.network.api.CallBackBase;
import com.skynet.psitech.network.api.ExceptionHandler;
import com.skynet.psitech.ui.base.Interactor;
import com.skynet.psitech.utils.AppConstant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ListChatImplRemote extends Interactor implements ListChatContract.Interactor {
    ListChatContract.Listener listener;

    public ListChatImplRemote(ListChatContract.Listener listener) {
        this.listener = listener;
    }

    @Override
    public ApiService createService() {
        return ApiUtil.createNotTokenApi();
    }

    @Override
    public void getListChat() {
        Profile profile = AppController.getInstance().getmProfileUser();
        if (profile == null) {
            listener.onErrorAuthorization();
            return;
        }
        getmService().getListChat(profile.getId(), profile.getType()).enqueue(new CallBackBase<ApiResponse<List<ChatItem>>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse<List<ChatItem>>> call, Response<ApiResponse<List<ChatItem>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS) {
                        listener.onSucessGetListChat(response.body().getData());
                    } else {
                        new ExceptionHandler<List<ChatItem>>(listener, response.body()).excute();
                    }
                } else {
                    listener.onError(response.message());
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse<List<ChatItem>>> call, Throwable t) {

            }
        });
    }

    @Override
    public void confirmHired(int idPost, String idHost,String idRent) {

    }

    @Override
    public void deleteChat(String idChat,int idPost) {

    }
}
