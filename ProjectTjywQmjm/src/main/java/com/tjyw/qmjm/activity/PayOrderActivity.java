package com.tjyw.qmjm.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.swiftfintech.pay.MainApplication;
import com.swiftfintech.pay.activity.PayPlugin;
import com.swiftfintech.pay.bean.RequestMsg;
import com.swiftfintech.pay.handle.PayHandlerManager;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.tjyw.atom.alipay.AlipayResult;
import com.tjyw.atom.alipay.IAlipayCallback;
import com.tjyw.atom.alipay.PayAlipayBuilder;
import com.tjyw.atom.network.IllegalRequestException;
import com.tjyw.atom.network.RxSchedulersHelper;
import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.conf.ICode;
import com.tjyw.atom.network.model.PayOrder;
import com.tjyw.atom.network.presenter.PayPresenter;
import com.tjyw.atom.network.presenter.listener.OnApiPayPostListener;
import com.tjyw.atom.network.presenter.listener.OnApiPostErrorListener;
import com.tjyw.atom.network.result.RNameDefinition;
import com.tjyw.atom.network.result.RetroPayPreviewResult;
import com.tjyw.atom.pub.inject.From;
import com.tjyw.qmjm.ClientInitializer;
import com.tjyw.qmjm.R;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import nucleus.factory.RequiresPresenter;
import rx.functions.Action1;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by stephen on 17-8-17.
 */
@RequiresPresenter(PayPresenter.class)
public class PayOrderActivity extends BaseToolbarActivity<PayPresenter<PayOrderActivity>>
        implements OnApiPayPostListener.PostPayOrderListener, OnApiPayPostListener.PostPayPreviewListener, OnApiPostErrorListener, IAlipayCallback {

    @From(R.id.payService)
    protected TextView payService;

    @From(R.id.payPrice)
    protected TextView payPrice;

    @From(R.id.payUseAlipay)
    protected TextView payUseAlipay;

    @From(R.id.payUseWxPay)
    protected TextView payUseWxPay;

    @From(R.id.atom_pub_resIdsOK)
    protected TextView atom_pub_resIdsOK;

    protected PayOrderHandler payOrderHandler;

    protected RNameDefinition.Param param;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        param = (RNameDefinition.Param) pGetSerializableExtra(IApiField.P.param);
        if (null == param) {
            finish();
            return ;
        } else {
            setContentView(R.layout.atom_pay_order);
            tSetToolBar(getString(R.string.atom_pub_resStringPayOrder));

            immersionBarWith()
                    .fitsSystemWindows(true)
                    .statusBarColor(R.color.colorPrimary)
                    .statusBarDarkFont(true)
                    .init();
        }

        payUseAlipay.setSelected(true);
        payUseAlipay.setOnClickListener(this);
        payUseWxPay.setOnClickListener(this);
        atom_pub_resIdsOK.setOnClickListener(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Timber.tag("Gx").e("data.getExtras::%s", data.getExtras());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != payOrderHandler) {
            payOrderHandler.clear();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.payUseAlipay:
                v.setSelected(true);
                payUseWxPay.setSelected(false);
                break ;
            case R.id.payUseWxPay:
                v.setSelected(true);
                payUseAlipay.setSelected(false);
                break ;
            case R.id.atom_pub_resIdsOK:
                new RxPermissions(this)
                        .request(Manifest.permission.READ_PHONE_STATE)
                        .delay(1, TimeUnit.SECONDS)
                        .compose(RxSchedulersHelper.<Boolean>io_main())
                        .subscribe(new Action1<Boolean>() {
                            @Override
                            public void call(Boolean granted) {
                                if (granted) {
                                    doPostPay();
                                } else {
                                    showToast(R.string.atom_pub_resStringPermissionByUserDeny);
                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                throwable.printStackTrace();
                                showToast(R.string.atom_pub_resStringPermissionByUserDeny);
                            }
                        });
        }
    }

    protected void doPostPay() {
        if (payUseAlipay.isSelected()) {
            getPresenter().postPayPreview(
                    1,
                    param.surname,
                    param.day,
                    param.gender,
                    param.nameNumber
            );
        } else if (payUseWxPay.isSelected()) {
            if (null == payOrderHandler) {
                payOrderHandler = new PayOrderHandler(PayOrderActivity.this);
            }

            PayHandlerManager.registerHandler(PayHandlerManager.PAY_H5_RESULT, payOrderHandler);
            maskerShowProgressView(true);
            getPresenter().postPayOrder(
                    1,
                    param.surname,
                    param.day,
                    param.gender,
                    param.nameNumber
            );
        }
    }

    @Override
    public void postOnPayOrderSuccess(PayOrder payOrder) {
        RequestMsg msg = new RequestMsg();
        msg.setTokenId(payOrder.token_id);
        msg.setTradeType(MainApplication.PAY_WX_WAP);
        PayPlugin.unifiedH5Pay(this, msg);
    }

    @Override
    public void postOnPayPreviewSuccess(RetroPayPreviewResult result) {
        PayAlipayBuilder.getInstance().build(this, result, this);
    }

    @Override
    public void postOnExplainError(int postId, Throwable throwable) {
        maskerHideProgressView();
        if (throwable instanceof IllegalRequestException) {
            showToast(throwable.getMessage());
        } else {
            throwable.printStackTrace();
            showToast(R.string.atom_pub_resStringNetworkBroken);
        }
    }

    @Override
    public void pOnAliPayCallback(int resultStatus, AlipayResult result) {
        switch (resultStatus) {
            case RESULT_STATUS.SUCCESS:
                setResult(ICode.PAY.ALIPAY_SUCCESS);
                ClientInitializer.getInstance().clearUserLocalCacheData(getApplicationContext());
                break ;
            case RESULT_STATUS.FAIL:
            default:

        }
    }

    public static class PayOrderHandler extends Handler {

        protected WeakReference<PayOrderActivity> context;

        public PayOrderHandler(PayOrderActivity payOrderActivity) {
            this.context = new WeakReference<PayOrderActivity>(payOrderActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            PayOrderActivity payOrderActivity = context.get();
            if (null != payOrderActivity && ! payOrderActivity.isFinishing()) {
                switch (msg.what) {
                    case PayHandlerManager.PAY_H5_FAILED:
                        payOrderActivity.showToast(String.valueOf(msg.obj));
                }
            } else {
                clear();
            }
        }

        public void clear() {
            removeCallbacksAndMessages(null);
            if (null != context) {
                context.clear();
                context = null;
            }
        }
    }
}
