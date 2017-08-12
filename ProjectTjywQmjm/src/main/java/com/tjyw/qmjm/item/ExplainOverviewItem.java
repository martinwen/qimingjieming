package com.tjyw.qmjm.item;

import android.view.View;

import com.tjyw.atom.pub.item.AtomPubFastAdapterAbstractItem;
import com.tjyw.qmjm.ClientQmjmApplication;
import com.tjyw.qmjm.R;
import com.tjyw.qmjm.holder.ExplainOverviewHolder;

import java.util.List;

/**
 * Created by stephen on 11/08/2017.
 */
public class ExplainOverviewItem extends AtomPubFastAdapterAbstractItem<String, ExplainOverviewItem, ExplainOverviewHolder> {

    public ExplainOverviewItem(String s) {
        super(s);
    }

    @Override
    public ExplainOverviewHolder getViewHolder(View v) {
        return new ExplainOverviewHolder(v);
    }

    @Override
    public int getType() {
        return R.id.atom_pub_resFasterAdapterType_ExplainOverviewItem;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.atom_explain_overview_body;
    }

    @Override
    public void bindView(ExplainOverviewHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        holder.onBindView(ClientQmjmApplication.getContext(), src);
    }
}
