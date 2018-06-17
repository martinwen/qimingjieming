package com.tjyw.bbbzqm.item;

import android.content.Context;
import android.view.View;

import com.tjyw.atom.network.model.Explain;
import com.tjyw.bbbzqm.R;

import atom.pub.item.AtomPubFastAdapterAbstractItem;

public class ExplainFooterItem extends AtomPubFastAdapterAbstractItem<Explain, ExplainFooterItem, ExplainFooterItem.ExplainFooterHolder> {

    public ExplainFooterItem(Explain src) {
        super(src);
    }

    @Override
    public int getType() {
        return R.id.atom_pub_resFasterAdapterType_ExplainFooterItem;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.atom_explain_footer;
    }

    @Override
    public ExplainFooterHolder getViewHolder(View v) {
        return new ExplainFooterHolder(v);
    }

    static class ExplainFooterHolder extends AtomPubFastAdapterAbstractItem.AtomPubFastAdapterItemHolder<Explain> {

        public ExplainFooterHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(Context context, Explain explain) {

        }
    }
}
