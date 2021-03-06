package com.tjyw.bbbzqm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikepenz.fastadapter.adapters.FooterAdapter;
import com.mikepenz.fastadapter.adapters.HeaderAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.model.Explain;
import atom.pub.fragment.AtomPubBaseFragment;
import atom.pub.inject.From;
import com.tjyw.bbbzqm.ClientQmjmApplication;
import com.tjyw.bbbzqm.R;
import com.tjyw.bbbzqm.item.ExplainFooterItem;
import com.tjyw.bbbzqm.item.ExplainHeaderItem;
import com.tjyw.bbbzqm.item.ExplainOverviewItem;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stephen on 17-8-11.
 */
public class ExplainMasterOverviewFragment extends AtomPubBaseFragment {

    public static ExplainMasterOverviewFragment newInstance(Explain explain) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(IApiField.E.explain, explain);

        ExplainMasterOverviewFragment fragment = new ExplainMasterOverviewFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @From(R.id.explainOverviewContainer)
    protected RecyclerView explainOverviewContainer;

    protected HeaderAdapter<ExplainHeaderItem> explainHeaderAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.atom_explain_overview, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Explain explain = (Explain) pGetSerializableExtra(IApiField.E.explain);
        if (null == explain) {
            return ;
        }

        List<ExplainOverviewItem> itemList = new ArrayList<ExplainOverviewItem>();
        for (int i = 0; i < explain.wordsList.size(); i ++) {
            itemList.add(new ExplainOverviewItem(explain.wordsList.get(i)));
        }

        FastItemAdapter<ExplainOverviewItem> itemAdapter = new FastItemAdapter<ExplainOverviewItem>();
        itemAdapter.set(itemList);

        explainHeaderAdapter = new HeaderAdapter<ExplainHeaderItem>();
        explainHeaderAdapter.wrap(itemAdapter);
        explainHeaderAdapter.add(new ExplainHeaderItem(explain));

        FooterAdapter<ExplainFooterItem> footerAdapter = new FooterAdapter<ExplainFooterItem>();
        footerAdapter.wrap(explainHeaderAdapter);
        footerAdapter.add(new ExplainFooterItem(explain));

        explainOverviewContainer.setLayoutManager(new LinearLayoutManager(ClientQmjmApplication.getContext()));
        explainOverviewContainer.setAdapter(footerAdapter);
        explainOverviewContainer.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(ClientQmjmApplication.getContext())
                        .color(android.R.color.transparent)
                        .sizeResId(R.dimen.atom_pubResDimenRecyclerViewDivider8dp)
                        .build());
    }
}
