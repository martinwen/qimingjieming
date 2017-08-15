package com.tjyw.qmjm.item;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.tjyw.atom.pub.inject.From;
import com.tjyw.atom.pub.item.AtomPubFastAdapterAbstractItem;
import com.tjyw.qmjm.R;
import com.tjyw.qmjm.widget.NameWordContainer;

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

    static class NamingWordHolder extends AtomPubFastAdapterAbstractItem.AtomPubFastAdapterItemHolder<String> {

        @From(R.id.nameWordSurname)
        protected NameWordContainer nameWordSurname;

        @From(R.id.nameWordGivenNameFirst)
        protected NameWordContainer nameWordGivenNameFirst;

        @From(R.id.nameWordGivenNameSecond)
        protected NameWordContainer nameWordGivenNameSecond;

        @From(R.id.nameWordCollect)
        protected TextView nameWordCollect;

        @From(R.id.nameWordAnalysis)
        protected TextView nameWordAnalysis;

        public NamingWordHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(Context context, String s) {

        }
    }
}
