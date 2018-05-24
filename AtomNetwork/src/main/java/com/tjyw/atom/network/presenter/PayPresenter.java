package com.tjyw.atom.network.presenter;

import com.tjyw.atom.network.IllegalRequestException;
import com.tjyw.atom.network.RetroHttpMethods;
import com.tjyw.atom.network.RxSchedulersHelper;
import com.tjyw.atom.network.model.NameDefinition;
import com.tjyw.atom.network.model.Order;
import com.tjyw.atom.network.model.PayOrder;
import com.tjyw.atom.network.model.PayOrderNumber;
import com.tjyw.atom.network.model.PayService;
import com.tjyw.atom.network.presenter.listener.OnApiPayPostListener;
import com.tjyw.atom.network.presenter.listener.OnApiPostErrorListener;
import com.tjyw.atom.network.presenter.listener.OnApiPostNamingListener;
import com.tjyw.atom.network.result.REmptyResult;
import com.tjyw.atom.network.result.RNameDefinition;
import com.tjyw.atom.network.result.RetroListResult;
import com.tjyw.atom.network.result.RetroPayPreviewResult;
import com.tjyw.atom.network.result.RetroResult;

import java.util.List;

import nucleus.view.ViewWithPresenter;
import rx.functions.Action1;

/**
 * Created by stephen on 17-8-18.
 */
public class PayPresenter<V extends ViewWithPresenter> extends FavoritePresenter<V> {

    public void postPayListVipDiscount(final int type, String surname, String day) {
        RetroHttpMethods.PAY().postPayListVipDiscount(type, surname, day, 1)
                .compose(RxSchedulersHelper.<RetroResult<PayService>>io_main())
                .subscribe(new Action1<RetroResult<PayService>>() {
                    @Override
                    public void call(RetroResult<PayService> result) {
                        if (null == result || result.illegalRequest()) {
                            if (presenterView instanceof OnApiPostErrorListener) {
                                ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.Pay.PayListVip, new IllegalRequestException(result));
                            }
                        } else if (presenterView instanceof OnApiPayPostListener.PostPayListVipListener) {
                            ((OnApiPayPostListener.PostPayListVipListener) presenterView).postOnPayListVipSuccess(type, result.items);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (presenterView instanceof OnApiPostErrorListener) {
                            ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.Pay.PayListVip, throwable);
                        }
                    }
                });
    }

    @Deprecated
    public void postPayListVip(final int type, String surname, String day) {
        RetroHttpMethods.PAY().postPayListVip(type, surname, day)
                .compose(RxSchedulersHelper.<RetroResult<PayService>>io_main())
                .subscribe(new Action1<RetroResult<PayService>>() {
                    @Override
                    public void call(RetroResult<PayService> result) {
                        if (null == result || result.illegalRequest()) {
                            if (presenterView instanceof OnApiPostErrorListener) {
                                ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.Pay.PayListVip, new IllegalRequestException(result));
                            }
                        } else if (presenterView instanceof OnApiPayPostListener.PostPayListVipListener) {
                            ((OnApiPayPostListener.PostPayListVipListener) presenterView).postOnPayListVipSuccess(type, result.items);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (presenterView instanceof OnApiPostErrorListener) {
                            ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.Pay.PayListVip, throwable);
                        }
                    }
                });
    }

    public void postPayPreview(int vipId, String money, String surname, String day, int gender, int nameNumber, Integer redPacketId) {
        RetroHttpMethods.PAY().postPayPreview(vipId, money, surname, day, gender, nameNumber, redPacketId)
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

    public void postPayOrder(int vipId, String surname, String day, int gender, int nameNumber, Integer redPacketId) {
        RetroHttpMethods.PAY().postPayOrder(vipId, surname, day, gender, nameNumber, redPacketId)
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

    public void postPayLog(String orderNo, String statusCode, int statusChannel, String statusMsg) {
        RetroHttpMethods.PAY().postPayLog(orderNo, statusCode, statusChannel, statusMsg)
                .compose(RxSchedulersHelper.<RetroResult<REmptyResult>>io_main())
                .subscribe(new Action1<RetroResult<REmptyResult>>() {
                    @Override
                    public void call(RetroResult<REmptyResult> result) {

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

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
        postPayOrderNameList(orderNo, null);
    }

    public void postPayOrderNameList(String orderNo, Integer listType) {
        RetroHttpMethods.PAY().postPayOrderNameList(orderNo, listType, 0, 1000)
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

    public void postPayOrderNameListPackage(String orderNo) {
        RetroHttpMethods.PAY().postPayOrderNameListPackage(orderNo)
                .compose(RxSchedulersHelper.<RetroResult<RetroListResult<List<NameDefinition>>>>io_main())
                .subscribe(new Action1<RetroResult<RetroListResult<List<NameDefinition>>>>() {
                    @Override
                    public void call(RetroResult<RetroListResult<List<NameDefinition>>> result) {
                        if (null == result || result.illegalRequest()) {
                            if (presenterView instanceof OnApiPostErrorListener) {
                                ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.Pay.PayOrderNameList, new IllegalRequestException(result));
                            }
                        } else if (presenterView instanceof OnApiPayPostListener.PostPayPackageListener) {
                            ((OnApiPayPostListener.PostPayPackageListener) presenterView).postOnPayPackageSuccess(result.items);
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

    public void postPayOrderUnReadNum() {
        RetroHttpMethods.PAY().postPayOrderUnReadNum(1)
                .compose(RxSchedulersHelper.<RetroResult<PayOrderNumber>>io_main())
                .subscribe(new Action1<RetroResult<PayOrderNumber>>() {
                    @Override
                    public void call(RetroResult<PayOrderNumber> result) {
                        if (null == result || result.illegalRequest()) {
                            if (presenterView instanceof OnApiPostErrorListener) {
                                ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.Pay.PayOrderNameList, new IllegalRequestException(result));
                            }
                        } else if (presenterView instanceof OnApiPayPostListener.PostPayOrderUnReadNumListener) {
                            ((OnApiPayPostListener.PostPayOrderUnReadNumListener) presenterView).postOnPayOrderUnReadNumSuccess(result.items);
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
