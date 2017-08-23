package com.tjyw.atom.pub.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.tjyw.atom.network.utils.ArrayUtil;
import com.tjyw.atom.pub.interfaces.IAtomPubFragment;
import com.tjyw.atom.pub.interfaces.IAtomPubIntentExtra;
import com.tjyw.atom.pub.interfaces.IAtomPubTools;

import java.io.Serializable;

import nucleus.presenter.Presenter;
import nucleus.view.NucleusAppCompatActivity;

/**
 * Created by stephen on 17-8-1.
 */
public abstract class AtomPubBaseActivity<P extends Presenter> extends NucleusAppCompatActivity<P>
        implements View.OnClickListener, IAtomPubIntentExtra, IAtomPubFragment, IAtomPubTools {

    protected ImmersionBar immersionBar;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != immersionBar) {
            immersionBar.destroy();
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public Intent pGetIntent() {
        return getIntent();
    }

    @Override
    public Bundle pGetBundle() {
        return null;
    }

    @Override
    public int pGetIntExtra(String key, int defaultValue) {
        Intent intent = getIntent();
        return null == intent ? defaultValue : intent.getIntExtra(key, defaultValue);
    }

    @Override
    public double pGetDoubleExtra(String key, double defaultValue) {
        Intent intent = getIntent();
        return null == intent ? defaultValue : intent.getDoubleExtra(key, defaultValue);
    }

    @Override
    public long pGetLongExtra(String key, long defaultValue) {
        Intent intent = getIntent();
        return null == intent ? defaultValue : intent.getLongExtra(key, defaultValue);
    }

    @Override
    public String pGetStringExtra(String key, String defaultValue) {
        Intent intent = getIntent();
        return null == intent ? defaultValue : intent.getStringExtra(key);
    }

    @Override
    public Boolean pGetBooleanExtra(String key, boolean defaultValue) {
        Intent intent = getIntent();
        return null == intent ? defaultValue : intent.getBooleanExtra(key, defaultValue);
    }

    @Override
    public Serializable pGetSerializableExtra(String key) {
        Intent intent = getIntent();
        return null == intent ? null : intent.getSerializableExtra(key);
    }

    @Override
    public void pShowFragment(Fragment... fragments) {
        pShowFragment(0, 0, fragments);
    }

    @Override
    public void pHideFragment(Fragment... fragments) {
        pHideFragment(0, 0, fragments);
    }

    @Override
    public void pShowFragment(int enter, int exit, Fragment... fragments) {
        if (! ArrayUtil.isEmpty(fragments)) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            for (Fragment fragment: fragments) {
                if (enter != 0 || exit != 0) {
                    ft.setCustomAnimations(enter, exit);
                }

                ft.show(fragment);
            }

            ft.commitAllowingStateLoss();
        }
    }

    @Override
    public void pHideFragment(int enter, int exit, Fragment... fragments) {
        if (! ArrayUtil.isEmpty(fragments)) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            for (Fragment fragment: fragments) {
                if (enter != 0 || exit != 0) {
                    ft.setCustomAnimations(enter, exit);
                }

                ft.hide(fragment);
            }

            ft.commitAllowingStateLoss();
        }
    }

    @Override
    public void showToast(@StringRes int id) {
        showToast(getString(id));
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void pHideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (null != imm) {
            View focus = getCurrentFocus();
            if (null != focus && null != focus.getWindowToken()) {
                imm.hideSoftInputFromWindow(focus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Override
    public void pShowSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (null != imm) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

    protected ImmersionBar immersionBarWith() {
        return immersionBar = ImmersionBar.with(this);
    }
}
