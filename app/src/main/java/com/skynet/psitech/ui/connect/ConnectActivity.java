package com.skynet.psitech.ui.connect;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.SquareCap;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;
import com.skynet.psitech.R;
import com.skynet.psitech.application.AppController;
import com.skynet.psitech.interfaces.ICallback;
import com.skynet.psitech.models.Booking;
import com.skynet.psitech.models.MyPlace;
import com.skynet.psitech.models.Profile;
import com.skynet.psitech.network.socket.SocketConstants;
import com.skynet.psitech.ui.base.BaseActivity;
import com.skynet.psitech.ui.chatting.ChatActivity;
import com.skynet.psitech.ui.views.DialogConnection;
import com.skynet.psitech.utils.AppConstant;
import com.skynet.psitech.utils.CommomUtils;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.google.android.gms.maps.model.JointType.ROUND;

public class ConnectActivity extends BaseActivity implements ConnectContract.View, OnMapReadyCallback, ICallback {


    @BindView(R.id.imgHome)
    ImageView imgHome;
    @BindView(R.id.tvStatus)
    TextView tvStatus;
    @BindView(R.id.appBarLayout2)
    AppBarLayout appBarLayout2;
    @BindView(R.id.avt)
    de.hdodenhof.circleimageview.CircleImageView avt;
    @BindView(R.id.tvNameTech)
    TextView tvNameTech;
    @BindView(R.id.imgCall)
    ImageView imgCall;
    @BindView(R.id.imgChat)
    ImageView imgChat;
    @BindView(R.id.layoutTop)
    ConstraintLayout layoutTop;
    private ConnectContract.Presenter presenter;
    private FusedLocationProviderClient mFusedLocationClient;
    private LatLng myLatlng;
    private GoogleMap mMap;
    private Geocoder geocoder;
    private MyPlace myPlace;
    private Booking booking;
    private DialogConnection dialogConnect;
    private Marker fromMarker, desMarker;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String msg = intent.getStringExtra(AppConstant.MSG);
                Booking booking = new Gson().fromJson(msg, Booking.class);
                if (booking != null && ConnectActivity.this.booking != null && booking.getBooking_id() == ConnectActivity.this.booking.getBooking_id()) {
                    onRefesh(booking.getBooking_id());
                }
            }
        }
    };

    private void onRefesh(int booking_id) {
        presenter.getDetailBooking(booking_id);
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_connect;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @SuppressLint("MissingPermission")
            @Override
            public void onMapLoaded() {
                if (myLatlng != null) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLatlng, 15));
                    fromMarker = CommomUtils.addMarker(getDrawable(R.drawable.ic_car_2_copy), mMap, myLatlng);
                }
//                mMap.setMyLocationEnabled(true);
//                mMap.getUiSettings().setMyLocationButtonEnabled(false);
//                CommomUtils.addMarker(getContext().getDrawable(R.drawable.ic_target),mMap,myLatlng);
//                mMap.getUiSettings().setZoomGesturesEnabled(false);
//                mMap.getUiSettings().setAllGesturesEnabled(false);
                if (booking != null && myLatlng != null) {
                    LatLng latLngDes = new LatLng(booking.getLat(), booking.getLng());
                    desMarker = CommomUtils.addMarker(getDrawable(R.drawable.ic_pin_map), mMap, latLngDes);
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    builder.include(latLngDes);
                    builder.include(myLatlng);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 10));
                    presenter.getDirection(myLatlng, new LatLng(booking.getLat(), booking.getLng()));
                }
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
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
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
        this.dialogConnect = new DialogConnection(this, this);
        presenter = new ConnectPresenter(this);
        booking = getIntent().getBundleExtra(AppConstant.BUNDLE).getParcelable(AppConstant.MSG);
        if (booking != null) {
            presenter.getDetailBooking(booking.getBooking_id());
            AppController.getInstance().setBooking(booking);
        }
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        geocoder = new Geocoder(this, Locale.getDefault());
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
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            setmyLatlng(location);
                        }
                    }
                });
    }

    @Override
    protected int initViewSBAnchor() {
        return 0;
    }

    private void bindData() {
        myLatlng = new LatLng(booking.getLat(), booking.getLng());

        if (booking.getUser() != null) {
            bindUser(booking.getUser());
        }


    }

    private void bindUser(Profile tech) {
        tvNameTech.setText(tech.getName());
        if (tech.getAvatar() != null && !tech.getAvatar().isEmpty()) {
            Picasso.with(this).load(tech.getAvatar()).fit().centerCrop().into(avt);
        }
    }


    @Override
    protected void onDestroy() {
        presenter.onDestroyView();
        super.onDestroy();
    }


    @Override
    public void onResume() {
        super.onResume();
        IntentFilter i = new IntentFilter();
        i.addAction(SocketConstants.SOCKET_BOOKING);
        registerReceiver(this.receiver, i);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    public Context getMyContext() {
        return this;
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
        showDialogExpired();
    }


    @OnClick({R.id.imgHome})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgHome:
                onBackPressed();
                break;
        }
    }


    @Override
    public void onCallBack(int pos) {

        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onSucessGetDetailBooking(Booking booking) {
        this.booking = booking;
        bindData();
    }

    @Override
    public void onSuccessGetDirection(double distance, String timeTXT, String distanceTXT, long time, List<LatLng> list) {
        if (mMap != null && list != null) {
            PolylineOptions greyOptions = new PolylineOptions();
            greyOptions.width(getResources().getDimension(R.dimen.dp2));
            greyOptions.color(Color.BLACK);
            greyOptions.startCap(new SquareCap());
            greyOptions.endCap(new SquareCap());
            greyOptions.jointType(ROUND);
            greyOptions.addAll(list);
            mMap.addPolyline(greyOptions);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.imgCall, R.id.imgChat})
    public void onViewActionClicked(View view) {
        if (booking != null && booking.getTech() != null) {
            switch (view.getId()) {
                case R.id.imgCall:
                    CommomUtils.dialPhoneNumber(this, booking.getUser().getPhone());
                    break;
                case R.id.imgChat:
                    //todo chat here
                    Intent i = new Intent(this, ChatActivity.class);
                    Bundle b = new Bundle();
                    b.putParcelable(AppConstant.INTENT, booking.getUser());
                    b.putParcelable("user", AppController.getInstance().getmProfileUser());
                    b.putInt("idPost", booking.getBooking_id());
                    b.putString("avt", AppController.getInstance().getmProfileUser().getType() == 1 ? booking.getTech().getAvatar() : AppController.getInstance().getmProfileUser().getAvatar());
                    i.putExtra(AppConstant.BUNDLE, b);
                    startActivityForResult(i, 1000);
                    break;
            }
        }
    }


    @OnClick(R.id.btnBackBottom)
    public void onViewClicked() {
        onBackPressed();
    }
}
