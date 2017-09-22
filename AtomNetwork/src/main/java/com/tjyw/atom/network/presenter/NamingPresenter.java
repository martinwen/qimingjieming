package com.tjyw.atom.network.presenter;

import com.tjyw.atom.network.IllegalRequestException;
import com.tjyw.atom.network.RetroHttpMethods;
import com.tjyw.atom.network.RxSchedulersHelper;
import com.tjyw.atom.network.model.Explain;
import com.tjyw.atom.network.presenter.listener.OnApiPostErrorListener;
import com.tjyw.atom.network.presenter.listener.OnApiPostExplainListener;
import com.tjyw.atom.network.presenter.listener.OnApiPostNamingListener;
import com.tjyw.atom.network.result.RNameDefinition;
import com.tjyw.atom.network.result.RetroResult;

import nucleus.view.ViewWithPresenter;
import rx.functions.Action1;

/**
 * Created by stephen on 31/03/2017.
 */
public class NamingPresenter<V extends ViewWithPresenter> extends PayPresenter<V> {

    public void postExplain(String surname, String name, String day, int gender) {
        RetroHttpMethods.NAMING().postExplain(surname, name, day, gender)
                .compose(RxSchedulersHelper.<RetroResult<Explain>>io_main())
                .subscribe(new Action1<RetroResult<Explain>>() {
                    @Override
                    public void call(RetroResult<Explain> result) {
                        if (null == result || result.illegalRequest()) {
                            if (presenterView instanceof OnApiPostErrorListener) {
                                ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.Explain, new IllegalRequestException(result));
                            }
                        } else if (presenterView instanceof OnApiPostExplainListener) {
                            ((OnApiPostExplainListener) presenterView).postOnExplainSuccess(result.items);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (presenterView instanceof OnApiPostErrorListener) {
                            ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.Explain, throwable);
                        }
                    }
                });
    }

    public void postNameDefinition(String surname, String day, int gender, int nameNumber) {
        RetroHttpMethods.NAMING().postNameDefinition(surname, day, gender, nameNumber)
                .compose(RxSchedulersHelper.<RetroResult<RNameDefinition>>io_main())
                .subscribe(new Action1<RetroResult<RNameDefinition>>() {
                    @Override
                    public void call(RetroResult<RNameDefinition> result) {
                        if (null == result || result.illegalRequest()) {
                            if (presenterView instanceof OnApiPostErrorListener) {
                                ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.Naming, new IllegalRequestException(result));
                            }
                        } else if (presenterView instanceof OnApiPostNamingListener) {
                            ((OnApiPostNamingListener) presenterView).postOnNamingSuccess(result.items);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (presenterView instanceof OnApiPostErrorListener) {
                            ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.Naming, throwable);
                        }
                    }
                });
    }

    public void postNameDefinitionData(String surname, String day, int gender, int nameNumber) {
        RetroHttpMethods.NAMING().postNameDefinitionData(surname, day, gender, nameNumber)
                .compose(RxSchedulersHelper.<RetroResult<RNameDefinition>>io_main())
                .subscribe(new Action1<RetroResult<RNameDefinition>>() {
                    @Override
                    public void call(RetroResult<RNameDefinition> result) {
                        if (null == result || result.illegalRequest()) {
                            if (presenterView instanceof OnApiPostErrorListener) {
                                ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.Naming, new IllegalRequestException(result));
                            }
                        } else if (presenterView instanceof OnApiPostNamingListener) {
                            ((OnApiPostNamingListener) presenterView).postOnNamingSuccess(result.items);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (presenterView instanceof OnApiPostErrorListener) {
                            ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.Naming, throwable);
                        }
                    }
                });
    }
}
