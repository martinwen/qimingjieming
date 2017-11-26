package com.tjyw.qmjmqd.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import atom.pub.inject.From;
import atom.pub.inject.Injector;
import atom.pub.widget.AtomPubWordContainer;
import com.tjyw.qmjmqd.R;

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
