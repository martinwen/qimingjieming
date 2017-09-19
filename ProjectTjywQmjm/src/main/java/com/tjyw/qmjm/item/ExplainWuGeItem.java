package com.tjyw.qmjm.item;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.tjyw.atom.network.model.Explain;
import com.tjyw.atom.pub.inject.From;
import com.tjyw.atom.pub.interfaces.IAtomPubElements;
import com.tjyw.atom.pub.item.AtomPubFastAdapterAbstractItem;
import com.tjyw.qmjm.ClientQmjmApplication;
import com.tjyw.qmjm.R;

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

        @From(R.id.bodyCount)
        protected TextView bodyCount;

        @From(R.id.bodySubTitle)
        protected TextView bodySubTitle;

        @From(R.id.bodyContent)
        protected TextView bodyContent;

        @From(R.id.bodyJiXiong)
        protected TextView bodyJiXiong;

        @From(R.id.bodyWuXing)
        protected TextView bodyWuXing;

        public DestinyBodyHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(Context context, Explain.WuGe wuGe) {
            bodyTitle.setText(wuGe.name);
            bodyCount.setText(String.valueOf(wuGe.number));
            bodySubTitle.setText(wuGe.zonglun);
            bodyContent.setText(wuGe.shiyi);
            bodyJiXiong.setText(wuGe.jixiong);
            bodyWuXing.setText(wuGe.wuxing);

            switch (wuGe.wuxing) {
                case IAtomPubElements.METAL:
                    bodyWuXing.setBackgroundResource(IAtomPubElements.Reference.METAL.second);
                    break ;
                case IAtomPubElements.WOOD:
                    bodyWuXing.setBackgroundResource(IAtomPubElements.Reference.WOOD.second);
                    break ;
                case IAtomPubElements.WATER:
                    bodyWuXing.setBackgroundResource(IAtomPubElements.Reference.WATER.second);
                    break ;
                case IAtomPubElements.FIRE:
                    bodyWuXing.setBackgroundResource(IAtomPubElements.Reference.FIRE.second);
                    break ;
                case IAtomPubElements.EARTH:
                    bodyWuXing.setBackgroundResource(IAtomPubElements.Reference.EARTH.second);
                    break ;
                default:
                    bodyWuXing.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
            }
        }
    }
}
