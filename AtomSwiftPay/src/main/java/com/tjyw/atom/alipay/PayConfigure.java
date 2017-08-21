package com.tjyw.atom.alipay;

import android.content.Context;

import timber.log.Timber;

/**
 * Created by stephen on 10/02/2017.
 */
public class PayConfigure {

    static final String TAG = PayConfigure.class.getSimpleName();

    private static volatile PayConfigure _instance;

    private PayConfigure() {

    }

    public static PayConfigure getInstance() {
        if (_instance == null) {
            synchronized (PayConfigure.class) {
                if (_instance == null) {
                    _instance = new PayConfigure();
                }
            }
        }

        return _instance;
    }

    protected Context context;

    protected String appId;

    protected String partner;

    protected String seller;

    protected String rsaPrivate;

    protected String rsaPublic;

    protected String notifyUrl;

    protected String wxAppId;

    public PayConfigure setContext(Context context) {
        this.context = context;
        return this;
    }

    public PayConfigure setAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public PayConfigure setPartner(String partner) {
        this.partner = partner;
        return this;
    }

    public PayConfigure setSeller(String seller) {
        this.seller = seller;
        return this;
    }

    public PayConfigure setRsaPrivate(String rsaPrivate) {
        this.rsaPrivate = rsaPrivate;
        return this;
    }

    public PayConfigure setRsaPublic(String rsaPublic) {
        this.rsaPublic = rsaPublic;
        return this;
    }

    public PayConfigure setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
        return this;
    }

    public PayConfigure setWxAppId(String wxAppId) {
        this.wxAppId = wxAppId;
        return this;
    }

    public PayConfigure dump() {
        Timber.tag(TAG).e("context::%s", context);
        Timber.tag(TAG).e("appId::%s", appId);
        Timber.tag(TAG).e("partner::%s", partner);
        Timber.tag(TAG).e("seller::%s", seller);
        Timber.tag(TAG).e("rsaPrivate::%s", rsaPrivate);
        Timber.tag(TAG).e("rsaPublic::%s", rsaPublic);
        Timber.tag(TAG).e("notifyUrl::%s", notifyUrl);
        Timber.tag(TAG).e("wxAppId::%s", wxAppId);
        return this;
    }
}
