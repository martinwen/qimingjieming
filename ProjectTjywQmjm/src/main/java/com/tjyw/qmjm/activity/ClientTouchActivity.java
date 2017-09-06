package com.tjyw.qmjm.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tjyw.atom.network.RxSchedulersHelper;
import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.pub.inject.From;
import com.tjyw.qmjm.R;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by stephen on 30/08/2017.
 */
public class ClientTouchActivity extends BaseToolbarActivity {

    @From(R.id.touchWebView)
    protected WebView touchWebView;

    protected String touchUrl;

    protected String defaultTitle;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        touchUrl = pGetStringExtra(IApiField.U.url, null);
        if (TextUtils.isEmpty(touchUrl)) {
            finish();
            return;
        } else {
            setContentView(R.layout.atom_client_touch);
            tSetToolBar(pGetStringExtra(IApiField.T.title, defaultTitle));
        }

        WebSettings settings = touchWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        settings.setSaveFormData(false);
        settings.setDomStorageEnabled(true);
        settings.setGeolocationEnabled(true);
        settings.setDisplayZoomControls(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        touchWebView.setInitialScale(39);
        settings.setUseWideViewPort(true);

        if (Build.VERSION.SDK_INT >= 21) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        bindWebViewCallbacks();

        Observable.timer(1, TimeUnit.SECONDS)
                .compose(RxSchedulersHelper.<Long>io_main())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        touchWebView.loadUrl(touchUrl);
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != touchWebView) {
            touchWebView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null != touchWebView) {
            touchWebView.onPause();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && touchWebView.canGoBack()) {
            touchWebView.goBack();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    protected void bindWebViewCallbacks() {
        touchWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                if (! TextUtils.isEmpty(title)) {
                    tSetToolBar(title);
                }
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    maskerShowProgressView(false);
                } else if (100 == newProgress) {
                    maskerHideProgressView();
                }
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }
        });

        touchWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//                maskerShowMaskerLayout(error.toString(), 0);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return shouldOverrideUrlLoadingForTouch(view, request);
            }
        });
    }

    public boolean shouldOverrideUrlLoadingForTouch(WebView view, WebResourceRequest request) {

        return false;
    }
}
