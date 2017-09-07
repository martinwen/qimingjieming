package com.tjyw.atom.pub.interfaces;

import android.view.View;

/**
 * Created by stephen on 03/01/2017.
 */
public interface IAtomPubToolBar {

    void tSetToolBar(CharSequence title);

    void tSetToolBar(CharSequence title, boolean hasBackBtn, Integer... right);

    void tSetToolbarVisibility(int visibility);

    void tSetToolbarCenterTitleMaxLines(int maxLines);

    void tSetToolbarRightSecondBubble(int count);

    void tOnToolbarNavigationClick(View v);

    void tOnToolbarRightViewClick(View v);
}
