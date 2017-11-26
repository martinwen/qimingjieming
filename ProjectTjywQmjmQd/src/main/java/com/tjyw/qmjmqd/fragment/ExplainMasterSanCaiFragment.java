package com.tjyw.qmjmqd.fragment;

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
import atom.pub.fragment.AtomPubBaseFragment;
import atom.pub.inject.From;
import com.tjyw.qmjmqd.ClientQmjmApplication;
import com.tjyw.qmjmqd.R;
import com.tjyw.qmjmqd.item.ExplainSanCaiItem;
import com.tjyw.qmjmqd.item.ExplainWuGeItem;

import java.util.ArrayList;
import java.util.List;

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
        explainScWgContainer.setLayoutManager(new LinearLayoutManager(ClientQmjmApplication.getContext()));
        explainScWgContainer.setAdapter(explainDestinyAdapter.wrap(itemAdapter));

        explainDestinyAdapter.add(new ExplainSanCaiItem(explain));
    }
}
