package com.tjyw.qmjm.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.SyncStateContract;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.swiftfintech.pay.handle.PayHandlerManager;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tjyw.atom.alipay.AlipayResult;
import com.tjyw.atom.alipay.IAlipayCallback;
import com.tjyw.atom.alipay.PayAlipayBuilder;
import com.tjyw.atom.network.IllegalRequestException;
import com.tjyw.atom.network.RxSchedulersHelper;
import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.conf.ICode;
import com.tjyw.atom.network.model.PayCoupon;
import com.tjyw.atom.network.model.PayOrder;
import com.tjyw.atom.network.model.PayOrderNumber;
import com.tjyw.atom.network.model.PayService;
import com.tjyw.atom.network.param.ListRequestParam;
import com.tjyw.atom.network.presenter.PayPresenter;
import com.tjyw.atom.network.presenter.listener.OnApiPayPostListener;
import com.tjyw.atom.network.presenter.listener.OnApiPostErrorListener;
import com.tjyw.atom.network.presenter.listener.OnApiUserPostListener;
import com.tjyw.atom.network.result.RPayPacketResult;
import com.tjyw.atom.network.result.RetroPayPreviewResult;
import com.tjyw.atom.network.utils.ArrayUtil;
import com.tjyw.qmjm.Configure;
import com.tjyw.qmjm.R;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import atom.pub.inject.From;
import nucleus.factory.RequiresPresenter;
import rx.Observable;
import rx.functions.Action1;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.tjyw.qmjm.Constans.PAY_BROADCASTRECEIVER;
import static com.tjyw.qmjm.Constans.PAY_ORDER_STATUS;

/**
 * Created by stephen on 17-8-17.
 */
