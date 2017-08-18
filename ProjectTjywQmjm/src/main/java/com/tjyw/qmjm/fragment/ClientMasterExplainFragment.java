package com.tjyw.qmjm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Pattern;
import com.tjyw.atom.network.conf.ISection;
import com.tjyw.atom.pub.fragment.AtomPubBaseFragment;
import com.tjyw.atom.pub.interfaces.AtomPubValidationListener;
import com.tjyw.qmjm.ClientQmjmApplication;
import com.tjyw.qmjm.R;
import com.tjyw.qmjm.activity.BaseActivity;
import com.tjyw.qmjm.factory.IClientActivityLaunchFactory;

import butterknife.BindView;

/**
 * Created by stephen on 07/08/2017.
 */
public class ClientMasterExplainFragment extends AtomPubBaseFragment {

    @BindView(R.id.nGenderMale)
    protected ViewGroup nGenderMale;

    @BindView(R.id.nGenderFemale)
    protected ViewGroup nGenderFemale;

    @Pattern(regex = "^[\\u4e00-\\u9fa5]{1,2}$")
    @BindView(R.id.nSurname)
    protected EditText nSurname;

    @Pattern(regex = "^[\\u4e00-\\u9fa5]{1,2}$")
    @BindView(R.id.nGivenName)
    protected EditText nGivenName;

    @BindView(R.id.nDateOfBirth)
    protected EditText nDateOfBirth;

    @BindView(R.id.atom_pub_resIdsOK)
    protected TextView atom_pub_resIdsOK;

    protected int postGender = ISection.GENDER.MALE;

    protected Validator validator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.atom_master_explain, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nGenderMale.setSelected(true);
        nGenderMale.setOnClickListener(this);
        nGenderFemale.setOnClickListener(this);
        atom_pub_resIdsOK.setOnClickListener(this);

        nDateOfBirth.setKeyListener(null);

        validator = new Validator(this);
        validator.setValidationListener(new AtomPubValidationListener(ClientQmjmApplication.getContext()) {

            @Override
            public void onValidationSucceeded() {
                IClientActivityLaunchFactory.launchExplainMasterActivity(
                        (BaseActivity) getActivity(),
                        nSurname.getText().toString(),
                        nGivenName.getText().toString(),
                        "1990-07-15 00",
                        postGender
                );
            }
        });
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
            case R.id.atom_pub_resIdsOK:
                validator.validate();
//                ClientLunarDatePicker.newInstance(getFragmentManager());
        }
    }
}
