package com.tjyw.qmjmqd.activity;

import android.support.annotation.LayoutRes;
import android.view.View;

import com.tjyw.qmjmqd.support.AtomClientToolbarSupport;

import atom.pub.interfaces.IAtomPubToolBar;
import nucleus.presenter.Presenter;

/**
 * Created by stephen on 17-8-4.
 */
public class BaseToolbarActivity<P extends Presenter> extends BaseActivity<P> implements IAtomPubToolBar {

    protected AtomClientToolbarSupport atomClientToolbarSupport;

    @Override
    protected void initLayoutSupport(View source) {
        super.initLayoutSupport(source);
        atomClientToolbarSupport = AtomClientToolbarSupport.newInstance(this, source);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    public void tSetToolBar(CharSequence title) {
        if (null != atomClientToolbarSupport) {
            atomClientToolbarSupport.tSetToolBar(title);
        }
    }

    @Override
    public void tSetToolBar(CharSequence title, boolean hasBackBtn, Integer... right) {
        if (null != atomClientToolbarSupport) {
            atomClientToolbarSupport.tSetToolBar(title, hasBackBtn, right);
        }
    }

    @Override
    public void tSetToolbarVisibility(int visibility) {
        if (null != atomClientToolbarSupport) {
            atomClientToolbarSupport.tSetToolbarVisibility(visibility);
        }
    }

    @Override
    public void tSetToolbarCenterTitleMaxLines(int maxLines) {
        if (null != atomClientToolbarSupport) {
            atomClientToolbarSupport.tSetToolbarCenterTitleMaxLines(maxLines);
        }
    }

    @Override
    public void tSetToolbarRightSecondBubble(int count) {
        if (null != atomClientToolbarSupport) {
            atomClientToolbarSupport.tSetToolbarRightSecondBubble(count);
        }
    }

    @Override
    public void tOnToolbarNavigationClick(View v) {
        if (null != atomClientToolbarSupport) {
            atomClientToolbarSupport.tOnToolbarNavigationClick(v);
        }
    }

    @Override
    public void tOnToolbarRightViewClick(View v) {

    }
}
