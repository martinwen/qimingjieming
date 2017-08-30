package com.tjyw.atom.network.presenter;

import com.tjyw.atom.network.IllegalRequestException;
import com.tjyw.atom.network.RetroHttpMethods;
import com.tjyw.atom.network.RxSchedulersHelper;
import com.tjyw.atom.network.model.ClientInit;
import com.tjyw.atom.network.presenter.listener.OnApiClientPostListener;
import com.tjyw.atom.network.presenter.listener.OnApiPostErrorListener;
import com.tjyw.atom.network.result.RetroResult;

import nucleus.view.ViewWithPresenter;
import rx.functions.Action1;

/**
 * Created by stephen on 30/08/2017.
 */
public class ClientPresenter<V extends ViewWithPresenter> extends BasePresenter<V> {

    public void postClientInit() {
        RetroHttpMethods.CLIENT().postClientInit(1)
                .compose(RxSchedulersHelper.<RetroResult<ClientInit>>io_main())
                .subscribe(new Action1<RetroResult<ClientInit>>() {
                    @Override
                    public void call(RetroResult<ClientInit> result) {
                        if (null == result || result.illegalRequest()) {
                            if (presenterView instanceof OnApiPostErrorListener) {
                                ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.Client.Init, new IllegalRequestException(result));
                            }
                        } else if (presenterView instanceof OnApiClientPostListener.PostClientInitListener) {
                            ((OnApiClientPostListener.PostClientInitListener) presenterView).postOnClientInitSuccess(result.items);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (presenterView instanceof OnApiPostErrorListener) {
                            ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.Client.Init, throwable);
                        }
                    }
                });
    }
}
