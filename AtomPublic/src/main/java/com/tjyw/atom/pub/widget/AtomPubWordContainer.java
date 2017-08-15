package com.tjyw.atom.pub.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.tjyw.atom.pub.R;

/**
 * Created by stephen on 14/08/2017.
 */
public class AtomPubWordContainer extends LinearLayout {

    public AtomPubWordContainer(Context context) {
        super(context);
    }

    public AtomPubWordContainer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AtomPubWordContainer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        setOrientation(VERTICAL);
        View.inflate(getContext(), R.layout.atom_pub_word, this);
    }
}
