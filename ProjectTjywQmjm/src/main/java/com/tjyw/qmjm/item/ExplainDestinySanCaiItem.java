package com.tjyw.qmjm.item;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.tjyw.atom.network.model.Explain;
import com.tjyw.atom.pub.inject.From;
import com.tjyw.atom.pub.item.AtomPubFastAdapterAbstractItem;
import com.tjyw.qmjm.ClientQmjmApplication;
import com.tjyw.qmjm.R;

import java.util.List;

/**
 * Created by stephen on 15/08/2017.
 */
public class ExplainDestinySanCaiItem extends AtomPubFastAdapterAbstractItem<Explain.SanCai, ExplainDestinySanCaiItem, ExplainDestinySanCaiItem.DestinyFooterHolder> {

    public ExplainDestinySanCaiItem(Explain.SanCai src) {
        super(src);
    }

    @Override
    public DestinyFooterHolder getViewHolder(View v) {
        return new DestinyFooterHolder(v);
    }

    @Override
    public int getType() {
        return R.id.atom_pub_resFasterAdapterType_ExplainDestinyFooterItem;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.atom_explain_destiny_sancai;
    }

    @Override
    public void bindView(DestinyFooterHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        holder.onBindView(ClientQmjmApplication.getContext(), src);
    }

    public static class DestinyFooterHolder extends AtomPubFastAdapterAbstractItem.AtomPubFastAdapterItemHolder<Explain.SanCai> {

        @From(R.id.bodyTitle)
        protected TextView bodyTitle;

        public DestinyFooterHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(Context context, Explain.SanCai sanCai) {
            bodyTitle.setText(sanCai.shiyi);
        }
    }
}
