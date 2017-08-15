package com.tjyw.qmjm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikepenz.fastadapter.adapters.HeaderAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.model.Explain;
import com.tjyw.atom.pub.fragment.AtomPubBaseFragment;
import com.tjyw.atom.pub.inject.From;
import com.tjyw.qmjm.ClientQmjmApplication;
import com.tjyw.qmjm.R;
import com.tjyw.qmjm.item.ExplainDestinyWuGeItem;
import com.tjyw.qmjm.item.ExplainDestinySanCaiItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stephen on 17-8-11.
 */
public class ExplainMasterDestinyFragment extends AtomPubBaseFragment {

    public static ExplainMasterDestinyFragment newInstance(Explain explain) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(IApiField.E.explain, explain);

        ExplainMasterDestinyFragment fragment = new ExplainMasterDestinyFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @From(R.id.explainDestinyContainer)
    protected RecyclerView explainDestinyContainer;

    protected HeaderAdapter<ExplainDestinySanCaiItem> explainDestinyAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.atom_explain_destiny, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Explain explain = (Explain) pGetSerializableExtra(IApiField.E.explain);
        if (null == explain) {
            return ;
        }

        List<ExplainDestinyWuGeItem> itemList = new ArrayList<ExplainDestinyWuGeItem>();
        for (int i = 0; i < explain.wugeList.size(); i ++) {
            itemList.add(new ExplainDestinyWuGeItem(explain.wugeList.get(i)));
        }

        FastItemAdapter<ExplainDestinyWuGeItem> itemAdapter = new FastItemAdapter<ExplainDestinyWuGeItem>();
        itemAdapter.set(itemList);

        explainDestinyAdapter = new HeaderAdapter<ExplainDestinySanCaiItem>();
        explainDestinyContainer.setLayoutManager(new LinearLayoutManager(ClientQmjmApplication.getContext()));
        explainDestinyContainer.setAdapter(explainDestinyAdapter.wrap(itemAdapter));
        explainDestinyAdapter.add(new ExplainDestinySanCaiItem(explain.sancai));
    }
}
