package com.tjyw.qmjm.interfaces;

import android.view.View;

/**
 * Created by stephen on 16-7-20.
 */
public interface IClientLayoutState {

    void pShowStateMasker(int state);

    void pShowStateMasker(int state, String errorStr);

    void pShowStateMasker(int state, String errorStr, View.OnClickListener onRetryListener);

    void pShowAlphaLoading(boolean isShow);
}
