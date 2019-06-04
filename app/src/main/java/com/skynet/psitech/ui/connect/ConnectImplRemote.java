package com.skynet.psitech.ui.connect;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.skynet.psitech.models.Booking;
import com.skynet.psitech.models.MyPlace;
import com.skynet.psitech.models.Routes;
import com.skynet.psitech.network.api.ApiResponse;
import com.skynet.psitech.network.api.ApiService;
import com.skynet.psitech.network.api.ApiUtil;
import com.skynet.psitech.network.api.CallBackBase;
import com.skynet.psitech.ui.base.Interactor;
import com.skynet.psitech.utils.AppConstant;
import com.skynet.psitech.utils.CommomUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ConnectImplRemote extends Interactor implements ConnectContract.Interactor {
    ConnectContract.Listener listener;

    public ConnectImplRemote(ConnectContract.Listener listener) {
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
    public void getDirection(LatLng from, LatLng des) {
        String fromStr = from.latitude + "," + from.longitude;
        String desStr = des.latitude + "," + des.longitude;
        ApiUtil.getAPIPLACE().getDirection(fromStr, desStr, AppConstant.GG_KEY).enqueue(new CallBackBase<ApiResponse<List<Routes>>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse<List<Routes>>> call, Response<ApiResponse<List<Routes>>> response) {
                if (response.body().getRoutes() != null && response.body().getRoutes().size() > 0) {
                    String time = response.body().getRoutes().get(0).getLegs().get(0).getDuration().getText().replace("day", "ngày").replace("min", "phút").replace("hour", "giờ").replace("s", "");
                    String distance = response.body().getRoutes().get(0).getLegs().get(0).getDistance().getText();
                    List<LatLng> listRoute = null;
                    try {
                        JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                        listRoute = CommomUtils.startRoute(CommomUtils.parse(jsonObject));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    listener.onSuccessGetDirection(Double.parseDouble(response.body().getRoutes().get(0).getLegs().get(0).getDistance().getText().split(" ")[0].replace(",", ".")), time, distance, response.body().getRoutes().get(0).getLegs().get(0).getDuration().getValue(), listRoute);

                } else {
                    listener.onError("direction null or no routes");
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse<List<Routes>>> call, Throwable t) {
                listener.onErrorApi(t.getMessage());
            }
        });
    }

}