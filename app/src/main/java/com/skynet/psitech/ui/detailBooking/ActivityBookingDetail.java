package com.skynet.psitech.ui.detailBooking;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.skynet.psitech.R;
import com.skynet.psitech.application.AppController;
import com.skynet.psitech.interfaces.SnackBarCallBack;
import com.skynet.psitech.models.Booking;
import com.skynet.psitech.ui.base.BaseActivity;
import com.skynet.psitech.ui.chatting.ChatActivity;
import com.skynet.psitech.ui.connect.ConnectActivity;
import com.skynet.psitech.ui.feedback.FeedbackActivity;
import com.skynet.psitech.ui.views.DialogConfirm;
import com.skynet.psitech.ui.views.ProgressDialogCustom;
import com.skynet.psitech.utils.AppConstant;
import com.skynet.psitech.utils.CommomUtils;
import com.squareup.picasso.Picasso;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.theartofdev.edmodo.cropper.CropImage;
import com.zhihu.matisse.Matisse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import me.iwf.photopicker.PhotoPicker;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class ActivityBookingDetail extends BaseActivity implements DetailBookingContract.View {

    public static final int STATUS_WATING = 1;
    public static final int STATUS_RECEIVE = 2;
    public static final int STATUS_INPROGRESS = 3;
    public static final int STATUS_FININISH = 4;
    public static final int STATUS_CUSTOMER_CANCEL = 5;
    public static final int STATUS_TECH_CANCEL = 6;
    public static final int STATUS_SERVER_CANCEL = 7;
    @BindView(R.id.textView79)
    TextView textView79;
    @BindView(R.id.tvTitle2)
    TextView tvTitle2;
    @BindView(R.id.imageView32)
    ImageView imageView32;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.imgService)
    ImageView imgService;
    @BindView(R.id.tvNameService)
    TextView tvNameService;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.rcvStep)
    RecyclerView rcvStep;
    @BindView(R.id.tvStatus)
    TextView tvStatus;
    @BindView(R.id.tvNameTech2)
    TextView tvNameTech2;
    @BindView(R.id.avt2)
    CircleImageView avt2;
    @BindView(R.id.tvTech3)
    TextView tvTech3;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.btnFeedback)
    Button btnFeedback;
    @BindView(R.id.cardView3)
    CardView cardView3;
    @BindView(R.id.rcvPhoto)
    RecyclerView rcvPhoto;
    @BindView(R.id.textView71)
    TextView textView71;
    @BindView(R.id.textView74)
    TextView textView74;
    @BindView(R.id.layoutContentBooking)
    ConstraintLayout layoutContentBooking;
    @BindView(R.id.layoutContact)
    ConstraintLayout layoutContact;

    @BindView(R.id.textView49)
    TextView textView49;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.tvCoupon)
    TextView tvCoupon;
    @BindView(R.id.textView59)
    TextView textView59;
    @BindView(R.id.textView64)
    TextView textView64;
    @BindView(R.id.layoutPayment)
    ConstraintLayout layoutPayment;
    @BindView(R.id.tvTimeCountdown)
    TextView tvTimeCountdown;
    @BindView(R.id.imageView5)
    ImageView imageView5;
    @BindView(R.id.tvReport)
    TextView tvReport;
    private DetailBookingContract.Presenter presenter;
    private ProgressDialogCustom dialogLoading;
    private Booking booking;
    private int nextActive = -1;
    private List<String> listPathPhoto;
    AdapterPhoto adapterPhoto;

    @Override
    protected int initLayout() {
        return R.layout.activity_detail_booking;
    }

    @Override
    protected void initVariables() {
        presenter = new DetailBookingPresenter(this);
        dialogLoading = new ProgressDialogCustom(this);
        listPathPhoto = new ArrayList<>();
        adapterPhoto = new AdapterPhoto(listPathPhoto, this);
        rcvPhoto.setAdapter(adapterPhoto);
        presenter.getDetailBooking(getIntent().getIntExtra(AppConstant.MSG, 0));
    }

    @Override
    protected void initViews() {
        rcvStep.setLayoutManager(new LinearLayoutManager(this));
        rcvStep.setHasFixedSize(true);
        rcvPhoto.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rcvStep.setHasFixedSize(true);
//        rcvStep.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

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

    @OnClick(R.id.btnFeedback)
    public void onViewClicked() {
        presenter.updateStatus(booking.getBooking_id(), STATUS_TECH_CANCEL);
        booking.setActive(STATUS_TECH_CANCEL);
        getmSocket().updateBooking(booking);

    }

    @OnClick(R.id.imageView32)
    public void onViewBackClicked() {
        onBackPressed();
    }

    @Override
    public void onSucessGetDetailBooking(Booking booking) {
        this.booking = booking;
        if (booking.getService_id() == 0) {
            showToast("Lịch đặt chưa có dịch vụ. Lịch đặt này sẽ bị huỷ tự động bởi server!", AppConstant.NEGATIVE, new SnackBarCallBack() {
                @Override
                public void onClosedSnackBar() {
                    finish();
                }
            });
            return;
        }
        tvTime.setText(booking.getDate_working() + " " + booking.getHour_working());
        tvNameService.setText(booking.getService_name());
        if (booking.getService_image() != null && !booking.getService_image().isEmpty()) {
            Picasso.with(this).load(booking.getService_image()).fit().centerCrop().into(imgService);
        }
        tvPrice.setText(String.format("%,.0fvnđ", booking.getPrice()));
        if (booking.getService_detail() != null && !booking.getService_detail().isEmpty()) {
            rcvStep.setAdapter(new AdapterStep(booking.getService_detail(), this));
        }
        tvAddress.setText(booking.getAddress());
        if (booking.getUser() != null) {
            tvNameTech2.setText(booking.getUser().getName());
            if (booking.getUser().getAvatar() != null && !booking.getUser().getAvatar().isEmpty()) {
                Picasso.with(this).load(booking.getUser().getAvatar()).fit().centerCrop().into(avt2);
            }
        }
        switch (booking.getActive()) {
            case STATUS_WATING: {
                tvTitle2.setText("Công việc mới");
                tvStatus.setVisibility(View.GONE);
                nextActive = STATUS_RECEIVE;
                btnSubmit.setText("Xác nhận công việc");
                layoutContentBooking.setVisibility(View.GONE);

                break;
            }
            case STATUS_RECEIVE: {
                tvTitle2.setText("Đang chờ thực hiện");
                tvStatus.setVisibility(View.GONE);
                nextActive = STATUS_INPROGRESS;
                btnSubmit.setText("Xem chi tiết công việc");
                btnFeedback.setVisibility(View.VISIBLE);
                layoutContentBooking.setVisibility(View.GONE);
                break;
            }
            case STATUS_INPROGRESS: {
                tvTitle2.setText("Đang thực hiện");
                tvStatus.setVisibility(View.GONE);
                nextActive = STATUS_FININISH;
                btnSubmit.setText("Kết thúc");
                btnFeedback.setVisibility(View.GONE);
                textView74.setText("Kết thúc công việc vui lòng chụp ảnh theo các góc quy định để gửi tới khách hàng.");
                layoutContentBooking.setVisibility(View.VISIBLE);
                break;
            }
            case STATUS_FININISH: {
                tvTitle2.setText("Lịch sử công việc");
                tvStatus.setText("Đã hoàn thành");
                tvStatus.setVisibility(View.VISIBLE);
                btnFeedback.setVisibility(View.GONE);
                btnSubmit.setVisibility(View.GONE);
                textView74.setText("");

                break;
            }
            case STATUS_CUSTOMER_CANCEL: {
                tvTitle2.setText("Lịch sử công việc");
                tvStatus.setText("Đã huỷ");
                tvStatus.setBackgroundResource(R.drawable.ic_faill);
                tvStatus.setTextColor(Color.parseColor("#FF1313"));
                tvStatus.setVisibility(View.VISIBLE);
                btnFeedback.setVisibility(View.GONE);
                btnSubmit.setVisibility(View.GONE);
                textView74.setText("");

                break;
            }
            case STATUS_TECH_CANCEL: {
                tvTitle2.setText("Lịch sử công việc");
                tvStatus.setText("Đã huỷ");
                tvStatus.setBackgroundResource(R.drawable.ic_faill);
                tvStatus.setTextColor(Color.parseColor("#FF1313"));
                tvStatus.setVisibility(View.VISIBLE);
                btnFeedback.setVisibility(View.GONE);
                btnSubmit.setVisibility(View.GONE);
                textView74.setText("");

                break;
            }
            case STATUS_SERVER_CANCEL: {
                tvTitle2.setText("Lịch sử công việc");
                tvStatus.setText("Đã huỷ");
                tvStatus.setBackgroundResource(R.drawable.ic_faill);
                tvStatus.setTextColor(Color.parseColor("#FF1313"));
                tvStatus.setVisibility(View.VISIBLE);
                btnFeedback.setVisibility(View.GONE);
                btnSubmit.setVisibility(View.GONE);
                textView74.setText("");

                break;
            }
        }


        if (booking.getActive() == 4) {
            tvStatus.setText("Đã hoàn thành");
            if (booking.getTech() != null && booking.getTech().getAvatar() != null && !booking.getTech().getAvatar().isEmpty()) {
                Picasso.with(this).load(booking.getTech().getAvatar()).fit().centerCrop().into(avt2);
                tvNameTech2.setText(booking.getTech().getName());
            }
            if (booking.getList_image() != null && !booking.getList_image().isEmpty()) {
                listPathPhoto.addAll(booking.getList_image());
                adapterPhoto.notifyDataSetChanged();
            }
        } else if (booking.getActive() >= 5) {
            tvStatus.setText("Đã huỷ");
            layoutContentBooking.setVisibility(View.GONE);
            tvStatus.setBackgroundResource(R.drawable.ic_faill);
            tvStatus.setTextColor(Color.parseColor("#FF1313"));
        }
        if (booking.getIdPromotion() != 0) {
            tvCoupon.setText("KM giảm giá " + booking.getPromotion_value() + "%");
            tvCoupon.setVisibility(View.VISIBLE);
        } else {
            tvCoupon.setVisibility(View.GONE);
        }
    }

    @Override
    public Context getMyContext() {
        return this;
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
        showDialogExpired();
    }

    @OnClick(R.id.tvAddress)
    public void onViewAddressClicked() {
        if (booking == null) return;
        Intent i = new Intent(ActivityBookingDetail.this, ConnectActivity.class);
        Bundle b = new Bundle();
        b.putParcelable(AppConstant.MSG, booking);
        i.putExtra(AppConstant.BUNDLE, b);
        startActivity(i);
    }

    @OnClick(R.id.btnSubmit)
    public void onViewSbmitClicked() {
        if (booking != null) {
            if (btnSubmit.getText().equals("Xem chi tiết công việc")) {
                btnSubmit.setText("Bắt đầu thực hiện");
                layoutContentBooking.setVisibility(View.VISIBLE);
                layoutContact.setVisibility(View.VISIBLE);
                return;
            } else if (btnSubmit.getText().toString().contains("Kết thúc")) {
                btnSubmit.setText("Xác nhận & Thanh toán");
                layoutPayment.setVisibility(View.VISIBLE);
                tvReport.setVisibility(View.VISIBLE);
                layoutContentBooking.setVisibility(View.GONE);
                return;
            }
            if (nextActive == STATUS_INPROGRESS) {
                new DialogConfirm(this, new DialogConfirm.DialogOneButtonClickListener() {
                    @Override
                    public void okClick() {
                        if (listPathPhoto != null && !listPathPhoto.isEmpty()) {
                            presenter.updateImgBooking(booking.getBooking_id(), 1, listPathPhoto);
                        }
                        presenter.updateStatus(booking.getBooking_id(), nextActive);
                        booking.setActive(nextActive);
                        getmSocket().updateBooking(booking);

                    }
                }).show();
            } else {
                if (nextActive == STATUS_FININISH && listPathPhoto != null && !listPathPhoto.isEmpty()) {
                    presenter.updateImgBooking(booking.getBooking_id(), 1, listPathPhoto);
                }
                presenter.updateStatus(booking.getBooking_id(), nextActive);
                booking.setActive(nextActive);
                getmSocket().updateBooking(booking);
            }
        }
    }

    @OnClick(R.id.imageView5)
    public void onViewPhotoClicked() {
        choosePhoto();
    }

    @OnClick(R.id.imgCall)
    public void onViewCallClicked() {
        CommomUtils.dialPhoneNumber(this, booking.getUser().getPhone());
    }

    @OnClick(R.id.imgSMS)
    public void onViewSMSClicked() {
//        Intent i = new Intent(this, ChatActivity.class);
//        Bundle b = new Bundle();
//        b.putParcelable(AppConstant.INTENT, booking.getUser());
//        b.putParcelable("user", AppController.getInstance().getmProfileUser());
//        b.putInt("idPost", booking.getBooking_id());
//        b.putString("avt", AppController.getInstance().getmProfileUser().getType() == 1 ? booking.getUser().getAvatar() : AppController.getInstance().getmProfileUser().getAvatar());
//        i.putExtra(AppConstant.BUNDLE, b);
//        startActivityForResult(i, 1000);
    }


    private void choosePhoto() {

        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
//
                    EasyImage.openChooserWithDocuments(ActivityBookingDetail.this, "Chọn ảnh ", 0);

                } else {

                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
//
//        Matisse.from(this)
//                .choose(MimeType.ofAll())
//                .countable(true)
//                .maxSelectable(1)
////                .captureStrategy(new CaptureStrategy(true, "com.skynet.psitech.provider","Pictures"))
////                .capture(true)
////                            .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
////                            .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
//                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
//                .thumbnailScale(0.85f)
//                .imageEngine(new Glide4Engine())
//                .forResult(PhotoPicker.REQUEST_CODE);
    }

    private boolean checkPermissionGranted() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 111);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 111:
                if (grantResults.length > 2 && grantResults[0] != PackageManager.PERMISSION_GRANTED && grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                    checkPermissionGranted();
                    return;
                } else {
                    choosePhoto();
                }
                return;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == RESULT_OK) {

            return;
        }
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                if (!imageFile.exists()) {
                    Toast.makeText(ActivityBookingDetail.this, "File không tồn tại.", Toast.LENGTH_SHORT).show();
                    return;
                }
                CropImage.activity(Uri.fromFile(imageFile))
                        .setAspectRatio(4, 3)
