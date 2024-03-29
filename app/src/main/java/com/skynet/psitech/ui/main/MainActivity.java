package com.skynet.psitech.ui.main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.skynet.psitech.R;
import com.skynet.psitech.application.AppController;
import com.skynet.psitech.interfaces.FragmentCallBackTitle;
import com.skynet.psitech.models.Profile;
import com.skynet.psitech.ui.Notification.NotificationActivity;
import com.skynet.psitech.ui.auth.updateProfile.ActivityProfileUpdate;
import com.skynet.psitech.ui.base.BaseActivity;
import com.skynet.psitech.ui.listchat.ListChatActivity;
import com.skynet.psitech.ui.listfeedback.ListFeedbackActivity;
import com.skynet.psitech.ui.profile.ProfileActivity;
import com.skynet.psitech.ui.reporting.ReportingActivity;
import com.skynet.psitech.ui.scanqr.ScannerQr;
import com.skynet.psitech.ui.schedule.ListHistoryActivity;
import com.skynet.psitech.ui.views.ViewpagerNotSwipe;
import com.skynet.psitech.utils.AppConstant;
import com.skynet.psitech.utils.CommomUtils;
import com.squareup.picasso.Picasso;
import com.tbruyelle.rxpermissions2.RxPermissions;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import q.rorbin.badgeview.Badge;

