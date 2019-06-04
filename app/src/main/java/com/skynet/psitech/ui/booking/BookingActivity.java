package com.skynet.psitech.ui.booking;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.google.android.gms.maps.model.LatLng;
import com.skynet.psitech.R;
import com.skynet.psitech.application.AppController;
import com.skynet.psitech.interfaces.ICallback;
import com.skynet.psitech.models.Booking;
import com.skynet.psitech.models.MyPlace;
import com.skynet.psitech.models.Promotion;
import com.skynet.psitech.models.Service;
import com.skynet.psitech.ui.base.BaseActivity;
import com.skynet.psitech.ui.connect.ConnectActivity;
import com.skynet.psitech.ui.payment.PaymentActivity;
import com.skynet.psitech.ui.promotion.PromotionActivity;
import com.skynet.psitech.ui.views.AlertDialogCustom;
import com.skynet.psitech.ui.views.DialogSucess;
import com.skynet.psitech.ui.views.ProgressDialogCustom;
import com.skynet.psitech.utils.AppConstant;
import com.skynet.psitech.utils.DateTimeUtil;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BookingActivity extends BaseActivity implements BookingContract.View, ICallback {
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.textView54)
    TextView textView54;
    @BindView(R.id.rcvService)
    RecyclerView rcvService;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvprice)
    TextView tvprice;
    @BindView(R.id.rcvStep)
    RecyclerView rcvStep;
    @BindView(R.id.linearLayout2)
    LinearLayout linearLayout2;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.textView56)
    TextView textView56;
    @BindView(R.id.textView58)
    TextView textView58;
    @BindView(R.id.layoutTime)
    ConstraintLayout layoutTime;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.textView57)
    TextView textView57;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.layoutDate)
    ConstraintLayout layoutDate;
    @BindView(R.id.constraintLayout14)
    ConstraintLayout constraintLayout14;
    @BindView(R.id.textView61)
    TextView tvPromotion;
    @BindView(R.id.textView62)
    TextView textView62;
    @BindView(R.id.note)
    EditText note;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.spinner)
    Spinner spinner;
    DatePickerDialog datePickerDialog;
    private AdapterService adapterService;
    private List<Service> listService;
    Calendar date;
    private BookingContract.Presenter presenter;
    private ProgressDialogCustom dialogLoading;
    private Service serviceSeleted;
    private MyPlace place;
    private Promotion promotion;
    Booking booking = new Booking();

    @Override
    protected int initLayout() {
        return R.layout.activity_booking;
    }

    @Override
    protected void initVariables() {
        date = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date.set(Calendar.YEAR, year);
                date.set(Calendar.MONTH, month);
                date.set(Calendar.DATE, dayOfMonth);
                tvDate.setText(DateTimeUtil.convertTimeToString(date.getTimeInMillis(), "dd/MM/yyyy"));
            }
        }, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DATE));
        datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
        tvDate.setText(DateTimeUtil.convertTimeToString(date.getTimeInMillis(), "dd/MM/yyyy"));
        dialogLoading = new ProgressDialogCustom(this);
        presenter = new BookingPresenter(this);
        presenter.getListProduct(getIntent().getIntExtra(AppConstant.MSG, 1));
        if (AppController.getInstance().getmSetting().getFloat(AppConstant.LAT, 0) != 0) {
            LatLng latLng = new LatLng(AppController.getInstance().getmSetting().getFloat(AppConstant.LAT, 0),
                    AppController.getInstance().getmSetting().getFloat(AppConstant.LNG, 0));
            presenter.getAddressFromLatlng(latLng, new Geocoder(this, Locale.getDefault()));
        }
    }

    @Override
    protected void initViews() {
        rcvService.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rcvService.setHasFixedSize(true);
        rcvStep.setLayoutManager(new LinearLayoutManager(this));
        rcvStep.setHasFixedSize(true);

    }

    @Override
    protected int initViewSBAnchor() {
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.imgBack, R.id.tvAddress, R.id.layoutDate, R.id.btnSubmit, R.id.textView61})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                onBackPressed();
                break;
            case R.id.tvAddress:
                break;
            case R.id.layoutDate:
                datePickerDialog.show();
                break;
            case R.id.btnSubmit:
                if (place != null) {
                    booking.setAddress(place.getDescription());
                    booking.setLat(place.getLat());
                    booking.setLng(place.getLng());
                }
                booking.setUserId(AppController.getInstance().getmProfileUser().getId());
                if (serviceSeleted != null)
                    booking.setService_id(serviceSeleted.getId());
                booking.setLocationID(0);
                if (promotion != null)
                    booking.setIdPromotion(promotion.getId());
                else booking.setIdPromotion(0);
                booking.setDate_working(DateTimeUtil.convertTimeToString(date.getTimeInMillis(), "dd/MM/yyyy"));
                booking.setHour_working(DateTimeUtil.convertTimeToString(date.getTimeInMillis(), "hh:mm"));
                booking.setNote(note.getText().toString());
                booking.setType_bike(AppController.getInstance().getmProfileUser().getType_bike());
                booking.setRepeat_type(spinner.getSelectedItemPosition());
                Intent i = new Intent(BookingActivity.this, PaymentActivity.class);
                startActivityForResult(i, 10000);
                break;
            case R.id.textView61:
                startActivityForResult(new Intent(BookingActivity.this, PromotionActivity.class), 1000);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10000 && resultCode == RESULT_OK && data != null) {
            booking.setMethod_payment(data.getIntExtra(AppConstant.MSG,1));
            presenter.book(booking);
            return;
        }
        if (resultCode == RESULT_OK && data != null) {
            Bundle b = data.getBundleExtra(AppConstant.BUNDLE);
            promotion = b.getParcelable(AppConstant.MSG);
            if (promotion != null) {
                tvPromotion.setText(promotion.getCode() + " - Giảm " + promotion.getValue() + "%");
            }
        }
    }

    @Override
    public void onSucessGetListService(List<Service> list) {
        listService = list;
        adapterService = new AdapterService(list, this, this);
        rcvService.setAdapter(adapterService);
    }

    @Override
    public void onGetAddressFromLatlngSuccess(MyPlace place) {
        this.place = place;
        tvAddress.setText(place.getDescription());
    }

    @Override
    public void onSucessBook(Booking booking) {
        new DialogSucess(this, new DialogSucess.DialogOneButtonClickListener() {
            @Override
            public void okClick() {
                Intent i = new Intent(BookingActivity.this,ConnectActivity.class);
                Bundle b = new Bundle();
                b.putParcelable(AppConstant.MSG,booking);
                i.putExtra(AppConstant.BUNDLE,b);
                startActivity(i);
                finish();
            }
        }).show();
    }

    @Override
    public Context getMyContext() {
        return null;
    }

    @Override
    public void showProgress() {
        dialogLoading.showDialog();
    }

    @Override
    public void hiddenProgress() {
        dialogLoading.hideDialog();
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

    @Override
    public void onErrorAuthorization() {

    }

    @Override
    public void onCallBack(int pos) {
        this.serviceSeleted = listService.get(pos);
        tvName.setText(serviceSeleted.getName());
        tvprice.setText(String.format("%,.0fđ", serviceSeleted.getPrice()));
        textView58.setText(serviceSeleted.getTime_work() + "s");
        if (serviceSeleted.getListStep() != null)
            rcvStep.setAdapter(new AdapterStep(serviceSeleted.getListStep(), this));
    }

    @OnClick(R.id.layoutTime)
    public void onViewClicked() {
        AlertDialogCustom.showDialogDateTime(this, new AlertDialogCustom.CallBack() {
            @Override
            public void onCallBack(Calendar start) {
                textView58.setText(String.format(getString(R.string.format_time_ship), start.get(Calendar.HOUR_OF_DAY),
                        start.get(Calendar.MINUTE)));
            }
        }, date).show();
    }
}
