package com.xhinliang.lunarcalendar.holder;

import android.content.res.Resources;

import com.xhinliang.lunarcalendar.LunarCalendar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stephen on 17-8-9.
 */
public abstract class AbsGregorianMonthHolder {

    protected List<LunarCalendar> calendarList;

    public AbsGregorianMonthHolder() {
        calendarList = new ArrayList<LunarCalendar>();
    }

    public boolean addLunarCalendar(LunarCalendar lunarCalendar) {
        return null != lunarCalendar && null != calendarList && calendarList.add(lunarCalendar);
    }

    public abstract void absSetMonth(LunarCalendar lunarCalendar);

    public abstract String absGetMonth(Resources resources);

    public static AbsGregorianMonthHolder newSolarInstance() {
        return new GregorianSolarMonthHolder();
    }

    public static AbsGregorianMonthHolder newLunarInstance() {
        return new GregorianLunarMonthHolder();
    }
}
