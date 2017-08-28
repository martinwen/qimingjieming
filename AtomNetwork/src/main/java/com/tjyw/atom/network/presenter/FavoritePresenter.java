package com.tjyw.atom.network.presenter;

import com.tjyw.atom.network.IllegalRequestException;
import com.tjyw.atom.network.RetroHttpMethods;
import com.tjyw.atom.network.RxSchedulersHelper;
import com.tjyw.atom.network.model.Favorite;
import com.tjyw.atom.network.presenter.listener.OnApiFavoritePostListener;
import com.tjyw.atom.network.presenter.listener.OnApiPostErrorListener;
import com.tjyw.atom.network.result.REmptyResult;
import com.tjyw.atom.network.result.RIdentifyResult;
import com.tjyw.atom.network.result.RetroListResult;
import com.tjyw.atom.network.result.RetroResult;

import nucleus.view.ViewWithPresenter;
import rx.functions.Action1;

/**
 * Created by stephen on 27/08/2017.
 */
public class FavoritePresenter<V extends ViewWithPresenter> extends BasePresenter<V> {

    public void postFavoriteAdd(String surname, String name, String day, int gender, final Object item) {
        RetroHttpMethods.FAVORITE().postFavoriteAdd(surname, name, day, gender)
                .compose(RxSchedulersHelper.<RetroResult<RIdentifyResult>>io_main())
                .subscribe(new Action1<RetroResult<RIdentifyResult>>() {
                    @Override
                    public void call(RetroResult<RIdentifyResult> result) {
                        if (null == result || result.illegalRequest()) {
                            if (presenterView instanceof OnApiPostErrorListener) {
                                ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.FavoriteAdd, new IllegalRequestException(result));
                            }
                        } else if (presenterView instanceof OnApiFavoritePostListener.PostAddListener) {
                            ((OnApiFavoritePostListener.PostAddListener) presenterView).postOnFavoriteAddSuccess(result.items, item);
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

    public void postFavoriteRemove(int id, final Object item) {
        RetroHttpMethods.FAVORITE().postFavoriteRemove(id)
                .compose(RxSchedulersHelper.<RetroResult<REmptyResult>>io_main())
                .subscribe(new Action1<RetroResult<REmptyResult>>() {
                    @Override
                    public void call(RetroResult<REmptyResult> result) {
                        if (null == result || result.illegalRequest()) {
                            if (presenterView instanceof OnApiPostErrorListener) {
                                ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.FavoriteRemove, new IllegalRequestException(result));
                            }
                        } else if (presenterView instanceof OnApiFavoritePostListener.PostRemoveListener) {
                            ((OnApiFavoritePostListener.PostRemoveListener) presenterView).postOnFavoriteRemoveSuccess(item);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (presenterView instanceof OnApiPostErrorListener) {
                            ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.FavoriteRemove, throwable);
                        }
                    }
                });
    }

    public void postFavoriteList(int offset, int limit) {
        RetroHttpMethods.FAVORITE().postFavoriteList(offset, limit)
                .compose(RxSchedulersHelper.<RetroResult<RetroListResult<Favorite>>>io_main())
                .subscribe(new Action1<RetroResult<RetroListResult<Favorite>>>() {
                    @Override
                    public void call(RetroResult<RetroListResult<Favorite>> result) {
                        if (null == result || result.illegalRequest()) {
                            if (presenterView instanceof OnApiPostErrorListener) {
                                ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.FavoriteList, new IllegalRequestException(result));
                            }
                        } else if (presenterView instanceof OnApiFavoritePostListener.PostListListener) {
                            ((OnApiFavoritePostListener.PostListListener) presenterView).postOnFavoriteListSuccess(result.items);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (presenterView instanceof OnApiPostErrorListener) {
                            ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.FavoriteList, throwable);
                        }
                    }
                });
    }
}
