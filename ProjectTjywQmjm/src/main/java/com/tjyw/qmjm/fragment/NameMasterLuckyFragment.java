package com.tjyw.qmjm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikepenz.fastadapter.adapters.HeaderAdapter;
import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.model.Explain;
import com.tjyw.atom.pub.fragment.AtomPubBaseFragment;
import com.tjyw.atom.pub.inject.From;
import com.tjyw.qmjm.R;
import com.tjyw.qmjm.item.ExplainSanCaiItem;

/**
 * Created by stephen on 17-8-11.
 */
public class NameMasterLuckyFragment extends AtomPubBaseFragment {

    public static NameMasterLuckyFragment newInstance(Explain explain) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(IApiField.E.explain, explain);

        NameMasterLuckyFragment fragment = new NameMasterLuckyFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @From(R.id.nameLuckyContainer)
    protected RecyclerView nameLuckyContainer;

    protected HeaderAdapter<ExplainSanCaiItem> explainDestinyAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.atom_name_master_lucky, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Explain explain = (Explain) pGetSerializableExtra(IApiField.E.explain);
        if (null == explain) {
            return ;
        }
    }
}
