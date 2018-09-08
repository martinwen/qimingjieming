package com.tjyw.bbqm.fragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.tjyw.atom.network.RxSchedulersHelper;
import com.tjyw.bbqm.R;

import java.util.concurrent.TimeUnit;

import atom.pub.fragment.AtomPubBaseFragment;
import atom.pub.inject.From;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by stephen on 07/08/2017.
 */
public class ClientMasterCeSuanFragment extends AtomPubBaseFragment {

    @From(R.id.masterCeSuanWebContainer)
    protected WebView masterCeSuanWebContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.atom_master_cesuan, null);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        WebSettings settings = masterCeSuanWebContainer.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        settings.setSaveFormData(false);
        settings.setDomStorageEnabled(true);
        settings.setGeolocationEnabled(true);
        settings.setDisplayZoomControls(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        masterCeSuanWebContainer.setInitialScale(39);
        settings.setUseWideViewPort(true);

        if (Build.VERSION.SDK_INT >= 21) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        Observable.timer(1, TimeUnit.SECONDS)
                .compose(RxSchedulersHelper.<Long>io_main())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        masterCeSuanWebContainer.loadUrl("http://www.qimingjieming.com/wap/qiming");
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != masterCeSuanWebContainer) {
            masterCeSuanWebContainer.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (null != masterCeSuanWebContainer) {
            masterCeSuanWebContainer.onPause();
        }
    }
}
