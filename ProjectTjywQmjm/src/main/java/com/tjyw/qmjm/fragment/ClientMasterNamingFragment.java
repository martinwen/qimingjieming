package com.tjyw.qmjm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Pattern;
import com.tjyw.atom.network.conf.ISection;
import com.tjyw.atom.network.presenter.NamingPresenter;
import com.tjyw.atom.pub.fragment.AtomPubBaseFragment;
import com.tjyw.atom.pub.inject.From;
import com.tjyw.atom.pub.interfaces.AtomPubValidationListener;
import com.tjyw.qmjm.ClientQmjmApplication;
import com.tjyw.qmjm.R;
import com.tjyw.qmjm.activity.BaseActivity;
import com.tjyw.qmjm.factory.IClientActivityLaunchFactory;

import java.util.List;

import nucleus.factory.RequiresPresenter;

/**
 * Created by stephen on 07/08/2017.
 */
@RequiresPresenter(NamingPresenter.class)
public class ClientMasterNamingFragment extends AtomPubBaseFragment {

    @From(R.id.nGenderMale)
    protected ViewGroup nGenderMale;

    @From(R.id.nGenderFemale)
    protected ViewGroup nGenderFemale;

    @From(R.id.nNameNumberSingle)
    protected ViewGroup nNameNumberSingle;

    @From(R.id.nNameNumberDouble)
    protected ViewGroup nNameNumberDouble;

    @Pattern(regex = "^[\\u4e00-\\u9fa5]{1,2}$")
    @From(R.id.nSurname)
    protected EditText nSurname;

    @From(R.id.nDateOfBirth)
    protected EditText nDateOfBirth;

    @From(R.id.atom_pub_resIdsOK)
    protected TextView atom_pub_resIdsOK;

    protected int postGender = ISection.GENDER.MALE;

    protected int postNameNumber = ISection.NAME_COUNT.SINGLE;

    protected Validator validator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.atom_master_naming, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        validator = new Validator(this);
        validator.setValidationListener(new AtomPubValidationListener(ClientQmjmApplication.getContext()) {

            @Override
            public void onValidationSucceeded() {
                IClientActivityLaunchFactory.launchNamingListActivity(
                        (BaseActivity) getActivity(), nSurname.getText().toString(), postGender, postNameNumber
                );
            }
        });

        nGenderMale.setSelected(true);
        nNameNumberSingle.setSelected(true);

        nGenderMale.setOnClickListener(this);
        nGenderFemale.setOnClickListener(this);
        nNameNumberSingle.setOnClickListener(this);
        nNameNumberDouble.setOnClickListener(this);
        atom_pub_resIdsOK.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nGenderMale:
                postGender = ISection.GENDER.MALE;
                v.setSelected(true);
                nGenderFemale.setSelected(false);
                break ;
            case R.id.nGenderFemale:
                postGender = ISection.GENDER.FEMALE;
                v.setSelected(true);
                nGenderMale.setSelected(false);
                break ;
            case R.id.nNameNumberSingle:
                postNameNumber = ISection.NAME_COUNT.SINGLE;
                v.setSelected(true);
                nNameNumberDouble.setSelected(false);
                break ;
            case R.id.nNameNumberDouble:
                postNameNumber = ISection.NAME_COUNT.DOUBLE;
                v.setSelected(true);
                nNameNumberSingle.setSelected(false);
                break ;
            case R.id.atom_pub_resIdsOK:
                validator.validate();
        }
    }
}
