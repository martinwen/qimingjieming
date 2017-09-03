package com.tjyw.atom.network.presenter;

import com.tjyw.atom.network.IllegalRequestException;
import com.tjyw.atom.network.RetroHttpMethods;
import com.tjyw.atom.network.RxSchedulersHelper;
import com.tjyw.atom.network.model.Order;
import com.tjyw.atom.network.model.PayOrder;
import com.tjyw.atom.network.model.PayService;
import com.tjyw.atom.network.presenter.listener.OnApiPayPostListener;
import com.tjyw.atom.network.presenter.listener.OnApiPostErrorListener;
import com.tjyw.atom.network.presenter.listener.OnApiPostNamingListener;
import com.tjyw.atom.network.result.RNameDefinition;
import com.tjyw.atom.network.result.RetroListResult;
import com.tjyw.atom.network.result.RetroPayPreviewResult;
import com.tjyw.atom.network.result.RetroResult;

import nucleus.view.ViewWithPresenter;
import rx.functions.Action1;

/**
 * Created by stephen on 17-8-18.
 */
public class PayPresenter<V extends ViewWithPresenter> extends FavoritePresenter<V> {

    public void postPayService(String surname, String day) {
        RetroHttpMethods.PAY().postPayService(surname, day)
                .compose(RxSchedulersHelper.<RetroResult<PayService>>io_main())
                .subscribe(new Action1<RetroResult<PayService>>() {
                    @Override
                    public void call(RetroResult<PayService> result) {
                        if (null == result || result.illegalRequest()) {
                            if (presenterView instanceof OnApiPostErrorListener) {
                                ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.PayOrder, new IllegalRequestException(result));
                            }
                        } else if (presenterView instanceof OnApiPayPostListener.PostPayServiceListener) {
                            ((OnApiPayPostListener.PostPayServiceListener) presenterView).postOnPayServiceSuccess(result.items);
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

    public void postPayPreview(int vipId, String surname, String day, int gender, int nameNumber) {
        RetroHttpMethods.PAY().postPayPreview(vipId, surname, day, gender, nameNumber)
                .compose(RxSchedulersHelper.<RetroResult<RetroPayPreviewResult>>io_main())
                .subscribe(new Action1<RetroResult<RetroPayPreviewResult>>() {
                    @Override
                    public void call(RetroResult<RetroPayPreviewResult> result) {
                        if (null == result || null == result.items) {
                            if (presenterView instanceof OnApiPostErrorListener) {
                                ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.PayPreview, new IllegalRequestException(result));
                            }
                        } else if (presenterView instanceof OnApiPayPostListener.PostPayPreviewListener) {
                            ((OnApiPayPostListener.PostPayPreviewListener) presenterView).postOnPayPreviewSuccess(result.items);
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

    public void postPayOrder(int vipId, String surname, String day, int gender, int nameNumber) {
        RetroHttpMethods.PAY().postPayOrder(vipId, surname, day, gender, nameNumber)
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

    public void postPayOrderList(int offset, int limit) {
        RetroHttpMethods.PAY().postPayOrderList(offset, limit)
                .compose(RxSchedulersHelper.<RetroResult<RetroListResult<Order>>>io_main())
                .subscribe(new Action1<RetroResult<RetroListResult<Order>>>() {
                    @Override
                    public void call(RetroResult<RetroListResult<Order>> result) {
                        if (null == result || result.illegalRequest()) {
                            if (presenterView instanceof OnApiPostErrorListener) {
                                ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.Pay.PayOrderList, new IllegalRequestException(result));
                            }
                        } else if (presenterView instanceof OnApiPayPostListener.PostPayOrderListListener) {
                            ((OnApiPayPostListener.PostPayOrderListListener) presenterView).postOnPayOrderListSuccess(result.items);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (presenterView instanceof OnApiPostErrorListener) {
                            ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.Pay.PayOrderList, throwable);
                        }
                    }
                });
    }

    public void postPayOrderNameList(String orderNo) {
        RetroHttpMethods.PAY().postPayOrderNameList(orderNo, 0, 1000)
                .compose(RxSchedulersHelper.<RetroResult<RNameDefinition>>io_main())
                .subscribe(new Action1<RetroResult<RNameDefinition>>() {
                    @Override
                    public void call(RetroResult<RNameDefinition> result) {
                        if (null == result || result.illegalRequest()) {
                            if (presenterView instanceof OnApiPostErrorListener) {
                                ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.Pay.PayOrderNameList, new IllegalRequestException(result));
                            }
                        } else if (presenterView instanceof OnApiPostNamingListener) {
                            ((OnApiPostNamingListener) presenterView).postOnNamingSuccess(result.items);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (presenterView instanceof OnApiPostErrorListener) {
                            ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.Pay.PayOrderNameList, throwable);
                        }
                    }
                });
    }
}
