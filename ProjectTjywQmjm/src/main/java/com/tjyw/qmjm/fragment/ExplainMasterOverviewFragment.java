package com.tjyw.qmjm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.model.Explain;
import com.tjyw.atom.pub.fragment.AtomPubBaseFragment;
import com.tjyw.atom.pub.inject.From;
import com.tjyw.qmjm.ClientQmjmApplication;
import com.tjyw.qmjm.R;
import com.tjyw.qmjm.item.ExplainOverviewItem;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.atom_explain_overview, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<ExplainOverviewItem> itemList = new ArrayList<ExplainOverviewItem>();
        itemList.add(new ExplainOverviewItem("郭"));
        itemList.add(new ExplainOverviewItem("祎"));
        itemList.add(new ExplainOverviewItem("赫"));

        FastItemAdapter<ExplainOverviewItem> adapter = new FastItemAdapter<>();
        adapter.set(itemList);

        explainOverviewContainer.setLayoutManager(new LinearLayoutManager(ClientQmjmApplication.getContext()));
        explainOverviewContainer.setAdapter(adapter);
    }
}
