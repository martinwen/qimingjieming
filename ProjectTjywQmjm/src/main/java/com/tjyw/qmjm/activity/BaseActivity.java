package com.tjyw.qmjm.activity;

import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.view.View;

import com.tjyw.atom.pub.activity.AtomPubBaseActivity;
import com.tjyw.atom.pub.inject.Injector;
import com.tjyw.atom.pub.interfaces.IAtomPubLayoutSupportMasker;
import com.tjyw.qmjm.support.AtomClientMaskerSupport;

import butterknife.ButterKnife;
import nucleus.presenter.Presenter;

/**
 * Created by stephen on 17-8-1.
 */
public class BaseActivity<P extends Presenter> extends AtomPubBaseActivity<P> implements IAtomPubLayoutSupportMasker, IAtomPubLayoutSupportMasker.OnMaskerClickListener {

    protected AtomClientMaskerSupport atomClientMaskerSupport;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this, this);
        Injector.inject(this);

        initLayoutSupport(findViewById(android.R.id.content));
    }

    protected void initLayoutSupport(View source) {
        atomClientMaskerSupport = AtomClientMaskerSupport.newInstance(this, source);
    }

    @Override
    public void maskerOnClick(View view) {

    }

    @Override
    public void maskerShowProgressView(boolean isAlpha) {
        if (null != atomClientMaskerSupport) {
            atomClientMaskerSupport.maskerShowProgressView(isAlpha);
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
