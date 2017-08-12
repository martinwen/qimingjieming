package com.tjyw.qmjm.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.tjyw.atom.pub.holder.AtomPubFastAdapterItemHolder;
import com.tjyw.atom.pub.inject.From;
import com.tjyw.qmjm.R;

/**
 * Created by stephen on 11/08/2017.
 */
public class ExplainHeaderWordHolder extends AtomPubFastAdapterItemHolder<String> {

    @From(R.id.bodyPinYin)
    protected TextView bodyPinYin;

    @From(R.id.bodyWord)
    protected TextView bodyWord;

    @From(R.id.bodyElement)
    protected TextView bodyElement;

    public ExplainHeaderWordHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onBindView(Context context, String s) {
        bodyWord.setText(s);
    }
}
