package com.skynet.thuenha.ui.favourite;

import com.skynet.thuenha.models.Post;
import com.skynet.thuenha.ui.base.Presenter;

import java.util.List;

public class FavouritePresenter extends Presenter<FavouriteContract.View> implements FavouriteContract.Presenter {
    FavouriteContract.Interactor interactor;

    public FavouritePresenter(FavouriteContract.View view) {
        super(view);
        interactor = new FavouriteIImplRemote(this);
    }

    @Override
    public void getList() {
        if (isAvaliableView()) {
            view.showProgress();
            interactor.getList();
        }
    }

    @Override
    public void toggleFav(int idPost, boolean isFav) {
        if (isAvaliableView()) {
            interactor.toggleFav(idPost, isFav ? 1 : 2);
        }
    }

    @Override
    public void onDestroyView() {
        view = null;
    }

    @Override
    public void onSucessGetList(List<Post> list) {
        if (isAvaliableView()) {
            view.hiddenProgress();
            if (list != null)
                view.onSucessGetList(list);
        }
    }

    @Override
    public void onErrorApi(String message) {
        if (isAvaliableView()) {
            view.hiddenProgress();

            view.onErrorApi(message);
        }
    }

    @Override
    public void onError(String message) {
        if (isAvaliableView()) {
            view.hiddenProgress();

            view.onError(message);
        }
    }

    @Override
    public void onErrorAuthorization() {
        if (isAvaliableView()) {
            view.hiddenProgress();
            view.onErrorAuthorization();
        }
    }
}
