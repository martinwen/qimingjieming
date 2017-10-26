package com.tjyw.qmjm.item;

import android.content.Context;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import atom.pub.inject.From;
import atom.pub.item.AtomPubFastAdapterAbstractItem;
import com.tjyw.qmjm.ClientQmjmApplication;
import com.tjyw.qmjm.R;

import java.util.List;

/**
 * Created by stephen on 14/08/2017.
 */
public class MasterMineItem extends AtomPubFastAdapterAbstractItem<Pair<Integer, Integer>, MasterMineItem, MasterMineItem.MasterMineHolder> {

    public MasterMineItem(Pair<Integer, Integer> src) {
        super(src);
    }

    @Override
    public MasterMineHolder getViewHolder(View v) {
        return new MasterMineHolder(v);
    }

    @Override
    public int getType() {
        return R.id.atom_pub_resFasterAdapterType_MasterMineItem;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.atom_master_mine_body;
    }

    @Override
    public void bindView(MasterMineHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        holder.onBindView(ClientQmjmApplication.getContext(), src);
    }

    static class MasterMineHolder extends AtomPubFastAdapterAbstractItem.AtomPubFastAdapterItemHolder<Pair<Integer, Integer>> {

        @From(R.id.bodyTitle)
        protected TextView bodyTitle;

        public MasterMineHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(Context context, Pair<Integer, Integer> pair) {
            bodyTitle.setText(pair.first);
            bodyTitle.setCompoundDrawablesWithIntrinsicBounds(0, pair.second, 0, 0);
        }
    }
}
