package com.tjyw.atom.network.presenter.listener;

/**
 * Created by stephen on 14/08/2017.
 */
public interface OnApiPostErrorListener {

    void postOnExplainError(int postId, Throwable throwable);
}
