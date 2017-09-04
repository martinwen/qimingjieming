package com.tjyw.qmjm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.model.Explain;
import com.tjyw.atom.pub.fragment.AtomPubBaseFragment;
import com.tjyw.atom.pub.inject.From;
import com.tjyw.qmjm.R;

/**
 * Created by stephen on 17-8-11.
 */
public class ExplainMasterZodiacFragment extends AtomPubBaseFragment {

    public static ExplainMasterZodiacFragment newInstance(Explain explain) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(IApiField.E.explain, explain);

        ExplainMasterZodiacFragment fragment = new ExplainMasterZodiacFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @From(R.id.explainZodiacContent)
    protected TextView explainZodiacContent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.atom_explain_zodiac, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Explain explain = (Explain) pGetSerializableExtra(IApiField.E.explain);
        if (null != explain && null != explain.nameZodiac) {
            explainZodiacContent.setText(explain.nameZodiac.shengxiaoxiji);
        }
    }
}
