package com.tjyw.bbqm.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alipay.sdk.app.H5PayCallback;
import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.util.H5PayResultModel;
import com.brianjmelton.stanley.ProxyGenerator;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.tjyw.atom.network.Network;
import com.tjyw.atom.network.RxSchedulersHelper;
import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.interfaces.IPrefUser;
import com.tjyw.atom.network.model.ClientInit;
import com.tjyw.atom.network.utils.DeviceUtil;
import com.tjyw.atom.network.utils.JsonUtil;
import com.tjyw.bbqm.ClientQmjmApplication;
import com.tjyw.bbqm.R;

import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import atom.pub.inject.From;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import timber.log.Timber;

/**
 * Created by stephen on 07/08/2017.
 */
public class ClientMasterCeSuanFragment extends BaseFragment {

    @From(R.id.masterCeSuanWebContainer)
    protected WebView masterCeSuanWebContainer;

    protected String homePage;

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
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setJavaScriptEnabled(true);
        settings.setSavePassword(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setMinimumFontSize(settings.getMinimumFontSize() + 8);
        settings.setAllowFileAccess(false);
        settings.setTextSize(WebSettings.TextSize.NORMAL);
        masterCeSuanWebContainer.setVerticalScrollbarOverlay(true);

        if (Build.VERSION.SDK_INT >= 21) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        bindWebViewCallbacks();

        homePage = "http://www.qimingjieming.com/wap/qiming";
        ClientInit clientInit = ClientInit.getInstance(ClientQmjmApplication.getContext());
        if (null != clientInit && ! TextUtils.isEmpty(clientInit.qmsmwap)) {
            homePage = clientInit.qmsmwap;
        }

        RxPermissions permissions = new RxPermissions(getActivity());
        if (permissions.isGranted(Manifest.permission.READ_PHONE_STATE)) {
            homePage += appendWebUrl(masterCeSuanWebContainer.getSettings());
        }

        Observable.just(homePage)
                .delay(1, TimeUnit.SECONDS)
                .compose(RxSchedulersHelper.<String>io_main())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String url) {
                        masterCeSuanWebContainer.loadUrl(url);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
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

    public boolean canGoBack() {
        return isVisible() && null != masterCeSuanWebContainer && masterCeSuanWebContainer.canGoBack();
    }

    public void goBack() {
        if (isVisible() && null != masterCeSuanWebContainer) {
            masterCeSuanWebContainer.goBack();
        }
    }

    protected void bindWebViewCallbacks() {
        masterCeSuanWebContainer.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }
        });

        masterCeSuanWebContainer.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                Timber.e("onReceivedError::%s, WebResourceError::%s", request, error);
            }

            @Override
            public boolean shouldOverrideUrlLoading(final WebView view, String url) {
                Timber.e("shouldOverrideUrlLoading::%s", url);
                if (url.startsWith("weixin://wap/pay?")) {
                    try {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                        view.goBack();
                        showToast("您的手机不支持微信");
                    }
                    return true;
                } else  {
                    PayTask task = new PayTask(getActivity());
                    boolean isIntercepted = task.payInterceptorWithUrl(url, true, new H5PayCallback() {
                        @Override
                        public void onPayResult(final H5PayResultModel result) {
                            final String url=result.getReturnUrl();
                            Observable.just(url)
                                    .subscribeOn(AndroidSchedulers.mainThread())
                                    .filter(new Func1<String, Boolean>() {
                                        @Override
                                        public Boolean call(String s) {
                                            if (TextUtils.isEmpty(url)) {
                                                if (view.canGoBack()) {
                                                    view.goBack();
                                                }

                                                return false;
                                            } else {
                                                return true;
                                            }
                                        }
                                    })
                                    .subscribe(new Action1<String>() {
                                        @Override
                                        public void call(String s) {
                                            view.loadUrl(url);
                                        }
                                    }, new Action1<Throwable>() {
                                        @Override
                                        public void call(Throwable throwable) {

                                        }
                                    });
                        }
                    });

                    if (isIntercepted) {
                        return true;
                    } else  {
                        view.loadUrl(url);
                        return super.shouldOverrideUrlLoading(view, url);
                    }
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
    }

    protected String appendWebUrl(WebSettings settings) {
        Context context = ClientQmjmApplication.getContext();

        ObjectNode fromNativeParam = JsonUtil.getInstance().getEmptyNode();
        fromNativeParam.put(IApiField.V.version, DeviceUtil.getClientVersionName(context));
        fromNativeParam.put(IApiField.C.cid, Network.getInstance().getCid());
        fromNativeParam.put(IApiField.P.pid, Network.getInstance().getPid());
        fromNativeParam.put(IApiField.P.packageName, context.getPackageName());
        fromNativeParam.put(IApiField.U.userAgent, settings.getUserAgentString());
        IPrefUser user = new ProxyGenerator().create(ClientQmjmApplication.getContext(), IPrefUser.class);
        if (null != user) {
            fromNativeParam.put(IApiField.U.uid, user.getUserSession());
        }

        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            @SuppressLint("MissingPermission") String imei = telephonyManager.getDeviceId();
            if (! TextUtils.isEmpty(imei)) {
                fromNativeParam.put(IApiField.I.imei, imei);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "?fromNative=" + URLEncoder.encode(JsonUtil.getInstance().toJsonString(fromNativeParam));
    }

}
