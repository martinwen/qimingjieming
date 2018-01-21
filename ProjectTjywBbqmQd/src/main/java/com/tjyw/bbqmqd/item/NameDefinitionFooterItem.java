package com.tjyw.bbqmqd.item;

import android.content.Context;
import android.view.View;

import atom.pub.item.AtomPubFastAdapterAbstractItem;
import com.tjyw.bbqmqd.R;

/**
 * Created by stephen on 04/09/2017.
 */
public class NameDefinitionFooterItem extends AtomPubFastAdapterAbstractItem<String, NameDefinitionFooterItem, NameDefinitionFooterItem.NameDefinitionFooterHolder> {

    public NameDefinitionFooterItem(String src) {
        super(src);
    }

    @Override
    public NameDefinitionFooterHolder getViewHolder(View v) {
        return new NameDefinitionFooterHolder(v);
    }

    @Override
    public int getType() {
        return R.id.atom_pub_resFasterAdapterType_NameDefinitionFooterItem;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.atom_naming_list_footer;
    }

    static class NameDefinitionFooterHolder extends AtomPubFastAdapterAbstractItem.AtomPubFastAdapterItemHolder<String> {

        public NameDefinitionFooterHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(Context context, String s) {

        }
    }
}
