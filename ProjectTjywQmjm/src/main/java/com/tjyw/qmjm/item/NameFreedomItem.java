package com.tjyw.qmjm.item;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.tjyw.atom.network.model.NameDefinition;
import com.tjyw.atom.pub.inject.From;
import com.tjyw.atom.pub.item.AtomPubFastAdapterAbstractItem;
import com.tjyw.qmjm.ClientQmjmApplication;
import com.tjyw.qmjm.R;

import java.util.List;

/**
 * Created by stephen on 14/08/2017.
 */
public class NameFreedomItem extends AtomPubFastAdapterAbstractItem<NameDefinition, NameFreedomItem, NameFreedomItem.NameFreedomHolder> {

    public NameFreedomItem(NameDefinition src) {
        super(src);
    }

    @Override
    public NameFreedomHolder getViewHolder(View v) {
        return new NameFreedomHolder(v);
    }

    @Override
    public int getType() {
        return R.id.atom_pub_resFasterAdapterType_NameFreedomItem;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.atom_name_master_freedom_body;
    }

    @Override
    public void bindView(NameFreedomHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        holder.onBindView(ClientQmjmApplication.getContext(), src);
    }

    public static class NameFreedomHolder extends AtomPubFastAdapterAbstractItem.AtomPubFastAdapterItemHolder<NameDefinition> {

        @From(R.id.bodyTitle)
        protected TextView bodyTitle;

        public NameFreedomHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(Context context, NameDefinition definition) {
            bodyTitle.setText(definition.name);
        }
    }
}
