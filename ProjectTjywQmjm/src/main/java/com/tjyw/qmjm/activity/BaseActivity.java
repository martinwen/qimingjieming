package com.tjyw.qmjm.activity;

import android.support.annotation.LayoutRes;

import com.tjyw.atom.pub.activity.AtomPubBaseActivity;
import com.tjyw.atom.pub.inject.Injector;

import butterknife.ButterKnife;
import nucleus.presenter.Presenter;

/**
 * Created by stephen on 17-8-1.
 */
public class BaseActivity<P extends Presenter> extends AtomPubBaseActivity<P> {

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this, this);
        Injector.inject(this);
    }
}
