package com.tjyw.qmjm.item;

import android.view.View;

import com.tjyw.atom.pub.item.AtomPubFastAdapterAbstractItem;
import com.tjyw.qmjm.ClientQmjmApplication;
import com.tjyw.qmjm.R;
import com.tjyw.qmjm.holder.ExplainHeaderWordHolder;

import java.util.List;

/**
 * Created by stephen on 11/08/2017.
 */
public class ExplainHeaderWordItem extends AtomPubFastAdapterAbstractItem<String, ExplainHeaderWordItem, ExplainHeaderWordHolder> {

    public ExplainHeaderWordItem(String s) {
        super(s);
    }

    @Override
    public ExplainHeaderWordHolder getViewHolder(View v) {
        return new ExplainHeaderWordHolder(v);
    }

    @Override
    public int getType() {
        return R.id.atom_pub_resFasterAdapterType_ExplainHeaderWordItem;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.atom_explain_header_word_body;
    }

    @Override
    public void bindView(ExplainHeaderWordHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        holder.onBindView(ClientQmjmApplication.getContext(), src);
    }
}
