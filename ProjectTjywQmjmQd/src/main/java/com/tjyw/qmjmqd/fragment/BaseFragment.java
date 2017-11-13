package com.tjyw.qmjmqd.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.View;

import com.tjyw.qmjmqd.support.AtomClientMaskerSupport;

import atom.pub.fragment.AtomPubBaseFragment;
import atom.pub.inject.Injector;
import atom.pub.interfaces.IAtomPubLayoutSupportMasker;
import butterknife.ButterKnife;
import nucleus.presenter.Presenter;

/**
 * Created by stephen on 17-8-1.
 */
public class BaseFragment<P extends Presenter> extends AtomPubBaseFragment<P> implements IAtomPubLayoutSupportMasker, IAtomPubLayoutSupportMasker.OnMaskerClickListener {

    protected AtomClientMaskerSupport atomClientMaskerSupport;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);
        Injector.inject(this, view);

        initLayoutSupport(view);
    }

    protected void initLayoutSupport(View source) {
        atomClientMaskerSupport = AtomClientMaskerSupport.newInstance(this, source);
    }

    @Override
    public void maskerOnClick(View view, int clickLabelRes) {
        maskerHideMaskerLayout();
    }

    @Override
    public void maskerShowProgressView(boolean isAlpha) {
        if (null != atomClientMaskerSupport) {
            atomClientMaskerSupport.maskerShowProgressView(isAlpha);
        }
    }

    @Override
    public void maskerShowProgressView(boolean isAlpha, boolean anim) {
        if (null != atomClientMaskerSupport) {
            atomClientMaskerSupport.maskerShowProgressView(isAlpha, anim);
        }
    }

    @Override
    public void maskerShowProgressView(boolean isAlpha, boolean anim, String hint) {
        if (null != atomClientMaskerSupport) {
            atomClientMaskerSupport.maskerShowProgressView(isAlpha, anim, hint);
        }
    }

    @Override
    public void maskerHideProgressView() {
        if (null != atomClientMaskerSupport) {
            atomClientMaskerSupport.maskerHideProgressView();
        }
    }

    @Override
    public void maskerShowMaskerLayout(String msg, @StringRes int clickLabelRes) {
        if (null != atomClientMaskerSupport) {
            atomClientMaskerSupport.maskerShowMaskerLayout(msg, clickLabelRes);
        }
    }

    @Override
    public void maskerHideMaskerLayout() {
        if (null != atomClientMaskerSupport) {
            atomClientMaskerSupport.maskerHideMaskerLayout();
        }
    }
}
