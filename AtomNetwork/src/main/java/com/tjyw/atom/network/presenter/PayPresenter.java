package com.tjyw.atom.network.presenter;

import com.tjyw.atom.network.IllegalRequestException;
import com.tjyw.atom.network.RetroHttpMethods;
import com.tjyw.atom.network.RxSchedulersHelper;
import com.tjyw.atom.network.model.PayOrder;
import com.tjyw.atom.network.presenter.listener.OnApiPostErrorListener;
import com.tjyw.atom.network.presenter.listener.OnApiPostListener;
import com.tjyw.atom.network.result.RetroResult;

import nucleus.view.ViewWithPresenter;
import rx.functions.Action1;

/**
 * Created by stephen on 17-8-18.
 */
public class PayPresenter<V extends ViewWithPresenter> extends BasePresenter<V> {

    public void postPayOrder(int vipId, String sessionKey) {
        RetroHttpMethods.PAY().postPayOrder(vipId, sessionKey)
                .compose(RxSchedulersHelper.<RetroResult<PayOrder>>io_main())
                .subscribe(new Action1<RetroResult<PayOrder>>() {
                    @Override
                    public void call(RetroResult<PayOrder> result) {
                        if (presenterView instanceof OnApiPostErrorListener) {
                            if (null == result || result.illegalRequest()) {
                                ((OnApiPostErrorListener) presenterView).postOnExplainError(
                                        IPost.PayOrder, new IllegalRequestException(result));
                            } else {
                                ((OnApiPostListener.PostPayOrderListener) presenterView).postOnPayOrderSuccess(result.items);
                            }
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
}
