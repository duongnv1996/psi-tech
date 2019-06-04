package com.skynet.psitech.ui.feedback;

import com.skynet.psitech.models.Feedback;
import com.skynet.psitech.ui.base.Presenter;
import com.skynet.psitech.ui.listfeedback.ListProductContract;
import com.skynet.psitech.ui.listfeedback.ListProductImplRemote;

import java.io.File;
import java.util.List;

public class FeedbackPresenter extends Presenter<FeedbackContract.View> implements FeedbackContract.Presenter {
    FeedbackContract.Interactor interactor;

    public FeedbackPresenter(FeedbackContract.View view) {
        super(view);
        interactor = new FeedbackImplRemote(this);
    }



    @Override
    public void onDestroyView() {
        view = null;
    }




    @Override
    public void onErrorApi(String message) {
        if(isAvaliableView()){
            view.hiddenProgress();
            view.onErrorApi(message);
        }
    }

    @Override
    public void onError(String message) {
        if(isAvaliableView()){
            view.hiddenProgress();
            view.onError(message);
        }
    }

    @Override
    public void onErrorAuthorization() {
        if(isAvaliableView()){
            view.hiddenProgress();
            view.onErrorAuthorization();
        }
    }

    @Override
    public void addFeedback(int bookingId, String content, String address, String time, List<File> fileList) {
        if(isAvaliableView()){
            view.showProgress();
            interactor.addFeedback(bookingId,content,address,time,fileList);
        }
    }

    @Override
    public void addFeedbackSucess() {
        if(isAvaliableView()){
            view.hiddenProgress();
            view.addFeedbackSucess();
        }
    }
}
