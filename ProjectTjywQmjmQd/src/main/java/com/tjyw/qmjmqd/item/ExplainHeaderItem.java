package com.tjyw.qmjmqd.item;

import android.content.Context;
import android.view.View;

import com.tjyw.atom.network.model.Explain;
import atom.pub.item.AtomPubFastAdapterAbstractItem;
import com.tjyw.qmjmqd.ClientQmjmApplication;
import com.tjyw.qmjmqd.R;
import com.tjyw.qmjmqd.holder.AtomExplainHeaderHolder;

import java.util.List;

/**
 * Created by stephen on 10/10/2017.
 */
public class ExplainHeaderItem extends AtomPubFastAdapterAbstractItem<Explain, ExplainHeaderItem, ExplainHeaderItem.ExplainHeaderHolder> {

    public ExplainHeaderItem(Explain src) {
        super(src);
    }

    @Override
    public ExplainHeaderHolder getViewHolder(View v) {
        return new ExplainHeaderHolder(v);
    }

    @Override
    public int getType() {
        return R.id.atom_pub_resFasterAdapterType_ExplainHeaderItem;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.atom_inc_explain_header;
    }

    @Override
    public void bindView(ExplainHeaderHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        holder.onBindView(ClientQmjmApplication.getContext(), src);
    }

    static class ExplainHeaderHolder extends AtomPubFastAdapterAbstractItem.AtomPubFastAdapterItemHolder<Explain> {

        protected AtomExplainHeaderHolder explainHeaderHolder;

        public ExplainHeaderHolder(View itemView) {
            super(itemView);
            explainHeaderHolder = new AtomExplainHeaderHolder(itemView);
        }

        @Override
        public void onBindView(Context context, Explain explain) {
            explainHeaderHolder.layout(explain);
        }
    }
}
