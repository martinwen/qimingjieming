package com.xhinliang.lunarcalendar.holder;

import android.content.res.Resources;

import com.xhinliang.lunarcalendar.LunarCalendar;

/**
 * Created by stephen on 17-8-9.
 */
public class GregorianLunarMonthHolder extends AbsGregorianMonthHolder {

    public String lunarMonth;

    @Override
    public void absSetMonth(LunarCalendar lunarCalendar) {
        if (null != lunarCalendar) {
            this.lunarMonth = lunarCalendar.getLunarMonth();
        }
    }

    @Override
    public String absGetMonth(Resources resources) {
        return lunarMonth;
    }
}
