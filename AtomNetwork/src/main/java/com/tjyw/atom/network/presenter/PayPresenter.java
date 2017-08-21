package com.tjyw.atom.network.presenter;

import com.tjyw.atom.network.IllegalRequestException;
import com.tjyw.atom.network.RetroHttpMethods;
import com.tjyw.atom.network.RxSchedulersHelper;
import com.tjyw.atom.network.model.Pay;
import com.tjyw.atom.network.model.PayOrder;
import com.tjyw.atom.network.presenter.listener.OnApiPayPostListener;
import com.tjyw.atom.network.presenter.listener.OnApiPostErrorListener;
import com.tjyw.atom.network.result.RetroPayPreviewResult;
import com.tjyw.atom.network.result.RetroResult;

import nucleus.view.ViewWithPresenter;
import rx.functions.Action1;

/**
 * Created by stephen on 17-8-18.
 */
public class PayPresenter<V extends ViewWithPresenter> extends BasePresenter<V> {

    public void postPay() {
        RetroHttpMethods.PAY().postPay(1)
                .compose(RxSchedulersHelper.<RetroResult<Pay>>io_main())
                .subscribe(new Action1<RetroResult<Pay>>() {
                    @Override
                    public void call(RetroResult<Pay> result) {
                        if (null == result || result.illegalRequest()) {
                            if (presenterView instanceof OnApiPostErrorListener) {
                                ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.PayOrder, new IllegalRequestException(result));
                            }
                        } else if (presenterView instanceof OnApiPayPostListener.PostPayListener) {
                            ((OnApiPayPostListener.PostPayListener) presenterView).postOnPaySuccess(result.items);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (presenterView instanceof OnApiPostErrorListener) {
                            ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.PayOrder, throwable);
                        }
                    }
                });
    }

    public void postPayOrder(int vipId) {
        RetroHttpMethods.PAY().postPayOrder(vipId)
                .compose(RxSchedulersHelper.<RetroResult<PayOrder>>io_main())
                .subscribe(new Action1<RetroResult<PayOrder>>() {
                    @Override
                    public void call(RetroResult<PayOrder> result) {
                        if (null == result || result.illegalRequest()) {
                            if (presenterView instanceof OnApiPostErrorListener) {
                                ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.PayOrder, new IllegalRequestException(result));
                            }
                        } else if (presenterView instanceof OnApiPayPostListener.PostPayOrderListener) {
                            ((OnApiPayPostListener.PostPayOrderListener) presenterView).postOnPayOrderSuccess(result.items);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (presenterView instanceof OnApiPostErrorListener) {
                            ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.PayOrder, throwable);
                        }
                    }
                });
    }

    public void postPayPreview(int vipId, final int payType) {
        RetroHttpMethods.PAY().postPayPreview(vipId, payType)
                .compose(RxSchedulersHelper.<RetroResult<RetroPayPreviewResult>>io_main())
                .subscribe(new Action1<RetroResult<RetroPayPreviewResult>>() {
                    @Override
                    public void call(RetroResult<RetroPayPreviewResult> result) {
                        if (null == result || null == result.items) {
                            if (presenterView instanceof OnApiPostErrorListener) {
                                ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.PayPreview, new IllegalRequestException(result));
                            }
                        } else if (presenterView instanceof OnApiPayPostListener.PostPayPreviewListener) {
                            ((OnApiPayPostListener.PostPayPreviewListener) presenterView).postOnPayPreviewSuccess(result.items, payType);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (presenterView instanceof OnApiPostErrorListener) {
                            ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.PayPreview, throwable);
                        }
                    }
                });
    }
}
