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
public class ExplainDestinyWuGeItem extends AtomPubFastAdapterAbstractItem<Explain.WuGe, ExplainDestinyWuGeItem, ExplainDestinyWuGeItem.DestinyBodyHolder> {

    public ExplainDestinyWuGeItem(Explain.WuGe src) {
        super(src);
    }

    @Override
    public DestinyBodyHolder getViewHolder(View v) {
        return new DestinyBodyHolder(v);
    }

    @Override
    public int getType() {
        return R.id.atom_pub_resFasterAdapterType_ExplainDestinyBodyItem;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.atom_explain_destiny_wuge;
    }

    @Override
    public void bindView(DestinyBodyHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        holder.onBindView(ClientQmjmApplication.getContext(), src);
    }

    public static class DestinyBodyHolder extends AtomPubFastAdapterAbstractItem.AtomPubFastAdapterItemHolder<Explain.WuGe> {

        @From(R.id.bodyTitle)
        protected TextView bodyTitle;

        @From(R.id.bodySubTitle)
        protected TextView bodySubTitle;

        @From(R.id.bodyContent)
        protected TextView bodyContent;

        public DestinyBodyHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(Context context, Explain.WuGe wuGe) {
            bodyTitle.setText(wuGe.name);
            bodySubTitle.setText(wuGe.zonglun);
            bodyContent.setText(wuGe.shiyi);
        }
    }
}
