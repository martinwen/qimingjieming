package com.tjyw.atom.pub.interfaces;

import android.support.annotation.StringRes;
import android.view.View;

/**
 * Created by stephen on 16-7-20.
 */
public interface IAtomPubLayoutSupportMasker {

    interface OnMaskerClickListener {

        void maskerOnClick(View view);
    }

    void maskerShowProgressView(boolean isAlpha);

    void maskerHideProgressView();

    void maskerShowMaskerLayout(String msg, @StringRes int clickLabelRes);

    void maskerHideMaskerLayout();
}
