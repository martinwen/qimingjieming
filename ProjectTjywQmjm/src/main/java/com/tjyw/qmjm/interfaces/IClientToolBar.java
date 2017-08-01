package com.tjyw.qmjm.interfaces;

import android.view.View;

/**
 * Created by stephen on 03/01/2017.
 */
public interface IClientToolBar {

    void tSetToolBar(CharSequence title);

    void tSetToolBar(CharSequence title, boolean hasBackBtn, Integer... right);

    void tSetToolbarVisibility(int visibility);

    void tSetToolbarCenterTitleMaxLines(int maxLines);

    void tSetToolbarRightSecondBubble(int count);

    void tOnToolbarRightViewClick(View v);
}
