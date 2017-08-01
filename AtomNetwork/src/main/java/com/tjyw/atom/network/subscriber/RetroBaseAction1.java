package com.tjyw.atom.network.subscriber;

import com.tjyw.atom.network.conf.IApiErrorCode;
import com.tjyw.atom.network.result.RetroResult;

import rx.functions.Action1;

/**
 * Created by stephen on 9/11/16.
 */
public abstract class RetroBaseAction1<T extends RetroResult<?>> implements Action1<T> {

    @Override
    public void call(T t) {
        if (null == t) {
            onNextIllegalResult(null, true);
        } else {
            switch (t.code) {
            case IApiErrorCode.OK:
                if (null == t.items) {
                    onNextIllegalResult(t, true);
                } else {
                    onNextSuccess(t);
                }
                break ;
            default:
                onNextIllegalResult(t, null == t.items);
            }
        }
    }

    public void onNextIllegalResult(T t, boolean nullItems) {

    }

    public abstract void onNextSuccess(T t);

    public static class SimpleBaseAction1<T extends RetroResult<?>> extends RetroBaseAction1<T> {

        @Override
        public void onNextSuccess(T t) {

        }
    }
}
