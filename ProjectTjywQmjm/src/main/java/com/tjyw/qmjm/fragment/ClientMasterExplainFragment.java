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
import com.tjyw.atom.network.utils.DateTimeUtils;
import com.tjyw.atom.pub.fragment.AtomPubBaseFragment;
import com.tjyw.atom.pub.inject.From;
import com.tjyw.atom.pub.interfaces.AtomPubValidationListener;
import com.tjyw.qmjm.ClientQmjmApplication;
import com.tjyw.qmjm.R;
import com.tjyw.qmjm.activity.BaseActivity;
import com.tjyw.qmjm.dialog.GregorianWindows;
import com.tjyw.qmjm.factory.IClientActivityLaunchFactory;
import com.xhinliang.lunarcalendar.LunarCalendar;

import java.util.Calendar;

/**
 * Created by stephen on 07/08/2017.
 */
public class ClientMasterExplainFragment extends AtomPubBaseFragment implements GregorianWindows.OnGregorianSelectedListener {

    @From(R.id.nGenderMale)
    protected ViewGroup nGenderMale;

    @From(R.id.nGenderFemale)
    protected ViewGroup nGenderFemale;

    @Pattern(regex = "^[\\u4e00-\\u9fa5]{1,2}$")
    @From(R.id.nSurname)
    protected EditText nSurname;

    @Pattern(regex = "^[\\u4e00-\\u9fa5]{1,2}$")
    @From(R.id.nGivenName)
    protected EditText nGivenName;

    @From(R.id.nDateOfBirth)
    protected TextView nDateOfBirth;

    @From(R.id.atom_pub_resIdsOK)
    protected TextView atom_pub_resIdsOK;

    protected GregorianWindows gregorianWindows;

    protected int postGender = ISection.GENDER.MALE;

    protected String postDay;

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
        nDateOfBirth.setOnClickListener(this);
        atom_pub_resIdsOK.setOnClickListener(this);

        validator = new Validator(this);
        validator.setValidationListener(new AtomPubValidationListener(ClientQmjmApplication.getContext()) {

            @Override
            public void onValidationSucceeded() {
                IClientActivityLaunchFactory.launchExplainMasterActivity(
                        (BaseActivity) getActivity(),
                        nSurname.getText().toString(),
                        nGivenName.getText().toString(),
                        postDay,
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
            case R.id.nDateOfBirth:
                if (null == gregorianWindows) {
                    gregorianWindows = GregorianWindows.newInstance(getFragmentManager(), null, this);
                } else {
                    gregorianWindows.show(getFragmentManager(), GregorianWindows.class.getName());
                }

                break ;
            case R.id.atom_pub_resIdsOK:
                validator.validate();
        }
    }

    @Override
    public void gregorianOnSelected(LunarCalendar lunarCalendar, boolean isGregorianSolar, String hour, int postHour) {
        Calendar calendar = DateTimeUtils.getCalendar(lunarCalendar.getDate());
        if (null != calendar) {
            calendar.set(Calendar.HOUR_OF_DAY, postHour);
            postDay = DateTimeUtils.printCalendarByPattern(calendar, DateTimeUtils.yyyy_MM_dd_HH);
        }

        if (isGregorianSolar) {
            nDateOfBirth.setText(DateTimeUtils.printCalendarByPattern(calendar, ClientQmjmApplication.pGetString(R.string.atom_pub_resStringDateSolar)));
        } else {
            nDateOfBirth.setText(
                    ClientQmjmApplication.pGetString(
                            R.string.atom_pub_resStringDateLunar,
                            lunarCalendar.getLunarYear(),
                            lunarCalendar.getLunarMonth(),
                            lunarCalendar.getLunarDay(),
                            hour
                    )
            );
        }
    }
}
