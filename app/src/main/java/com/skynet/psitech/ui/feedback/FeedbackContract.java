package com.skynet.psitech.ui.feedback;

import com.google.android.gms.maps.model.LatLng;
import com.skynet.psitech.models.MyPlace;
import com.skynet.psitech.ui.base.BaseView;
import com.skynet.psitech.ui.base.IBasePresenter;
import com.skynet.psitech.ui.base.OnFinishListener;

import java.io.File;
import java.util.List;

public interface FeedbackContract {
    interface View extends BaseView {
        void addFeedbackSucess();
    }

    interface Presenter extends IBasePresenter, Listener {
        void addFeedback(int bookingId, String content, String address, String time, List<File> fileList);
    }

    interface Interactor {
        void addFeedback(int bookingId, String content, String address, String time, List<File> fileList);

    }

    interface Listener extends OnFinishListener {
        void addFeedbackSucess();
    }
}
