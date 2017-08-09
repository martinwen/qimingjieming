package com.tjyw.qmjm.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.crazypumpkin.versatilerecyclerview.library.WheelRecyclerView;
import com.tjyw.atom.pub.inject.From;
import com.tjyw.atom.pub.inject.Injector;
import com.tjyw.qmjm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stephen on 08/08/2017.
 */
public class ClientLunarDatePicker extends DialogFragment implements WheelRecyclerView.OnSelectListener {

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        convertView = inflater.inflate(R.layout.atom_master_lunar_date_picker, container, true);
        Injector.inject(this, convertView);

        List<String> years = new ArrayList<String>();
        years.add("公历");
        years.add("农历");
        gregorianSwitchContainer.setData(years);

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
    public void onSelect(WheelRecyclerView recyclerView, int position, String data) {
        switch (recyclerView.getId()) {
            case R.id.gregorianSwitchContainer:
//                NumericWheelAdapter adapter = (NumericWheelAdapter) gregorianYearContainer.getViewAdapter();
//                CharSequence year = adapter.getItemText(gregorianYearContainer.getCurrentItem());
//
//                LunarSolarSource.getInstance().set(Integer.parseInt(year.toString()));
//
//                Pair<List<AbsGregorianMonthHolder>, List<AbsGregorianMonthHolder>> pair = LunarSolarSource.getInstance().get(Integer.parseInt(year.toString()));
//
//                Timber.tag("ClientMasterNamingFragment").e("pair::%s", pair);

        }
    }
}