@RequiresPresenter(PayPresenter.class)
public class PayOrderActivity extends BaseToolbarActivity<PayPresenter<PayOrderActivity>> implements
        OnApiPostErrorListener,
        OnApiUserPostListener.PostUserListPacketListener,
        OnApiPayPostListener.PostPayOrderListener,
        OnApiPayPostListener.PostPayPreviewListener,
        IAlipayCallback {

    @From(R.id.payServiceName)
    protected TextView payServiceName;

    @From(R.id.payServicePrice)
    protected TextView payServicePrice;

    @From(R.id.payServiceCoupon)
    protected TextView payServiceCoupon;

    @From(R.id.payServiceSummary)
    protected TextView payServiceSummary;

    @From(R.id.payUseAlipay)
    protected ViewGroup payUseAlipay;

    @From(R.id.payUseWxPay)
    protected TextView payUseWxPay;

    @From(R.id.atom_pub_resIdsOK)
    protected TextView atom_pub_resIdsOK;

    protected ListRequestParam listRequestParam;

    protected PayService payService;

    private MyReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listRequestParam = (ListRequestParam) pGetSerializableExtra(IApiField.P.param);
        payService = (PayService) pGetSerializableExtra(IApiField.P.payService);
        if (null == listRequestParam || null == payService) {
            finish();
            return;
        } else {
            setContentView(R.layout.atom_pay_order);
            tSetToolBar(getString(R.string.atom_pub_resStringPayOrder));

            immersionBarWith()
                    .fitsSystemWindows(true)
                    .statusBarColor(R.color.colorPrimary)
                    .statusBarDarkFont(true)
                    .init();
        }

        SpannableStringBuilder builder = new SpannableStringBuilder(getString(R.string.atom_pub_resStringPayPrice));
        int length = builder.length();
        builder.append(getString(R.string.atom_pub_resStringRMB_s_Yuan_Simple, payService.money));
        builder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.atom_pub_resTextColorRed)), length, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        payServicePrice.setText(builder);
        payServiceName.setText(getString(R.string.atom_pub_resStringPayService, payService.service));

        payUseAlipay.setSelected(true);
        payUseAlipay.setOnClickListener(this);
        payUseWxPay.setOnClickListener(this);
        atom_pub_resIdsOK.setOnClickListener(this);
        receiver = new MyReceiver();
        registerReceiver(receiver, new IntentFilter(PAY_BROADCASTRECEIVER));

        maskerShowProgressView(true);
        getPresenter().postUserMyPacket(payService.id);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.payUseAlipay:
                v.setSelected(true);
                payUseWxPay.setSelected(false);
                break;
            case R.id.payUseWxPay:
                v.setSelected(true);
                payUseAlipay.setSelected(false);
                break;
            case R.id.atom_pub_resIdsOK:
                RxPermissions permissions = new RxPermissions(this);
                if (permissions.isGranted(Manifest.permission.READ_PHONE_STATE)) {
                    doPostPay();
                } else {
                    permissions
                            .request(Manifest.permission.READ_PHONE_STATE)
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
    }

    protected void doPostPay() {
        if (payUseAlipay.isSelected()) {
            maskerShowProgressView(true);
            getPresenter().postPayPreview(
                    payService.id,
                    payService.money,
                    listRequestParam.surname,
                    listRequestParam.day,
                    listRequestParam.gender,
                    listRequestParam.nameNumber,
                    listRequestParam.redPacketId
            );
        } else if (payUseWxPay.isSelected()) {
            maskerShowProgressView(true);
            getPresenter().postPayOrder(
                    payService.id,
                    null, listRequestParam.surname,
                    listRequestParam.day,
                    listRequestParam.gender,
                    listRequestParam.nameNumber,
                    listRequestParam.redPacketId
            );
        }
    }

    @Override
    public void postOnUserListPacketSuccess(RPayPacketResult result) {
        maskerHideProgressView();

        List<PayCoupon> list = new ArrayList<PayCoupon>();
        result.getPayPacketList(list);
        if (!ArrayUtil.isEmpty(list)) {
            PayCoupon payCoupon = list.get(0);
            if (null != payCoupon) {
                SpannableStringBuilder builder = new SpannableStringBuilder(getString(R.string.atom_pub_resStringPayCoupon));
                int length = builder.length();
                builder.append(" - ");
                builder.append(getString(R.string.atom_pub_resStringRMB_s_Yuan_Simple, payCoupon.money));
                builder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.atom_pub_resTextColorRed)), length, builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                payServiceCoupon.setText(builder);

                if (null != listRequestParam) {
                    listRequestParam.redPacketId = payCoupon.id;
                }

                builder = new SpannableStringBuilder(getString(R.string.atom_pub_resStringPaySummary));
                length = builder.length();
                builder.append(getString(R.string.atom_pub_resStringRMB_s_Yuan_Simple, payCoupon.subTotal));
                builder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.atom_pub_resTextColorRed)), length, builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                payServiceSummary.setText(builder);
            }

            return;
        }

        SpannableStringBuilder builder = new SpannableStringBuilder(getString(R.string.atom_pub_resStringPayCoupon));
        int length = builder.length();
        builder.append(getString(R.string.atom_pub_resStringPayCouponLack));
        builder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.atom_pub_resTextColorGrey)), length, builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        payServiceCoupon.setText(builder);

        builder = new SpannableStringBuilder(getString(R.string.atom_pub_resStringPaySummary));
        length = builder.length();
        builder.append(getString(R.string.atom_pub_resStringRMB_s_Yuan_Simple, payService.money));
        builder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.atom_pub_resTextColorRed)), length, builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        payServiceSummary.setText(builder);
    }

    @Override
    public void postOnPayOrderSuccess(PayOrder payOrder) {
        listRequestParam.orderNo = payOrder.orderNo;
        maskerHideProgressView();

        PayOrder.Wxparameter wxparameter = payOrder.data;
        final IWXAPI iwxapi = WXAPIFactory.createWXAPI(this, wxparameter.appID);
        iwxapi.registerApp(wxparameter.appID);
        PayReq request = new PayReq();
        request.appId = wxparameter.appID;
        request.partnerId = wxparameter.partnerId;
        request.prepayId = wxparameter.prepayId;
        request.packageValue = wxparameter.packageValue;
        request.nonceStr = wxparameter.nonceStr;
        request.timeStamp = wxparameter.timestamp;
        request.sign = wxparameter.sign;
        iwxapi.sendReq(request);
    }

    public class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getIntExtra(PAY_ORDER_STATUS, 100) == 0) {
                Intent data = new Intent();
                data.putExtra(IApiField.O.orderNo, listRequestParam.orderNo);
                PayOrderActivity.this.setResult(ICode.PAY.WX_SUCCESS, data);
                finishDelayed();
            } else if (!TextUtils.isEmpty(listRequestParam.orderNo)) {
                getPresenter().postPayLog(
                        listRequestParam.orderNo,
                        "9999",
                        2,
                        intent.getIntExtra(PAY_ORDER_STATUS, 100) + ""
                );
            }
        }
    }

    @Override
    public void postOnPayPreviewSuccess(RetroPayPreviewResult result) {
        listRequestParam.orderNo = result.orderNo;
        PayAlipayBuilder.getInstance().build(this, result, this);
        maskerHideProgressView();
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
    public void pOnAliPayCallback(int resultStatus, AlipayResult result, String orderNo) {
        switch (resultStatus) {
            case RESULT_STATUS.SUCCESS:
                Intent data = new Intent();
                data.putExtra(IApiField.O.orderNo, listRequestParam.orderNo);
                setResult(ICode.PAY.ALIPAY_SUCCESS, data);
                finishDelayed();
                break;
            case RESULT_STATUS.FAIL:
            default:
                if (null != result && !TextUtils.isEmpty(listRequestParam.orderNo)) {
                    getPresenter().postPayLog(
                            listRequestParam.orderNo,
                            String.valueOf(resultStatus),
                            1,
                            null == result.alipay_trade_app_pay_response ? null : result.alipay_trade_app_pay_response.msg
                    );
                }
        }
    }

    protected void finishDelayed() {
        EventBus.getDefault().post(new PayOrderNumber());
        maskerShowProgressView(true, true, getString(R.string.atom_pub_resStringNetworkRequesting));
        Observable.timer(3, TimeUnit.SECONDS)
                .compose(RxSchedulersHelper.<Long>io_main())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        finish();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        finish();
                    }
                });
    }
}
