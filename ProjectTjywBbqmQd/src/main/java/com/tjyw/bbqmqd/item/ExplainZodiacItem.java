package com.tjyw.bbqmqd.item;

import android.content.Context;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import atom.pub.inject.From;
import atom.pub.item.AtomPubFastAdapterAbstractItem;
import com.tjyw.bbqmqd.ClientQmjmApplication;
import com.tjyw.bbqmqd.R;

import java.util.List;

/**
 * Created by stephen on 11/08/2017.
 */
public class ExplainZodiacItem extends AtomPubFastAdapterAbstractItem<Pair<Integer, String>, ExplainZodiacItem, ExplainZodiacItem.ZodiacHolder> {

    public ExplainZodiacItem(Pair<Integer, String> src) {
        super(src);
    }

    @Override
    public ZodiacHolder getViewHolder(View v) {
        return new ZodiacHolder(v);
    }

    @Override
    public int getType() {
        return R.id.atom_pub_resFasterAdapterType_ExplainZodiacItem;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.atom_explain_zodiac_body;
    }

    @Override
    public void bindView(ZodiacHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        holder.onBindView(ClientQmjmApplication.getContext(), src);
    }

    public static class ZodiacHolder extends AtomPubFastAdapterAbstractItem.AtomPubFastAdapterItemHolder<Pair<Integer, String>> {

        @From(R.id.bodyTitle)
        protected TextView bodyTitle;

        @From(R.id.bodyContent)
        protected TextView bodyContent;

        public ZodiacHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(Context context, Pair<Integer, String> pair) {
            bodyTitle.setText(pair.first);
            bodyContent.setText(pair.second);
        }
    }
}
