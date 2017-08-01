package com.tjyw.atom.network.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;

import nucleus.presenter.RxPresenter;

/**
 * Created by stephen on 17-3-29.
 */
public class BasePresenter<View> extends RxPresenter<View> {

    protected View presenterView;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
    }

    @Override
    protected void onSave(@NonNull Bundle state) {
        super.onSave(state);
    }

    @Override
    protected void onTakeView(View view) {
        super.onTakeView(view);
        this.presenterView = view;
    }
}
