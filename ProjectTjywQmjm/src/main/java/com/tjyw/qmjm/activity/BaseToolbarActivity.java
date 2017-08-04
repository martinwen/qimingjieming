package com.tjyw.qmjm.activity;

import android.support.annotation.LayoutRes;
import android.view.View;

import com.tjyw.qmjm.support.AtomClientToolbarSupport;

import nucleus.presenter.Presenter;

/**
 * Created by stephen on 17-8-4.
 */
public class BaseToolbarActivity<P extends Presenter> extends BaseActivity<P> {

    protected AtomClientToolbarSupport atomClientToolbarSupport;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        atomClientToolbarSupport = AtomClientToolbarSupport.newInstance(null, findViewById(android.R.id.content));
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        atomClientToolbarSupport = AtomClientToolbarSupport.newInstance(null, view);
    }
}