//                            .setRequestedSize(800, 800, CropImageView.RequestSizeOptions.RESIZE_EXACT)
                        .start(ActivityBookingDetail.this);
            }


        });
        if (requestCode == PhotoPicker.REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                List<String> mSelected;
                mSelected = Matisse.obtainPathResult(data);
                Log.d("Matisse", "mSelected: " + mSelected);
//                ArrayList<String> photos =
//                        data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                for (String urlPath : mSelected) {
                    File fileImage = new File(urlPath);
                    if (!fileImage.exists()) {
                        Toast.makeText(this, "File không tồn tại.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    CropImage.activity(Uri.fromFile(fileImage))
                            .setAspectRatio(4, 3)
//                            .setRequestedSize(800, 800, CropImageView.RequestSizeOptions.RESIZE_EXACT)
                            .start(this);
                    break;
                }
            }

        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                File file = new File(resultUri.getPath());
                listPathPhoto.add(file.getPath());
                adapterPhoto.notifyItemInserted(adapterPhoto.getItemCount());
                rcvPhoto.smoothScrollToPosition(adapterPhoto.getItemCount());
                // presenter.upload(file, 1);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }

        }


    }

    @OnClick(R.id.tvReport)
    public void onViewReportClicked() {
        Intent i = new Intent(ActivityBookingDetail.this, FeedbackActivity.class);
        i.putExtra(AppConstant.MSG, booking.getBooking_id());
        startActivity(i);

    }
}
