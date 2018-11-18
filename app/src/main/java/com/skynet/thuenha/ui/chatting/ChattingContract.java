package com.skynet.thuenha.ui.chatting;


import com.skynet.thuenha.models.Message;
import com.skynet.thuenha.network.socket.SocketClient;
import com.skynet.thuenha.ui.base.BaseView;
import com.skynet.thuenha.ui.base.IBasePresenter;
import com.skynet.thuenha.ui.base.OnFinishListener;

import java.util.List;

public interface ChattingContract {
    interface View extends BaseView {
        void onSuccessGetMessages(List<Message> list);
        void onSuccessSendMessage(Message mess);
    }

    interface Presenter extends IBasePresenter,ChattingListener{
        void getMessages(int udId,int hostId,int idPost);
        void sendMessage(int idPost,int idUser,int hostId, String content, SocketClient socketClient);
    }

    interface Interactor {
        void getMessages(int udId, int idShop,int idPost);
        void sendMessage(int idPost,int idUser, int idShop, String content, String time);
    }

    interface ChattingListener extends OnFinishListener {
        void onSuccessGetMessages(List<Message> list);
        void onSuccessSendMessage(Message message);
    }
}
