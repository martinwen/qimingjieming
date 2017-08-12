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
public class ExplainOverviewItem extends AtomPubFastAdapterAbstractItem<String, ExplainOverviewItem, ExplainOverviewItem.OverviewHolder> {

    public ExplainOverviewItem(String s) {
        super(s);
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

    public static class OverviewHolder extends AtomPubFastAdapterAbstractItem.AtomPubFastAdapterItemHolder<String> {

        @From(R.id.bodyTitle)
        protected TextView bodyTitle;

        public OverviewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(Context context, String s) {
            bodyTitle.setText(s);
        }
    }
}
