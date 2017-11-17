package com.tjyw.atom.network.presenter;

import com.tjyw.atom.network.IllegalRequestException;
import com.tjyw.atom.network.RetroHttpMethods;
import com.tjyw.atom.network.RxSchedulersHelper;
import com.tjyw.atom.network.model.UserInfo;
import com.tjyw.atom.network.presenter.listener.OnApiPostErrorListener;
import com.tjyw.atom.network.presenter.listener.OnApiUserPostListener;
import com.tjyw.atom.network.result.REmptyResult;
import com.tjyw.atom.network.result.RPayPacketResult;
import com.tjyw.atom.network.result.RetroResult;

import nucleus.view.ViewWithPresenter;
import rx.functions.Action1;

/**
 * Created by stephen on 17-8-18.
 */
public class UserPresenter<V extends ViewWithPresenter> extends ClientPresenter<V> {

    public void postUserGetLoginCode(final String mobile) {
        RetroHttpMethods.USER().postUserGetLoginCode(mobile)
                .compose(RxSchedulersHelper.<RetroResult<REmptyResult>>io_main())
                .subscribe(new Action1<RetroResult<REmptyResult>>() {
                    @Override
                    public void call(RetroResult<REmptyResult> result) {
                        if (null == result || null == result.items) {
                            if (presenterView instanceof OnApiPostErrorListener) {
                                ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.User.LoginCode, new IllegalRequestException(result));
                            }
                        } else if (presenterView instanceof OnApiUserPostListener.PostUserLoginCodeListener) {
                            ((OnApiUserPostListener.PostUserLoginCodeListener) presenterView).postOnUserLoginCodeSuccess(mobile);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (presenterView instanceof OnApiPostErrorListener) {
                            ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.User.LoginCode, throwable);
                        }
                    }
                });
    }

    public void postUserLogin(String mobile, String code) {
        RetroHttpMethods.USER().postUserLogin(mobile, code)
                .compose(RxSchedulersHelper.<RetroResult<UserInfo>>io_main())
                .subscribe(new Action1<RetroResult<UserInfo>>() {
                    @Override
                    public void call(RetroResult<UserInfo> result) {
                        if (null == result || null == result.items) {
                            if (presenterView instanceof OnApiPostErrorListener) {
                                ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.User.Login, new IllegalRequestException(result));
                            }
                        } else if (presenterView instanceof OnApiUserPostListener.PostUserLoginListener) {
                            ((OnApiUserPostListener.PostUserLoginListener) presenterView).postOnUserLoginSuccess(result.items);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (presenterView instanceof OnApiPostErrorListener) {
                            ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.User.Login, throwable);
                        }
                    }
                });
    }

    public void postUserGetNewRedPacket(int id) {
        RetroHttpMethods.USER().postUserGetNewRedPacket(id)
                .compose(RxSchedulersHelper.<RetroResult<REmptyResult>>io_main())
                .subscribe(new Action1<RetroResult<REmptyResult>>() {
                    @Override
                    public void call(RetroResult<REmptyResult> result) {
                        if (null == result || null == result.items) {
                            if (presenterView instanceof OnApiPostErrorListener) {
                                ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.User.GetNewRedPacket, new IllegalRequestException(result));
                            }
                        } else if (presenterView instanceof OnApiUserPostListener.PostUserGetNewRedPacketListener) {
                            ((OnApiUserPostListener.PostUserGetNewRedPacketListener) presenterView).postOnUserGetNewRedPacketSuccess(result.message);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (presenterView instanceof OnApiPostErrorListener) {
                            ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.User.GetNewRedPacket, throwable);
                        }
                    }
                });
    }

    public void postUserListPacket() {
        RetroHttpMethods.USER().postUserListPacket(1)
                .compose(RxSchedulersHelper.<RetroResult<RPayPacketResult>>io_main())
                .subscribe(new Action1<RetroResult<RPayPacketResult>>() {
                    @Override
                    public void call(RetroResult<RPayPacketResult> result) {
                        if (null == result || null == result.items) {
                            if (presenterView instanceof OnApiPostErrorListener) {
                                ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.User.ListPacket, new IllegalRequestException(result));
                            }
                        } else if (presenterView instanceof OnApiUserPostListener.PostUserListPacketListener) {
                            ((OnApiUserPostListener.PostUserListPacketListener) presenterView).postOnUserListPacketSuccess(result.items);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (presenterView instanceof OnApiPostErrorListener) {
                            ((OnApiPostErrorListener) presenterView).postOnExplainError(IPost.User.ListPacket, throwable);
                        }
                    }
                });
    }
}
