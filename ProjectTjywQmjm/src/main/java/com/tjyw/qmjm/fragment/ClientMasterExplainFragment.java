package com.tjyw.qmjm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.tjyw.atom.pub.fragment.AtomPubBaseFragment;
import com.tjyw.qmjm.R;

import butterknife.BindView;

/**
 * Created by stephen on 07/08/2017.
 */
public class ClientMasterExplainFragment extends AtomPubBaseFragment {

    @BindView(R.id.masterExplainGenderMale)
    protected ViewGroup masterExplainGenderMale;

    @BindView(R.id.masterExplainGenderFemale)
    protected ViewGroup masterExplainGenderFemale;

    @BindView(R.id.masterExplainSingleName)
    protected ViewGroup masterExplainSingleName;

    @BindView(R.id.masterExplainDoubleName)
    protected ViewGroup masterExplainDoubleName;

    @BindView(R.id.masterExplainSurname)
    protected EditText masterExplainSurname;

    @BindView(R.id.masterExplainDateOfBirth)
    protected EditText masterExplainDateOfBirth;

    @BindView(R.id.masterExplainContain1st)
    protected EditText masterExplainContain1st;

    @BindView(R.id.masterExplainContain2nd)
    protected EditText masterExplainContain2nd;

    @BindView(R.id.masterExplainConfirm)
    protected TextView masterExplainConfirm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.atom_master_explain, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        masterExplainGenderMale.setSelected(true);
        masterExplainSingleName.setSelected(true);

        masterExplainGenderMale.setOnClickListener(this);
        masterExplainGenderFemale.setOnClickListener(this);
        masterExplainSingleName.setOnClickListener(this);
        masterExplainDoubleName.setOnClickListener(this);
        masterExplainConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.masterExplainGenderMale:
                v.setSelected(true);
                masterExplainGenderFemale.setSelected(false);
                break ;
            case R.id.masterExplainGenderFemale:
                v.setSelected(true);
                masterExplainGenderMale.setSelected(false);
                break ;
            case R.id.masterExplainSingleName:
                v.setSelected(true);
                masterExplainDoubleName.setSelected(false);
                break ;
            case R.id.masterExplainDoubleName:
                v.setSelected(true);
                masterExplainSingleName.setSelected(false);
                break ;
            case R.id.masterExplainConfirm:

        }
    }
}
