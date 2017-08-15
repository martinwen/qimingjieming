package com.tjyw.qmjm.item;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.tjyw.atom.network.model.Explain;
import com.tjyw.atom.pub.inject.From;
import com.tjyw.atom.pub.item.AtomPubFastAdapterAbstractItem;
import com.tjyw.qmjm.ClientQmjmApplication;
import com.tjyw.qmjm.R;

import java.util.List;

/**
 * Created by stephen on 11/08/2017.
 */
public class ExplainHeaderWordItem extends AtomPubFastAdapterAbstractItem<Explain.Word, ExplainHeaderWordItem, ExplainHeaderWordItem.HeaderWordHolder> {

    public ExplainHeaderWordItem(Explain.Word src) {
        super(src);
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

    static class HeaderWordHolder extends AtomPubFastAdapterAbstractItem.AtomPubFastAdapterItemHolder<Explain.Word> {

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
        public void onBindView(Context context, Explain.Word word) {
            bodyPinYin.setText(word.jiantipinyin);
            bodyWord.setText(word.word);
            bodyElement.setText(word.wuxing);
        }
    }
}
