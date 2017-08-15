package com.tjyw.qmjm.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.tjyw.atom.pub.inject.From;
import com.tjyw.atom.pub.inject.Injector;
import com.tjyw.atom.pub.widget.AtomPubWordContainer;
import com.tjyw.qmjm.R;

/**
 * Created by stephen on 14/08/2017.
 */
public class NameWordContainer extends AtomPubWordContainer {

    @From(R.id.bodyPinYin)
    protected TextView bodyPinYin;

    @From(R.id.bodyWord)
    protected TextView bodyWord;

    @From(R.id.bodyElement)
    protected TextView bodyElement;

    public NameWordContainer(Context context) {
        super(context);
    }

    public NameWordContainer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NameWordContainer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Injector.inject(this);
    }
}
