package com.tjyw.bbqmqd.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.model.Explain;
import com.tjyw.bbqmqd.R;
import com.tjyw.bbqmqd.activity.ExplainMasterActivity;
import com.tjyw.bbqmqd.adapter.NameMasterAdapter;
import com.tjyw.bbqmqd.holder.AtomExplainHeaderHolder;
import com.tjyw.bbqmqd.holder.BaZiSheetHolder;
import com.tjyw.bbqmqd.holder.YunShiSheetHolder;

import atom.pub.fragment.AtomPubBaseFragment;
import atom.pub.inject.From;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.atom_explain_destiny, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Explain explain = (Explain) pGetSerializableExtra(IApiField.E.explain);
        if (null != explain) {
            new AtomExplainHeaderHolder(view).layout(explain);
            new BaZiSheetHolder(view).sheet(explain.nameZodiac);
            new YunShiSheetHolder(view).sheet(explain.nameZodiac);
        }
    }
}
