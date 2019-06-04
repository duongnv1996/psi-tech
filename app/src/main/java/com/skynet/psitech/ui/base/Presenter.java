package com.skynet.psitech.ui.base;

public class Presenter<V> {
   public V view;

    public Presenter(V view) {
        this.view = view;
    }

  protected    boolean isAvaliableView() {
        return view != null;
    }
}
