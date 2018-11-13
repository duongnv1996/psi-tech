package com.skynet.thuenha.network.api;

import com.skynet.thuenha.ui.base.OnFinishListener;
import com.skynet.thuenha.utils.AppConstant;

public class  ExceptionHandler<T> {
    private OnFinishListener listener;
    private ApiResponse<T> apiResponse;



    public   ExceptionHandler(OnFinishListener onFinishListener, ApiResponse<T> apiResponse) {
       this.apiResponse =  apiResponse;
        listener = onFinishListener;
    }

    public void excute() {
        switch (apiResponse.getCode()) {
            case AppConstant.CODE_EXPIRED: {
                listener.onErrorAuthorization();
                break;
            }
            case AppConstant.CODE_NOT_FOUND: {
                listener.onError(apiResponse.getMessage());
            }
            case AppConstant.CODE_SERVER_ERROR: {
                listener.onErrorApi(apiResponse.getMessage());
            }
        }
    }
}
