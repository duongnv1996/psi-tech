//package com.skynet.psitech.ui.views;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.graphics.drawable.ColorDrawable;
//import android.text.Html;
//import android.text.Spannable;
//import android.text.Spanned;
//import android.view.View;
//import android.view.Window;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.skynet.psitech.R;
//
//import androidx.annotation.NonNull;
//
//
///**
// * Created by thaopt on 8/28/17.
// */
//
//public class DialogTwoButtonUtil extends Dialog {
//    private TextView mContentTextView;
//    private ImageView imageView;
//    private TextView butonRight, btnLeft, title;
//    private int mType = 0;
//    private Context mContext;
//    private DialogOneButtonClickListener mListener;
//
//    public DialogTwoButtonUtil(@NonNull Context context) {
//        super(context);
//        this.mContext = context;
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.dialog_price_confirm);
//        getWindow().setBackgroundDrawable(
//                new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        initView();
//    }
//
//    public DialogTwoButtonUtil(@NonNull Context context, int resourceHeader, String titleContent, Spanned content, DialogOneButtonClickListener listener) {
//        super(context);
//        this.mContext = context;
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.dialog_price_confirm);
//        getWindow().setBackgroundDrawable(
//                new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        this.mListener = listener;
//
//        initView();
//        imageView.setImageResource(resourceHeader);
//        mContentTextView.setText(content);
//        title.setText(titleContent);
//    }
//    public DialogTwoButtonUtil(@NonNull Context context, int resourceHeader, String titleContent, String content, DialogOneButtonClickListener listener) {
//        super(context);
//        this.mContext = context;
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.dialog_price_confirm);
//        getWindow().setBackgroundDrawable(
//                new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        this.mListener = listener;
//
//        initView();
//        imageView.setImageResource(resourceHeader);
//        mContentTextView.setText(content);
//        title.setText(titleContent);
//    }
//
//    private void initView() {
//        imageView = (ImageView) findViewById(R.id.imgHeader);
//        btnLeft = (TextView) findViewById(R.id.tvLeftOpt);
//        butonRight = (TextView) findViewById(R.id.tvRightOpt);
//        title = (TextView) findViewById(R.id.tvTitle);
//        mContentTextView = (TextView) findViewById(R.id.content_dialog_one_button);
//        btnLeft.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dismiss();
//            }
//        });
//        butonRight.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mListener != null) mListener.okClick();
//                dismiss();
//            }
//        });
//    }
//
//    public void setText(String content) {
//        mContentTextView.setText(content);
//    }
//
//    public void setResourceImage(int content) {
//        imageView.setImageResource(content);
//    }
//
//
//
//    public void setTextHtml(String content) {
//        mContentTextView.setText(Html.fromHtml(content));
//    }
//
//    public void setContentHtml(Spannable spannable) {
//        mContentTextView.setText(spannable);
//    }
//
//    public void setType(int type) {
//        this.mType = type;
//    }
//
//    //callback
//
//    public interface DialogOneButtonClickListener {
//        void okClick();
//    }
//
//
//    public void setDialogOneButtonClick(DialogOneButtonClickListener listener) {
//        this.mListener = listener;
//    }
//}
