package com.tjyw.bbqm.fragment;

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
import com.tjyw.bbqm.ClientQmjmApplication;
import com.tjyw.bbqm.R;
import com.tjyw.bbqm.item.ExplainFooterItem;
import com.tjyw.bbqm.item.ExplainSanCaiItem;
import com.tjyw.bbqm.item.ExplainWuGeItem;
import com.yqritc.recyclerviewflexibledivider.FlexibleDividerDecoration;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import atom.pub.fragment.AtomPubBaseFragment;
import atom.pub.inject.From;

/**
 * Created by stephen on 17-8-11.
 */
public class ExplainMasterSanCaiFragment extends AtomPubBaseFragment {

    public static ExplainMasterSanCaiFragment newInstance(Explain explain) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(IApiField.E.explain, explain);

        ExplainMasterSanCaiFragment fragment = new ExplainMasterSanCaiFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @From(R.id.explainScWgContainer)
    protected RecyclerView explainScWgContainer;

    protected HeaderAdapter<ExplainSanCaiItem> explainDestinyAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.atom_explain_scwg, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Explain explain = (Explain) pGetSerializableExtra(IApiField.E.explain);
        if (null == explain) {
            return ;
        }

        List<ExplainWuGeItem> itemList = new ArrayList<ExplainWuGeItem>();
        for (int i = 0; i < explain.wugeList.size(); i ++) {
            itemList.add(new ExplainWuGeItem(explain.wugeList.get(i)));
        }

        FastItemAdapter<ExplainWuGeItem> itemAdapter = new FastItemAdapter<ExplainWuGeItem>();
        itemAdapter.set(itemList);

        explainDestinyAdapter = new HeaderAdapter<ExplainSanCaiItem>();
        explainDestinyAdapter.wrap(itemAdapter);
        explainDestinyAdapter.add(new ExplainSanCaiItem(explain));

        FooterAdapter<ExplainFooterItem> footerAdapter = new FooterAdapter<ExplainFooterItem>();
        footerAdapter.wrap(explainDestinyAdapter);
        footerAdapter.add(new ExplainFooterItem(explain));

        explainScWgContainer.setLayoutManager(new LinearLayoutManager(ClientQmjmApplication.getContext()));
        explainScWgContainer.setAdapter(footerAdapter);
        explainScWgContainer.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(ClientQmjmApplication.getContext())
                        .color(android.R.color.transparent)
                        .sizeProvider(new FlexibleDividerDecoration.SizeProvider() {
                            @Override
                            public int dividerSize(int position, RecyclerView parent) {
                                return position == 0 ? 0 : ClientQmjmApplication.pGetResources().getDimensionPixelOffset(R.dimen.atom_pubResDimenRecyclerViewDivider8dp);
                            }
                        })
                        .build());

    }
}
