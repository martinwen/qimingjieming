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
import com.tjyw.qmjm.activity.BaseActivity;
import com.tjyw.qmjm.dialog.ClientLunarDatePicker;
import com.tjyw.qmjm.factory.IClientActivityLaunchFactory;
import com.xhinliang.lunarcalendar.LunarCalendar;

import butterknife.BindView;
import timber.log.Timber;

/**
 * Created by stephen on 07/08/2017.
 */
public class ClientMasterNamingFragment extends AtomPubBaseFragment {

    @BindView(R.id.masterNamingGenderMale)
    protected ViewGroup masterNamingGenderMale;

    @BindView(R.id.masterNamingGenderFemale)
    protected ViewGroup masterNamingGenderFemale;

    @BindView(R.id.masterNamingSurname)
    protected EditText masterNamingSurname;

    @BindView(R.id.masterNamingGivenName)
    protected EditText masterNamingGivenName;

    @BindView(R.id.masterNamingDateOfBirth)
    protected EditText masterNamingDateOfBirth;

    @BindView(R.id.masterNamingConfirm)
    protected TextView masterNamingConfirm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.atom_master_naming, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        masterNamingGenderMale.setSelected(true);
        masterNamingGenderMale.setOnClickListener(this);
        masterNamingGenderFemale.setOnClickListener(this);
        masterNamingConfirm.setOnClickListener(this);

        masterNamingDateOfBirth.setKeyListener(null);
    }

    @Override
    public void onClick(View v) {
        IClientActivityLaunchFactory.launchExplainMasterActivity((BaseActivity) getActivity());

//        switch (v.getId()) {
//            case R.id.masterNamingGenderMale:
//                v.setSelected(true);
//                masterNamingGenderFemale.setSelected(false);
//                break ;
//            case R.id.masterNamingGenderFemale:
//                v.setSelected(true);
//                masterNamingGenderMale.setSelected(false);
//                masterNamingDateOfBirth.setText("" + v.getId());
//                break ;
//            case R.id.masterNamingConfirm:
//                ClientLunarDatePicker.newInstance(getFragmentManager());
//        }
    }
}
