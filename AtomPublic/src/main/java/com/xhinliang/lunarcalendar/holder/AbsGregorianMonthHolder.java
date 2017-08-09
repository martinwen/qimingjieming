package com.xhinliang.lunarcalendar.holder;

import android.content.res.Resources;

import com.xhinliang.lunarcalendar.LunarCalendar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stephen on 17-8-9.
 */
public abstract class AbsGregorianMonthHolder {

    public static AbsGregorianMonthHolder newSolarInstance() {
        return new GregorianSolarMonthHolder();
    }

    public static AbsGregorianMonthHolder newLunarInstance() {
        return new GregorianLunarMonthHolder();
    }

    protected List<LunarCalendar> calendarList;

    public AbsGregorianMonthHolder() {
        calendarList = new ArrayList<LunarCalendar>();
    }

    public boolean addLunarCalendar(LunarCalendar lunarCalendar) {
        return null != lunarCalendar && null != calendarList && calendarList.add(lunarCalendar);
    }

    public List<String> getDateOfMonth(Resources resources) {
        int size = null == calendarList ? 0 : calendarList.size();
        if (size == 0) {
            return null;
        }

        List<String> dateOfMonth = new ArrayList<String>();
        for (int i = 0; i < size; i ++) {
            LunarCalendar calendar = calendarList.get(i);
            if (null != calendar) {
                dateOfMonth.add(this.absGetDateOfMonth(resources, calendar));
            }
        }

        return dateOfMonth;
    }

    public abstract void absSetMonth(LunarCalendar lunarCalendar);

    public abstract String absGetMonth(Resources resources);

    protected abstract String absGetDateOfMonth(Resources resources, LunarCalendar calendar);
}
