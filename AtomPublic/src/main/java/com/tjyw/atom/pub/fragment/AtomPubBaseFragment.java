package com.tjyw.atom.pub.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.View;
import android.widget.Toast;

import com.tjyw.atom.network.utils.ArrayUtil;
import com.tjyw.atom.pub.AtomPubDisplayConfigure;
import com.tjyw.atom.pub.activity.AtomPubBaseActivity;
import com.tjyw.atom.pub.interfaces.IAtomPubFragment;
import com.tjyw.atom.pub.interfaces.IAtomPubIntentExtra;
import com.tjyw.atom.pub.interfaces.IAtomPubTools;

import java.io.Serializable;

import butterknife.ButterKnife;
import nucleus.presenter.Presenter;
import nucleus.view.NucleusSupportFragment;

/**
 * Created by stephen on 07/08/2017.
 */
public class AtomPubBaseFragment<P extends Presenter> extends NucleusSupportFragment<P> implements IAtomPubIntentExtra, IAtomPubFragment, IAtomPubTools {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        AtomPubDisplayConfigure.getInstance().pSetScreenSize(display);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public Intent pGetIntent() {
        return null;
    }

    @Override
    public Bundle pGetBundle() {
        return getArguments();
    }

    @Override
    public int pGetIntExtra(String key, int defaultValue) {
        Bundle bundle = pGetBundle();
        return null == bundle ? defaultValue : bundle.getInt(key, defaultValue);
    }

    @Override
    public double pGetDoubleExtra(String key, double defaultValue) {
        Bundle bundle = pGetBundle();
        return null == bundle ? defaultValue : bundle.getDouble(key, defaultValue);
    }

    @Override
    public long pGetLongExtra(String key, long defaultValue) {
        Bundle bundle = pGetBundle();
        return null == bundle ? defaultValue : bundle.getLong(key, defaultValue);
    }

    @Override
    public String pGetStringExtra(String key, String defaultValue) {
        Bundle bundle = pGetBundle();
        return null == bundle ? defaultValue : bundle.getString(key);
    }

    @Override
    public Boolean pGetBooleanExtra(String key, boolean defaultValue) {
        Bundle bundle = pGetBundle();
        return null == bundle ? defaultValue : bundle.getBoolean(key, defaultValue);
    }

    @Override
    public Serializable pGetSerializableExtra(String key) {
        Bundle bundle = pGetBundle();
        return null == bundle ? null : bundle.getSerializable(key);
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
            FragmentTransaction ft = getFragmentManager().beginTransaction();
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
            FragmentTransaction ft = getFragmentManager().beginTransaction();
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
        if (null != getContext()) {
            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void pHideSoftInput() {
        if (getActivity() instanceof AtomPubBaseActivity) {
            ((AtomPubBaseActivity) getActivity()).pHideSoftInput();
        }
    }

    @Override
    public void pShowSoftInput() {
        if (getActivity() instanceof AtomPubBaseActivity) {
            ((AtomPubBaseActivity) getActivity()).pShowSoftInput();
        }
    }
}
