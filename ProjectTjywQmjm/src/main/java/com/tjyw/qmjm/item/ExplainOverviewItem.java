package com.tjyw.qmjm.item;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.tjyw.atom.network.model.NameCharacter;
import com.tjyw.atom.pub.inject.From;
import com.tjyw.atom.pub.interfaces.IAtomPubElements;
import com.tjyw.atom.pub.item.AtomPubFastAdapterAbstractItem;
import com.tjyw.qmjm.ClientQmjmApplication;
import com.tjyw.qmjm.R;

import java.util.List;

/**
 * Created by stephen on 11/08/2017.
 */
public class ExplainOverviewItem extends AtomPubFastAdapterAbstractItem<NameCharacter, ExplainOverviewItem, ExplainOverviewItem.OverviewHolder> {

    public ExplainOverviewItem(NameCharacter src) {
        super(src);
    }

    @Override
    public OverviewHolder getViewHolder(View v) {
        return new OverviewHolder(v);
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
    public void bindView(OverviewHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        holder.onBindView(ClientQmjmApplication.getContext(), src);
    }

    public static class OverviewHolder extends AtomPubFastAdapterAbstractItem.AtomPubFastAdapterItemHolder<NameCharacter> {

        @From(R.id.bodyWord)
        protected TextView bodyWord;

        @From(R.id.bodyPinYin)
        protected TextView bodyPinYin;

        @From(R.id.bodyWuXing)
        protected TextView bodyWuXing;

        @From(R.id.bodyContent)
        protected TextView bodyContent;

        public OverviewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(Context context, NameCharacter character) {
            bodyWord.setText(context.getString(R.string.atom_pub_resStringExplainWord, character.word));
            bodyPinYin.setText(context.getString(R.string.atom_pub_resStringExplainPinYin, character.jiantipinyin));
            bodyWuXing.setText(character.shuxing);
            bodyContent.setText(character.xiangxijieshi.trim());

            switch (character.shuxing) {
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
