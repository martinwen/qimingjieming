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
public class ExplainOverviewHolder extends AtomPubFastAdapterItemHolder<String> {

    @From(R.id.bodyTitle)
    protected TextView bodyTitle;

    public ExplainOverviewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onBindView(Context context, String s) {
        bodyTitle.setText(s);
    }
}
