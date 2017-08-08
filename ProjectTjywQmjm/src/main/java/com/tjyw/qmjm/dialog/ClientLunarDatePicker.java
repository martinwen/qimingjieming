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

import com.tjyw.atom.pub.inject.From;
import com.tjyw.atom.pub.inject.Injector;
import com.tjyw.qmjm.ClientQmjmApplication;
import com.tjyw.qmjm.R;
import com.xhinliang.lunarcalendar.LunarCalendar;
import com.xhinliang.lunarcalendar.LunarSolarSource;

import java.util.List;

import antistatic.spinnerwheel.AbstractWheel;
import antistatic.spinnerwheel.OnWheelScrollListener;
import antistatic.spinnerwheel.WheelVerticalView;
import antistatic.spinnerwheel.adapters.NumericWheelAdapter;
import timber.log.Timber;

/**
 * Created by stephen on 08/08/2017.
 */
public class ClientLunarDatePicker extends DialogFragment implements OnWheelScrollListener {

    public static ClientLunarDatePicker newInstance(FragmentManager manager) {
        ClientLunarDatePicker lunarDatePicker = new ClientLunarDatePicker();
        lunarDatePicker.show(manager, ClientLunarDatePicker.class.getName());
        return lunarDatePicker;
    }

    protected View convertView;

    @From(R.id.lunarPickerSwitchContainer)
    protected WheelVerticalView lunarPickerSwitchContainer;

    @From(R.id.lunarPickerYearContainer)
    protected WheelVerticalView lunarPickerYearContainer;

    @From(R.id.lunarPickerMonthContainer)
    protected WheelVerticalView lunarPickerMonthContainer;

    @From(R.id.lunarPickerDayContainer)
    protected WheelVerticalView lunarPickerDayContainer;

    @From(R.id.lunarPickerHourContainer)
    protected WheelVerticalView lunarPickerHourContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        convertView = inflater.inflate(R.layout.atom_master_lunar_date_picker, container, true);
        Injector.inject(this, convertView);

        lunarPickerSwitchContainer.addScrollingListener(this);
        lunarPickerYearContainer.addScrollingListener(this); // 变年农历月份、日期可能改变
        lunarPickerMonthContainer.addScrollingListener(this); // 变月时日期会变

        lunarPickerYearContainer.setViewAdapter(new NumericWheelAdapter(ClientQmjmApplication.getContext(), 1900, 2099));

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
    public void onScrollingStarted(AbstractWheel wheel) {

    }

    @Override
    public void onScrollingFinished(AbstractWheel wheel) {
        switch (wheel.getId()) {
            case R.id.lunarPickerYearContainer:
                NumericWheelAdapter adapter = (NumericWheelAdapter) lunarPickerYearContainer.getViewAdapter();
                CharSequence year = adapter.getItemText(lunarPickerYearContainer.getCurrentItem());

                LunarSolarSource.getInstance().set(ClientQmjmApplication.getContext(), Integer.parseInt(year.toString()));

                List<List<LunarCalendar>> list = LunarSolarSource.getInstance().get(Integer.parseInt(year.toString()));

                Timber.tag("ClientMasterNamingFragment").e("list::%s", list);

                break ;
        }
    }
}
