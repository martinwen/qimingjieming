package com.tjyw.atom.alipay;

import android.app.Activity;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.conf.ISymbol;
import com.tjyw.atom.network.result.RetroPayPreviewResult;
import com.tjyw.atom.network.utils.JsonUtil;

import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by stephen on 16-8-18.
 */
public class PayAlipayBuilder {

    private static final String TAG = PayAlipayBuilder.class.getSimpleName();

    private static PayAlipayBuilder builder = new PayAlipayBuilder();

    public static PayAlipayBuilder getInstance() {
        return builder;
    }

    protected String buildAlipayInfo(RetroPayPreviewResult payPreviewResult) {
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(PayConfigure.getInstance().appId, payPreviewResult);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        String sign = OrderInfoUtil2_0.getSign(params, PayConfigure.getInstance().rsaPrivate);
        return new StringBuilder(orderParam).append(ISymbol.AND).append(sign).toString();
    }

    public void build(Activity context, final RetroPayPreviewResult payPreviewResult, final IAlipayCallback callback) {
        final String payInfo = buildAlipayInfo(payPreviewResult);
        if (TextUtils.isEmpty(payInfo) || null == callback) {
            return ;
        } else {
            Timber.tag(TAG).e("payInfo::%s", payInfo);
        }

        Observable.just(context)
                .subscribeOn(Schedulers.newThread()) // 必须异步调用
                .map(new Func1<Activity, Map<String, String>>() {
                    @Override
                    public Map<String,String> call(Activity context) {
                        PayTask alipay = new PayTask(context);
                        return alipay.payV2(payInfo, true);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Map<String,String>>() {
                    @Override
                    public void call(Map<String,String> resultMap) {
                        if (null == resultMap) {
                            callback.pOnAliPayCallback(IAlipayCallback.RESULT_STATUS.FAIL, null, payPreviewResult.orderNo);
                        } else {
                            int resultStatus = IAlipayCallback.RESULT_STATUS.FAIL;
                            if (resultMap.containsKey(IApiField.R.resultStatus)) {
                                resultStatus = Integer.parseInt(resultMap.get(IApiField.R.resultStatus));
                            }

                            AlipayResult result = null;
                            if (resultMap.containsKey(IApiField.R.result)) {
                                if (! TextUtils.isEmpty(resultMap.get(IApiField.R.result))) {
                                    result = JsonUtil.getInstance().parseObject(resultMap.get(IApiField.R.result), AlipayResult.class);
                                }
                            }

                            callback.pOnAliPayCallback(resultStatus, result, payPreviewResult.orderNo);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        callback.pOnAliPayCallback(IAlipayCallback.RESULT_STATUS.FAIL, null, payPreviewResult.orderNo);
                    }
                });
    }
}
