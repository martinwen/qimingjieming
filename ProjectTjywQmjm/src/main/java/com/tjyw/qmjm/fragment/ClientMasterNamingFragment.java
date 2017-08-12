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
public class ClientMasterNamingFragment extends AtomPubBaseFragment {

    @BindView(R.id.nGenderMale)
    protected ViewGroup nGenderMale;

    @BindView(R.id.nGenderFemale)
    protected ViewGroup nGenderFemale;

    @BindView(R.id.nSingleName)
    protected ViewGroup nSingleName;

    @BindView(R.id.nDoubleName)
    protected ViewGroup nDoubleName;

    @BindView(R.id.nSurname)
    protected EditText nSurname;

    @BindView(R.id.nDateOfBirth)
    protected EditText nDateOfBirth;

    @BindView(R.id.nContain1st)
    protected EditText nContain1st;

    @BindView(R.id.nContain2nd)
    protected EditText nContain2nd;

    @BindView(R.id.atom_pub_resIdsOK)
    protected TextView atom_pub_resIdsOK;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.atom_master_naming, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nGenderMale.setSelected(true);
        nSingleName.setSelected(true);

        nGenderMale.setOnClickListener(this);
        nGenderFemale.setOnClickListener(this);
        nSingleName.setOnClickListener(this);
        nDoubleName.setOnClickListener(this);
        atom_pub_resIdsOK.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nGenderMale:
                v.setSelected(true);
                nGenderFemale.setSelected(false);
                break ;
            case R.id.nGenderFemale:
                v.setSelected(true);
                nGenderMale.setSelected(false);
                break ;
            case R.id.nSingleName:
                v.setSelected(true);
                nDoubleName.setSelected(false);
                break ;
            case R.id.nDoubleName:
                v.setSelected(true);
                nSingleName.setSelected(false);
                break ;
            case R.id.atom_pub_resIdsOK:

        }
    }
}
