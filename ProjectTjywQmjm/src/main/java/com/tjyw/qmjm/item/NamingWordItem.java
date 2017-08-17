package com.tjyw.qmjm.item;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tjyw.atom.network.model.NameCharacter;
import com.tjyw.atom.pub.inject.From;
import com.tjyw.atom.pub.item.AtomPubFastAdapterAbstractItem;
import com.tjyw.qmjm.ClientQmjmApplication;
import com.tjyw.qmjm.R;
import com.tjyw.qmjm.holder.HeaderWordHolder;

import java.util.List;

/**
 * Created by stephen on 14/08/2017.
 */
public class NamingWordItem extends AtomPubFastAdapterAbstractItem<String, NamingWordItem, NamingWordItem.NamingWordHolder> {

    public NamingWordItem(String src) {
        super(src);
    }

    @Override
    public NamingWordHolder getViewHolder(View v) {
        return new NamingWordHolder(v);
    }

    @Override
    public int getType() {
        return R.id.atom_pub_resFasterAdapterType_NamingWordItem;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.atom_naming_word_body;
    }

    @Override
    public void bindView(NamingWordHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        holder.onBindView(ClientQmjmApplication.getContext(), src);
    }

    static class NamingWordHolder extends AtomPubFastAdapterAbstractItem.AtomPubFastAdapterItemHolder<String> {

        @From(R.id.nameWordContainer)
        protected ViewGroup nameWordContainer;

        @From(R.id.nameWordCollect)
        protected TextView nameWordCollect;

        @From(R.id.nameWordAnalysis)
        protected TextView nameWordAnalysis;

        public NamingWordHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(Context context, String name) {
            nameWordContainer.removeAllViews();

            for (int i = 0; i < name.length(); i++) {
                String word = String.valueOf(name.charAt(i));
                nameWordContainer.addView(
                        HeaderWordHolder.newInstance(ClientQmjmApplication.getContext(), new NameCharacter(word)),
                        nameWordContainer.getChildCount()
                );
            }
        }
    }
}
