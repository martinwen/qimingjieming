package com.tjyw.qmjmqd.item;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.tjyw.atom.network.model.Explain;
import atom.pub.inject.From;
import atom.pub.item.AtomPubFastAdapterAbstractItem;
import com.tjyw.qmjmqd.ClientQmjmApplication;
import com.tjyw.qmjmqd.R;

import java.util.List;

/**
 * Created by stephen on 15/08/2017.
 */
public class ExplainWuGeItem extends AtomPubFastAdapterAbstractItem<Explain.WuGe, ExplainWuGeItem, ExplainWuGeItem.DestinyBodyHolder> {

    public ExplainWuGeItem(Explain.WuGe src) {
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

        @From(R.id.bodyDesc)
        protected TextView bodyDesc;

        @From(R.id.bodyJiXiong)
        protected TextView bodyJiXiong;

        @From(R.id.bodyLiShu)
        protected TextView bodyLiShu;

        @From(R.id.bodyWuXing)
        protected TextView bodyWuXing;

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
            bodyDesc.setText(wuGe.desc);
            bodyJiXiong.setText(wuGe.jixiong);
            bodyLiShu.setText(context.getString(R.string.atom_pub_resStringExplainLiShu, wuGe.number));
            bodyWuXing.setText(context.getString(R.string.atom_pub_resStringExplainWuXingFormat, wuGe.wuxing));
            bodySubTitle.setText(wuGe.zonglun);
            bodyContent.setText(wuGe.shiyi);
        }
    }
}
