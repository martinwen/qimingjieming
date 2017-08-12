package com.tjyw.qmjm.item;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.tjyw.atom.pub.inject.From;
import com.tjyw.atom.pub.item.AtomPubFastAdapterAbstractItem;
import com.tjyw.qmjm.ClientQmjmApplication;
import com.tjyw.qmjm.R;

import java.util.List;

/**
 * Created by stephen on 11/08/2017.
 */
public class ExplainHeaderWordItem extends AtomPubFastAdapterAbstractItem<String, ExplainHeaderWordItem, ExplainHeaderWordItem.HeaderWordHolder> {

    public ExplainHeaderWordItem(String s) {
        super(s);
    }

    @Override
    public HeaderWordHolder getViewHolder(View v) {
        return new HeaderWordHolder(v);
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
    public void bindView(HeaderWordHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        holder.onBindView(ClientQmjmApplication.getContext(), src);
    }

    static class HeaderWordHolder extends AtomPubFastAdapterAbstractItem.AtomPubFastAdapterItemHolder<String> {

        @From(R.id.bodyPinYin)
        protected TextView bodyPinYin;

        @From(R.id.bodyWord)
        protected TextView bodyWord;

        @From(R.id.bodyElement)
        protected TextView bodyElement;

        public HeaderWordHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(Context context, String s) {
            bodyPinYin.setText(s);
            bodyWord.setText(s);
            bodyElement.setText(s);
        }
    }
}
