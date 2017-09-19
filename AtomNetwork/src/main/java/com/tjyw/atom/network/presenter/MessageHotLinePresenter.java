package com.tjyw.atom.network.presenter;

import com.tjyw.atom.network.IllegalRequestException;
import com.tjyw.atom.network.RetroHttpMethods;
import com.tjyw.atom.network.RxSchedulersHelper;
import com.tjyw.atom.network.model.MessageConverse;
import com.tjyw.atom.network.presenter.listener.OnApiMessageHotLinePostListener;
import com.tjyw.atom.network.presenter.listener.OnApiPostErrorListener;
import com.tjyw.atom.network.result.REmptyResult;
import com.tjyw.atom.network.result.RetroResult;

import nucleus.view.ViewWithPresenter;
import rx.functions.Action1;

/**
 * Created by stephen on 19/09/2017.
 */
public class MessageHotLinePresenter<V extends ViewWithPresenter> extends FavoritePresenter<V> {

    public void postCustomerServiceDetail(int id) {
        RetroHttpMethods.HOTLINE().postCustomerServiceDetail(id)
                .compose(RxSchedulersHelper.<RetroResult<MessageConverse>>io_main())
                .subscribe(new Action1<RetroResult<MessageConverse>>() {
                    @Override
                    public void call(RetroResult<MessageConverse> result) {
                        if (null == result || result.illegalRequest()) {
                            if (presenterView instanceof OnApiPostErrorListener) {
                                ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.HotLine.Detail, new IllegalRequestException(result));
                            }
                        } else if (presenterView instanceof OnApiMessageHotLinePostListener.PostMessageHotLineDetailListener) {
                            ((OnApiMessageHotLinePostListener.PostMessageHotLineDetailListener) presenterView).postOnMessageHotLineDetailSuccess(result.items);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (presenterView instanceof OnApiPostErrorListener) {
                            ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.HotLine.Detail, throwable);
                        }
                    }
                });
    }

    public void postCustomerServiceWrite(String detail) {
        RetroHttpMethods.HOTLINE().postCustomerServiceWrite(detail)
                .compose(RxSchedulersHelper.<RetroResult<REmptyResult>>io_main())
                .subscribe(new Action1<RetroResult<REmptyResult>>() {
                    @Override
                    public void call(RetroResult<REmptyResult> result) {
                        if (null == result || result.illegalRequest()) {
                            if (presenterView instanceof OnApiPostErrorListener) {
                                ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.HotLine.Detail, new IllegalRequestException(result));
                            }
                        } else if (presenterView instanceof OnApiMessageHotLinePostListener.PostMessageHotLineWriteListener) {
                            ((OnApiMessageHotLinePostListener.PostMessageHotLineWriteListener) presenterView).postMessageHotLineWriteSuccess(result.items);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (presenterView instanceof OnApiPostErrorListener) {
                            ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.HotLine.Detail, throwable);
                        }
                    }
                });
    }

}
