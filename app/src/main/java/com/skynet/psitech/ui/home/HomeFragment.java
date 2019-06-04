package com.skynet.psitech.ui.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;
import com.skynet.psitech.R;
import com.skynet.psitech.application.AppController;
import com.skynet.psitech.models.MyPlace;
import com.skynet.psitech.models.Profile;
import com.skynet.psitech.ui.base.BaseFragment;
import com.skynet.psitech.ui.main.MainActivity;
import com.skynet.psitech.utils.AppConstant;
import com.skynet.psitech.utils.CommomUtils;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment implements HomeContract.View, OnMapReadyCallback {


    @BindView(R.id.imgHome)
    ImageView imgHome;
    @BindView(R.id.swOnOff)
    Switch swOnOff;
    @BindView(R.id.appBarLayout2)
    AppBarLayout appBarLayout2;
    @BindView(R.id.imageView28)
    ImageView imageView28;
    @BindView(R.id.tvStatus)
    TextView tvStatus;
    private HomePresenterI presenter;
    private FusedLocationProviderClient mFusedLocationClient;
    private LatLng myLatlng;
    private GoogleMap mMap;
    private Geocoder geocoder;
    private MyPlace myPlace;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initViews(View view) {
        ButterKnife.bind(this, view);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        geocoder = new Geocoder(getContext(), Locale.getDefault());

        if (ActivityCompat.checkSelfPermission(getMyContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getMyContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
            }
            return;
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getMyContext());
        if (ActivityCompat.checkSelfPermission(getMyContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getMyContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
            }
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            setmyLatlng(location);
                        }
                    }
                });
        swOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                tvStatus.setText(isChecked ? "Dịch vụ đã sẵn sàng" : "Dịch vụ không sẵn sàng");
                presenter.toggleOnOFF(isChecked);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @SuppressLint("MissingPermission")
            @Override
            public void onMapLoaded() {
                if (myLatlng != null)
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLatlng, 15));
//                mMap.setMyLocationEnabled(true);
//                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                CommomUtils.addMarker(getContext().getDrawable(R.drawable.ic_target), mMap, myLatlng);
                mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                    @Override
                    public void onCameraIdle() {
                        myLatlng = mMap.getCameraPosition().target;
                        presenter.getAddressFromLatlng(myLatlng, geocoder);
                    }
                });
            }
        });
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getMyContext());
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                myLatlng = new LatLng(location.getLatitude(), location.getLongitude());
                                AppController.getInstance().getmSetting().put("latlng", new Gson().toJson(myLatlng));

                            }
                        }
                    });
        }
    }

    @Override
    protected void initVariables() {
        presenter = new HomePresenterI(this);
        presenter.getInfor();
    }


    private void bindData() {
        Profile profile = AppController.getInstance().getmProfileUser();
        if (profile != null) {
            swOnOff.setChecked(profile.getOnline()==1);
        } else {
            showDialogExpiredToken();
            return;
        }
    }

    private void setupChart(Profile profile) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void doAction() {

    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onSuccessGetInfor() {
        bindData();
    }

    @Override
    public void onGetAddressFromLatlngSuccess(MyPlace place) {
        this.myPlace = place;
    }

    @Override
    public Context getMyContext() {
        return getContext();
    }

    @Override
    public void showProgress() {
    }

    @Override
    public void hiddenProgress() {
    }

    @Override
    public void onErrorApi(String message) {
        LogUtils.e(message);
    }

    @Override
    public void onError(String message) {
        LogUtils.e(message);
        showToast(message, AppConstant.NEGATIVE);
    }

    private void setmyLatlng(Location location) {
        myLatlng = new LatLng(location.getLatitude(), location.getLongitude());
        AppController.getInstance().getmSetting().put(AppConstant.LAT, (float) myLatlng.latitude);
        AppController.getInstance().getmSetting().put(AppConstant.LNG, (float) myLatlng.longitude);
//        if (mMarker == null)
//            mMarker = CommomUtils.addMarker(getDrawable(R.drawable.ic_pin), mMap, myLatlng);

    }

    @Override
    public void onErrorAuthorization() {
        showDialogExpiredToken();
    }


    @OnClick({R.id.imgHome,})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgHome:
                ((MainActivity) getActivity()).openMenu();
                break;
        }
    }
}
