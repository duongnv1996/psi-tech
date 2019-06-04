package com.skynet.psitech.ui.detailshop;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import com.skynet.psitech.R;
import com.skynet.psitech.application.AppController;
import com.skynet.psitech.models.Product;
import com.skynet.psitech.network.api.ApiResponse;
import com.skynet.psitech.network.api.ApiUtil;
import com.skynet.psitech.network.api.CallBackBase;
import com.skynet.psitech.ui.base.BaseFragment;
import com.skynet.psitech.ui.detailProduct.ActivityDetailProduct;
import com.skynet.psitech.utils.AppConstant;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class FragmentListProductOfShop extends BaseFragment implements AdapterProduct.CallBackProduct {
    @BindView(R.id.rcv)
    RecyclerView rcv;

    private List<Product> list;

    public static FragmentListProductOfShop newInstance(List<Product> list) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(AppConstant.MSG, (ArrayList<? extends Parcelable>) list);
        FragmentListProductOfShop fragment = new FragmentListProductOfShop();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void doAction() {
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_recyclerview;
    }

    @Override
    protected void initViews(View view) {
        ButterKnife.bind(this, view);
        rcv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rcv.setHasFixedSize(true);
    }

    @Override
    protected void initVariables() {
        list = getArguments().getParcelableArrayList(AppConstant.MSG);
        if (list != null) {
            rcv.setAdapter(new AdapterProduct(list, getContext(), this));
        }
    }


    @Override
    public void toggleFav(int pos, Product product, boolean toggle) {
        ApiUtil.createNotTokenApi().toggleFavProduct(AppController.getInstance().getmProfileUser().getId(), product.getId(), toggle ? 1 : 2).enqueue(new CallBackBase<ApiResponse>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse> call, Response<ApiResponse> response) {

            }

            @Override
            public void onRequestFailure(Call<ApiResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onCallBack(int pos) {
        Intent i = new Intent(getActivity(), ActivityDetailProduct.class);
        i.putExtra(AppConstant.MSG, list.get(pos).getId());
        startActivity(i);
    }
}