public class MainActivity extends BaseActivity implements OptionBottomSheet.MoreOptionCallback, ContactContract.View, FragmentCallBackTitle {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.viewpager)
    ViewpagerNotSwipe viewpager;

    @BindView(R.id.layoutRootViewpager)
    FrameLayout layoutRootViewpager;
    @BindView(R.id.imgAvatarProfile)
    CircleImageView imgAvatarProfile;
    @BindView(R.id.tvNameProfile)
    TextView tvNameProfile;
    @BindView(R.id.nav_home)
    LinearLayout navHome;
    @BindView(R.id.nav_fav)
    LinearLayout navFav;
    @BindView(R.id.nav_cart)
    LinearLayout navCart;
    @BindView(R.id.nav_history)
    LinearLayout navHistory;
    @BindView(R.id.nav_message)
    LinearLayout navMessage;
    @BindView(R.id.nav_news)
    LinearLayout navNews;
    @BindView(R.id.nav_help)
    LinearLayout navHelp;
    @BindView(R.id.nav_customer)
    LinearLayout navCustomer;
    @BindView(R.id.tvTitleToolbar)
    TextView tvTitleToolbar;
    @BindView(R.id.imgRight)
    ImageView imgRight;
    @BindView(R.id.imgHome)
    ImageView imgHome;
    @BindView(R.id.imageView9)
    ImageView imageView9;
    @BindView(R.id.textView16)
    TextView textView16;
    @BindView(R.id.nav_noti)
    LinearLayout navNoti;
    @BindView(R.id.nav_setting)
    LinearLayout navSetting;
    @BindView(R.id.nav_report)
    LinearLayout navReport;
    @BindView(R.id.nav_share)
    LinearLayout navShare;
    private AdapterMainViewpager adapter;
    private boolean doubleBackToExitPressedOnce;
    private Badge badge;
    private ContactContract.Presenter presenter;
    private OptionBottomSheet optionBottomSheet;
    private OptionBottomSheet bottomAddFriendRequest;
    private String userIdRequestFriend;
    private OptionBottomSheet.MoreOptionCallback addFriendCallback = new OptionBottomSheet.MoreOptionCallback() {
        @Override
        public void onMoreOptionCallback() {
            if (userIdRequestFriend != null) {
            }
        }
    };

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initVariables() {
//        showDialogExpired();
        bottomAddFriendRequest = new OptionBottomSheet(this, addFriendCallback);

        if (getIntent() != null && getIntent().getExtras() != null) {
            String data = getIntent().getExtras().getString(AppConstant.NOTIFICATION_SOCKET);
            if (data != null) {
//
            }
        }
        presenter = new ContactPresenter(this);
        optionBottomSheet = new OptionBottomSheet(this, this);
        presenter.updateToken();

        bindUserData();

    }

    @Override
    public void onSocketConnected() {
        super.onSocketConnected();

    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, null, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        ;
        viewpager.setAdapter(new AdapterMainViewpager(getSupportFragmentManager()));
        viewpager.setPagingEnabled(false);
//        viewpager.setCurrentItem(1);
    }


    @Override
    protected void onResume() {
        super.onResume();
//        int count = AppController.getInstance().getmProfileUser().getMessage() + AppController.getInstance().getmProfileUser().getNoty();
//        if (count > 0)
//            addBadgeAt(2, count);
//        else if (badge != null)
//            badge.hide(true);

        if ((AppController.getInstance().getmProfileUser().getName().isEmpty() || AppController.getInstance().getmProfileUser().getEmail().isEmpty()) && !AppController.getInstance().getmSetting().getBoolean("show")) {
            startActivityForResult(new Intent(MainActivity.this, ActivityProfileUpdate.class), 1001);
        }
    }

    public void selectTab(int index) {
        viewpager.setCurrentItem(index);
    }

    private void addBadgeAt(int position, int number) {
        // add badge
//        if (badge == null)
//            badge = new QBadgeView(this)
//                    .setBadgeNumber(number)
//                    .setGravityOffset(12, 2, true)
//                    .bindTarget(bnve.getBottomNavigationItemView(position))
//                    .setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
//                        @Override
//                        public void onDragStateChanged(int dragState, Badge badge, View targetView) {
////                        if (Badge.OnDragStateChangedListener.STATE_SUCCEED == dragState)
////                            Toast.makeText(BadgeViewActivity.this, R.string.tips_badge_removed, Toast.LENGTH_SHORT).show();
//                        }
//                    });
//        else
//            badge.setBadgeNumber(number);
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


    private void bindUserData() {
        Profile profile = AppController.getInstance().getmProfileUser();
        if (profile != null) {
            tvNameProfile.setText(profile.getName());
            if (profile.getAvatar() != null && !profile.getAvatar().isEmpty()) {
                Picasso.with(this).load(profile.getAvatar()).fit().centerCrop().into(imgAvatarProfile);
            }
        } else {
            return;
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Nhấn BACK 2 lần để thoát", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);

        }
    }

    @Override
    public void onMoreOptionCallback() {
//        if (bnve.getCurrentItem() == 1)

    }


    @Override
    public Context getMyContext() {
        return null;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hiddenProgress() {

    }

    @Override
    public void onErrorApi(String message) {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onErrorAuthorization() {

    }

    @Override
    public void setTilte(String title) {
        tvTitleToolbar.setText(title);
    }

    @OnClick({R.id.nav_home, R.id.nav_fav, R.id.nav_cart, R.id.nav_history, R.id.nav_report, R.id.nav_message, R.id.nav_news, R.id.nav_help, R.id.nav_setting, R.id.nav_share, R.id.nav_noti})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.nav_home:
                startActivity(new Intent(MainActivity.this, ListHistoryActivity.class));
                break;
            case R.id.nav_fav:
//                radGroup.check(R.id.btmFav);
                break;
            case R.id.nav_cart:
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                break;
            case R.id.nav_history:
                startActivity(new Intent(MainActivity.this, ListHistoryActivity.class));

                break;
            case R.id.nav_message:
                startActivity(new Intent(MainActivity.this, ReportingActivity.class));
                break;
            case R.id.nav_news:
                startActivity(new Intent(MainActivity.this, com.skynet.psitech.ui.history.ListHistoryActivity.class));
                break;
            case R.id.nav_help:
                //                startActivity(new Intent(MainActivity.this, ContactUsActivity.class));
                CommomUtils.dialPhoneNumber(this, "19001000");
                break;
            case R.id.nav_noti:
                startActivity(new Intent(MainActivity.this, ListChatActivity.class));

                break;
            case R.id.nav_setting:
                startActivity(new Intent(MainActivity.this, NotificationActivity.class));

                break;
            case R.id.nav_report:
                startActivity(new Intent(MainActivity.this, ListFeedbackActivity.class));

                break;

            case R.id.nav_share:
                logOut();
                break;
        }
        drawerLayout.closeDrawer(Gravity.LEFT);

    }

    public void openMenu() {
        drawerLayout.openDrawer(Gravity.LEFT);
    }

    @OnClick({R.id.imgHome, R.id.imgRight, R.id.imageView9})
    public void onViewToolbarClicked(View view) {
        switch (view.getId()) {
            case R.id.imgHome:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.imgRight:
                RxPermissions rxPermissions = new RxPermissions(this);
                rxPermissions.request(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            startActivity(new Intent(MainActivity.this, ScannerQr.class));
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

                break;
            case R.id.imageView9:
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
        }
    }

    @OnClick({R.id.imgAvatarProfile, R.id.tvNameProfile})
    public void onViewProfileClicked(View view) {
        startActivityForResult(new Intent(MainActivity.this, ProfileActivity.class), 1000);
        drawerLayout.closeDrawer(Gravity.LEFT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == RESULT_OK) {
            bindUserData();
        }
    }

    @OnClick({R.id.btmNewest, R.id.btmShop, R.id.btmCategory, R.id.btmFav})
    public void onViewCheckClicked(View view) {
        switch (view.getId()) {
            case R.id.btmNewest: {
                viewpager.setCurrentItem(0);

                break;
            }
            case R.id.btmShop: {
                startActivity(new Intent(MainActivity.this, ListChatActivity.class));

                break;
            }
            case R.id.btmCategory: {
//                        viewpager.setCurrentItem(2);
                // setTilte("Ngành hàng");
                startActivity(new Intent(MainActivity.this, NotificationActivity.class));
//                        if(AppController.getInstance().getBooking()!=null){
//                            Intent i = new Intent(MainActivity.this, ConnectActivity.class);
//                            Bundle b = new Bundle();
//                            b.putParcelable(AppConstant.MSG, AppController.getInstance().getBooking());
//                            i.putExtra(AppConstant.BUNDLE, b);
//                            startActivityForResult(i, 1000);
//                        }else{
//                            startActivity(new Intent(MainActivity.this, com.skynet.psitech.ui.history.ListFeedbackActivity.class));
//
//                        }

                break;
            }
            case R.id.btmFav: {
//                        viewpager.setCurrentItem(3);
                //   setTilte("Yêu thích");
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                break;
            }
        }
    }
}
