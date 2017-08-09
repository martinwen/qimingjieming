package com.tjyw.qmjm.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.crazypumpkin.versatilerecyclerview.library.WheelRecyclerView;
import com.tjyw.atom.pub.inject.From;
import com.tjyw.atom.pub.inject.Injector;
import com.tjyw.qmjm.ClientQmjmApplication;
import com.tjyw.qmjm.R;
import com.xhinliang.lunarcalendar.LunarSolarSource;
import com.xhinliang.lunarcalendar.holder.AbsGregorianMonthHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stephen on 08/08/2017.
 */
public class ClientLunarDatePicker extends DialogFragment implements View.OnClickListener, WheelRecyclerView.OnSelectListener {

    public static ClientLunarDatePicker newInstance(FragmentManager manager) {
        ClientLunarDatePicker lunarDatePicker = new ClientLunarDatePicker();
        lunarDatePicker.show(manager, ClientLunarDatePicker.class.getName());
        return lunarDatePicker;
    }

    protected View convertView;

    @From(R.id.gregorianSwitchContainer)
    protected WheelRecyclerView gregorianSwitchContainer;

    @From(R.id.gregorianYearContainer)
    protected WheelRecyclerView gregorianYearContainer;

    @From(R.id.gregorianMonthContainer)
    protected WheelRecyclerView gregorianMonthContainer;

    @From(R.id.gregorianDayContainer)
    protected WheelRecyclerView gregorianDayContainer;

    @From(R.id.gregorianHourContainer)
    protected WheelRecyclerView gregorianHourContainer;

    @From(R.id.gregorianCancel)
    protected TextView gregorianCancel;

    @From(R.id.gregorianOK)
    protected TextView gregorianOK;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        convertView = inflater.inflate(R.layout.atom_master_lunar_date_picker, container, true);
        Injector.inject(this, convertView);

        gregorianCancel.setOnClickListener(this);
        gregorianOK.setOnClickListener(this);

        List<String> gregorian = new ArrayList<String>();
        gregorian.add("公历");
        gregorian.add("农历");
        gregorianSwitchContainer.setData(gregorian);
        gregorianSwitchContainer.setOnSelectListener(this);

        List<String> years = new ArrayList<String>();
        for (int i = 0; i < 199; i ++) {
            years.add(String.valueOf(i + 1900));
        }

        gregorianYearContainer.setData(years);
        gregorianYearContainer.setSelect(Math.abs(1900 - 2017));
        gregorianYearContainer.setOnSelectListener(this);

        return convertView;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gregorianCancel:
                dismissAllowingStateLoss();
                break ;
            case R.id.gregorianOK:
        }
    }

    protected List<AbsGregorianMonthHolder> gregorianMonthHolderSelected;

    @Override
    public void onSelect(WheelRecyclerView recyclerView, int position, String data) {
        switch (recyclerView.getId()) {
            case R.id.gregorianSwitchContainer:

                break ;
            case R.id.gregorianYearContainer:
                int solarYear = Integer.parseInt(data);
                Pair<List<AbsGregorianMonthHolder>, List<AbsGregorianMonthHolder>> gregorianMonthHolderPair = LunarSolarSource.getInstance().get(solarYear);
                if (null == gregorianMonthHolderPair) {
                    gregorianMonthHolderPair = LunarSolarSource.getInstance().set(solarYear);
                }

                List<String> solarMonth = new ArrayList<String>();
                if (null != gregorianMonthHolderPair) {
                    gregorianMonthHolderSelected = gregorianMonthHolderPair.second;
                    int size = gregorianMonthHolderSelected.size();
                    for (int i = 0; i < size; i ++) {
                        AbsGregorianMonthHolder gregorianSolarMonthHolder = gregorianMonthHolderSelected.get(i);
                        if (null != gregorianSolarMonthHolder) {
                            solarMonth.add(gregorianSolarMonthHolder.absGetMonth(ClientQmjmApplication.pGetResources()));
                        }
                    }
                }

                gregorianMonthContainer.setData(solarMonth);
                gregorianMonthContainer.setOnSelectListener(this);
                break ;
            case R.id.gregorianMonthContainer:
                if (null != gregorianMonthHolderSelected) {
                    AbsGregorianMonthHolder gregorianMonthHolder = gregorianMonthHolderSelected.get(position);
                    if (null != gregorianMonthHolder) {
                        List<String> dateOfMonth = gregorianMonthHolder.getDateOfMonth(ClientQmjmApplication.pGetResources());
                        gregorianDayContainer.setData(dateOfMonth);
                    }
                }
        }
    }
}
