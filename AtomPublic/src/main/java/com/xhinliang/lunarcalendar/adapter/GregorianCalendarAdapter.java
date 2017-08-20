package com.xhinliang.lunarcalendar.adapter;

import android.content.Context;

import com.xhinliang.lunarcalendar.LunarCalendar;

import java.util.List;

import antistatic.spinnerwheel.adapters.ListWheelAdapter;

/**
 * Created by stephen on 19/08/2017.
 */
public class GregorianCalendarAdapter extends ListWheelAdapter<LunarCalendar> {

    protected boolean isGregorianSolar;

    public GregorianCalendarAdapter(Context context, List<LunarCalendar> items, boolean isGregorianSolar) {
        super(context, items);
        this.isGregorianSolar = isGregorianSolar;
    }

    @Override
    public CharSequence getItemText(int index) {
        if (index >= 0 && index < items.size()) {
            LunarCalendar item = get(index);
            if (null == item) {
                return null;
            } else if (isGregorianSolar) {
                return String.valueOf(item.getDay());
            } else {
                return item.getLunarDay();
            }
        }
        return null;
    }

    @Override
    public int getItemsCount() {
        return null == items ? 0 : items.size();
    }
}
