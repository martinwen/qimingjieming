package com.tjyw.bbqmqd.item;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.tjyw.atom.network.model.NameData;
import atom.pub.inject.From;
import atom.pub.item.AtomPubFastAdapterAbstractItem;
import com.tjyw.bbqmqd.ClientQmjmApplication;
import com.tjyw.bbqmqd.R;
import com.tjyw.bbqmqd.holder.BaZiSheetHolder;
import com.tjyw.bbqmqd.holder.NameBaseInfoHolder;

import java.util.List;

/**
 * Created by stephen on 17-9-22.
 */
public class NameMasterAnalyzeItem extends AtomPubFastAdapterAbstractItem<NameData, NameMasterAnalyzeItem, NameMasterAnalyzeItem.NameMasterAnalyzeHolder> {

    public NameMasterAnalyzeItem(NameData src) {
        super(src);
    }

    @Override
    public NameMasterAnalyzeHolder getViewHolder(View v) {
        return new NameMasterAnalyzeHolder(v);
    }

    @Override
    public int getType() {
        return R.id.atom_pub_resFasterAdapterType_NameMasterAnalyzeItem;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.atom_name_master_analyze_body;
    }

    @Override
    public void bindView(NameMasterAnalyzeHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        holder.onBindView(ClientQmjmApplication.getContext(), src);
    }

    public static class NameMasterAnalyzeHolder extends AtomPubFastAdapterAbstractItem.AtomPubFastAdapterItemHolder<NameData> {

        @From(R.id.bodyAnalyzeContent)
        protected TextView bodyAnalyzeContent;

        @From(R.id.bodyAnalyzeTip)
        protected TextView bodyAnalyzeTip;

        @From(R.id.nameMakeAGoodName)
        protected TextView nameMakeAGoodName;

        public NameMasterAnalyzeHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(Context context, NameData data) {
            BaZiSheetHolder baZiSheetHolder = new BaZiSheetHolder(itemView);
            baZiSheetHolder.sheet(data);

            NameBaseInfoHolder nameBaseInfoHolder = new NameBaseInfoHolder(itemView);
            nameBaseInfoHolder.baseInfo(data);

            bodyAnalyzeContent.setText(data.fenxi);
            bodyAnalyzeTip.setText(data.tixing);
        }
    }
}
