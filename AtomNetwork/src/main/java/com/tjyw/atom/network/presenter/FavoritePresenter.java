package com.tjyw.atom.network.presenter;

import com.tjyw.atom.network.IllegalRequestException;
import com.tjyw.atom.network.RetroHttpMethods;
import com.tjyw.atom.network.RxSchedulersHelper;
import com.tjyw.atom.network.presenter.listener.OnApiFavoritePostListener;
import com.tjyw.atom.network.presenter.listener.OnApiPostErrorListener;
import com.tjyw.atom.network.result.REmptyResult;
import com.tjyw.atom.network.result.RNameDefinition;
import com.tjyw.atom.network.result.RetroResult;

import nucleus.view.ViewWithPresenter;
import rx.functions.Action1;

/**
 * Created by stephen on 27/08/2017.
 */
public class FavoritePresenter<V extends ViewWithPresenter> extends BasePresenter<V> {

    public void postFavoriteAdd(String surname, String name, String day, int gender) {
        RetroHttpMethods.FAVORITE().postFavoriteAdd(surname, name, day, gender)
                .compose(RxSchedulersHelper.<RetroResult<REmptyResult>>io_main())
                .subscribe(new Action1<RetroResult<REmptyResult>>() {
                    @Override
                    public void call(RetroResult<REmptyResult> result) {
                        if (null == result || result.illegalRequest()) {
                            if (presenterView instanceof OnApiPostErrorListener) {
                                ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.FavoriteAdd, new IllegalRequestException(result));
                            }
                        } else if (presenterView instanceof OnApiFavoritePostListener.PostFavoriteAddListener) {
                            ((OnApiFavoritePostListener.PostFavoriteAddListener) presenterView).postOnFavoriteAddSuccess();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (presenterView instanceof OnApiPostErrorListener) {
                            ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.FavoriteAdd, throwable);
                        }
                    }
                });
    }

    public void postFavoriteRemove(String surname, String name) {
        RetroHttpMethods.FAVORITE().postFavoriteRemove(surname, name)
                .compose(RxSchedulersHelper.<RetroResult<REmptyResult>>io_main())
                .subscribe(new Action1<RetroResult<REmptyResult>>() {
                    @Override
                    public void call(RetroResult<REmptyResult> result) {
                        if (null == result || result.illegalRequest()) {
                            if (presenterView instanceof OnApiPostErrorListener) {
                                ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.FavoriteAdd, new IllegalRequestException(result));
                            }
                        } else if (presenterView instanceof OnApiFavoritePostListener.PostFavoriteRemoveListener) {
                            ((OnApiFavoritePostListener.PostFavoriteRemoveListener) presenterView).postOnFavoriteRemoveSuccess();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (presenterView instanceof OnApiPostErrorListener) {
                            ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.FavoriteAdd, throwable);
                        }
                    }
                });
    }

    public void postFavoriteList() {
        RetroHttpMethods.FAVORITE().postFavoriteList(1)
                .compose(RxSchedulersHelper.<RetroResult<RNameDefinition>>io_main())
                .subscribe(new Action1<RetroResult<RNameDefinition>>() {
                    @Override
                    public void call(RetroResult<RNameDefinition> result) {
                        if (null == result || result.illegalRequest()) {
                            if (presenterView instanceof OnApiPostErrorListener) {
                                ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.FavoriteAdd, new IllegalRequestException(result));
                            }
                        } else if (presenterView instanceof OnApiFavoritePostListener.PostFavoriteListListener) {
                            ((OnApiFavoritePostListener.PostFavoriteListListener) presenterView).postOnFavoriteListSuccess(result.items);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (presenterView instanceof OnApiPostErrorListener) {
                            ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.FavoriteAdd, throwable);
                        }
                    }
                });
    }
}
