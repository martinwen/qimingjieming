package com.xhinliang.lunarcalendar.holder;

import android.content.res.Resources;

import com.xhinliang.lunarcalendar.LunarCalendar;

/**
 * Created by stephen on 17-8-9.
 */
public class GregorianSolarMonthHolder extends AbsGregorianMonthHolder {

    public int solarMonth;

    @Override
    public void absSetMonth(LunarCalendar lunarCalendar) {
        if (null != lunarCalendar) {
            this.solarMonth = lunarCalendar.getMonth();
        }
    }

    @Override
    public String absGetMonth(Resources resources) {
        return String.valueOf(solarMonth);
    }

    @Override
    protected String absGetDateOfMonth(Resources resources, LunarCalendar calendar) {
        return String.valueOf(calendar.getDay());
    }
}
